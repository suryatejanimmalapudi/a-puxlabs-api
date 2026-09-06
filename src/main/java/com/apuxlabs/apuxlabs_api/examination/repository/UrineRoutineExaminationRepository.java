package com.apuxlabs.apuxlabs_api.examination.repository;

import com.apuxlabs.apuxlabs_api.examination.entity.UrineRoutineExamination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrineRoutineExaminationRepository
        extends JpaRepository<UrineRoutineExamination, Long> {

    List<UrineRoutineExamination> findAllByRegistrationId(
            Long registrationId
    );
}