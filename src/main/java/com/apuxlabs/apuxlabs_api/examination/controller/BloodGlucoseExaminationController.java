package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.BloodGlucoseExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.BloodGlucoseExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.BloodGlucoseExaminationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blood-glucose-examinations")
@Tag(
        name = "Blood Glucose Examination",
        description = "APIs for managing FBS and PLBS blood glucose examinations"
)
public class BloodGlucoseExaminationController {

    private final BloodGlucoseExaminationService bloodGlucoseExaminationService;

    public BloodGlucoseExaminationController(
            BloodGlucoseExaminationService bloodGlucoseExaminationService) {

        this.bloodGlucoseExaminationService =
                bloodGlucoseExaminationService;
    }

    /**
     * Creates a new blood glucose examination for an existing registration.
     *
     * @param registrationId registration ID
     * @param request blood glucose examination details
     * @return created blood glucose examination
     */
    @Operation(
            summary = "Create blood glucose examination",
            description = "Creates an FBS or PLBS blood glucose examination for an existing registration."
    )
    @PostMapping("/registration/{registrationId}")
    public ResponseEntity<BloodGlucoseExaminationResponseDto>
    createBloodGlucoseExamination(
            @PathVariable Long registrationId,
            @RequestBody BloodGlucoseExaminationRequestDto request) {

        BloodGlucoseExaminationResponseDto response =
                bloodGlucoseExaminationService
                        .createBloodGlucoseExamination(
                                registrationId,
                                request
                        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves a blood glucose examination by its ID.
     *
     * @param id blood glucose examination ID
     * @return blood glucose examination details
     */
    @Operation(
            summary = "Get blood glucose examination by ID",
            description = "Retrieves a single blood glucose examination using its examination ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<BloodGlucoseExaminationResponseDto>
    getBloodGlucoseExaminationById(
            @PathVariable Long id) {

        BloodGlucoseExaminationResponseDto response =
                bloodGlucoseExaminationService
                        .getBloodGlucoseExaminationById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all FBS examinations associated
     * with a registration.
     *
     * @param registrationId registration ID
     * @return list of FBS examinations
     */
    @Operation(
            summary = "Get FBS examinations by registration",
            description = "Retrieves all fasting blood sugar examinations associated with a registration."
    )
    @GetMapping("/registration/{registrationId}/fbs")
    public ResponseEntity<List<BloodGlucoseExaminationResponseDto>>
    getFbsExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<BloodGlucoseExaminationResponseDto> response =
                bloodGlucoseExaminationService
                        .getFbsExaminationsByRegistrationId(
                                registrationId
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all PLBS examinations associated
     * with a registration.
     *
     * @param registrationId registration ID
     * @return list of PLBS examinations
     */
    @Operation(
            summary = "Get PLBS examinations by registration",
            description = "Retrieves all post-prandial blood sugar examinations associated with a registration."
    )
    @GetMapping("/registration/{registrationId}/plbs")
    public ResponseEntity<List<BloodGlucoseExaminationResponseDto>>
    getPlbsExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<BloodGlucoseExaminationResponseDto> response =
                bloodGlucoseExaminationService
                        .getPlbsExaminationsByRegistrationId(
                                registrationId
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all blood glucose examinations associated
     * with a registration.
     *
     * @param registrationId registration ID
     * @return list of blood glucose examinations
     */
    @Operation(
            summary = "Get blood glucose examinations by registration",
            description = "Retrieves all FBS and PLBS examinations associated with a registration."
    )
    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<List<BloodGlucoseExaminationResponseDto>>
    getBloodGlucoseExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<BloodGlucoseExaminationResponseDto> response =
                bloodGlucoseExaminationService
                        .getBloodGlucoseExaminationsByRegistrationId(
                                registrationId
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing blood glucose examination.
     *
     * @param id blood glucose examination ID
     * @param request updated blood glucose examination details
     * @return updated blood glucose examination
     */
    @Operation(
            summary = "Update blood glucose examination",
            description = "Updates an existing FBS or PLBS blood glucose examination using its examination ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<BloodGlucoseExaminationResponseDto>
    updateBloodGlucoseExamination(
            @PathVariable Long id,
            @RequestBody BloodGlucoseExaminationRequestDto request) {

        BloodGlucoseExaminationResponseDto response =
                bloodGlucoseExaminationService
                        .updateBloodGlucoseExamination(
                                id,
                                request
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a blood glucose examination by its ID.
     *
     * @param id blood glucose examination ID
     * @return HTTP 204 No Content
     */
    @Operation(
            summary = "Delete blood glucose examination",
            description = "Deletes an existing blood glucose examination using its examination ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloodGlucoseExamination(
            @PathVariable Long id) {

        bloodGlucoseExaminationService
                .deleteBloodGlucoseExamination(id);

        return ResponseEntity.noContent().build();
    }
}