package com.apuxlabs.apuxlabs_api.testorder.controller;

import com.apuxlabs.apuxlabs_api.testorder.dto.PendingTestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.ResultEntryDto;
import com.apuxlabs.apuxlabs_api.testorder.service.LaboratoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/worklist")
@RequiredArgsConstructor
@Tag(name = "Laboratory Worklist", description = "Endpoints for managing the laboratory technician worklist and result entry")
public class LaboratoryController {

    private final LaboratoryService laboratoryService;

    @Operation(
            summary = "Get pending worklist",
            description = "Retrieves the list of pending lab tests for the technician UI, ordered by priority and order date."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the pending worklist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PendingTestDto.class))
            )
    })
    @GetMapping("/pending")
    public ResponseEntity<List<PendingTestDto>> getPendingWorklist() {
        List<PendingTestDto> dtos = laboratoryService.getPendingWorklist();
        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "Submit test results",
            description = "Submits the results for a lab test identified by its barcode."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Results submitted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No lab test request found for the provided barcode",
                    content = @Content
            )
    })
    @PutMapping("/{barcode}/results")
    public ResponseEntity<Void> submitResults(
            @Parameter(description = "Barcode identifying the lab test request", required = true)
            @PathVariable String barcode,
            @RequestBody ResultEntryDto payload) {

        laboratoryService.submitResults(barcode, payload);
        return ResponseEntity.ok().build();
    }
}
