package com.apuxlabs.apuxlabs_api.testorder.service;

import com.apuxlabs.apuxlabs_api.testorder.dto.PendingTestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.ResultEntryDto;
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
public class LaboratoryService {

    private final LabTestRequestRepository testRequestRepository;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

    @Transactional(readOnly = true)
    public List<PendingTestDto> getPendingWorklist() {
        List<LabTestRequest> pendingRequests = testRequestRepository.findPendingWorklist(TestStatus.PENDING_COLLECTION);

        return pendingRequests.stream().map(this::mapToPendingTestDto).collect(Collectors.toList());
    }

    @Transactional
    public void submitResults(String barcode, ResultEntryDto payload) {
        LabTestRequest request = testRequestRepository.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Test request not found with barcode: " + barcode));

        if (request.getStatus() != TestStatus.PENDING_COLLECTION) {
            throw new IllegalStateException("Can only submit results for pending tests.");
        }

        request.setResultData(payload.getResults());
        request.setStatus(TestStatus.COMPLETED);
        request.setCompletedAt(LocalDateTime.now());

        testRequestRepository.save(request);
    }

    private PendingTestDto mapToPendingTestDto(LabTestRequest req) {
        PendingTestDto dto = new PendingTestDto();
        dto.setId(req.getBarcode());

        // Null checks are a good idea when traversing nested entities
        if (req.getLabOrder() != null && req.getLabOrder().getRegistration() != null) {
            dto.setPatientId(req.getLabOrder().getRegistration().getId());
            dto.setPatientName(req.getLabOrder().getRegistration().getFirstName() + " " + req.getLabOrder().getRegistration().getLastName());
            dto.setGender(req.getLabOrder().getRegistration().getGender());
            dto.setAge(31); // Hardcoded for now
        }

        if (req.getTestMaster() != null) {
            dto.setTestName(req.getTestMaster().getName());
            dto.setCode(req.getTestMaster().getCode());
            dto.setDepartment(req.getTestMaster().getDepartment());
        }

        dto.setPriority(req.getPriority() != null ? req.getPriority().name() : "Routine");

        dto.setTimeCollected(req.getLabOrder() != null && req.getLabOrder().getOrderDate() != null
                ? req.getLabOrder().getOrderDate().format(TIME_FORMATTER)
                : "N/A");

        return dto;
    }
}