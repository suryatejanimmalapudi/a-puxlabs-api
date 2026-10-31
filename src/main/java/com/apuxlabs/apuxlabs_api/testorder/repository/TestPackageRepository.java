package com.apuxlabs.apuxlabs_api.testorder.repository;

import com.apuxlabs.apuxlabs_api.testorder.entity.TestPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestPackageRepository extends JpaRepository<TestPackage, Long> {
    List<TestPackage> findByActiveTrueOrderByNameAsc();
}
