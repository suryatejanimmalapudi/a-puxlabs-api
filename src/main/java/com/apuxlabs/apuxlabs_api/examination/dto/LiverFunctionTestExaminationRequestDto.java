package com.apuxlabs.apuxlabs_api.examination.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LiverFunctionTestExaminationRequestDto {

    private LocalDateTime examinationDateTime;

    private String sampleType;

    private Double bilirubinTotal;
    private Double bilirubinDirect;
    private Double bilirubinIndirect;

    private Double sgpt;
    private Double sgot;
    private Double sgotSgptRatio;

    private Double alkalinePhosphatase;
    private Double gammaGlutamylTransferase;

    private Double totalProteins;
    private Double albumin;
    private Double globulin;
    private Double albuminGlobulinRatio;

    private String remarks;
    private String pathologistName;
}