package com.apuxlabs.apuxlabs_api.testorder.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReportDto {
    private Long id;
    private String reportNumber;
    private Long patientId;
    private String patientName;
    private Integer age;
    private String gender;
    private String testName;
    private String testCode;
    private String department;
    private String orderedBy;
    private String collectedAt;
    private String reportedAt;
    private String verifiedBy;
    private String verifiedAt;
    private String status;
    private List<ResultDto> results;

    @Data
    public static class ResultDto {
        private String label;
        private String value;
        private String unit;
        private String referenceRange;
    }
}
