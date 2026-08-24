package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.EyeExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.EyeExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.EyeExaminationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eye-examinations")
public class EyeExaminationController {

    private final EyeExaminationService eyeExaminationService;

    public EyeExaminationController(
            EyeExaminationService eyeExaminationService) {

        this.eyeExaminationService = eyeExaminationService;
    }

    /**
     * Creates an eye examination for an existing registration.
     *
     * @param registrationId registration to which the examination belongs
     * @param request eye examination details
     * @return newly created eye examination
     */
    @PostMapping("/registration/{registrationId}")
    public ResponseEntity<EyeExaminationResponseDto> createEyeExamination(
            @PathVariable Long registrationId,
            @RequestBody EyeExaminationRequestDto request) {

        EyeExaminationResponseDto response =
                eyeExaminationService.createEyeExamination(
                        registrationId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves an eye examination by its ID.
     *
     * @param id eye examination ID
     * @return eye examination details
     */
    @GetMapping("/{id}")
    public ResponseEntity<EyeExaminationResponseDto> getEyeExaminationById(
            @PathVariable Long id) {

        EyeExaminationResponseDto response =
                eyeExaminationService.getEyeExaminationById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all eye examinations for a registration.
     *
     * @param registrationId registration ID
     * @return list of eye examinations
     */
    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<List<EyeExaminationResponseDto>>
    getEyeExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<EyeExaminationResponseDto> response =
                eyeExaminationService
                        .getEyeExaminationsByRegistrationId(registrationId);

        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing eye examination.
     *
     * @param id eye examination ID
     * @param request updated eye examination details
     * @return updated eye examination
     */
    @PutMapping("/{id}")
    public ResponseEntity<EyeExaminationResponseDto> updateEyeExamination(
            @PathVariable Long id,
            @RequestBody EyeExaminationRequestDto request) {

        EyeExaminationResponseDto response =
                eyeExaminationService.updateEyeExamination(id, request);

        return ResponseEntity.ok(response);
    }
    /**
     * Deletes an eye examination by its ID.
     *
     * @param id eye examination ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEyeExamination(
            @PathVariable Long id) {

        eyeExaminationService.deleteEyeExamination(id);

        return ResponseEntity.noContent().build();
    }
}