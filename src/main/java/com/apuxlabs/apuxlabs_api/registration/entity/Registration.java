package com.apuxlabs.apuxlabs_api.registration.entity;

import com.apuxlabs.apuxlabs_api.examination.entity.Audiogram;
import com.apuxlabs.apuxlabs_api.examination.entity.EyeExamination;
import com.apuxlabs.apuxlabs_api.examination.entity.PhysicalExamination;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "registration")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String designation;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 20)
    private String gender;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String email;

    @Column(name = "referring_doctor_id")
    private Long referringDoctorId;

    @Column(name = "rate_list_id")
    private Long rateListId;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "registration",
            cascade = CascadeType.ALL,
            orphanRemoval = true
              )
    private List<RegistrationDispatchMethod> dispatchMethods = new ArrayList<>();

    /**
     * Physical examinations performed for this registration.
     *
     * A registration can have multiple physical examinations over time,
     * such as annual health checkups.
     */
    @OneToMany(
            mappedBy = "registration",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PhysicalExamination> physicalExaminations = new ArrayList<>();

    @OneToMany(
            mappedBy = "registration",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EyeExamination> eyeExaminations = new ArrayList<>();

    /**
     * Audiograms performed for this registration over time.
     */
    @OneToMany(
            mappedBy = "registration",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Audiogram> audiograms = new ArrayList<>();
}