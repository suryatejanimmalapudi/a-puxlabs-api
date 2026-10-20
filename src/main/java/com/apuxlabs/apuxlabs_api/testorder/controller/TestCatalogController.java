package com.apuxlabs.apuxlabs_api.testorder.controller;

import com.apuxlabs.apuxlabs_api.testorder.dto.TestMasterDto;
import com.apuxlabs.apuxlabs_api.testorder.service.TestCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test-catalog")
@RequiredArgsConstructor
@Tag(name = "Test Catalog", description = "Endpoints for retrieving available lab tests and pricing")
public class TestCatalogController {

    private final TestCatalogService testCatalogService;

    @Operation(
            summary = "Get all available tests",
            description = "Retrieves the full list of tests including their base prices to populate frontend dropdowns."
    )
    @GetMapping
    public ResponseEntity<List<TestMasterDto>> getAllTests() {
        return ResponseEntity.ok(testCatalogService.getAllTests());
    }
}