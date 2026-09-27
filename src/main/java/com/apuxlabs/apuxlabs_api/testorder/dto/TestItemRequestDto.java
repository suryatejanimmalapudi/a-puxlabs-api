package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TestItemRequestDto {
    private Long testMasterId;
    private BigDecimal discountAmount; // e.g., 50.00 if giving a discount on this specific test
}