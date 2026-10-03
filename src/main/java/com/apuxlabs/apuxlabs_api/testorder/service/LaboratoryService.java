package com.apuxlabs.apuxlabs_api.testorder.service;

import com.apuxlabs.apuxlabs_api.testorder.dto.PendingTestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.ResultEntryDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.ReportDto;
import com.apuxlabs.apuxlabs_api.testorder.entity.LabTestRequest;
import com.apuxlabs.apuxlabs_api.testorder.enums.TestStatus;
import com.apuxlabs.apuxlabs_api.testorder.repository.LabTestRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LaboratoryService {

    private final LabTestRequestRepository testRequestRepository;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

    @Transactional(readOnly = true)
    public List<PendingTestDto> getPendingWorklist() {
        List<LabTestRequest> pendingRequests = testRequestRepository.findPendingWorklist(TestStatus.COLLECTED);

        return pendingRequests.stream().map(this::mapToPendingTestDto).collect(Collectors.toList());
    }

    @Transactional
    public void submitResults(String barcode, ResultEntryDto payload) {
        LabTestRequest request = testRequestRepository.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Test request not found with barcode: " + barcode));

        if (request.getStatus() != TestStatus.COLLECTED) {
            throw new IllegalStateException("Can only submit results for collected samples.");
        }

        request.setResultData(payload.getResults());
        request.setStatus(TestStatus.COMPLETED);
        request.setCompletedAt(LocalDateTime.now());

        testRequestRepository.save(request);
    }

    @Transactional(readOnly = true)
    public List<ReportDto> getReports() {
        return testRequestRepository.findReports(reportStatuses())
                .stream()
                .map(this::mapToReportDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReportDto> getPatientReports(Long patientId) {
        return testRequestRepository.findPatientReports(patientId,
                        Arrays.asList(TestStatus.VERIFIED, TestStatus.DELIVERED))
                .stream()
                .map(this::mapToReportDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void verifyReport(Long id, String verifiedBy) {
        if (verifiedBy == null || verifiedBy.isBlank()) {
            throw new IllegalArgumentException("Verifier name is required.");
        }
        LabTestRequest request = findReport(id);
        if (request.getStatus() != TestStatus.COMPLETED) {
            throw new IllegalStateException("Only completed reports can be verified.");
        }
        request.setStatus(TestStatus.VERIFIED);
        request.setVerifiedBy(verifiedBy.trim());
        request.setVerifiedAt(LocalDateTime.now());
    }

    @Transactional
    public void deliverReport(Long id) {
        LabTestRequest request = findReport(id);
        if (request.getStatus() != TestStatus.VERIFIED) {
            throw new IllegalStateException("Only verified reports can be delivered.");
        }
        request.setStatus(TestStatus.DELIVERED);
        request.setDeliveredAt(LocalDateTime.now());
    }

    @Transactional
    public void rejectReport(Long id) {
        LabTestRequest request = findReport(id);
        if (request.getStatus() != TestStatus.COMPLETED) {
            throw new IllegalStateException("Only reports awaiting verification can be returned for correction.");
        }
        request.setStatus(TestStatus.COLLECTED);
        request.setCompletedAt(null);
    }

    private LabTestRequest findReport(Long id) {
        return testRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found: " + id));
    }

    private List<TestStatus> reportStatuses() {
        return Arrays.asList(TestStatus.COMPLETED, TestStatus.VERIFIED, TestStatus.DELIVERED);
    }

    private PendingTestDto mapToPendingTestDto(LabTestRequest req) {
        PendingTestDto dto = new PendingTestDto();
        dto.setId(req.getBarcode());

        // Null checks are a good idea when traversing nested entities
        if (req.getLabOrder() != null && req.getLabOrder().getRegistration() != null) {
            dto.setPatientId(req.getLabOrder().getRegistration().getId());
            dto.setPatientName(req.getLabOrder().getRegistration().getFirstName() + " " + req.getLabOrder().getRegistration().getLastName());
            dto.setGender(req.getLabOrder().getRegistration().getGender());
            dto.setAge(calculateAge(req.getLabOrder().getRegistration().getDateOfBirth()));
        }

        if (req.getTestMaster() != null) {
            dto.setTestName(req.getTestMaster().getName());
            dto.setCode(req.getTestMaster().getCode());
            dto.setDepartment(req.getTestMaster().getDepartment());
        }

        dto.setPriority(req.getPriority() != null ? req.getPriority().name() : "Routine");

        dto.setTimeCollected(req.getCollectedAt() != null
                ? req.getCollectedAt().format(TIME_FORMATTER)
                : "N/A");

        return dto;
    }

    private ReportDto mapToReportDto(LabTestRequest req) {
        ReportDto dto = new ReportDto();
        dto.setId(req.getId());
        dto.setReportNumber("RPT-" + req.getBarcode());
        dto.setPatientId(req.getLabOrder().getRegistration().getId());
        dto.setPatientName(req.getLabOrder().getRegistration().getFirstName() + " " +
                Objects.toString(req.getLabOrder().getRegistration().getLastName(), ""));
        dto.setAge(calculateAge(req.getLabOrder().getRegistration().getDateOfBirth()));
        dto.setGender(req.getLabOrder().getRegistration().getGender());
        dto.setTestName(req.getTestMaster().getName());
        dto.setTestCode(req.getTestMaster().getCode());
        dto.setDepartment(req.getTestMaster().getDepartment());
        dto.setOrderedBy(req.getLabOrder().getReferringDoctorName());
        dto.setCollectedAt(formatDateTime(req.getCollectedAt()));
        dto.setReportedAt(formatDateTime(req.getCompletedAt()));
        dto.setVerifiedBy(req.getVerifiedBy());
        dto.setVerifiedAt(formatDateTime(req.getVerifiedAt()));
        dto.setStatus(switch (req.getStatus()) {
            case COMPLETED -> "Awaiting Verification";
            case VERIFIED -> "Verified";
            case DELIVERED -> "Delivered";
            default -> throw new IllegalStateException("Unexpected report status: " + req.getStatus());
        });
        dto.setResults(mapResults(req.getResultData()));
        return dto;
    }

    private List<ReportDto.ResultDto> mapResults(Map<String, Object> resultData) {
        if (resultData == null) return List.of();
        return resultData.values().stream()
                .filter(value -> value instanceof Map<?, ?>)
                .map(value -> (Map<?, ?>) value)
                .filter(value -> value.containsKey("label") && value.containsKey("value"))
                .map(value -> {
                    ReportDto.ResultDto result = new ReportDto.ResultDto();
                    result.setLabel(Objects.toString(value.get("label"), ""));
                    result.setValue(Objects.toString(value.get("value"), ""));
                    result.setUnit(Objects.toString(value.get("unit"), ""));
                    result.setReferenceRange(Objects.toString(value.get("referenceRange"), ""));
                    return result;
                }).collect(Collectors.toList());
    }

    private Integer calculateAge(LocalDate dateOfBirth) {
        return dateOfBirth == null ? null : Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private String formatDateTime(LocalDateTime value) {
        return value == null ? null : value.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }
}
