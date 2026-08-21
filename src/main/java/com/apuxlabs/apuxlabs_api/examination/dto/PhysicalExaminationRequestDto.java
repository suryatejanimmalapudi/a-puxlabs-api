package com.apuxlabs.apuxlabs_api.examination.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PhysicalExaminationRequestDto {

    private LocalDate examinationDate;

    private Double height;

    private Double weight;

    private Integer pulseRate;

    private String bloodPressure;

    private Double temperature;

    private String presentComplaints;

    private String pastHistory;

    private String cardiovascularSystem;

    private String respiratorySystem;

    private String abdomen;

    private String centralNervousSystem;

    private String skinExamination;

    private String epilepsy;

    private String medicalCertificate;

    private String doctorName;

    private String doctorRegistrationNumber;
}