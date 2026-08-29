package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.LipidProfileExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.LipidProfileExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.LipidProfileExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.LipidProfileExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.LipidProfileExaminationRepository;
import com.apuxlabs.apuxlabs_api.examination.service.LipidProfileExaminationService;
import com.apuxlabs.apuxlabs_api.exception.LipidProfileExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LipidProfileExaminationServiceImpl
        implements LipidProfileExaminationService {

    private final LipidProfileExaminationRepository
            lipidProfileExaminationRepository;

    private final LipidProfileExaminationMapper
            lipidProfileExaminationMapper;

    private final RegistrationRepository registrationRepository;

    public LipidProfileExaminationServiceImpl(
            LipidProfileExaminationRepository lipidProfileExaminationRepository,
            LipidProfileExaminationMapper lipidProfileExaminationMapper,
            RegistrationRepository registrationRepository) {

        this.lipidProfileExaminationRepository =
                lipidProfileExaminationRepository;

        this.lipidProfileExaminationMapper =
                lipidProfileExaminationMapper;

        this.registrationRepository =
                registrationRepository;
    }

    /**
     * Creates a new lipid profile examination
     * for an existing registration.
     *
     * @param registrationId registration ID
     * @param request lipid profile examination details
     * @return created lipid profile examination
     */
    @Override
    public LipidProfileExaminationResponseDto
    createLipidProfileExamination(
            Long registrationId,
            LipidProfileExaminationRequestDto request) {

        Registration registration =
                registrationRepository.findById(registrationId)
                        .orElseThrow(() ->
                                new RegistrationNotFoundException(
                                        registrationId
                                )
                        );

        LipidProfileExamination examination =
                lipidProfileExaminationMapper.toEntity(request);

        examination.setRegistration(registration);

        LocalDateTime now = LocalDateTime.now();

        examination.setCreatedAt(now);
        examination.setUpdatedAt(now);

        LipidProfileExamination savedExamination =
                lipidProfileExaminationRepository.save(examination);

        return lipidProfileExaminationMapper.toResponseDto(
                savedExamination
        );
    }

    /**
     * Retrieves a lipid profile examination by its ID.
     *
     * @param id lipid profile examination ID
     * @return lipid profile examination details
     */
    @Override
    public LipidProfileExaminationResponseDto
    getLipidProfileExaminationById(Long id) {

        LipidProfileExamination examination =
                lipidProfileExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new LipidProfileExaminationNotFoundException(
                                        id
                                )
                        );

        return lipidProfileExaminationMapper.toResponseDto(
                examination
        );
    }

    /**
     * Retrieves all lipid profile examinations
     * associated with an existing registration.
     *
     * @param registrationId registration ID
     * @return list of lipid profile examinations
     */
    @Override
    public List<LipidProfileExaminationResponseDto>
    getLipidProfileExaminationsByRegistrationId(
            Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        return lipidProfileExaminationRepository
                .findAllByRegistrationId(registrationId)
                .stream()
                .map(lipidProfileExaminationMapper::toResponseDto)
                .toList();
    }

    /**
     * Updates an existing lipid profile examination.
     *
     * Registration and createdAt remain unchanged.
     * Examination values and updatedAt are modified.
     *
     * @param id lipid profile examination ID
     * @param request updated lipid profile examination details
     * @return updated lipid profile examination
     */
    @Override
    public LipidProfileExaminationResponseDto
    updateLipidProfileExamination(
            Long id,
            LipidProfileExaminationRequestDto request) {

        LipidProfileExamination examination =
                lipidProfileExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new LipidProfileExaminationNotFoundException(
                                        id
                                )
                        );

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        examination.setSampleType(
                request.getSampleType()
        );

        examination.setTotalCholesterol(
                request.getTotalCholesterol()
        );

        examination.setTriglycerides(
                request.getTriglycerides()
        );

        examination.setHdlCholesterol(
                request.getHdlCholesterol()
        );

        examination.setLdlCholesterol(
                request.getLdlCholesterol()
        );

        examination.setVldlCholesterol(
                request.getVldlCholesterol()
        );

        examination.setLdlHdlRatio(
                request.getLdlHdlRatio()
        );

        examination.setTotalCholesterolHdlRatio(
                request.getTotalCholesterolHdlRatio()
        );

        examination.setRemarks(
                request.getRemarks()
        );

        examination.setPathologistName(
                request.getPathologistName()
        );

        examination.setUpdatedAt(
                LocalDateTime.now()
        );

        LipidProfileExamination updatedExamination =
                lipidProfileExaminationRepository.save(examination);

        return lipidProfileExaminationMapper.toResponseDto(
                updatedExamination
        );
    }

    /**
     * Deletes a lipid profile examination by its ID.
     *
     * @param id lipid profile examination ID
     */
    @Override
    public void deleteLipidProfileExamination(Long id) {

        LipidProfileExamination examination =
                lipidProfileExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new LipidProfileExaminationNotFoundException(
                                        id
                                )
                        );

        lipidProfileExaminationRepository.delete(examination);
    }
}