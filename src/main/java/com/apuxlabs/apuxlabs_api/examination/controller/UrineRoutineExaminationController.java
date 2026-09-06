package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.UrineRoutineExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.UrineRoutineExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.UrineRoutineExaminationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urine-routine-examinations")
@Tag(
        name = "Urine Routine Examination",
        description = "APIs for managing urine routine examinations"
)
public class UrineRoutineExaminationController {

    private final UrineRoutineExaminationService
            urineRoutineExaminationService;

    public UrineRoutineExaminationController(
            UrineRoutineExaminationService urineRoutineExaminationService) {

        this.urineRoutineExaminationService =
                urineRoutineExaminationService;
    }

    /**
     * Creates a new urine routine examination
     * for an existing registration.
     *
     * @param registrationId registration ID
     * @param request urine routine examination details
     * @return created urine routine examination
     */
    @Operation(
            summary = "Create urine routine examination",
            description =
                    "Creates a urine routine examination "
                            + "for an existing registration."
    )
    @PostMapping("/registration/{registrationId}")
    public ResponseEntity<UrineRoutineExaminationResponseDto>
    createUrineRoutineExamination(
            @PathVariable Long registrationId,
            @RequestBody UrineRoutineExaminationRequestDto request) {

        UrineRoutineExaminationResponseDto response =
                urineRoutineExaminationService
                        .createUrineRoutineExamination(
                                registrationId,
                                request
                        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves a urine routine examination by its ID.
     *
     * @param id urine routine examination ID
     * @return urine routine examination details
     */
    @Operation(
            summary = "Get urine routine examination by ID",
            description =
                    "Retrieves a single urine routine examination "
                            + "using its examination ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<UrineRoutineExaminationResponseDto>
    getUrineRoutineExaminationById(
            @PathVariable Long id) {

        UrineRoutineExaminationResponseDto response =
                urineRoutineExaminationService
                        .getUrineRoutineExaminationById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all urine routine examinations
     * associated with a registration.
     *
     * @param registrationId registration ID
     * @return list of urine routine examinations
     */
    @Operation(
            summary = "Get urine routine examinations by registration",
            description =
                    "Retrieves all urine routine examinations "
                            + "associated with a registration."
    )
    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<List<UrineRoutineExaminationResponseDto>>
    getUrineRoutineExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<UrineRoutineExaminationResponseDto> response =
                urineRoutineExaminationService
                        .getUrineRoutineExaminationsByRegistrationId(
                                registrationId
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing urine routine examination.
     *
     * @param id urine routine examination ID
     * @param request updated urine routine examination details
     * @return updated urine routine examination
     */
    @Operation(
            summary = "Update urine routine examination",
            description =
                    "Updates an existing urine routine examination "
                            + "using its examination ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<UrineRoutineExaminationResponseDto>
    updateUrineRoutineExamination(
            @PathVariable Long id,
            @RequestBody UrineRoutineExaminationRequestDto request) {

        UrineRoutineExaminationResponseDto response =
                urineRoutineExaminationService
                        .updateUrineRoutineExamination(
                                id,
                                request
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a urine routine examination by its ID.
     *
     * @param id urine routine examination ID
     * @return HTTP 204 No Content
     */
    @Operation(
            summary = "Delete urine routine examination",
            description =
                    "Deletes an existing urine routine examination "
                            + "using its examination ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteUrineRoutineExamination(
            @PathVariable Long id) {

        urineRoutineExaminationService
                .deleteUrineRoutineExamination(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}