package com.apuxlabs.apuxlabs_api.testorder.controller;

import com.apuxlabs.apuxlabs_api.testorder.service.ReportArtifactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/worklist/reports/patient")
@RequiredArgsConstructor
public class ReportArtifactController {
    private final ReportArtifactService artifactService;

    @PostMapping(value = "/{patientId}/artifact", consumes = "multipart/form-data")
    public ResponseEntity<Void> upload(@PathVariable Long patientId,
                                       @RequestParam("file") MultipartFile file) throws IOException {
        artifactService.storePdf(patientId, file);
        return ResponseEntity.ok().build();
    }
}
