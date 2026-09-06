package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.SerumCholinesteraseExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.SerumCholinesteraseExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.SerumCholinesteraseExaminationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/serum-cholinesterase-examinations")
@Tag(
        name = "Serum Cholinesterase Examination",
        description = "APIs for managing Serum Cholinesterase examinations"
)
public class SerumCholinesteraseExaminationController {

    private final SerumCholinesteraseExaminationService
            serumCholinesteraseExaminationService;

    public SerumCholinesteraseExaminationController(
            SerumCholinesteraseExaminationService serumCholinesteraseExaminationService) {

        this.serumCholinesteraseExaminationService =
                serumCholinesteraseExaminationService;
    }

    /**
     * Creates a new serum cholinesterase examination
     * for an existing registration.
     *
     * @param registrationId registration ID
     * @param request serum cholinesterase examination details
     * @return created serum cholinesterase examination
     */
    @Operation(
            summary = "Create serum cholinesterase examination",
            description = "Creates a Serum Cholinesterase examination for an existing registration."
    )
    @PostMapping("/registration/{registrationId}")
    public ResponseEntity<SerumCholinesteraseExaminationResponseDto>
    createSerumCholinesteraseExamination(
            @PathVariable Long registrationId,
            @RequestBody SerumCholinesteraseExaminationRequestDto request) {

        SerumCholinesteraseExaminationResponseDto response =
                serumCholinesteraseExaminationService
                        .createSerumCholinesteraseExamination(
                                registrationId,
                                request
                        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves a serum cholinesterase examination by its ID.
     *
     * @param id serum cholinesterase examination ID
     * @return serum cholinesterase examination details
     */
    @Operation(
            summary = "Get serum cholinesterase examination by ID",
            description = "Retrieves a single Serum Cholinesterase examination using its examination ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<SerumCholinesteraseExaminationResponseDto>
    getSerumCholinesteraseExaminationById(
            @PathVariable Long id) {

        SerumCholinesteraseExaminationResponseDto response =
                serumCholinesteraseExaminationService
                        .getSerumCholinesteraseExaminationById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all serum cholinesterase examinations
     * associated with a registration.
     *
     * @param registrationId registration ID
     * @return list of serum cholinesterase examinations
     */
    @Operation(
            summary = "Get serum cholinesterase examinations by registration",
            description = "Retrieves all Serum Cholinesterase examinations associated with a registration."
    )
    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<List<SerumCholinesteraseExaminationResponseDto>>
    getSerumCholinesteraseExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<SerumCholinesteraseExaminationResponseDto> response =
                serumCholinesteraseExaminationService
                        .getSerumCholinesteraseExaminationsByRegistrationId(
                                registrationId
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing serum cholinesterase examination.
     *
     * @param id serum cholinesterase examination ID
     * @param request updated serum cholinesterase examination details
     * @return updated serum cholinesterase examination
     */
    @Operation(
            summary = "Update serum cholinesterase examination",
            description = "Updates an existing Serum Cholinesterase examination using its examination ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<SerumCholinesteraseExaminationResponseDto>
    updateSerumCholinesteraseExamination(
            @PathVariable Long id,
            @RequestBody SerumCholinesteraseExaminationRequestDto request) {

        SerumCholinesteraseExaminationResponseDto response =
                serumCholinesteraseExaminationService
                        .updateSerumCholinesteraseExamination(
                                id,
                                request
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a serum cholinesterase examination by its ID.
     *
     * @param id serum cholinesterase examination ID
     * @return HTTP 204 No Content
     */
    @Operation(
            summary = "Delete serum cholinesterase examination",
            description = "Deletes an existing Serum Cholinesterase examination using its examination ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSerumCholinesteraseExamination(
            @PathVariable Long id) {

        serumCholinesteraseExaminationService
                .deleteSerumCholinesteraseExamination(id);

        return ResponseEntity.noContent().build();
    }
}