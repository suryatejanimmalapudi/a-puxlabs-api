package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.BloodGlucoseExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.BloodGlucoseExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.BloodGlucoseExamination;
import com.apuxlabs.apuxlabs_api.examination.enums.BloodGlucoseTestType;
import com.apuxlabs.apuxlabs_api.examination.mapper.BloodGlucoseExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.BloodGlucoseExaminationRepository;
import com.apuxlabs.apuxlabs_api.examination.service.BloodGlucoseExaminationService;
import com.apuxlabs.apuxlabs_api.exception.BloodGlucoseExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BloodGlucoseExaminationServiceImpl
        implements BloodGlucoseExaminationService {

    private final BloodGlucoseExaminationRepository bloodGlucoseExaminationRepository;
    private final BloodGlucoseExaminationMapper bloodGlucoseExaminationMapper;
    private final RegistrationRepository registrationRepository;

    public BloodGlucoseExaminationServiceImpl(
            BloodGlucoseExaminationRepository bloodGlucoseExaminationRepository,
            BloodGlucoseExaminationMapper bloodGlucoseExaminationMapper,
            RegistrationRepository registrationRepository) {

        this.bloodGlucoseExaminationRepository =
                bloodGlucoseExaminationRepository;

        this.bloodGlucoseExaminationMapper =
                bloodGlucoseExaminationMapper;

        this.registrationRepository =
                registrationRepository;
    }

    /**
     * Creates a new blood glucose examination for an existing registration.
     *
     * The registration is verified first. The request DTO is then
     * converted into an entity, associated with the registration,
     * and persisted in the database.
     *
     * @param registrationId registration ID
     * @param request blood glucose examination details
     * @return created blood glucose examination
     */
    @Override
    public BloodGlucoseExaminationResponseDto createBloodGlucoseExamination(
            Long registrationId,
            BloodGlucoseExaminationRequestDto request) {

        Registration registration =
                registrationRepository.findById(registrationId)
                        .orElseThrow(() ->
                                new RegistrationNotFoundException(
                                        registrationId
                                )
                        );

        BloodGlucoseExamination examination =
                bloodGlucoseExaminationMapper.toEntity(request);

        examination.setRegistration(registration);

        LocalDateTime now = LocalDateTime.now();

        examination.setCreatedAt(now);
        examination.setUpdatedAt(now);

        BloodGlucoseExamination savedExamination =
                bloodGlucoseExaminationRepository.save(examination);

        return bloodGlucoseExaminationMapper.toResponseDto(
                savedExamination
        );
    }

    /**
     * Retrieves a blood glucose examination by its ID.
     *
     * @param id blood glucose examination ID
     * @return blood glucose examination details
     * @throws BloodGlucoseExaminationNotFoundException when the examination
     *         does not exist
     */
    @Override
    public BloodGlucoseExaminationResponseDto getBloodGlucoseExaminationById(
            Long id) {

        BloodGlucoseExamination examination =
                bloodGlucoseExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new BloodGlucoseExaminationNotFoundException(id)
                        );

        return bloodGlucoseExaminationMapper.toResponseDto(
                examination
        );
    }



    /**
     * Retrieves all FBS examinations associated with
     * an existing registration.
     *
     * @param registrationId registration ID
     * @return list of FBS examinations
     */
    @Override
    public List<BloodGlucoseExaminationResponseDto>
    getFbsExaminationsByRegistrationId(
            Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        return bloodGlucoseExaminationRepository
                .findAllByRegistrationIdAndTestType(
                        registrationId,
                        BloodGlucoseTestType.FBS
                )
                .stream()
                .map(bloodGlucoseExaminationMapper::toResponseDto)
                .toList();
    }

    /**
     * Retrieves all PLBS examinations associated with
     * an existing registration.
     *
     * @param registrationId registration ID
     * @return list of PLBS examinations
     */
    @Override
    public List<BloodGlucoseExaminationResponseDto>
    getPlbsExaminationsByRegistrationId(
            Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        return bloodGlucoseExaminationRepository
                .findAllByRegistrationIdAndTestType(
                        registrationId,
                        BloodGlucoseTestType.PLBS
                )
                .stream()
                .map(bloodGlucoseExaminationMapper::toResponseDto)
                .toList();
    }

    /**
     * Retrieves all blood glucose examinations associated with
     * an existing registration.
     *
     * The registration is verified before its blood glucose
     * examination records are retrieved.
     *
     * @param registrationId registration ID
     * @return list of blood glucose examinations

     */
    @Override
    public List<BloodGlucoseExaminationResponseDto>
    getBloodGlucoseExaminationsByRegistrationId(
            Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        return bloodGlucoseExaminationRepository
                .findAllByRegistrationId(registrationId)
                .stream()
                .map(bloodGlucoseExaminationMapper::toResponseDto)
                .toList();
    }

    /**
     * Updates an existing blood glucose examination.
     *
     * Only examination data is updated. Registration and createdAt
     * remain unchanged, while updatedAt receives the current time.
     *
     * @param id blood glucose examination ID
     * @param request updated blood glucose examination details
     * @return updated blood glucose examination
     * @throws BloodGlucoseExaminationNotFoundException when the examination
     *         does not exist
     */
    @Override
    public BloodGlucoseExaminationResponseDto updateBloodGlucoseExamination(
            Long id,
            BloodGlucoseExaminationRequestDto request) {

        BloodGlucoseExamination examination =
                bloodGlucoseExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new BloodGlucoseExaminationNotFoundException(id)
                        );

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        // Blood glucose test type - FBS or PLBS
        examination.setTestType(
                request.getTestType()
        );

        // Plasma glucose result
        examination.setGlucoseValue(
                request.getGlucoseValue()
        );

        // Laboratory sample details
        examination.setSampleType(
                request.getSampleType()
        );

        examination.setReferenceRange(
                request.getReferenceRange()
        );

        examination.setUnit(
                request.getUnit()
        );

        // Laboratory interpretation
        examination.setRemarks(
                request.getRemarks()
        );

        examination.setPathologistName(
                request.getPathologistName()
        );

        examination.setUpdatedAt(
                LocalDateTime.now()
        );

        BloodGlucoseExamination updatedExamination =
                bloodGlucoseExaminationRepository.save(examination);

        return bloodGlucoseExaminationMapper.toResponseDto(
                updatedExamination
        );
    }

    /**
     * Deletes a blood glucose examination by its ID.
     *
     * @param id blood glucose examination ID
     * @throws BloodGlucoseExaminationNotFoundException when the examination
     *         does not exist
     */
    @Override
    public void deleteBloodGlucoseExamination(Long id) {

        BloodGlucoseExamination examination =
                bloodGlucoseExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new BloodGlucoseExaminationNotFoundException(id)
                        );

        bloodGlucoseExaminationRepository.delete(examination);
    }
}