package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.Data;

@Data
public class PendingTestDto {
    private String id; // Barcode
    private Long patientId;
    private String patientName;
    private String gender;
    private Integer age;
    private String testName;
    private String code;
    private String department;
    private String priority;
    private String timeCollected;
}