package com.apuxlabs.apuxlabs_api.examination.service;

import com.apuxlabs.apuxlabs_api.examination.dto.SerumCholinesteraseExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.SerumCholinesteraseExaminationResponseDto;

import java.util.List;

public interface SerumCholinesteraseExaminationService {

    /**
     * Creates a new serum cholinesterase examination
     * for an existing registration.
     *
     * @param registrationId registration ID
     * @param request serum cholinesterase examination details
     * @return created serum cholinesterase examination
     */
    SerumCholinesteraseExaminationResponseDto createSerumCholinesteraseExamination(
            Long registrationId,
            SerumCholinesteraseExaminationRequestDto request
    );

    /**
     * Retrieves a serum cholinesterase examination by its ID.
     *
     * @param id serum cholinesterase examination ID
     * @return serum cholinesterase examination details
     */
    SerumCholinesteraseExaminationResponseDto getSerumCholinesteraseExaminationById(
            Long id
    );

    /**
     * Retrieves all serum cholinesterase examinations
     * belonging to a registration.
     *
     * @param registrationId registration ID
     * @return list of serum cholinesterase examinations
     */
    List<SerumCholinesteraseExaminationResponseDto>
    getSerumCholinesteraseExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Updates an existing serum cholinesterase examination.
     *
     * @param id serum cholinesterase examination ID
     * @param request updated serum cholinesterase examination details
     * @return updated serum cholinesterase examination
     */
    SerumCholinesteraseExaminationResponseDto updateSerumCholinesteraseExamination(
            Long id,
            SerumCholinesteraseExaminationRequestDto request
    );

    /**
     * Deletes a serum cholinesterase examination by its ID.
     *
     * @param id serum cholinesterase examination ID
     */
    void deleteSerumCholinesteraseExamination(
            Long id
    );
}