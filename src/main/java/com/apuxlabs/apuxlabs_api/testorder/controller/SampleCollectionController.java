package com.apuxlabs.apuxlabs_api.testorder.controller;

import com.apuxlabs.apuxlabs_api.testorder.dto.CollectSampleRequestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.CollectionOrderDto;
import com.apuxlabs.apuxlabs_api.testorder.service.SampleCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/v1/collections")
@RequiredArgsConstructor
public class SampleCollectionController {

    private final SampleCollectionService collectionService;

    @GetMapping
    public ResponseEntity<?> getCollectionOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
                                                  @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "") String status) {
        if (page < 0 || size < 1 || size > 100) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(collectionService.searchCollectionOrders(search, status, PageRequest.of(page, size, Sort.by("priority").descending())));
    }

    @PutMapping("/{barcode}/collect")
    public ResponseEntity<Void> collectSample(@PathVariable String barcode, @RequestBody CollectSampleRequestDto request) {
        collectionService.collectSample(barcode, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{barcode}/reject")
    public ResponseEntity<Void> rejectSample(@PathVariable String barcode, @RequestBody Map<String, String> payload) {
        collectionService.rejectSample(barcode, payload.get("reason"), payload.get("notes"));
        return ResponseEntity.ok().build();
    }
}
