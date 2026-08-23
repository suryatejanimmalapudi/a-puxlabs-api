package com.apuxlabs.apuxlabs_api.cardiology.ecg.repository;

import com.apuxlabs.apuxlabs_api.cardiology.ecg.entity.EcgRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EcgRecordRepository extends JpaRepository<EcgRecord, UUID> {

    // Spring Data JPA automatically implements this query for you
    List<EcgRecord> findByPatientId(String patientId);
}