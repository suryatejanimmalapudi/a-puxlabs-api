package com.apuxlabs.apuxlabs_api.examination.entity;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cbp_examination")
@Getter
@Setter
public class CbpExamination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "registration_id",
            nullable = false
    )
    private Registration registration;

    private LocalDateTime examinationDateTime;

    // Basic haematology
    private Double haemoglobin;
    private Integer totalLeukocyteCount;

    // Differential leukocyte count (%)
    private Double neutrophils;
    private Double lymphocytes;
    private Double eosinophils;
    private Double monocytes;
    private Double basophils;

    // Absolute leukocyte count (/cumm)
    private Integer absoluteNeutrophils;
    private Integer absoluteLymphocytes;
    private Integer absoluteEosinophils;
    private Integer absoluteMonocytes;

    // RBC indices
    private Double rbcCount;
    private Double hct;
    private Double mcv;
    private Double mch;
    private Double mchc;
    private Double rdwCv;
    private Double rdwSd;

    // Platelet indices
    private Integer plateletCount;
    private Double pct;
    private Double mpv;
    private Double pdw;
    private Double pLcr;
    private Double pLcc;

    // Optional laboratory interpretation
    @Column(length = 1000)
    private String remarks;

    private String pathologistName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}