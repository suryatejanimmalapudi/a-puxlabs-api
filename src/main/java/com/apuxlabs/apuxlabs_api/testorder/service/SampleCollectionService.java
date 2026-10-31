package com.apuxlabs.apuxlabs_api.testorder.service;

import com.apuxlabs.apuxlabs_api.testorder.dto.CollectSampleRequestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.CollectionOrderDto;
import com.apuxlabs.apuxlabs_api.testorder.entity.LabTestRequest;
import com.apuxlabs.apuxlabs_api.testorder.enums.TestStatus;
import com.apuxlabs.apuxlabs_api.testorder.repository.LabTestRequestRepository;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SampleCollectionService {

    private final LabTestRequestRepository labTestRequestRepository;
    private final RegistrationRepository registrationRepository;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");
    private static final List<TestStatus> COLLECTION_STATUSES = List.of(
            TestStatus.PENDING_COLLECTION,
            TestStatus.COLLECTED,
            TestStatus.REJECTED
    );

    @Transactional(readOnly = true)
    public List<CollectionOrderDto> getAllCollectionOrders() {
        // Fetch orders that are not completed (Pending Collection, Collected, or Rejected)
        List<LabTestRequest> requests = labTestRequestRepository.findByStatusNot(TestStatus.COMPLETED);

        return requests.stream().map(req -> CollectionOrderDto.builder()
                .uid(req.getId().toString())
                .id(req.getLabOrder().getId().toString())
                .patientId(req.getLabOrder().getRegistration().getId())
                .uhid(req.getLabOrder().getRegistration().getUhid())
                .patientName(req.getLabOrder().getRegistration().getFirstName() + " " + req.getLabOrder().getRegistration().getLastName())
                .age(req.getLabOrder().getRegistration().getAge())
                .gender(req.getLabOrder().getRegistration().getGender())
                .testName(req.getTestMaster().getName())
                .code(req.getTestMaster().getCode())
                .department(req.getTestMaster().getDepartment())
                .sampleType("Serum") // Replace with actual TestMaster property if available
                .container("Red top (plain)") // Replace with actual TestMaster property
                .orderedBy(req.getLabOrder().getReferringDoctorName())
                .orderedTime(req.getLabOrder().getOrderDate() != null ? req.getLabOrder().getOrderDate().format(TIME_FORMATTER) : "")
                .priority(req.getPriority().name())
                .status(req.getStatus().name().replace("_", " "))
                .collectedAt(req.getCollectedAt() != null ? req.getCollectedAt().format(TIME_FORMATTER) : null)
                .collectedBy(req.getCollectedBy())
                .barcode(req.getLabOrder().getRegistration().getBarcode())
                .rejectionReason(req.getRejectionReason())
                .build()
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<CollectionOrderDto> searchCollectionOrders(String search, String status, Pageable pageable) {
        TestStatus parsedStatus = status == null || status.isBlank() || status.equalsIgnoreCase("All") ? null : TestStatus.valueOf(status.toUpperCase().replace(" ", "_"));
        return labTestRequestRepository.searchCollections(COLLECTION_STATUSES, parsedStatus, search == null ? "" : search.trim(), pageable)
                .map(this::mapCollectionOrder);
    }

    private CollectionOrderDto mapCollectionOrder(LabTestRequest req) {
        return CollectionOrderDto.builder().uid(req.getId().toString()).id(req.getLabOrder().getId().toString()).patientId(req.getLabOrder().getRegistration().getId()).uhid(req.getLabOrder().getRegistration().getUhid())
                .patientName(req.getLabOrder().getRegistration().getFirstName() + " " + req.getLabOrder().getRegistration().getLastName()).age(req.getLabOrder().getRegistration().getAge())
                .gender(req.getLabOrder().getRegistration().getGender()).testName(req.getTestMaster().getName()).code(req.getTestMaster().getCode()).department(req.getTestMaster().getDepartment())
                .sampleType("Serum").container("Red top (plain)").orderedBy(req.getLabOrder().getReferringDoctorName())
                .orderedTime(req.getLabOrder().getOrderDate() != null ? req.getLabOrder().getOrderDate().format(TIME_FORMATTER) : "")
                .priority(req.getPriority().name()).status(req.getStatus().name().replace("_", " ")).collectedAt(req.getCollectedAt() != null ? req.getCollectedAt().format(TIME_FORMATTER) : null)
                .collectedBy(req.getCollectedBy()).barcode(req.getLabOrder().getRegistration().getBarcode()).rejectionReason(req.getRejectionReason()).build();
    }

    @Transactional
    public void collectSample(String barcode, CollectSampleRequestDto request) {
        Registration registration = findRegistrationByBarcode(barcode);
        LocalDateTime collectedAt = LocalDateTime.now();
        List<LabTestRequest> pendingTests = registration.getLabOrders().stream()
                .flatMap(order -> order.getTestRequests().stream())
                .filter(test -> test.getStatus() == TestStatus.PENDING_COLLECTION || test.getStatus() == TestStatus.REJECTED)
                .collect(Collectors.toList());
        if (pendingTests.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No pending sample was found for " + barcode);
        }
        pendingTests.forEach(test -> {
            test.setStatus(TestStatus.COLLECTED);
            test.setCollectedBy(request.getCollectedBy());
            test.setCollectedAt(collectedAt);
            test.setRejectionReason(null);
        });
    }

    @Transactional
    public void rejectSample(String barcode, String reason, String notes) {
        String finalReason = notes != null && !notes.isEmpty() ? reason + " - " + notes : reason;
        Registration registration = findRegistrationByBarcode(barcode);
        registration.getLabOrders().stream().flatMap(order -> order.getTestRequests().stream())
                .filter(test -> test.getStatus() == TestStatus.PENDING_COLLECTION || test.getStatus() == TestStatus.COLLECTED)
                .forEach(test -> {
                    test.setStatus(TestStatus.REJECTED);
                    test.setRejectionReason(finalReason);
                    test.setCollectedBy(null);
                    test.setCollectedAt(null);
                });
    }

    private Registration findRegistrationByBarcode(String barcode) {
        return registrationRepository.findByBarcode(barcode)
                .orElseGet(() -> labTestRequestRepository.findByBarcode(barcode)
                        .map(request -> request.getLabOrder().getRegistration())
                        .orElseGet(() -> findRegistrationByRequestId(barcode)));
    }

    private Registration findRegistrationByRequestId(String identifier) {
        try {
            long id = Long.parseLong(identifier.replaceFirst("(?i)^REG-", ""));
            if (identifier.toUpperCase().startsWith("REG-")) {
                return registrationRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Registration not found for " + identifier));
            }
            return labTestRequestRepository.findById(id)
                    .map(request -> request.getLabOrder().getRegistration())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Test request not found for " + identifier));
        } catch (NumberFormatException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Registration or test request not found for " + identifier, exception);
        }
    }
}
