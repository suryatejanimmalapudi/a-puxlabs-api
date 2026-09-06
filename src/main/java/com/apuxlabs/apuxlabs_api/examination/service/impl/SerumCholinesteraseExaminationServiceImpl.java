package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.SerumCholinesteraseExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.SerumCholinesteraseExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.SerumCholinesteraseExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.SerumCholinesteraseExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.SerumCholinesteraseExaminationRepository;
import com.apuxlabs.apuxlabs_api.examination.service.SerumCholinesteraseExaminationService;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.SerumCholinesteraseExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SerumCholinesteraseExaminationServiceImpl
        implements SerumCholinesteraseExaminationService {

    private final SerumCholinesteraseExaminationRepository
            serumCholinesteraseExaminationRepository;

    private final SerumCholinesteraseExaminationMapper
            serumCholinesteraseExaminationMapper;

    private final RegistrationRepository registrationRepository;

    public SerumCholinesteraseExaminationServiceImpl(
            SerumCholinesteraseExaminationRepository serumCholinesteraseExaminationRepository,
            SerumCholinesteraseExaminationMapper serumCholinesteraseExaminationMapper,
            RegistrationRepository registrationRepository) {

        this.serumCholinesteraseExaminationRepository =
                serumCholinesteraseExaminationRepository;

        this.serumCholinesteraseExaminationMapper =
                serumCholinesteraseExaminationMapper;

        this.registrationRepository =
                registrationRepository;
    }

    /**
     * Creates a new serum cholinesterase examination
     * for an existing registration.
     *
     * The registration is verified first. The request DTO is then
     * converted into an entity, associated with the registration,
     * and persisted in the database.
     *
     * @param registrationId registration ID
     * @param request serum cholinesterase examination details
     * @return created serum cholinesterase examination
     */
    @Override
    public SerumCholinesteraseExaminationResponseDto
    createSerumCholinesteraseExamination(
            Long registrationId,
            SerumCholinesteraseExaminationRequestDto request) {

        Registration registration =
                registrationRepository.findById(registrationId)
                        .orElseThrow(() ->
                                new RegistrationNotFoundException(
                                        registrationId
                                )
                        );

        SerumCholinesteraseExamination examination =
                serumCholinesteraseExaminationMapper.toEntity(request);

        examination.setRegistration(registration);

        LocalDateTime now = LocalDateTime.now();

        examination.setCreatedAt(now);
        examination.setUpdatedAt(now);

        SerumCholinesteraseExamination savedExamination =
                serumCholinesteraseExaminationRepository.save(
                        examination
                );

        return serumCholinesteraseExaminationMapper.toResponseDto(
                savedExamination
        );
    }

    /**
     * Retrieves a serum cholinesterase examination by its ID.
     *
     * @param id serum cholinesterase examination ID
     * @return serum cholinesterase examination details
     * @throws SerumCholinesteraseExaminationNotFoundException
     *         when the examination does not exist
     */
    @Override
    public SerumCholinesteraseExaminationResponseDto
    getSerumCholinesteraseExaminationById(
            Long id) {

        SerumCholinesteraseExamination examination =
                serumCholinesteraseExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new SerumCholinesteraseExaminationNotFoundException(
                                        id
                                )
                        );

        return serumCholinesteraseExaminationMapper.toResponseDto(
                examination
        );
    }

    /**
     * Retrieves all serum cholinesterase examinations
     * associated with an existing registration.
     *
     * The registration is verified before its serum
     * cholinesterase examination records are retrieved.
     *
     * @param registrationId registration ID
     * @return list of serum cholinesterase examinations
     */
    @Override
    public List<SerumCholinesteraseExaminationResponseDto>
    getSerumCholinesteraseExaminationsByRegistrationId(
            Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        return serumCholinesteraseExaminationRepository
                .findAllByRegistrationId(registrationId)
                .stream()
                .map(serumCholinesteraseExaminationMapper::toResponseDto)
                .toList();
    }

    /**
     * Updates an existing serum cholinesterase examination.
     *
     * Only examination data is updated. Registration and createdAt
     * remain unchanged, while updatedAt receives the current time.
     *
     * @param id serum cholinesterase examination ID
     * @param request updated serum cholinesterase examination details
     * @return updated serum cholinesterase examination
     * @throws SerumCholinesteraseExaminationNotFoundException
     *         when the examination does not exist
     */
    @Override
    public SerumCholinesteraseExaminationResponseDto
    updateSerumCholinesteraseExamination(
            Long id,
            SerumCholinesteraseExaminationRequestDto request) {

        SerumCholinesteraseExamination examination =
                serumCholinesteraseExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new SerumCholinesteraseExaminationNotFoundException(
                                        id
                                )
                        );

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        examination.setResultValue(
                request.getResultValue()
        );

        examination.setMethod(
                request.getMethod()
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

        SerumCholinesteraseExamination updatedExamination =
                serumCholinesteraseExaminationRepository.save(
                        examination
                );

        return serumCholinesteraseExaminationMapper.toResponseDto(
                updatedExamination
        );
    }

    /**
     * Deletes a serum cholinesterase examination by its ID.
     *
     * @param id serum cholinesterase examination ID
     * @throws SerumCholinesteraseExaminationNotFoundException
     *         when the examination does not exist
     */
    @Override
    public void deleteSerumCholinesteraseExamination(
            Long id) {

        SerumCholinesteraseExamination examination =
                serumCholinesteraseExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new SerumCholinesteraseExaminationNotFoundException(
                                        id
                                )
                        );

        serumCholinesteraseExaminationRepository.delete(
                examination
        );
    }
}