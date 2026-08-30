package com.apuxlabs.apuxlabs_api.examination.service;

import com.apuxlabs.apuxlabs_api.examination.dto.BloodGlucoseExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.BloodGlucoseExaminationResponseDto;

import java.util.List;

public interface BloodGlucoseExaminationService {

    /**
     * Creates a new blood glucose examination for an existing registration.
     *
     * @param registrationId registration ID
     * @param request blood glucose examination details
     * @return created blood glucose examination
     */
    BloodGlucoseExaminationResponseDto createBloodGlucoseExamination(
            Long registrationId,
            BloodGlucoseExaminationRequestDto request
    );

    /**
     * Retrieves a blood glucose examination by its ID.
     *
     * @param id blood glucose examination ID
     * @return blood glucose examination details
     */
    BloodGlucoseExaminationResponseDto getBloodGlucoseExaminationById(
            Long id
    );
    /**
     * Retrieves all FBS examinations belonging to a registration.
     *
     * @param registrationId registration ID
     * @return list of FBS examinations
     */
    List<BloodGlucoseExaminationResponseDto> getFbsExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Retrieves all PLBS examinations belonging to a registration.
     *
     * @param registrationId registration ID
     * @return list of PLBS examinations
     */
    List<BloodGlucoseExaminationResponseDto> getPlbsExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Retrieves all blood glucose examinations belonging to a registration.
     *
     * @param registrationId registration ID
     * @return list of blood glucose examinations
     */
    List<BloodGlucoseExaminationResponseDto>
    getBloodGlucoseExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Updates an existing blood glucose examination.
     *
     * @param id blood glucose examination ID
     * @param request updated blood glucose examination details
     * @return updated blood glucose examination
     */
    BloodGlucoseExaminationResponseDto updateBloodGlucoseExamination(
            Long id,
            BloodGlucoseExaminationRequestDto request
    );

    /**
     * Deletes a blood glucose examination by its ID.
     *
     * @param id blood glucose examination ID
     */
    void deleteBloodGlucoseExamination(
            Long id
    );
}