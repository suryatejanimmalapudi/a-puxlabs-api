package com.apuxlabs.apuxlabs_api.service;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.testorder.entity.LabTestRequest;
import com.apuxlabs.apuxlabs_api.testorder.enums.TestStatus;
import com.apuxlabs.apuxlabs_api.testorder.repository.LabTestRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicReportBundleService {
    private final LabTestRequestRepository requestRepository;
    private final ReportPdfService reportPdfService;
    private final GcsPdfStorageService storageService;

    public String createOrRefreshBundle(Registration registration) {
        List<LabTestRequest> reports = requestRepository.findPublicReports(
                registration.getReportDownloadToken(),
                List.of(TestStatus.VERIFIED, TestStatus.DELIVERED));
        if (reports.isEmpty()) {
            throw new IllegalStateException("No verified reports are available for this visit.");
        }
        String objectPath = "reports/" + registration.getId() + "/bundle-"
                + registration.getReportDownloadToken() + ".pdf";
        String storedPath = storageService.uploadBytes(reportPdfService.createCombinedPdf(reports), objectPath, "application/pdf");
        registration.setReportBundleFilePath(storedPath);
        return storedPath;
    }
}
