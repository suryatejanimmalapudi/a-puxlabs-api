package com.apuxlabs.apuxlabs_api.examination.entity;


import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "physical_examination")
@Getter
@Setter
public class PhysicalExamination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Links this physical examination to the registration/person
     * for whom the examination was performed.
     *
     * Multiple physical examinations can belong to the same registration
     * because a person may undergo health examinations multiple times.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "registration_id",
            nullable = false
    )
    private Registration registration;

    private LocalDate examinationDate;

    private Double height;

    private Double weight;

    private Integer pulseRate;

    private String bloodPressure;

    private Double temperature;

    @Column(columnDefinition = "TEXT")
    private String presentComplaints;

    @Column(columnDefinition = "TEXT")
    private String pastHistory;

    @Column(columnDefinition = "TEXT")
    private String cardiovascularSystem;

    @Column(columnDefinition = "TEXT")
    private String respiratorySystem;

    @Column(columnDefinition = "TEXT")
    private String abdomen;

    @Column(columnDefinition = "TEXT")
    private String centralNervousSystem;

    @Column(columnDefinition = "TEXT")
    private String skinExamination;

    @Column(columnDefinition = "TEXT")
    private String epilepsy;

    @Column(columnDefinition = "TEXT")
    private String medicalCertificate;

    private String doctorName;

    private String doctorRegistrationNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}