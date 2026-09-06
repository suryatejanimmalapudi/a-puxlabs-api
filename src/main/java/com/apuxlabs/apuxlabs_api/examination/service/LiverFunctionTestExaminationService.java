package com.apuxlabs.apuxlabs_api.examination.service;

import com.apuxlabs.apuxlabs_api.examination.dto.LiverFunctionTestExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.LiverFunctionTestExaminationResponseDto;

import java.util.List;

public interface LiverFunctionTestExaminationService {

    /**
     * Creates a new liver function test examination
     * for an existing registration.
     *
     * @param registrationId registration ID
     * @param request liver function test examination details
     * @return created liver function test examination
     */
    LiverFunctionTestExaminationResponseDto createLiverFunctionTestExamination(
            Long registrationId,
            LiverFunctionTestExaminationRequestDto request
    );

    /**
     * Retrieves a liver function test examination by its ID.
     *
     * @param id liver function test examination ID
     * @return liver function test examination details
     */
    LiverFunctionTestExaminationResponseDto getLiverFunctionTestExaminationById(
            Long id
    );

    /**
     * Retrieves all liver function test examinations
     * belonging to a registration.
     *
     * @param registrationId registration ID
     * @return list of liver function test examinations
     */
    List<LiverFunctionTestExaminationResponseDto>
    getLiverFunctionTestExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Updates an existing liver function test examination.
     *
     * @param id liver function test examination ID
     * @param request updated liver function test examination details
     * @return updated liver function test examination
     */
    LiverFunctionTestExaminationResponseDto updateLiverFunctionTestExamination(
            Long id,
            LiverFunctionTestExaminationRequestDto request
    );

    /**
     * Deletes a liver function test examination by its ID.
     *
     * @param id liver function test examination ID
     */
    void deleteLiverFunctionTestExamination(Long id);
}