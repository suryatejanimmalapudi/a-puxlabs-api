package com.apuxlabs.apuxlabs_api.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GcsPdfStorageService {

    private final Storage storage;
    private final String bucketName;

    public GcsPdfStorageService(Storage storage, @Value("${gcp.bucket.name}") String bucketName) {
        this.storage = storage;
        this.bucketName = bucketName;
    }

    /**
     * Uploads a PDF to GCP.
     * @param file The multipart file to upload.
     * @param customFileName Optional. If provided, saves the file with this name. If null, uses the file's original name.
     */
    public String uploadPdf(MultipartFile file, String customFileName) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload an empty file.");
        }

        if (!"application/pdf".equals(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF files are allowed.");
        }

        // Determine which filename to use
        var fileName = (customFileName != null && !customFileName.isBlank())
                ? customFileName
                : file.getOriginalFilename();

        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("File name cannot be empty.");
        }

        var blobId = BlobId.of(bucketName, fileName);
        var blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType("application/pdf")
                .build();

        storage.create(blobInfo, file.getBytes());

        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }
}