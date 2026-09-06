package com.apuxlabs.apuxlabs_api.examination.service;

import com.apuxlabs.apuxlabs_api.examination.dto.CbpExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.CbpExaminationResponseDto;

import java.util.List;

public interface CbpExaminationService {

    /**
     * Creates a new CBP examination for an existing registration.
     *
     * @param registrationId registration ID
     * @param request CBP examination details
     * @return created CBP examination
     */
    CbpExaminationResponseDto createCbpExamination(
            Long registrationId,
            CbpExaminationRequestDto request
    );

    /**
     * Retrieves a CBP examination by its ID.
     *
     * @param id CBP examination ID
     * @return CBP examination details
     */
    CbpExaminationResponseDto getCbpExaminationById(Long id);

    /**
     * Retrieves all CBP examinations belonging to a registration.
     *
     * @param registrationId registration ID
     * @return list of CBP examinations
     */
    List<CbpExaminationResponseDto> getCbpExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Updates an existing CBP examination.
     *
     * @param id CBP examination ID
     * @param request updated CBP examination details
     * @return updated CBP examination
     */
    CbpExaminationResponseDto updateCbpExamination(
            Long id,
            CbpExaminationRequestDto request
    );

    /**
     * Deletes a CBP examination by its ID.
     *
     * @param id CBP examination ID
     */
    void deleteCbpExamination(Long id);
}