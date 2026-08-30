package com.apuxlabs.apuxlabs_api.examination.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CbpExaminationRequestDto {

    private LocalDateTime examinationDateTime;

    private Double haemoglobin;
    private Integer totalLeukocyteCount;

    private Double neutrophils;
    private Double lymphocytes;
    private Double eosinophils;
    private Double monocytes;
    private Double basophils;

    private Integer absoluteNeutrophils;
    private Integer absoluteLymphocytes;
    private Integer absoluteEosinophils;
    private Integer absoluteMonocytes;

    private Double rbcCount;
    private Double hct;
    private Double mcv;
    private Double mch;
    private Double mchc;
    private Double rdwCv;
    private Double rdwSd;

    private Integer plateletCount;
    private Double pct;
    private Double mpv;
    private Double pdw;
    private Double pLcr;
    private Double pLcc;

    private String remarks;
    private String pathologistName;
}