package com.apuxlabs.apuxlabs_api.testorder.entity;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lab_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Assuming you have a Registration entity. If not, you can just use 'private Long registrationId;'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    private String referringDoctorName;
    private BigDecimal registrationFee;
    private BigDecimal totalDiscount;
    private BigDecimal grossAmount;
    private BigDecimal netAmount;

    @Column(insertable = false, updatable = false)
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "labOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabTestRequest> testRequests = new ArrayList<>();
}