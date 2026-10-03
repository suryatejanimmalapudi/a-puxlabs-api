package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.Data;

@Data
public class CollectSampleRequestDto {
    private String collectedBy;
    private String notes; // Can be saved to a new notes field or appended to rejection_reason
}