package com.apuxlabs.apuxlabs_api.examination.dto;

import com.apuxlabs.apuxlabs_api.examination.enums.BloodGlucoseTestType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BloodGlucoseExaminationResponseDto {

    private Long id;

    private Long registrationId;

    private LocalDateTime examinationDateTime;

    private BloodGlucoseTestType testType;

    private Double glucoseValue;

    private String sampleType;

    private String referenceRange;

    private String unit;

    private String remarks;

    private String pathologistName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}