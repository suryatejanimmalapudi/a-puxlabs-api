package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TestPackageItemResponseDto {
    private Long id;
    private Long testMasterId;
    private String code;
    private String name;
    private BigDecimal packageTestPrice;
}
