package com.apuxlabs.apuxlabs_api.testorder.controller;

import com.apuxlabs.apuxlabs_api.testorder.dto.PendingTestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.ResultEntryDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.ReportDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.VerifyReportRequestDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
    public ResponseEntity<?> getPendingWorklist(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
                                                 @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "") String department,
                                                 @RequestParam(defaultValue = "") String priority) {
        if (page < 0 || size < 1 || size > 100) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(laboratoryService.searchPendingWorklist(search, department, priority, PageRequest.of(page, size, Sort.by("collectedAt").ascending())));
    }

    @GetMapping("/integration/targets")
    public ResponseEntity<List<PendingTestDto>> getIntegrationTargets(@RequestParam String barcode) {
        return ResponseEntity.ok(laboratoryService.findIntegrationTargets(barcode));
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
    @PutMapping("/{requestId}/results")
    public ResponseEntity<Void> submitResults(
            @Parameter(description = "Internal test request ID", required = true)
            @PathVariable Long requestId,
            @RequestBody ResultEntryDto payload) {

        laboratoryService.submitResults(requestId, payload);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reports")
    public ResponseEntity<?> getReports(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "") String status) {
        if (page < 0 || size < 1 || size > 100) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(laboratoryService.searchReports(search, status, PageRequest.of(page, size, Sort.by("completedAt").descending())));
    }

    @GetMapping("/reports/patient/{patientId}")
    public ResponseEntity<List<ReportDto>> getPatientReports(@PathVariable Long patientId) {
        return ResponseEntity.ok(laboratoryService.getPatientReports(patientId));
    }

    @PutMapping("/reports/{id}/verify")
    public ResponseEntity<Void> verifyReport(@PathVariable Long id, @RequestBody VerifyReportRequestDto request) {
        laboratoryService.verifyReport(id, request.getPathologistId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reports/{id}/deliver")
    public ResponseEntity<Void> deliverReport(@PathVariable Long id) {
        laboratoryService.deliverReport(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reports/{id}/reject")
    public ResponseEntity<Void> rejectReport(@PathVariable Long id) {
        laboratoryService.rejectReport(id);
        return ResponseEntity.ok().build();
    }
}
