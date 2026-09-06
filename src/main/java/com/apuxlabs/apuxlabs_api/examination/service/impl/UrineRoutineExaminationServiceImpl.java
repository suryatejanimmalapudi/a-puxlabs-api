package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.UrineRoutineExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.UrineRoutineExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.UrineRoutineExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.UrineRoutineExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.UrineRoutineExaminationRepository;
import com.apuxlabs.apuxlabs_api.examination.service.UrineRoutineExaminationService;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.UrineRoutineExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UrineRoutineExaminationServiceImpl
        implements UrineRoutineExaminationService {

    private final UrineRoutineExaminationRepository
            urineRoutineExaminationRepository;

    private final UrineRoutineExaminationMapper
            urineRoutineExaminationMapper;

    private final RegistrationRepository registrationRepository;

    public UrineRoutineExaminationServiceImpl(
            UrineRoutineExaminationRepository
                    urineRoutineExaminationRepository,
            UrineRoutineExaminationMapper
                    urineRoutineExaminationMapper,
            RegistrationRepository registrationRepository) {

        this.urineRoutineExaminationRepository =
                urineRoutineExaminationRepository;

        this.urineRoutineExaminationMapper =
                urineRoutineExaminationMapper;

        this.registrationRepository =
                registrationRepository;
    }

    /**
     * Creates a new urine routine examination
     * for an existing registration.
     *
     * @param registrationId registration ID
     * @param request urine routine examination details
     * @return created urine routine examination
     */
    @Override
    public UrineRoutineExaminationResponseDto
    createUrineRoutineExamination(
            Long registrationId,
            UrineRoutineExaminationRequestDto request) {

        Registration registration =
                registrationRepository.findById(registrationId)
                        .orElseThrow(() ->
                                new RegistrationNotFoundException(
                                        registrationId
                                )
                        );

        UrineRoutineExamination examination =
                urineRoutineExaminationMapper.toEntity(request);

        examination.setRegistration(registration);

        LocalDateTime now = LocalDateTime.now();

        examination.setCreatedAt(now);
        examination.setUpdatedAt(now);

        UrineRoutineExamination savedExamination =
                urineRoutineExaminationRepository.save(examination);

        return urineRoutineExaminationMapper.toResponseDto(
                savedExamination
        );
    }

    /**
     * Retrieves a urine routine examination by its ID.
     *
     * @param id urine routine examination ID
     * @return urine routine examination details
     */
    @Override
    public UrineRoutineExaminationResponseDto
    getUrineRoutineExaminationById(Long id) {

        UrineRoutineExamination examination =
                urineRoutineExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new UrineRoutineExaminationNotFoundException(
                                        id
                                )
                        );

        return urineRoutineExaminationMapper.toResponseDto(
                examination
        );
    }

    /**
     * Retrieves all urine routine examinations
     * associated with an existing registration.
     *
     * @param registrationId registration ID
     * @return list of urine routine examinations
     */
    @Override
    public List<UrineRoutineExaminationResponseDto>
    getUrineRoutineExaminationsByRegistrationId(
            Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        return urineRoutineExaminationRepository
                .findAllByRegistrationId(registrationId)
                .stream()
                .map(urineRoutineExaminationMapper::toResponseDto)
                .toList();
    }

    /**
     * Updates an existing urine routine examination.
     *
     * Registration and createdAt remain unchanged.
     * Examination values and updatedAt are modified.
     *
     * @param id urine routine examination ID
     * @param request updated urine routine examination details
     * @return updated urine routine examination
     */
    @Override
    public UrineRoutineExaminationResponseDto
    updateUrineRoutineExamination(
            Long id,
            UrineRoutineExaminationRequestDto request) {

        UrineRoutineExamination examination =
                urineRoutineExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new UrineRoutineExaminationNotFoundException(
                                        id
                                )
                        );

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        examination.setSampleType(
                request.getSampleType()
        );

        // Physical examination
        examination.setColour(
                request.getColour()
        );

        examination.setDeposit(
                request.getDeposit()
        );

        examination.setAppearance(
                request.getAppearance()
        );

        examination.setPh(
                request.getPh()
        );

        examination.setSpecificGravity(
                request.getSpecificGravity()
        );

        examination.setQuantity(
                request.getQuantity()
        );

        // Chemical examination
        examination.setUrineProtein(
                request.getUrineProtein()
        );

        examination.setBileSalt(
                request.getBileSalt()
        );

        examination.setUrineGlucose(
                request.getUrineGlucose()
        );

        examination.setUrineKetones(
                request.getUrineKetones()
        );

        examination.setBilePigment(
                request.getBilePigment()
        );

        examination.setOccultBlood(
                request.getOccultBlood()
        );

        // Microscopic examination
        examination.setRbcs(
                request.getRbcs()
        );

        examination.setPusCells(
                request.getPusCells()
        );

        examination.setEpithelialCells(
                request.getEpithelialCells()
        );

        examination.setCrystals(
                request.getCrystals()
        );

        examination.setCasts(
                request.getCasts()
        );

        examination.setAmorphousDeposit(
                request.getAmorphousDeposit()
        );

        examination.setBacteria(
                request.getBacteria()
        );

        examination.setTrichomonasVaginalis(
                request.getTrichomonasVaginalis()
        );

        examination.setYeastCells(
                request.getYeastCells()
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

        UrineRoutineExamination updatedExamination =
                urineRoutineExaminationRepository.save(examination);

        return urineRoutineExaminationMapper.toResponseDto(
                updatedExamination
        );
    }

    /**
     * Deletes a urine routine examination by its ID.
     *
     * @param id urine routine examination ID
     */
    @Override
    public void deleteUrineRoutineExamination(Long id) {

        UrineRoutineExamination examination =
                urineRoutineExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new UrineRoutineExaminationNotFoundException(
                                        id
                                )
                        );

        urineRoutineExaminationRepository.delete(examination);
    }
}