package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.CbpExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.CbpExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.CbpExaminationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cbp-examinations")
@Tag(
        name = "CBP Examination",
        description = "APIs for managing Complete Blood Picture examinations"
)
public class CbpExaminationController {

    private final CbpExaminationService cbpExaminationService;

    public CbpExaminationController(
            CbpExaminationService cbpExaminationService) {

        this.cbpExaminationService = cbpExaminationService;
    }

    /**
     * Creates a new CBP examination for an existing registration.
     *
     * @param registrationId registration ID
     * @param request CBP examination details
     * @return created CBP examination
     */
    @Operation(
            summary = "Create CBP examination",
            description = "Creates a Complete Blood Picture examination for an existing registration."
    )
    @PostMapping("/registration/{registrationId}")
    public ResponseEntity<CbpExaminationResponseDto>
    createCbpExamination(
            @PathVariable Long registrationId,
            @RequestBody CbpExaminationRequestDto request) {

        CbpExaminationResponseDto response =
                cbpExaminationService.createCbpExamination(
                        registrationId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves a CBP examination by its ID.
     *
     * @param id CBP examination ID
     * @return CBP examination details
     */
    @Operation(
            summary = "Get CBP examination by ID",
            description = "Retrieves a single CBP examination using its examination ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<CbpExaminationResponseDto>
    getCbpExaminationById(
            @PathVariable Long id) {

        CbpExaminationResponseDto response =
                cbpExaminationService.getCbpExaminationById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all CBP examinations associated
     * with a registration.
     *
     * @param registrationId registration ID
     * @return list of CBP examinations
     */
    @Operation(
            summary = "Get CBP examinations by registration",
            description = "Retrieves all CBP examinations associated with a registration."
    )
    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<List<CbpExaminationResponseDto>>
    getCbpExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<CbpExaminationResponseDto> response =
                cbpExaminationService
                        .getCbpExaminationsByRegistrationId(
                                registrationId
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing CBP examination.
     *
     * @param id CBP examination ID
     * @param request updated CBP examination details
     * @return updated CBP examination
     */

    @Operation(
            summary = "Update CBP examination",
            description = "Updates an existing CBP examination using its examination ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<CbpExaminationResponseDto>
    updateCbpExamination(
            @PathVariable Long id,
            @RequestBody CbpExaminationRequestDto request) {

        CbpExaminationResponseDto response =
                cbpExaminationService.updateCbpExamination(
                        id,
                        request
                );

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a CBP examination by its ID.
     *
     * @param id CBP examination ID
     * @return HTTP 204 No Content
     */

    @Operation(
            summary = "Delete CBP examination",
            description = "Deletes an existing CBP examination using its examination ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCbpExamination(
            @PathVariable Long id) {

        cbpExaminationService.deleteCbpExamination(id);

        return ResponseEntity.noContent().build();
    }
}