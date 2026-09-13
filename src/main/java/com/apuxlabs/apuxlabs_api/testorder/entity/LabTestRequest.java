package com.apuxlabs.apuxlabs_api.testorder.entity;

import com.apuxlabs.apuxlabs_api.testorder.enums.TestPriority;
import com.apuxlabs.apuxlabs_api.testorder.enums.TestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "lab_test_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabTestRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private LabOrder labOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_master_id", nullable = false)
    private TestMaster testMaster;

    @Column(unique = true)
    private String barcode;

    private BigDecimal basePrice;
    private BigDecimal discountAmount;
    private BigDecimal priceCharged;

    @Enumerated(EnumType.STRING)
    private TestPriority priority;

    @Enumerated(EnumType.STRING)
    private TestStatus status;


    // Hibernate 6/7 native JSONB mapping
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> resultData;

    private LocalDateTime completedAt;
}