package com.apuxlabs.apuxlabs_api.examination.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class EyeExaminationResponseDto {

    private Long id;

    private Long registrationId;

    private LocalDate examinationDate;

    private Double rightDistanceSph;
    private Double rightDistanceCyl;
    private Integer rightDistanceAxis;
    private String rightDistanceVa;

    private Double leftDistanceSph;
    private Double leftDistanceCyl;
    private Integer leftDistanceAxis;
    private String leftDistanceVa;

    private Double rightNearSph;
    private Double rightNearCyl;
    private Integer rightNearAxis;
    private String rightNearVa;

    private Double leftNearSph;
    private Double leftNearCyl;
    private Integer leftNearAxis;
    private String leftNearVa;

    private String colourVision;

    private String remarks;

    private String optometristName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}