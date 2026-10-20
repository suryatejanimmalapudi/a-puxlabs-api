package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TestPackageRequestDto {
    private String code;
    private String name;
    private BigDecimal packagePrice;
    private List<TestPackageItemRequestDto> items;
}
