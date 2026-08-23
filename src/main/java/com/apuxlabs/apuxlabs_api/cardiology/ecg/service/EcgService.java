package com.apuxlabs.apuxlabs_api.cardiology.ecg.service;

import com.apuxlabs.apuxlabs_api.cardiology.ecg.dto.EcgUploadResponseDto;
import com.apuxlabs.apuxlabs_api.cardiology.ecg.entity.EcgRecord;
import com.apuxlabs.apuxlabs_api.cardiology.ecg.repository.EcgRecordRepository;
import com.apuxlabs.apuxlabs_api.service.GcsPdfStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class EcgService {

    private final GcsPdfStorageService storageService;
    private final EcgRecordRepository ecgRepository;

    public EcgService(GcsPdfStorageService storageService, EcgRecordRepository ecgRepository) {
        this.storageService = storageService;
        this.ecgRepository = ecgRepository;
    }

    @Transactional
    public EcgUploadResponseDto processAndSaveEcg(MultipartFile file, String originalName) throws IOException {

// 1. Extract patientId, baseName, and dynamic extension
        var patientId = originalName.substring(0, originalName.indexOf('_'));
        var baseName = originalName.substring(0, originalName.lastIndexOf('.'));
        var extension = originalName.substring(originalName.lastIndexOf('.')); // captures .pdf, .jpg, or .jpeg

        var newFileName = baseName + "_ecg" + extension;

        // 2. Upload to GCP
        var fileUrl = storageService.uploadPdf(file, newFileName);

        // 3. Save to Database
        var record = EcgRecord.builder()
                .patientId(patientId)
                .fileUrl(fileUrl)
                .originalFileName(originalName)
                .build();
        ecgRepository.save(record);

        // 4. Return success DTO
        return EcgUploadResponseDto.builder()
                .message("Uploaded successfully")
                .patientId(patientId)
                .fileUrl(fileUrl)
                .uploadedAt(LocalDateTime.now())
                .build();
    }
}