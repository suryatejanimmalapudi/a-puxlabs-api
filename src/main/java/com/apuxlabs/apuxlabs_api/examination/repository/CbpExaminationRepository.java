package com.apuxlabs.apuxlabs_api.examination.repository;

import com.apuxlabs.apuxlabs_api.examination.entity.CbpExamination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CbpExaminationRepository
        extends JpaRepository<CbpExamination, Long> {

    List<CbpExamination> findAllByRegistrationId(
            Long registrationId
    );
}