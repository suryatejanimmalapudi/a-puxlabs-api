package com.apuxlabs.apuxlabs_api.testorder.controller;

import com.apuxlabs.apuxlabs_api.service.GcsPdfStorageService;
import com.apuxlabs.apuxlabs_api.testorder.dto.DiagnosticFileAttachmentDto;
import com.apuxlabs.apuxlabs_api.testorder.entity.DiagnosticFileAttachment;
import com.apuxlabs.apuxlabs_api.testorder.repository.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/lab-test-requests")
public class DiagnosticFileController {
    private final LabTestRequestRepository requests;
    private final DiagnosticFileAttachmentRepository attachments;
    private final GcsPdfStorageService storage;

    public DiagnosticFileController(LabTestRequestRepository requests, DiagnosticFileAttachmentRepository attachments,
                                    GcsPdfStorageService storage) {
        this.requests = requests; this.attachments = attachments; this.storage = storage;
    }

    @PostMapping(value = "/{requestId}/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DiagnosticFileAttachmentDto upload(@PathVariable Long requestId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "SOURCE") String fileType,
            @RequestParam(defaultValue = "system") String uploadedBy) throws IOException {
        var request = requests.findById(requestId).orElseThrow(() -> new IllegalArgumentException("Test request not found"));
        var original = Optional.ofNullable(file.getOriginalFilename()).orElse("attachment");
        var safeName = original.replaceAll("[^a-zA-Z0-9._-]", "_");
        var path = "diagnostics/requests/" + requestId + "/" + UUID.randomUUID() + "-" + safeName;
        var objectPath = storage.uploadObject(file, path, file.getContentType());
        var saved = attachments.save(DiagnosticFileAttachment.builder().labTestRequest(request).storagePath(objectPath)
                .originalFileName(original).contentType(Optional.ofNullable(file.getContentType()).orElse("application/octet-stream"))
                .fileSize(file.getSize()).fileType(fileType).uploadedBy(uploadedBy).uploadedAt(LocalDateTime.now()).build());
        return DiagnosticFileAttachmentDto.from(saved);
    }

    @GetMapping("/{requestId}/attachments")
    public List<DiagnosticFileAttachmentDto> list(@PathVariable Long requestId) {
        return attachments.findByLabTestRequestIdOrderByUploadedAtDesc(requestId).stream().map(DiagnosticFileAttachmentDto::from).toList();
    }

    @PostMapping(value = "/barcode/{barcode}/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DiagnosticFileAttachmentDto uploadByBarcode(@PathVariable String barcode,
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "RESULT") String fileType,
            @RequestParam(defaultValue = "system") String uploadedBy) throws IOException {
        var request = requests.findByBarcode(barcode).orElseThrow(() -> new IllegalArgumentException("Test request not found"));
        var original = Optional.ofNullable(file.getOriginalFilename()).orElse("attachment");
        var safeName = original.replaceAll("[^a-zA-Z0-9._-]", "_");
        var path = "diagnostics/requests/" + request.getId() + "/" + UUID.randomUUID() + "-" + safeName;
        var objectPath = storage.uploadObject(file, path, file.getContentType());
        return DiagnosticFileAttachmentDto.from(attachments.save(DiagnosticFileAttachment.builder().labTestRequest(request)
                .storagePath(objectPath).originalFileName(original)
                .contentType(Optional.ofNullable(file.getContentType()).orElse("application/octet-stream"))
                .fileSize(file.getSize()).fileType(fileType).uploadedBy(uploadedBy).uploadedAt(LocalDateTime.now()).build()));
    }
}
