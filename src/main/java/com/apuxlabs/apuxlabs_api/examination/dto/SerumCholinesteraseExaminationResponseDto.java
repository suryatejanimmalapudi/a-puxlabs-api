package com.apuxlabs.apuxlabs_api.examination.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SerumCholinesteraseExaminationResponseDto {

    private Long id;

    private Long registrationId;

    private LocalDateTime examinationDateTime;

    private Double resultValue;

    private String method;

    private String sampleType;

    private String referenceRange;

    private String unit;

    private String remarks;

    private String pathologistName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}