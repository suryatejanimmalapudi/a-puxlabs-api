package com.apuxlabs.apuxlabs_api.examination.entity;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "eye_examination")
@Getter
@Setter
public class EyeExamination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Links this eye examination to the registered person.
     * <p>
     * One registration can have multiple eye examinations over time.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    private LocalDate examinationDate;

    // Right eye - distance vision
    private Double rightDistanceSph;
    private Double rightDistanceCyl;
    private Integer rightDistanceAxis;
    private String rightDistanceVa;

    // Left eye - distance vision
    private Double leftDistanceSph;
    private Double leftDistanceCyl;
    private Integer leftDistanceAxis;
    private String leftDistanceVa;

    // Right eye - near vision
    private Double rightNearSph;
    private Double rightNearCyl;
    private Integer rightNearAxis;
    private String rightNearVa;

    // Left eye - near vision
    private Double leftNearSph;
    private Double leftNearCyl;
    private Integer leftNearAxis;
    private String leftNearVa;

    private String colourVision;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    private String optometristName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Eye examinations performed for this registration.
     *
     * A registration can have multiple eye examinations over time,
     * such as annual or follow-up eye checkups.
     */
}