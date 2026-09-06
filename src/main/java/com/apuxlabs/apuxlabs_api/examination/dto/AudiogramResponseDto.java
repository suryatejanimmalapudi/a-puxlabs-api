package com.apuxlabs.apuxlabs_api.examination.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AudiogramResponseDto {

    private Long id;

    private Long registrationId;

    private LocalDate examinationDate;

    // Right ear thresholds in dB HL
    private Integer right125Hz;
    private Integer right250Hz;
    private Integer right500Hz;
    private Integer right1000Hz;
    private Integer right2000Hz;
    private Integer right4000Hz;
    private Integer right8000Hz;

    // Left ear thresholds in dB HL
    private Integer left125Hz;
    private Integer left250Hz;
    private Integer left500Hz;
    private Integer left1000Hz;
    private Integer left2000Hz;
    private Integer left4000Hz;
    private Integer left8000Hz;

    private String impression;

    private String audiologistName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}