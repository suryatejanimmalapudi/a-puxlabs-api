package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.Data;

@Data
public class PendingTestDto {
    private String id; // Internal test-request ID
    private String barcode; // Shared registration/visit barcode
    private Long patientId;
    private String uhid;
    private String patientName;
    private String gender;
    private Integer age;
    private String testName;
    private String code;
    private String department;
    private boolean fileUploadAllowed;
    private String priority;
    private String timeCollected;
}
