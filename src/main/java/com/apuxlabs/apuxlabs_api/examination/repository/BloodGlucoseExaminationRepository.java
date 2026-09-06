package com.apuxlabs.apuxlabs_api.examination.repository;

import com.apuxlabs.apuxlabs_api.examination.entity.BloodGlucoseExamination;
import com.apuxlabs.apuxlabs_api.examination.enums.BloodGlucoseTestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloodGlucoseExaminationRepository
        extends JpaRepository<BloodGlucoseExamination, Long> {

    List<BloodGlucoseExamination> findAllByRegistrationId(
            Long registrationId
    );

    List<BloodGlucoseExamination> findAllByRegistrationIdAndTestType(
            Long registrationId,
            BloodGlucoseTestType testType
    );
}