package com.apuxlabs.apuxlabs_api.examination.entity;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "serum_cholinesterase_examination")
@Getter
@Setter
public class SerumCholinesteraseExamination {

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

    /**
     * Serum cholinesterase result.
     * Example: 8883.15
     */
    @Column(nullable = false)
    private Double resultValue;

    /**
     * Laboratory method used for the test.
     * Example: Butyrylthio Choline Method
     */
    private String method;

    /**
     * Sample used for the examination.
     * Kept optional because some reports may not specify it.
     */
    private String sampleType;

    /**
     * Laboratory reference range.
     * Example: 4000 - 11500
     */
    private String referenceRange;

    /**
     * Measurement unit.
     * Example: U/L
     */
    private String unit;

    @Column(length = 1000)
    private String remarks;

    private String pathologistName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}