package com.apuxlabs.apuxlabs_api.examination.entity;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "lipid_profile_examination")
@Getter
@Setter
public class LipidProfileExamination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    private LocalDateTime examinationDateTime;

    private String sampleType;

    private Double totalCholesterol;

    private Double triglycerides;

    private Double hdlCholesterol;

    private Double ldlCholesterol;

    private Double vldlCholesterol;

    private Double ldlHdlRatio;

    private Double totalCholesterolHdlRatio;

    private String remarks;

    private String pathologistName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}