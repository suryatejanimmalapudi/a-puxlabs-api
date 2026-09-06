package com.apuxlabs.apuxlabs_api.examination.service;

import com.apuxlabs.apuxlabs_api.examination.dto.PhysicalExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.PhysicalExaminationResponseDto;

import java.util.List;

public interface PhysicalExaminationService {

    /**
     * Creates a physical examination for a registration.
     *
     * @param registrationId registration ID
     * @param request physical examination details
     * @return created physical examination
     */
    PhysicalExaminationResponseDto createPhysicalExamination(
            Long registrationId,
            PhysicalExaminationRequestDto request
    );

    /**
     * Retrieves a physical examination by its ID.
     *
     * @param id physical examination ID
     * @return physical examination details
     */
    PhysicalExaminationResponseDto getPhysicalExaminationById(Long id);

    /**
     * Retrieves all physical examinations for a registration.
     *
     * @param registrationId registration ID
     * @return list of physical examinations
     */
    List<PhysicalExaminationResponseDto> getPhysicalExaminationsByRegistrationId(
            Long registrationId
    );

    /**
     * Updates an existing physical examination.
     *
     * @param id physical examination ID
     * @param request updated physical examination details
     * @return updated physical examination
     */
    PhysicalExaminationResponseDto updatePhysicalExamination(
            Long id,
            PhysicalExaminationRequestDto request
    );

    /**
     * Deletes a physical examination.
     *
     * @param id physical examination ID
     */
    void deletePhysicalExamination(Long id);
}