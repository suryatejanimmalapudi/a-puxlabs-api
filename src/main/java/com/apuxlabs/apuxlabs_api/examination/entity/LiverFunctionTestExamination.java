package com.apuxlabs.apuxlabs_api.examination.entity;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "liver_function_test_examination")
@Getter
@Setter
public class LiverFunctionTestExamination {

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

    // Bilirubin values
    private Double bilirubinTotal;
    private Double bilirubinDirect;
    private Double bilirubinIndirect;

    // Liver enzymes
    private Double sgpt;
    private Double sgot;
    private Double sgotSgptRatio;

    private Double alkalinePhosphatase;

    private Double gammaGlutamylTransferase;

    // Protein values
    private Double totalProteins;
    private Double albumin;
    private Double globulin;
    private Double albuminGlobulinRatio;

    @Column(length = 1000)
    private String remarks;

    private String pathologistName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}