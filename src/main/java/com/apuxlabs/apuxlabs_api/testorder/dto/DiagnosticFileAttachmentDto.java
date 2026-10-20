package com.apuxlabs.apuxlabs_api.testorder.dto;

import com.apuxlabs.apuxlabs_api.testorder.entity.DiagnosticFileAttachment;
import java.time.LocalDateTime;

public record DiagnosticFileAttachmentDto(Long id, Long testRequestId, String storagePath,
        String originalFileName, String contentType, Long fileSize, String fileType,
        String uploadedBy, LocalDateTime uploadedAt) {
    public static DiagnosticFileAttachmentDto from(DiagnosticFileAttachment a) {
        return new DiagnosticFileAttachmentDto(a.getId(), a.getLabTestRequest().getId(), a.getStoragePath(),
                a.getOriginalFileName(), a.getContentType(), a.getFileSize(), a.getFileType(),
                a.getUploadedBy(), a.getUploadedAt());
    }
}
