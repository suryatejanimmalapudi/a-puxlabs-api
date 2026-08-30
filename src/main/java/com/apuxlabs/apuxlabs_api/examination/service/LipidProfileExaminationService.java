package com.apuxlabs.apuxlabs_api.examination.service;

import com.apuxlabs.apuxlabs_api.examination.dto.LipidProfileExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.LipidProfileExaminationResponseDto;

import java.util.List;

public interface LipidProfileExaminationService {

    /**
     * Creates a new lipid profile examination
     * for an existing registration.
     *
     * @param registrationId registration ID
     * @param request lipid profile examination details
     * @return created lipid profile examination
     */
    LipidProfileExaminationResponseDto createLipidProfileExamination(
            Long registrationId,
            LipidProfileExaminationRequestDto request
    );

    /**
     * Retrieves a lipid profile examination by its ID.
     *
     * @param id lipid profile examination ID
     * @return lipid profile examination details
     */
    LipidProfileExaminationResponseDto getLipidProfileExaminationById(
            Long id
    );

    /**
     * Retrieves all lipid profile examinations
     * belonging to a registration.
     *
     * @param registrationId registration ID
     * @return list of lipid profile examinations
     */
    List<LipidProfileExaminationResponseDto>
    getLipidProfileExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Updates an existing lipid profile examination.
     *
     * @param id lipid profile examination ID
     * @param request updated lipid profile examination details
     * @return updated lipid profile examination
     */
    LipidProfileExaminationResponseDto updateLipidProfileExamination(
            Long id,
            LipidProfileExaminationRequestDto request
    );

    /**
     * Deletes a lipid profile examination by its ID.
     *
     * @param id lipid profile examination ID
     */
    void deleteLipidProfileExamination(Long id);
}