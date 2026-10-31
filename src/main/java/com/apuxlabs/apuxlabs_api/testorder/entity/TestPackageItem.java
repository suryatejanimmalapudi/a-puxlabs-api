package com.apuxlabs.apuxlabs_api.testorder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "test_package_item", uniqueConstraints = @UniqueConstraint(columnNames = {"package_id", "test_master_id"}))
@Getter
@Setter
@NoArgsConstructor
public class TestPackageItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "package_id", nullable = false)
    private TestPackage testPackage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_master_id", nullable = false)
    private TestMaster testMaster;

    @Column(name = "package_test_price", nullable = false)
    private BigDecimal packageTestPrice;
}
