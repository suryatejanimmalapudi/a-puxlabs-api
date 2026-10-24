package com.apuxlabs.apuxlabs_api.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
        return uploadFile(file, customFileName, "application/pdf");
    }

    public String uploadFile(MultipartFile file, String objectPath, String contentType) throws IOException {
        var fileName = uploadObject(file, objectPath, contentType);
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }

    /** Uploads and returns the bucket object path, keeping storage details out of the database URL layer. */
    public String uploadObject(MultipartFile file, String objectPath, String contentType) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload an empty file.");
        }

        // Determine which filename to use
        var fileName = (objectPath != null && !objectPath.isBlank()) ? objectPath :
                UUID.randomUUID() + "-" + file.getOriginalFilename();

        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("File name cannot be empty.");
        }

        var blobId = BlobId.of(bucketName, fileName);
        var blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(contentType == null ? "application/octet-stream" : contentType)
                .build();

        storage.create(blobInfo, file.getBytes());

        return fileName;
    }

    public String uploadBytes(byte[] bytes, String objectPath, String contentType) {
        if (bytes == null || bytes.length == 0) throw new IllegalArgumentException("Cannot upload an empty file.");
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectPath))
                .setContentType(contentType == null ? "application/octet-stream" : contentType)
                .build();
        storage.create(blobInfo, bytes);
        return objectPath;
    }

    public String createSignedGetUrl(String objectPath, long ttlMinutes) {
        if (objectPath == null || objectPath.isBlank()) throw new IllegalArgumentException("Report file is not available.");
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectPath)).build();
        return storage.signUrl(blobInfo, ttlMinutes, TimeUnit.MINUTES,
                Storage.SignUrlOption.withV4Signature()).toString();
    }
}
