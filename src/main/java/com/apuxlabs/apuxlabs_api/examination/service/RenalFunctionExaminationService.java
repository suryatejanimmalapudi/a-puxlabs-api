package com.apuxlabs.apuxlabs_api.examination.service;

import com.apuxlabs.apuxlabs_api.examination.dto.RenalFunctionExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.RenalFunctionExaminationResponseDto;

import java.util.List;

public interface RenalFunctionExaminationService {

    /**
     * Creates a new renal function examination
     * for an existing registration.
     *
     * @param registrationId registration ID
     * @param request renal function examination details
     * @return created renal function examination
     */
    RenalFunctionExaminationResponseDto createRenalFunctionExamination(
            Long registrationId,
            RenalFunctionExaminationRequestDto request
    );

    /**
     * Retrieves a renal function examination by its ID.
     *
     * @param id renal function examination ID
     * @return renal function examination details
     */
    RenalFunctionExaminationResponseDto getRenalFunctionExaminationById(
            Long id
    );

    /**
     * Retrieves all renal function examinations
     * belonging to a registration.
     *
     * @param registrationId registration ID
     * @return list of renal function examinations
     */
    List<RenalFunctionExaminationResponseDto>
    getRenalFunctionExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Retrieves all Urea examinations
     * belonging to a registration.
     *
     * @param registrationId registration ID
     * @return list of Urea examinations
     */
    List<RenalFunctionExaminationResponseDto>
    getUreaExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Retrieves all Creatinine examinations
     * belonging to a registration.
     *
     * @param registrationId registration ID
     * @return list of Creatinine examinations
     */
    List<RenalFunctionExaminationResponseDto>
    getCreatinineExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Updates an existing renal function examination.
     *
     * @param id renal function examination ID
     * @param request updated renal function examination details
     * @return updated renal function examination
     */
    RenalFunctionExaminationResponseDto updateRenalFunctionExamination(
            Long id,
            RenalFunctionExaminationRequestDto request
    );

    /**
     * Deletes a renal function examination by its ID.
     *
     * @param id renal function examination ID
     */
    void deleteRenalFunctionExamination(
            Long id
    );
}