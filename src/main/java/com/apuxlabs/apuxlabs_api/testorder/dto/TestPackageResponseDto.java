package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class TestPackageResponseDto {
    private Long id;
    private String code;
    private String name;
    private BigDecimal packagePrice;
    private boolean active;
    private List<TestPackageItemResponseDto> items;
}
