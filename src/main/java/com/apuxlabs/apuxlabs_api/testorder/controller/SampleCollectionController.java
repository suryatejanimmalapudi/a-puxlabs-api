package com.apuxlabs.apuxlabs_api.testorder.controller;

import com.apuxlabs.apuxlabs_api.testorder.dto.CollectSampleRequestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.CollectionOrderDto;
import com.apuxlabs.apuxlabs_api.testorder.service.SampleCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/collections")
@RequiredArgsConstructor
public class SampleCollectionController {

    private final SampleCollectionService collectionService;

    @GetMapping
    public ResponseEntity<List<CollectionOrderDto>> getCollectionOrders() {
        return ResponseEntity.ok(collectionService.getAllCollectionOrders());
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