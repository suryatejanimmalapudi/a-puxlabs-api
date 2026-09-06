package com.apuxlabs.apuxlabs_api.examination.service;

import com.apuxlabs.apuxlabs_api.examination.dto.EyeExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.EyeExaminationResponseDto;

import java.util.List;

public interface EyeExaminationService {

    /**
     * Creates an eye examination for a registration.
     *
     * @param registrationId registration ID
     * @param request eye examination details
     * @return created eye examination
     */
    EyeExaminationResponseDto createEyeExamination(
            Long registrationId,
            EyeExaminationRequestDto request
    );

    /**
     * Retrieves an eye examination by its ID.
     *
     * @param id eye examination ID
     * @return eye examination details
     */
    EyeExaminationResponseDto getEyeExaminationById(Long id);

    /**
     * Retrieves all eye examinations for a registration.
     *
     * @param registrationId registration ID
     * @return list of eye examinations
     */
    List<EyeExaminationResponseDto> getEyeExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Updates an existing eye examination.
     *
     * @param id eye examination ID
     * @param request updated eye examination details
     * @return updated eye examination
     */
    EyeExaminationResponseDto updateEyeExamination(
            Long id,
            EyeExaminationRequestDto request
    );

    /**
     * Deletes an eye examination by ID.
     *
     * @param id eye examination ID
     */
    void deleteEyeExamination(Long id);
}