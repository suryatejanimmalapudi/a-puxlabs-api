package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.LipidProfileExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.LipidProfileExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.LipidProfileExaminationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lipid-profile-examinations")
@Tag(
        name = "Lipid Profile Examination",
        description = "APIs for managing lipid profile examinations"
)
public class LipidProfileExaminationController {

    private final LipidProfileExaminationService
            lipidProfileExaminationService;

    public LipidProfileExaminationController(
            LipidProfileExaminationService
                    lipidProfileExaminationService) {

        this.lipidProfileExaminationService =
                lipidProfileExaminationService;
    }

    /**
     * Creates a new lipid profile examination
     * for an existing registration.
     *
     * @param registrationId registration ID
     * @param request lipid profile examination details
     * @return created lipid profile examination
     */
    @Operation(
            summary = "Create lipid profile examination",
            description =
                    "Creates a lipid profile examination "
                            + "for an existing registration."
    )
    @PostMapping("/registration/{registrationId}")
    public ResponseEntity<LipidProfileExaminationResponseDto>
    createLipidProfileExamination(
            @PathVariable Long registrationId,
            @RequestBody LipidProfileExaminationRequestDto request) {

        LipidProfileExaminationResponseDto response =
                lipidProfileExaminationService
                        .createLipidProfileExamination(
                                registrationId,
                                request
                        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves a lipid profile examination by its ID.
     *
     * @param id lipid profile examination ID
     * @return lipid profile examination details
     */
    @Operation(
            summary = "Get lipid profile examination by ID",
            description =
                    "Retrieves a single lipid profile examination "
                            + "using its examination ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<LipidProfileExaminationResponseDto>
    getLipidProfileExaminationById(
            @PathVariable Long id) {

        LipidProfileExaminationResponseDto response =
                lipidProfileExaminationService
                        .getLipidProfileExaminationById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all lipid profile examinations
     * associated with a registration.
     *
     * @param registrationId registration ID
     * @return list of lipid profile examinations
     */
    @Operation(
            summary = "Get lipid profile examinations by registration",
            description =
                    "Retrieves all lipid profile examinations "
                            + "associated with a registration."
    )
    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<List<LipidProfileExaminationResponseDto>>
    getLipidProfileExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<LipidProfileExaminationResponseDto> response =
                lipidProfileExaminationService
                        .getLipidProfileExaminationsByRegistrationId(
                                registrationId
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing lipid profile examination.
     *
     * @param id lipid profile examination ID
     * @param request updated lipid profile examination details
     * @return updated lipid profile examination
     */
    @Operation(
            summary = "Update lipid profile examination",
            description =
                    "Updates an existing lipid profile examination "
                            + "using its examination ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<LipidProfileExaminationResponseDto>
    updateLipidProfileExamination(
            @PathVariable Long id,
            @RequestBody LipidProfileExaminationRequestDto request) {

        LipidProfileExaminationResponseDto response =
                lipidProfileExaminationService
                        .updateLipidProfileExamination(
                                id,
                                request
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a lipid profile examination by its ID.
     *
     * @param id lipid profile examination ID
     * @return HTTP 204 No Content
     */
    @Operation(
            summary = "Delete lipid profile examination",
            description =
                    "Deletes an existing lipid profile examination "
                            + "using its examination ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteLipidProfileExamination(
            @PathVariable Long id) {

        lipidProfileExaminationService
                .deleteLipidProfileExamination(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}