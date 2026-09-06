package com.apuxlabs.apuxlabs_api.examination.service;

import com.apuxlabs.apuxlabs_api.examination.dto.UrineRoutineExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.UrineRoutineExaminationResponseDto;

import java.util.List;

public interface UrineRoutineExaminationService {

    /**
     * Creates a new urine routine examination
     * for an existing registration.
     *
     * @param registrationId registration ID
     * @param request urine routine examination details
     * @return created urine routine examination
     */
    UrineRoutineExaminationResponseDto createUrineRoutineExamination(
            Long registrationId,
            UrineRoutineExaminationRequestDto request
    );

    /**
     * Retrieves a urine routine examination by its ID.
     *
     * @param id urine routine examination ID
     * @return urine routine examination details
     */
    UrineRoutineExaminationResponseDto getUrineRoutineExaminationById(
            Long id
    );

    /**
     * Retrieves all urine routine examinations
     * associated with a registration.
     *
     * @param registrationId registration ID
     * @return list of urine routine examinations
     */
    List<UrineRoutineExaminationResponseDto>
    getUrineRoutineExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Updates an existing urine routine examination.
     *
     * @param id urine routine examination ID
     * @param request updated urine routine examination details
     * @return updated urine routine examination
     */
    UrineRoutineExaminationResponseDto updateUrineRoutineExamination(
            Long id,
            UrineRoutineExaminationRequestDto request
    );

    /**
     * Deletes a urine routine examination by its ID.
     *
     * @param id urine routine examination ID
     */
    void deleteUrineRoutineExamination(Long id);
}