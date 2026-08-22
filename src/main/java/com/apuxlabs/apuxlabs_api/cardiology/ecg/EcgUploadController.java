package com.apuxlabs.apuxlabs_api.cardiology.ecg;

import com.apuxlabs.apuxlabs_api.service.GcsPdfStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/ecg")
public class EcgUploadController {

    private final GcsPdfStorageService storageService;
    // TODO: Inject your actual Database Repository/Service here
    // private final EcgRecordRepository ecgRepository;

    public EcgUploadController(GcsPdfStorageService storageService /*, EcgRecordRepository ecgRepository*/) {
        this.storageService = storageService;
        // this.ecgRepository = ecgRepository;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadEcg(@RequestParam("file") MultipartFile file) {
        try {
            var originalName = file.getOriginalFilename();

            // 1. Basic validation
            if (originalName == null || !originalName.contains("_") || !originalName.toLowerCase().endsWith(".pdf")) {
                return ResponseEntity.badRequest().body("Invalid file name format. Expected: patientId_filename.pdf");
            }

            // 2. Extract patientId and construct the new filename
            // Example: "10045_heartscan.pdf" -> patientId = "10045"
            var patientId = originalName.substring(0, originalName.indexOf('_'));
            var baseName = originalName.substring(0, originalName.lastIndexOf('.'));
            var newFileName = baseName + "_ecg.pdf";

            // 3. Upload to GCP using the custom filename
            var fileUrl = storageService.uploadPdf(file, newFileName);

            // 4. Save to Database
            // var record = new EcgRecord();
            // record.setPatientId(patientId);
            // record.setFileUrl(fileUrl);
            // ecgRepository.save(record);

            return ResponseEntity.ok("ECG uploaded successfully for patient " + patientId + ". Access it at: " + fileUrl);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload ECG document to cloud storage.");
        }
    }
}