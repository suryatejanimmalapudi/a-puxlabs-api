package com.apuxlabs.apuxlabs_api.cardiology.ecg.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EcgUploadResponseDto {
    private String message;
    private String patientId;
    private String fileUrl;
    private LocalDateTime uploadedAt;
}