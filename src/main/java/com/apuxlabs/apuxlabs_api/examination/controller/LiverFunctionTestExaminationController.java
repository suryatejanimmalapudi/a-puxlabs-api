package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.LiverFunctionTestExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.LiverFunctionTestExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.LiverFunctionTestExaminationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/liver-function-test-examinations")
@Tag(
        name = "Liver Function Test Examination",
        description = "APIs for managing liver function test examinations"
)
public class LiverFunctionTestExaminationController {

    private final LiverFunctionTestExaminationService
            liverFunctionTestExaminationService;

    public LiverFunctionTestExaminationController(
            LiverFunctionTestExaminationService
                    liverFunctionTestExaminationService) {

        this.liverFunctionTestExaminationService =
                liverFunctionTestExaminationService;
    }

    /**
     * Creates a new liver function test examination
     * for an existing registration.
     *
     * @param registrationId registration ID
     * @param request liver function test examination details
     * @return created liver function test examination
     */
    @Operation(
            summary = "Create liver function test examination",
            description =
                    "Creates a liver function test examination "
                            + "for an existing registration."
    )
    @PostMapping("/registration/{registrationId}")
    public ResponseEntity<LiverFunctionTestExaminationResponseDto>
    createLiverFunctionTestExamination(
            @PathVariable Long registrationId,
            @RequestBody LiverFunctionTestExaminationRequestDto request) {

        LiverFunctionTestExaminationResponseDto response =
                liverFunctionTestExaminationService
                        .createLiverFunctionTestExamination(
                                registrationId,
                                request
                        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves a liver function test examination by its ID.
     *
     * @param id liver function test examination ID
     * @return liver function test examination details
     */
    @Operation(
            summary = "Get liver function test examination by ID",
            description =
                    "Retrieves a single liver function test examination "
                            + "using its examination ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<LiverFunctionTestExaminationResponseDto>
    getLiverFunctionTestExaminationById(
            @PathVariable Long id) {

        LiverFunctionTestExaminationResponseDto response =
                liverFunctionTestExaminationService
                        .getLiverFunctionTestExaminationById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all liver function test examinations
     * associated with a registration.
     *
     * @param registrationId registration ID
     * @return list of liver function test examinations
     */
    @Operation(
            summary = "Get liver function test examinations by registration",
            description =
                    "Retrieves all liver function test examinations "
                            + "associated with a registration."
    )
    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<List<LiverFunctionTestExaminationResponseDto>>
    getLiverFunctionTestExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<LiverFunctionTestExaminationResponseDto> response =
                liverFunctionTestExaminationService
                        .getLiverFunctionTestExaminationsByRegistrationId(
                                registrationId
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing liver function test examination.
     *
     * @param id liver function test examination ID
     * @param request updated liver function test examination details
     * @return updated liver function test examination
     */
    @Operation(
            summary = "Update liver function test examination",
            description =
                    "Updates an existing liver function test examination "
                            + "using its examination ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<LiverFunctionTestExaminationResponseDto>
    updateLiverFunctionTestExamination(
            @PathVariable Long id,
            @RequestBody LiverFunctionTestExaminationRequestDto request) {

        LiverFunctionTestExaminationResponseDto response =
                liverFunctionTestExaminationService
                        .updateLiverFunctionTestExamination(
                                id,
                                request
                        );

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a liver function test examination by its ID.
     *
     * @param id liver function test examination ID
     * @return HTTP 204 No Content
     */
    @Operation(
            summary = "Delete liver function test examination",
            description =
                    "Deletes an existing liver function test examination "
                            + "using its examination ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteLiverFunctionTestExamination(
            @PathVariable Long id) {

        liverFunctionTestExaminationService
                .deleteLiverFunctionTestExamination(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}