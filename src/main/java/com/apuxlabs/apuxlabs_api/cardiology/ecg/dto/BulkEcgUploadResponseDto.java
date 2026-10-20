package com.apuxlabs.apuxlabs_api.cardiology.ecg.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class BulkEcgUploadResponseDto {
    private int totalFiles;
    private int successfulCount;
    private int failedCount;
    private List<EcgUploadResponseDto> successes;
    private List<FailedUpload> failures;

    @Data
    @Builder
    public static class FailedUpload {
        private String fileName;
        private String reason;
    }
}