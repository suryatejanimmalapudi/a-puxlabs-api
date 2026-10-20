package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollectionOrderDto {
    private String uid; // Internal ID for React tracking
    private String id; // The Barcode or Order ID
    private Long patientId;
    private String patientName;
    private Integer age;
    private String gender;
    private String testName;
    private String code;
    private String department;
    private String sampleType; // Usually stored in test_master, or default to "Blood"
    private String container;  // Usually stored in test_master
    private String orderedBy;
    private String orderedTime;
    private String priority;
    private String status;
    private String collectedAt;
    private String collectedBy;
    private String barcode;
    private String rejectionReason;
}