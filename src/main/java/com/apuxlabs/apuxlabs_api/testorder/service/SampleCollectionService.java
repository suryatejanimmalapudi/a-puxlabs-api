package com.apuxlabs.apuxlabs_api.testorder.service;

import com.apuxlabs.apuxlabs_api.testorder.dto.CollectSampleRequestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.CollectionOrderDto;
import com.apuxlabs.apuxlabs_api.testorder.entity.LabTestRequest;
import com.apuxlabs.apuxlabs_api.testorder.enums.TestStatus;
import com.apuxlabs.apuxlabs_api.testorder.repository.LabTestRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SampleCollectionService {

    private final LabTestRequestRepository labTestRequestRepository;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

    @Transactional(readOnly = true)
    public List<CollectionOrderDto> getAllCollectionOrders() {
        // Fetch orders that are not completed (Pending Collection, Collected, or Rejected)
        List<LabTestRequest> requests = labTestRequestRepository.findByStatusNot(TestStatus.COMPLETED);

        return requests.stream().map(req -> CollectionOrderDto.builder()
                .uid(req.getBarcode())
                .id(req.getLabOrder().getId().toString())
                .patientId(req.getLabOrder().getRegistration().getId())
                .patientName(req.getLabOrder().getRegistration().getFirstName() + " " + req.getLabOrder().getRegistration().getLastName())
                .age(25) // Calculate from Registration DOB
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
                .barcode(req.getBarcode())
                .rejectionReason(req.getRejectionReason())
                .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    public void collectSample(String barcode, CollectSampleRequestDto request) {
        LabTestRequest testReq = labTestRequestRepository.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Test request not found"));

        testReq.setStatus(TestStatus.COLLECTED);
        testReq.setCollectedBy(request.getCollectedBy());
        testReq.setCollectedAt(LocalDateTime.now());
        testReq.setRejectionReason(null); // Clear any previous rejections
    }

    @Transactional
    public void rejectSample(String barcode, String reason, String notes) {
        LabTestRequest testReq = labTestRequestRepository.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Test request not found"));

        testReq.setStatus(TestStatus.REJECTED);
        String finalReason = notes != null && !notes.isEmpty() ? reason + " - " + notes : reason;
        testReq.setRejectionReason(finalReason);
        testReq.setCollectedBy(null);
        testReq.setCollectedAt(null);
    }
}