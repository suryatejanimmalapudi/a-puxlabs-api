package com.apuxlabs.apuxlabs_api.testorder.controller;

import com.apuxlabs.apuxlabs_api.testorder.dto.CreateOrderRequestDto;
import com.apuxlabs.apuxlabs_api.testorder.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "Endpoints for front-desk order creation and billing")
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Create a new lab order",
            description = "Creates a parent lab order and its associated test requests, generating unique barcodes for each test tube."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully")
    })
    @PostMapping
    public ResponseEntity<Map<String, Long>> createOrder(@RequestBody CreateOrderRequestDto request) {
        Long orderId = orderService.createOrder(request);

        return ResponseEntity.ok(Map.of("orderId", orderId));
    }
}