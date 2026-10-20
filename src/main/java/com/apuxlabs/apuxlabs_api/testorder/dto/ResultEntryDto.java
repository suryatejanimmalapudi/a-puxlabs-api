package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ResultEntryDto {
    private Map<String, Object> results; // e.g. {"hemoglobin": 14.2, "wbc": 5500}
}