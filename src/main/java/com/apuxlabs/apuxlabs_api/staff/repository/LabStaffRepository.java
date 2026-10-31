package com.apuxlabs.apuxlabs_api.staff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apuxlabs.apuxlabs_api.staff.entity.LabStaff;

public interface LabStaffRepository extends JpaRepository<LabStaff, Long> {
    Optional<LabStaff> findByEmailIgnoreCaseAndActiveTrue(String email);
}
