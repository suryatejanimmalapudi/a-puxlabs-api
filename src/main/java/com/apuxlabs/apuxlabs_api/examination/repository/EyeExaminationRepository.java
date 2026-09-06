package com.apuxlabs.apuxlabs_api.examination.repository;

import com.apuxlabs.apuxlabs_api.examination.entity.EyeExamination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EyeExaminationRepository
        extends JpaRepository<EyeExamination, Long>
{

    /**
     * Retrieves all eye examinations associated with a registration.
     *
     * @param registrationId registration ID
     * @return list of eye examinations
     */
    List<EyeExamination> findAllByRegistrationId(Long registrationId);
}