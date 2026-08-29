package com.apuxlabs.apuxlabs_api.examination.entity;

import com.apuxlabs.apuxlabs_api.examination.enums.BloodGlucoseTestType;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "blood_glucose_examination")
@Getter
@Setter
public class BloodGlucoseExamination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Patient/registration associated with this blood glucose examination.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "registration_id",
            nullable = false
    )
    private Registration registration;

    /**
     * Date and time when the examination was performed.
     */
    private LocalDateTime examinationDateTime;

    /**
     * Type of glucose examination:
     * FBS  - Fasting Blood Sugar
     * PLBS - Post-Prandial Blood Sugar
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BloodGlucoseTestType testType;

    /**
     * Plasma glucose result.
     * Example: 108.92 mg/dl.
     */
    @Column(nullable = false)
    private Double glucoseValue;

    /**
     * Sample used for testing.
     * Example: Fluoride plasma.
     */
    private String sampleType;

    /**
     * Reference range displayed by the laboratory.
     * Examples:
     * FBS  -> 70 - 110
     * PLBS -> 70 - 160
     */
    private String referenceRange;

    /**
     * Unit of measurement.
     * Example: mg/dl.
     */
    private String unit;

    /**
     * Optional laboratory interpretation or remarks.
     */
    @Column(length = 1000)
    private String remarks;

    private String pathologistName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}