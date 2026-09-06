package com.apuxlabs.apuxlabs_api.examination.repository;

import com.apuxlabs.apuxlabs_api.examination.entity.PhysicalExamination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhysicalExaminationRepository
        extends JpaRepository<PhysicalExamination, Long>
{

    List<PhysicalExamination> findAllByRegistrationId(Long registrationId);
}