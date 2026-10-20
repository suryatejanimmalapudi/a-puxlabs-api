package com.apuxlabs.apuxlabs_api.testorder.controller;

import com.apuxlabs.apuxlabs_api.testorder.dto.TestPackageRequestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.TestPackageResponseDto;
import com.apuxlabs.apuxlabs_api.testorder.service.TestPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test-packages")
@RequiredArgsConstructor
public class TestPackageController {
    private final TestPackageService testPackageService;

    @GetMapping
    public List<TestPackageResponseDto> getActivePackages() {
        return testPackageService.getActivePackages();
    }

    @PostMapping
    public ResponseEntity<TestPackageResponseDto> createPackage(@RequestBody TestPackageRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testPackageService.createPackage(request));
    }

    @PutMapping("/{id}")
    public TestPackageResponseDto updatePackage(@PathVariable Long id, @RequestBody TestPackageRequestDto request) {
        return testPackageService.updatePackage(id, request);
    }
}
