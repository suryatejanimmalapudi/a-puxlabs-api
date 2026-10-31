package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TestPackageItemRequestDto {
    private Long testMasterId;
    private BigDecimal packageTestPrice;
}
