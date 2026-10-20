package com.apuxlabs.apuxlabs_api.testorder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pathologist")
@Getter
@Setter
@NoArgsConstructor
public class Pathologist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String designation;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "signature_image_path")
    private String signatureImagePath;

    @Column(name = "is_default", nullable = false)
    private boolean defaultPathologist;

    @Column(nullable = false)
    private boolean active;
}
