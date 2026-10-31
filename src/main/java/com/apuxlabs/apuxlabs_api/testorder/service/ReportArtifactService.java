package com.apuxlabs.apuxlabs_api.testorder.service;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;
import com.apuxlabs.apuxlabs_api.service.GcsPdfStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ReportArtifactService {
    private final RegistrationRepository registrationRepository;
    private final GcsPdfStorageService storageService;

    @Transactional
    public String storePdf(Long patientId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("Report PDF cannot be empty.");
        Registration registration = registrationRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient registration not found."));
        // Keep one QR-enabled artifact per patient; reprinting replaces the previous version.
        String objectPath = "reports/" + patientId + "/qr-report.pdf";
        String storedPath = storageService.uploadObject(file, objectPath, "application/pdf");
        registration.setReportBundleFilePath(storedPath);
        return storedPath;
    }
}
