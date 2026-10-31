package com.apuxlabs.apuxlabs_api.cardiology.ecg.controller;

import com.apuxlabs.apuxlabs_api.cardiology.ecg.dto.BulkEcgUploadResponseDto;
import com.apuxlabs.apuxlabs_api.cardiology.ecg.dto.EcgUploadResponseDto;
import com.apuxlabs.apuxlabs_api.cardiology.ecg.service.EcgService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ecg")
public class EcgUploadController {

    private final EcgService ecgService;

    public EcgUploadController(EcgService ecgService) {
        this.ecgService = ecgService;
    }

    private boolean isValidFileName(String fileName) {
        if (fileName == null || !fileName.contains("_")) {
            return false;
        }
        String lowerCaseName = fileName.toLowerCase();
        return lowerCaseName.endsWith(".pdf") ||
                lowerCaseName.endsWith(".jpg") ||
                lowerCaseName.endsWith(".jpeg");
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadEcg(@RequestParam("file") MultipartFile file) {
        try {
            var originalName = file.getOriginalFilename();

            if (!isValidFileName(originalName)) {
                return ResponseEntity.badRequest().body("Invalid file name format. Expected: patientId_filename.(pdf|jpg|jpeg)");
            }

            var responseDto = ecgService.processAndSaveEcg(file, originalName);

            return ResponseEntity.ok(responseDto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload ECG document to cloud storage.");
        }
    }

    @PostMapping(value = "/bulk-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BulkEcgUploadResponseDto> bulkUploadEcg(@RequestParam("files") MultipartFile[] files) {

        List<EcgUploadResponseDto> successes = new ArrayList<>();
        List<BulkEcgUploadResponseDto.FailedUpload> failures = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();

            if (originalName == null || originalName.isBlank()) {
                failures.add(BulkEcgUploadResponseDto.FailedUpload.builder()
                        .fileName("Unknown File")
                        .reason("File name is missing or empty")
                        .build());
                continue;
            }

            try {
                if (!isValidFileName(originalName)) {
                    failures.add(BulkEcgUploadResponseDto.FailedUpload.builder()
                            .fileName(originalName)
                            .reason("Invalid format. Expected: patientId_filename.(pdf|jpg|jpeg)")
                            .build());
                    continue;
                }

                // Hand off to service
                var successDto = ecgService.processAndSaveEcg(file, originalName);
                successes.add(successDto);

            } catch (IOException e) {
                failures.add(BulkEcgUploadResponseDto.FailedUpload.builder()
                        .fileName(originalName)
                        .reason("Cloud storage upload failed: " + e.getMessage())
                        .build());
            } catch (Exception e) {
                failures.add(BulkEcgUploadResponseDto.FailedUpload.builder()
                        .fileName(originalName)
                        .reason("Unexpected error processing file")
                        .build());
            }
        }

        var responseDto = BulkEcgUploadResponseDto.builder()
                .totalFiles(files.length)
                .successfulCount(successes.size())
                .failedCount(failures.size())
                .successes(successes)
                .failures(failures)
                .build();

        if (successes.isEmpty() && files.length > 0) {
            return ResponseEntity.badRequest().body(responseDto);
        } else if (!failures.isEmpty()) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(responseDto);
        }

        return ResponseEntity.ok(responseDto);
    }
}