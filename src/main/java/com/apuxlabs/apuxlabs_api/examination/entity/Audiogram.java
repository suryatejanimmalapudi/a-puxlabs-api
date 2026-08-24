package com.apuxlabs.apuxlabs_api.examination.entity;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "audiogram")
@Getter
@Setter
public class Audiogram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Registration/person to whom this audiogram belongs.
     * One registration can have multiple audiograms over time.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    @Column(name = "examination_date")
    private LocalDate examinationDate;

    // =========================
    // Right Ear
    // =========================

    @Column(name = "right_125_hz")
    private Integer right125Hz;

    @Column(name = "right_250_hz")
    private Integer right250Hz;

    @Column(name = "right_500_hz")
    private Integer right500Hz;

    @Column(name = "right_1000_hz")
    private Integer right1000Hz;

    @Column(name = "right_2000_hz")
    private Integer right2000Hz;

    @Column(name = "right_4000_hz")
    private Integer right4000Hz;

    @Column(name = "right_8000_hz")
    private Integer right8000Hz;

    // =========================
    // Left Ear
    // =========================

    @Column(name = "left_125_hz")
    private Integer left125Hz;

    @Column(name = "left_250_hz")
    private Integer left250Hz;

    @Column(name = "left_500_hz")
    private Integer left500Hz;

    @Column(name = "left_1000_hz")
    private Integer left1000Hz;

    @Column(name = "left_2000_hz")
    private Integer left2000Hz;

    @Column(name = "left_4000_hz")
    private Integer left4000Hz;

    @Column(name = "left_8000_hz")
    private Integer left8000Hz;

    /**
     * Overall interpretation/result of the audiogram.
     */
    @Column(name = "impression", columnDefinition = "TEXT")
    private String impression;

    @Column(name = "audiologist_name")
    private String audiologistName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}