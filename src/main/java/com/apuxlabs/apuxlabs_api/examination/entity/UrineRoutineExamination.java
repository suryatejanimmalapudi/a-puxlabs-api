package com.apuxlabs.apuxlabs_api.examination.entity;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "urine_routine_examination")
@Getter
@Setter
public class UrineRoutineExamination {

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

    private String sampleType;

    // Physical examination
    private String colour;
    private String deposit;
    private String appearance;

    private Double ph;
    private Double specificGravity;

    private String quantity;

    // Chemical examination
    private String urineProtein;
    private String bileSalt;
    private String urineGlucose;
    private String urineKetones;
    private String bilePigment;
    private String occultBlood;

    // Microscopic examination
    private String rbcs;
    private String pusCells;
    private String epithelialCells;
    private String crystals;
    private String casts;
    private String amorphousDeposit;
    private String bacteria;
    private String trichomonasVaginalis;
    private String yeastCells;

    @Column(length = 1000)
    private String remarks;

    private String pathologistName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}