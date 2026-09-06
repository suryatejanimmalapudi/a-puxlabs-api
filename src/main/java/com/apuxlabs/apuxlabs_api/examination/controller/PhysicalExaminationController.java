package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.PhysicalExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.PhysicalExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.PhysicalExaminationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/physical-examinations")
public class PhysicalExaminationController {

    private final PhysicalExaminationService physicalExaminationService;

    public PhysicalExaminationController(
            PhysicalExaminationService physicalExaminationService) {
        this.physicalExaminationService = physicalExaminationService;
    }

    @PostMapping("/registration/{registrationId}")
    public ResponseEntity<PhysicalExaminationResponseDto>
    createPhysicalExamination(
            @PathVariable Long registrationId,
            @RequestBody PhysicalExaminationRequestDto request) {

        PhysicalExaminationResponseDto response =
                physicalExaminationService.createPhysicalExamination(
                        registrationId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicalExaminationResponseDto>
    getPhysicalExaminationById(
            @PathVariable Long id) {

        PhysicalExaminationResponseDto response =
                physicalExaminationService.getPhysicalExaminationById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<List<PhysicalExaminationResponseDto>>
    getPhysicalExaminationsByRegistrationId(
            @PathVariable Long registrationId) {

        List<PhysicalExaminationResponseDto> response =
                physicalExaminationService
                        .getPhysicalExaminationsByRegistrationId(
                                registrationId
                        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhysicalExaminationResponseDto>
    updatePhysicalExamination(
            @PathVariable Long id,
            @RequestBody PhysicalExaminationRequestDto request) {

        PhysicalExaminationResponseDto response =
                physicalExaminationService.updatePhysicalExamination(
                        id,
                        request
                );

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhysicalExamination(
            @PathVariable Long id) {

        physicalExaminationService.deletePhysicalExamination(id);

        return ResponseEntity.noContent().build();
    }
}