package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.RenalFunctionExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.RenalFunctionExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.RenalFunctionExaminationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/renal-function-examinations")
@Tag(
        name = "Renal Function Examination",
        description = "APIs for managing Urea and Creatinine renal function examinations"
)
public class RenalFunctionExaminationController {

    private final RenalFunctionExaminationService renalFunctionExaminationService;

    public RenalFunctionExaminationController(
            RenalFunctionExaminationService renalFunctionExaminationService) {

        this.renalFunctionExaminationService =
                renalFunctionExaminationService;
    }

    /**
     * Creates a new renal function examination for an existing registration.
     *
     * @param registrationId registration ID
     * @param request renal function examination details
     * @return created renal function examination
     */
    @Operation(
            summary = "Create renal function examination",
            description = "Creates a Urea or Creatinine examination for an existing registration."
    )
    @PostMapping("/registration/{registrationId}")
    public ResponseEntity<RenalFunctionExaminationResponseDto>
    createRenalFunctionExamination(
            @PathVariable Long registrationId,
            @RequestBody RenalFunctionExaminationRequestDto request) {

        RenalFunctionExaminationResponseDto response =
                renalFunctionExaminationService
                        .createRenalFunctionExamination(
                                registrationId,
                                request
                        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves a renal function examination by its ID.
     *
     * @param id renal function examination ID
     * @return renal function examination details
     */
    @Operation(
            summary = "Get renal function examination by ID",
            description = "Retrieves a single renal function examination using its examination ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<RenalFunctionExaminationResponseDto>
    getRenalFunctionExaminationById(
            @PathVariable Long id) {

        RenalFunctionExaminationResponseDto response =
                renalFunctionExaminationService
                        .getRenalFunctionExaminationById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all renal function examinations
     * associated with a registration.
     *
     * @param registrationId registration ID
     * @return list of renal function examinations
     */
    @Operation(
            summary = "Get renal function examinations by registration",
            description = "Retrieves all Urea and Creatinine examinations associated with a registration."
    )
    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<List<RenalFunctionExaminationResponseDto>>
    getRenalFunctionExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<RenalFunctionExaminationResponseDto> response =
                renalFunctionExaminationService
                        .getRenalFunctionExaminationsByRegistrationId(
                                registrationId
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Urea examinations
     * associated with a registration.
     *
     * @param registrationId registration ID
     * @return list of Urea examinations
     */
    @Operation(
            summary = "Get Urea examinations by registration",
            description = "Retrieves all Urea examinations associated with a registration."
    )
    @GetMapping("/registration/{registrationId}/urea")
    public ResponseEntity<List<RenalFunctionExaminationResponseDto>>
    getUreaExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<RenalFunctionExaminationResponseDto> response =
                renalFunctionExaminationService
                        .getUreaExaminationsByRegistrationId(
                                registrationId
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Creatinine examinations
     * associated with a registration.
     *
     * @param registrationId registration ID
     * @return list of Creatinine examinations
     */
    @Operation(
            summary = "Get Creatinine examinations by registration",
            description = "Retrieves all Creatinine examinations associated with a registration."
    )
    @GetMapping("/registration/{registrationId}/creatinine")
    public ResponseEntity<List<RenalFunctionExaminationResponseDto>>
    getCreatinineExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<RenalFunctionExaminationResponseDto> response =
                renalFunctionExaminationService
                        .getCreatinineExaminationsByRegistrationId(
                                registrationId
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing renal function examination.
     *
     * @param id renal function examination ID
     * @param request updated renal function examination details
     * @return updated renal function examination
     */
    @Operation(
            summary = "Update renal function examination",
            description = "Updates an existing Urea or Creatinine examination using its examination ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<RenalFunctionExaminationResponseDto>
    updateRenalFunctionExamination(
            @PathVariable Long id,
            @RequestBody RenalFunctionExaminationRequestDto request) {

        RenalFunctionExaminationResponseDto response =
                renalFunctionExaminationService
                        .updateRenalFunctionExamination(
                                id,
                                request
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a renal function examination by its ID.
     *
     * @param id renal function examination ID
     * @return HTTP 204 No Content
     */
    @Operation(
            summary = "Delete renal function examination",
            description = "Deletes an existing renal function examination using its examination ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRenalFunctionExamination(
            @PathVariable Long id) {

        renalFunctionExaminationService
                .deleteRenalFunctionExamination(id);

        return ResponseEntity.noContent().build();
    }
}