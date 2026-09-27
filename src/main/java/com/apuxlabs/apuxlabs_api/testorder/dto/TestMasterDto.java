package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TestMasterDto {
    private Long id;
    private String code;
    private String name;
    private String department;
    private BigDecimal defaultPrice;
}