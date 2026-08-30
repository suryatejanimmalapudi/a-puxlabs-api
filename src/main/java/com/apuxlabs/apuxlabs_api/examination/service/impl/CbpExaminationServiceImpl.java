package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.CbpExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.CbpExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.CbpExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.CbpExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.CbpExaminationRepository;
import com.apuxlabs.apuxlabs_api.examination.service.CbpExaminationService;
import com.apuxlabs.apuxlabs_api.exception.CbpExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CbpExaminationServiceImpl
        implements CbpExaminationService {

    private final CbpExaminationRepository cbpExaminationRepository;
    private final CbpExaminationMapper cbpExaminationMapper;
    private final RegistrationRepository registrationRepository;

    public CbpExaminationServiceImpl(
            CbpExaminationRepository cbpExaminationRepository,
            CbpExaminationMapper cbpExaminationMapper,
            RegistrationRepository registrationRepository) {

        this.cbpExaminationRepository = cbpExaminationRepository;
        this.cbpExaminationMapper = cbpExaminationMapper;
        this.registrationRepository = registrationRepository;
    }

    /**
     * Creates a new CBP examination for an existing registration.
     *
     * The registration is verified first. The request DTO is then
     * converted into an entity, associated with the registration,
     * and persisted in the database.
     *
     * @param registrationId registration ID
     * @param request CBP examination details
     * @return created CBP examination
     */
    @Override
    public CbpExaminationResponseDto createCbpExamination(
            Long registrationId,
            CbpExaminationRequestDto request) {

        Registration registration =
                registrationRepository.findById(registrationId)
                        .orElseThrow(() ->
                                new RegistrationNotFoundException(
                                        registrationId
                                )
                        );

        CbpExamination examination =
                cbpExaminationMapper.toEntity(request);

        examination.setRegistration(registration);

        LocalDateTime now = LocalDateTime.now();

        examination.setCreatedAt(now);
        examination.setUpdatedAt(now);

        CbpExamination savedExamination =
                cbpExaminationRepository.save(examination);

        return cbpExaminationMapper.toResponseDto(
                savedExamination
        );
    }

    /**
     * Retrieves a CBP examination by its ID.
     *
     * @param id CBP examination ID
     * @return CBP examination details
     * @throws CbpExaminationNotFoundException when the examination
     *         does not exist
     */
    @Override
    public CbpExaminationResponseDto getCbpExaminationById(
            Long id) {

        CbpExamination examination =
                cbpExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new CbpExaminationNotFoundException(id)
                        );

        return cbpExaminationMapper.toResponseDto(
                examination
        );
    }

    /**
     * Retrieves all CBP examinations associated with
     * an existing registration.
     *
     * The registration is verified before its CBP examination
     * records are retrieved.
     *
     * @param registrationId registration ID
     * @return list of CBP examinations
     */
    @Override
    public List<CbpExaminationResponseDto>
    getCbpExaminationsByRegistrationId(
            Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        return cbpExaminationRepository
                .findAllByRegistrationId(registrationId)
                .stream()
                .map(cbpExaminationMapper::toResponseDto)
                .toList();
    }

    /**
     * Updates an existing CBP examination.
     *
     * Only examination data is updated. Registration and createdAt
     * remain unchanged, while updatedAt receives the current time.
     *
     * @param id CBP examination ID
     * @param request updated CBP examination details
     * @return updated CBP examination
     * @throws CbpExaminationNotFoundException when the examination
     *         does not exist
     */
    @Override
    public CbpExaminationResponseDto updateCbpExamination(
            Long id,
            CbpExaminationRequestDto request) {

        CbpExamination examination =
                cbpExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new CbpExaminationNotFoundException(id)
                        );

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        // Basic haematology
        examination.setHaemoglobin(
                request.getHaemoglobin()
        );

        examination.setTotalLeukocyteCount(
                request.getTotalLeukocyteCount()
        );

        // Differential leukocyte count
        examination.setNeutrophils(
                request.getNeutrophils()
        );

        examination.setLymphocytes(
                request.getLymphocytes()
        );

        examination.setEosinophils(
                request.getEosinophils()
        );

        examination.setMonocytes(
                request.getMonocytes()
        );

        examination.setBasophils(
                request.getBasophils()
        );

        // Absolute leukocyte count
        examination.setAbsoluteNeutrophils(
                request.getAbsoluteNeutrophils()
        );

        examination.setAbsoluteLymphocytes(
                request.getAbsoluteLymphocytes()
        );

        examination.setAbsoluteEosinophils(
                request.getAbsoluteEosinophils()
        );

        examination.setAbsoluteMonocytes(
                request.getAbsoluteMonocytes()
        );

        // RBC indices
        examination.setRbcCount(
                request.getRbcCount()
        );

        examination.setHct(
                request.getHct()
        );

        examination.setMcv(
                request.getMcv()
        );

        examination.setMch(
                request.getMch()
        );

        examination.setMchc(
                request.getMchc()
        );

        examination.setRdwCv(
                request.getRdwCv()
        );

        examination.setRdwSd(
                request.getRdwSd()
        );

        // Platelet indices
        examination.setPlateletCount(
                request.getPlateletCount()
        );

        examination.setPct(
                request.getPct()
        );

        examination.setMpv(
                request.getMpv()
        );

        examination.setPdw(
                request.getPdw()
        );

        examination.setPLcr(
                request.getPLcr()
        );

        examination.setPLcc(
                request.getPLcc()
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

        CbpExamination updatedExamination =
                cbpExaminationRepository.save(examination);

        return cbpExaminationMapper.toResponseDto(
                updatedExamination
        );
    }

    /**
     * Deletes a CBP examination by its ID.
     *
     * @param id CBP examination ID
     * @throws CbpExaminationNotFoundException when the examination
     *         does not exist
     */
    @Override
    public void deleteCbpExamination(Long id) {

        CbpExamination examination =
                cbpExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new CbpExaminationNotFoundException(id)
                        );

        cbpExaminationRepository.delete(examination);
    }
}