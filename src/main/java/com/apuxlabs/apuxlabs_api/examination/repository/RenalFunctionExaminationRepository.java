package com.apuxlabs.apuxlabs_api.examination.repository;

import com.apuxlabs.apuxlabs_api.examination.entity.RenalFunctionExamination;
import com.apuxlabs.apuxlabs_api.examination.enums.RenalFunctionTestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RenalFunctionExaminationRepository
        extends JpaRepository<RenalFunctionExamination, Long> {

    /**
     * Retrieves all renal function examinations
     * belonging to a registration.
     *
     * @param registrationId registration ID
     * @return list of renal function examinations
     */
    List<RenalFunctionExamination> findAllByRegistrationId(
            Long registrationId
    );

    /**
     * Retrieves renal function examinations for a registration
     * filtered by test type such as UREA or CREATININE.
     *
     * @param registrationId registration ID
     * @param testType renal function test type
     * @return list of matching renal function examinations
     */
    List<RenalFunctionExamination> findAllByRegistrationIdAndTestType(
            Long registrationId,
            RenalFunctionTestType testType
    );
}