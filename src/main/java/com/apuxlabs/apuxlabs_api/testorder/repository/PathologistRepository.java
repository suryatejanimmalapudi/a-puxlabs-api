package com.apuxlabs.apuxlabs_api.testorder.repository;

import com.apuxlabs.apuxlabs_api.testorder.entity.Pathologist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PathologistRepository extends JpaRepository<Pathologist, Long> {
    List<Pathologist> findByActiveTrueOrderByNameAsc();
    Optional<Pathologist> findFirstByActiveTrueAndDefaultPathologistTrue();
}
