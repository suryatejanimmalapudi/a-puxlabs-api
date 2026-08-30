package com.apuxlabs.apuxlabs_api.examination.repository;

import com.apuxlabs.apuxlabs_api.examination.entity.LiverFunctionTestExamination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiverFunctionTestExaminationRepository
        extends JpaRepository<LiverFunctionTestExamination, Long> {

    List<LiverFunctionTestExamination> findAllByRegistrationId(
            Long registrationId
    );
}