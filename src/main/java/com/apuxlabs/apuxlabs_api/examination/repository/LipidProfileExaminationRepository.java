package com.apuxlabs.apuxlabs_api.examination.repository;

import com.apuxlabs.apuxlabs_api.examination.entity.LipidProfileExamination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LipidProfileExaminationRepository
        extends JpaRepository<LipidProfileExamination, Long> {

    List<LipidProfileExamination> findAllByRegistrationId(
            Long registrationId
    );
}