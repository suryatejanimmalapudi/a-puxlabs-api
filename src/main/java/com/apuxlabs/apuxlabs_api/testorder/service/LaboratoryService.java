package com.apuxlabs.apuxlabs_api.testorder.service;

import com.apuxlabs.apuxlabs_api.testorder.dto.PendingTestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.ResultEntryDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.ReportDto;
import com.apuxlabs.apuxlabs_api.testorder.entity.LabTestRequest;
import com.apuxlabs.apuxlabs_api.testorder.enums.TestStatus;
import com.apuxlabs.apuxlabs_api.testorder.repository.LabTestRequestRepository;
import com.apuxlabs.apuxlabs_api.testorder.repository.PathologistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.apuxlabs.apuxlabs_api.testorder.enums.TestPriority;
import com.apuxlabs.apuxlabs_api.service.GcsPdfStorageService;
import com.apuxlabs.apuxlabs_api.service.ReportPdfService;
import com.apuxlabs.apuxlabs_api.service.ReportQrCodeService;
import com.apuxlabs.apuxlabs_api.service.PublicReportBundleService;

@Service
@RequiredArgsConstructor
public class LaboratoryService {

    private final LabTestRequestRepository testRequestRepository;
    private final PathologistRepository pathologistRepository;
    private final GcsPdfStorageService storageService;
    private final ReportPdfService reportPdfService;
    private final ReportQrCodeService reportQrCodeService;
    private final PublicReportBundleService publicReportBundleService;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

    @Transactional(readOnly = true)
    public List<PendingTestDto> getPendingWorklist() {
        List<LabTestRequest> pendingRequests = testRequestRepository.findPendingWorklist(TestStatus.COLLECTED);

        return pendingRequests.stream().map(this::mapToPendingTestDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PendingTestDto> searchPendingWorklist(String search, String department, String priority, Pageable pageable) {
        TestPriority parsedPriority = priority == null || priority.isBlank() ? null : TestPriority.valueOf(priority.toUpperCase());
        return testRequestRepository.searchPendingWorklist(TestStatus.COLLECTED, search == null ? "" : search.trim(), department == null ? "" : department.trim(), parsedPriority, pageable)
                .map(this::mapToPendingTestDto);
    }

    @Transactional
    public void submitResults(Long requestId, ResultEntryDto payload) {
        LabTestRequest request = testRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Test request not found: " + requestId));

        if (request.getStatus() == TestStatus.COMPLETED
                || request.getStatus() == TestStatus.VERIFIED
                || request.getStatus() == TestStatus.DELIVERED
                || request.getStatus() == TestStatus.REJECTED) {
            throw new IllegalStateException("Results have already been finalized for this test request.");
        }

        request.setResultData(payload.getResults());
        request.setStatus(TestStatus.COMPLETED);
        request.setCompletedAt(LocalDateTime.now());

        testRequestRepository.save(request);
    }

    @Transactional(readOnly = true)
    public List<PendingTestDto> findIntegrationTargets(String barcode) {
        return testRequestRepository.findIntegrationTargets(
                        barcode,
                        Arrays.asList(TestStatus.COMPLETED, TestStatus.VERIFIED, TestStatus.DELIVERED, TestStatus.REJECTED))
                .stream()
                .map(this::mapToPendingTestDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReportDto> getReports() {
        return testRequestRepository.findReports(reportStatuses())
                .stream()
                .map(this::mapToReportDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ReportDto> searchReports(String search, String status, Pageable pageable) {
        TestStatus parsedStatus = status == null || status.isBlank() || status.equalsIgnoreCase("All") ? null : TestStatus.valueOf(status.toUpperCase().replace(" ", "_"));
        return testRequestRepository.searchReports(reportStatuses(), parsedStatus, search == null ? "" : search.trim(), pageable).map(this::mapToReportDto);
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
    public void verifyReport(Long id, Long pathologistId) {
        if (pathologistId == null) throw new IllegalArgumentException("Pathologist is required.");
        var pathologist = pathologistRepository.findById(pathologistId)
                .filter(com.apuxlabs.apuxlabs_api.testorder.entity.Pathologist::isActive)
                .orElseThrow(() -> new IllegalArgumentException("Active pathologist not found."));
        LabTestRequest request = findReport(id);
        if (request.getStatus() != TestStatus.COMPLETED) {
            throw new IllegalStateException("Only completed reports can be verified.");
        }
        request.setStatus(TestStatus.VERIFIED);
        request.setPathologist(pathologist);
        request.setVerifiedBy(pathologist.getName());
        request.setVerifiedAt(LocalDateTime.now());
        String barcode = request.getLabOrder().getRegistration().getBarcode();
        String objectPath = "reports/" + request.getId() + "/" + barcode + ".pdf";
        request.setReportFilePath(storageService.uploadBytes(reportPdfService.createPdf(request), objectPath, "application/pdf"));
        publicReportBundleService.createOrRefreshBundle(request.getLabOrder().getRegistration());
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
        // Result entry targets the internal test request, while the accession barcode
        // remains shared by the whole visit/order.
        dto.setId(req.getId().toString());

        // Null checks are a good idea when traversing nested entities
        if (req.getLabOrder() != null && req.getLabOrder().getRegistration() != null) {
            dto.setBarcode(req.getLabOrder().getRegistration().getBarcode());
            dto.setPatientId(req.getLabOrder().getRegistration().getId());
            dto.setUhid(req.getLabOrder().getRegistration().getUhid());
            dto.setPatientName(req.getLabOrder().getRegistration().getFirstName() + " " + req.getLabOrder().getRegistration().getLastName());
            dto.setGender(req.getLabOrder().getRegistration().getGender());
            dto.setAge(req.getLabOrder().getRegistration().getAge());
        }

        if (req.getTestMaster() != null) {
            dto.setTestName(req.getTestMaster().getName());
            dto.setCode(req.getTestMaster().getCode());
            dto.setDepartment(req.getTestMaster().getDepartment());
            dto.setFileUploadAllowed(req.getTestMaster().isFileUploadAllowed());
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
        dto.setReportNumber("RPT-" + req.getLabOrder().getRegistration().getBarcode());
        var registrationToken = req.getLabOrder().getRegistration().getReportDownloadToken();
        if (registrationToken != null) {
            dto.setDownloadUrl(reportQrCodeService.downloadUrl(registrationToken.toString()));
            dto.setQrCodeDataUrl(reportQrCodeService.dataUrl(registrationToken.toString()));
        }
        dto.setPatientId(req.getLabOrder().getRegistration().getId());
        dto.setUhid(req.getLabOrder().getRegistration().getUhid());
        dto.setPatientName(req.getLabOrder().getRegistration().getFirstName() + " " +
                Objects.toString(req.getLabOrder().getRegistration().getLastName(), ""));
        dto.setAge(req.getLabOrder().getRegistration().getAge());
        dto.setGender(req.getLabOrder().getRegistration().getGender());
        dto.setTestName(req.getTestMaster().getName());
        dto.setTestCode(req.getTestMaster().getCode());
        dto.setDepartment(req.getTestMaster().getDepartment());
        dto.setPackageName(req.getTestPackage() != null ? req.getTestPackage().getName() : null);
        if (req.getPathologist() != null) {
            dto.setPathologistName(req.getPathologist().getName());
            dto.setPathologistDesignation(req.getPathologist().getDesignation());
            dto.setPathologistRegistrationNumber(req.getPathologist().getRegistrationNumber());
            dto.setPathologistSignatureImagePath(req.getPathologist().getSignatureImagePath());
        }
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

    private String formatDateTime(LocalDateTime value) {
        return value == null ? null : value.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }
}
