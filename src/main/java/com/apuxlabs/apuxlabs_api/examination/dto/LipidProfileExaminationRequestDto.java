package com.apuxlabs.apuxlabs_api.examination.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LipidProfileExaminationRequestDto {

    private LocalDateTime examinationDateTime;

    private String sampleType;

    private Double totalCholesterol;

    private Double triglycerides;

    private Double hdlCholesterol;

    private Double ldlCholesterol;

    private Double vldlCholesterol;

    private Double ldlHdlRatio;

    private Double totalCholesterolHdlRatio;

    private String remarks;

    private String pathologistName;
}