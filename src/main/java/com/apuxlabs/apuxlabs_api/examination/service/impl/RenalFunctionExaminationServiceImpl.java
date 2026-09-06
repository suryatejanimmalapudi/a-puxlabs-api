package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.RenalFunctionExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.RenalFunctionExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.RenalFunctionExamination;
import com.apuxlabs.apuxlabs_api.examination.enums.RenalFunctionTestType;
import com.apuxlabs.apuxlabs_api.examination.mapper.RenalFunctionExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.RenalFunctionExaminationRepository;
import com.apuxlabs.apuxlabs_api.examination.service.RenalFunctionExaminationService;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RenalFunctionExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RenalFunctionExaminationServiceImpl
        implements RenalFunctionExaminationService {

    private final RenalFunctionExaminationRepository renalFunctionExaminationRepository;
    private final RenalFunctionExaminationMapper renalFunctionExaminationMapper;
    private final RegistrationRepository registrationRepository;

    public RenalFunctionExaminationServiceImpl(
            RenalFunctionExaminationRepository renalFunctionExaminationRepository,
            RenalFunctionExaminationMapper renalFunctionExaminationMapper,
            RegistrationRepository registrationRepository) {

        this.renalFunctionExaminationRepository =
                renalFunctionExaminationRepository;

        this.renalFunctionExaminationMapper =
                renalFunctionExaminationMapper;

        this.registrationRepository =
                registrationRepository;
    }

    /**
     * Creates a new renal function examination for an existing registration.
     *
     * The registration is verified first. The request DTO is then
     * converted into an entity, associated with the registration,
     * and persisted in the database.
     *
     * @param registrationId registration ID
     * @param request renal function examination details
     * @return created renal function examination
     */
    @Override
    public RenalFunctionExaminationResponseDto createRenalFunctionExamination(
            Long registrationId,
            RenalFunctionExaminationRequestDto request) {

        Registration registration =
                registrationRepository.findById(registrationId)
                        .orElseThrow(() ->
                                new RegistrationNotFoundException(
                                        registrationId
                                )
                        );

        RenalFunctionExamination examination =
                renalFunctionExaminationMapper.toEntity(request);

        examination.setRegistration(registration);

        LocalDateTime now = LocalDateTime.now();

        examination.setCreatedAt(now);
        examination.setUpdatedAt(now);

        RenalFunctionExamination savedExamination =
                renalFunctionExaminationRepository.save(examination);

        return renalFunctionExaminationMapper.toResponseDto(
                savedExamination
        );
    }

    /**
     * Retrieves a renal function examination by its ID.
     *
     * @param id renal function examination ID
     * @return renal function examination details
     * @throws RenalFunctionExaminationNotFoundException when the examination
     *         does not exist
     */
    @Override
    public RenalFunctionExaminationResponseDto getRenalFunctionExaminationById(
            Long id) {

        RenalFunctionExamination examination =
                renalFunctionExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new RenalFunctionExaminationNotFoundException(id)
                        );

        return renalFunctionExaminationMapper.toResponseDto(
                examination
        );
    }

    /**
     * Retrieves all renal function examinations associated with
     * an existing registration.
     *
     * @param registrationId registration ID
     * @return list of renal function examinations
     */
    @Override
    public List<RenalFunctionExaminationResponseDto>
    getRenalFunctionExaminationsByRegistrationId(
            Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        return renalFunctionExaminationRepository
                .findAllByRegistrationId(registrationId)
                .stream()
                .map(renalFunctionExaminationMapper::toResponseDto)
                .toList();
    }

    /**
     * Retrieves all Urea examinations associated with
     * an existing registration.
     *
     * @param registrationId registration ID
     * @return list of Urea examinations
     */
    @Override
    public List<RenalFunctionExaminationResponseDto>
    getUreaExaminationsByRegistrationId(
            Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        return renalFunctionExaminationRepository
                .findAllByRegistrationIdAndTestType(
                        registrationId,
                        RenalFunctionTestType.UREA
                )
                .stream()
                .map(renalFunctionExaminationMapper::toResponseDto)
                .toList();
    }

    /**
     * Retrieves all Creatinine examinations associated with
     * an existing registration.
     *
     * @param registrationId registration ID
     * @return list of Creatinine examinations
     */
    @Override
    public List<RenalFunctionExaminationResponseDto>
    getCreatinineExaminationsByRegistrationId(
            Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        return renalFunctionExaminationRepository
                .findAllByRegistrationIdAndTestType(
                        registrationId,
                        RenalFunctionTestType.CREATININE
                )
                .stream()
                .map(renalFunctionExaminationMapper::toResponseDto)
                .toList();
    }

    /**
     * Updates an existing renal function examination.
     *
     * Only examination data is updated. Registration and createdAt
     * remain unchanged, while updatedAt receives the current time.
     *
     * @param id renal function examination ID
     * @param request updated renal function examination details
     * @return updated renal function examination
     * @throws RenalFunctionExaminationNotFoundException when the examination
     *         does not exist
     */
    @Override
    public RenalFunctionExaminationResponseDto updateRenalFunctionExamination(
            Long id,
            RenalFunctionExaminationRequestDto request) {

        RenalFunctionExamination examination =
                renalFunctionExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new RenalFunctionExaminationNotFoundException(id)
                        );

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        // Renal test type - UREA or CREATININE
        examination.setTestType(
                request.getTestType()
        );

        // Laboratory result
        examination.setResultValue(
                request.getResultValue()
        );

        examination.setSampleType(
                request.getSampleType()
        );

        examination.setReferenceRange(
                request.getReferenceRange()
        );

        examination.setUnit(
                request.getUnit()
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

        RenalFunctionExamination updatedExamination =
                renalFunctionExaminationRepository.save(examination);

        return renalFunctionExaminationMapper.toResponseDto(
                updatedExamination
        );
    }

    /**
     * Deletes a renal function examination by its ID.
     *
     * @param id renal function examination ID
     * @throws RenalFunctionExaminationNotFoundException when the examination
     *         does not exist
     */
    @Override
    public void deleteRenalFunctionExamination(Long id) {

        RenalFunctionExamination examination =
                renalFunctionExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new RenalFunctionExaminationNotFoundException(id)
                        );

        renalFunctionExaminationRepository.delete(examination);
    }
}