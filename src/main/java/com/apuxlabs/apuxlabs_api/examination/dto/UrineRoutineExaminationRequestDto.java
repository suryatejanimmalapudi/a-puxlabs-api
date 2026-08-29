package com.apuxlabs.apuxlabs_api.examination.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UrineRoutineExaminationRequestDto {

    private LocalDateTime examinationDateTime;

    private String sampleType;

    private String colour;
    private String deposit;
    private String appearance;

    private Double ph;
    private Double specificGravity;

    private String quantity;

    private String urineProtein;
    private String bileSalt;
    private String urineGlucose;
    private String urineKetones;
    private String bilePigment;
    private String occultBlood;

    private String rbcs;
    private String pusCells;
    private String epithelialCells;
    private String crystals;
    private String casts;
    private String amorphousDeposit;
    private String bacteria;
    private String trichomonasVaginalis;
    private String yeastCells;

    private String remarks;

    private String pathologistName;
}