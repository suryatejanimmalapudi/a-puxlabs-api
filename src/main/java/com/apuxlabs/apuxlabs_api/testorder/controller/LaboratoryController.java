package com.apuxlabs.apuxlabs_api.testorder.controller;

import com.apuxlabs.apuxlabs_api.testorder.dto.PendingTestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.ResultEntryDto;
import com.apuxlabs.apuxlabs_api.testorder.service.LaboratoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/worklist")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LaboratoryController {

    private final LaboratoryService laboratoryService;

    @GetMapping("/pending")
    public ResponseEntity<List<PendingTestDto>> getPendingWorklist() {
        List<PendingTestDto> dtos = laboratoryService.getPendingWorklist();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{barcode}/results")
    public ResponseEntity<Void> submitResults(
            @PathVariable String barcode,
            @RequestBody ResultEntryDto payload) {

        laboratoryService.submitResults(barcode, payload);
        return ResponseEntity.ok().build();
    }
}