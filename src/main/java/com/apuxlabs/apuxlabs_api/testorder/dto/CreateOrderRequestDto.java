package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderRequestDto {
    private Long registrationId;
    private String referringDoctorName;
    private List<TestItemRequestDto> tests;
}
