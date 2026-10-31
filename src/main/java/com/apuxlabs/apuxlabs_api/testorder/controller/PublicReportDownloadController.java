package com.apuxlabs.apuxlabs_api.testorder.controller;

import com.apuxlabs.apuxlabs_api.security.ReportDownloadRateLimiter;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;
import com.apuxlabs.apuxlabs_api.service.GcsPdfStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/public/reports")
@RequiredArgsConstructor
public class PublicReportDownloadController {
    private final RegistrationRepository registrationRepository;
    private final GcsPdfStorageService storageService;
    private final ReportDownloadRateLimiter rateLimiter;

    @Value("${app.report-download-token-ttl-minutes:5}")
    private long tokenTtlMinutes;

    @GetMapping("/download/{downloadToken}")
    public ResponseEntity<Void> download(@PathVariable UUID downloadToken, HttpServletRequest request) {
        if (!rateLimiter.tryConsume(request.getRemoteAddr())) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        Registration registration = registrationRepository.findByReportDownloadToken(downloadToken).orElse(null);
        if (registration == null) return ResponseEntity.notFound().build();
        if (registration.getReportBundleFilePath() == null || registration.getReportBundleFilePath().isBlank()) {
            return ResponseEntity.notFound().build();
        }
        String signedUrl = storageService.createSignedGetUrl(registration.getReportBundleFilePath(), tokenTtlMinutes);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, URI.create(signedUrl).toString())
                .build();
    }
}
