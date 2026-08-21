package com.apuxlabs.apuxlabs_api.registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "registration_dispatch_method",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_registration_dispatch_method",
                        columnNames = {"registration_id", "dispatch_method"}
                )
        }
)
public class RegistrationDispatchMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    @Column(name = "dispatch_method", nullable = false, length = 30)
    private String dispatchMethod;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Getters and setters
}