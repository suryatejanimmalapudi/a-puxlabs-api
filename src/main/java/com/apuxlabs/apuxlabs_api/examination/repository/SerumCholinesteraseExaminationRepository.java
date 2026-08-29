package com.apuxlabs.apuxlabs_api.examination.repository;

import com.apuxlabs.apuxlabs_api.examination.entity.SerumCholinesteraseExamination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SerumCholinesteraseExaminationRepository
        extends JpaRepository<SerumCholinesteraseExamination, Long> {

    /**
     * Retrieves all serum cholinesterase examinations
     * belonging to a registration.
     *
     * @param registrationId registration ID
     * @return list of serum cholinesterase examinations
     */
    List<SerumCholinesteraseExamination> findAllByRegistrationId(
            Long registrationId
    );
}