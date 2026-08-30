package com.apuxlabs.apuxlabs_api.examination.entity;

import com.apuxlabs.apuxlabs_api.examination.enums.RenalFunctionTestType;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "renal_function_examination")
@Getter
@Setter
public class RenalFunctionExamination {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RenalFunctionTestType testType;

    @Column(nullable = false)
    private Double resultValue;

    private String sampleType;

    private String referenceRange;

    private String unit;

    @Column(length = 1000)
    private String remarks;

    private String pathologistName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}