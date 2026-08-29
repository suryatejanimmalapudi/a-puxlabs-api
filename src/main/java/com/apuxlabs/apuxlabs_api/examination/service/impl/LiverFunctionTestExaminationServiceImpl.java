package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.LiverFunctionTestExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.LiverFunctionTestExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.LiverFunctionTestExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.LiverFunctionTestExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.LiverFunctionTestExaminationRepository;
import com.apuxlabs.apuxlabs_api.examination.service.LiverFunctionTestExaminationService;
import com.apuxlabs.apuxlabs_api.exception.LiverFunctionTestExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LiverFunctionTestExaminationServiceImpl
        implements LiverFunctionTestExaminationService {

    private final LiverFunctionTestExaminationRepository
            liverFunctionTestExaminationRepository;

    private final LiverFunctionTestExaminationMapper
            liverFunctionTestExaminationMapper;

    private final RegistrationRepository registrationRepository;

    public LiverFunctionTestExaminationServiceImpl(
            LiverFunctionTestExaminationRepository
                    liverFunctionTestExaminationRepository,
            LiverFunctionTestExaminationMapper
                    liverFunctionTestExaminationMapper,
            RegistrationRepository registrationRepository) {

        this.liverFunctionTestExaminationRepository =
                liverFunctionTestExaminationRepository;

        this.liverFunctionTestExaminationMapper =
                liverFunctionTestExaminationMapper;

        this.registrationRepository =
                registrationRepository;
    }

    /**
     * Creates a new liver function test examination
     * for an existing registration.
     *
     * @param registrationId registration ID
     * @param request liver function test examination details
     * @return created liver function test examination
     */
    @Override
    public LiverFunctionTestExaminationResponseDto
    createLiverFunctionTestExamination(
            Long registrationId,
            LiverFunctionTestExaminationRequestDto request) {

        Registration registration =
                registrationRepository.findById(registrationId)
                        .orElseThrow(() ->
                                new RegistrationNotFoundException(
                                        registrationId
                                )
                        );

        LiverFunctionTestExamination examination =
                liverFunctionTestExaminationMapper.toEntity(request);

        examination.setRegistration(registration);

        LocalDateTime now = LocalDateTime.now();

        examination.setCreatedAt(now);
        examination.setUpdatedAt(now);

        LiverFunctionTestExamination savedExamination =
                liverFunctionTestExaminationRepository.save(
                        examination
                );

        return liverFunctionTestExaminationMapper.toResponseDto(
                savedExamination
        );
    }

    /**
     * Retrieves a liver function test examination by its ID.
     *
     * @param id liver function test examination ID
     * @return liver function test examination details
     */
    @Override
    public LiverFunctionTestExaminationResponseDto
    getLiverFunctionTestExaminationById(Long id) {

        LiverFunctionTestExamination examination =
                liverFunctionTestExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new LiverFunctionTestExaminationNotFoundException(
                                        id
                                )
                        );

        return liverFunctionTestExaminationMapper.toResponseDto(
                examination
        );
    }

    /**
     * Retrieves all liver function test examinations
     * associated with an existing registration.
     *
     * @param registrationId registration ID
     * @return list of liver function test examinations
     */
    @Override
    public List<LiverFunctionTestExaminationResponseDto>
    getLiverFunctionTestExaminationsByRegistrationId(
            Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        return liverFunctionTestExaminationRepository
                .findAllByRegistrationId(registrationId)
                .stream()
                .map(liverFunctionTestExaminationMapper::toResponseDto)
                .toList();
    }

    /**
     * Updates an existing liver function test examination.
     *
     * Registration and createdAt remain unchanged.
     * Examination values and updatedAt are modified.
     *
     * @param id liver function test examination ID
     * @param request updated liver function test examination details
     * @return updated liver function test examination
     */
    @Override
    public LiverFunctionTestExaminationResponseDto
    updateLiverFunctionTestExamination(
            Long id,
            LiverFunctionTestExaminationRequestDto request) {

        LiverFunctionTestExamination examination =
                liverFunctionTestExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new LiverFunctionTestExaminationNotFoundException(
                                        id
                                )
                        );

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        examination.setSampleType(
                request.getSampleType()
        );

        examination.setBilirubinTotal(
                request.getBilirubinTotal()
        );

        examination.setBilirubinDirect(
                request.getBilirubinDirect()
        );

        examination.setBilirubinIndirect(
                request.getBilirubinIndirect()
        );

        examination.setSgpt(
                request.getSgpt()
        );

        examination.setSgot(
                request.getSgot()
        );

        examination.setSgotSgptRatio(
                request.getSgotSgptRatio()
        );

        examination.setAlkalinePhosphatase(
                request.getAlkalinePhosphatase()
        );

        examination.setGammaGlutamylTransferase(
                request.getGammaGlutamylTransferase()
        );

        examination.setTotalProteins(
                request.getTotalProteins()
        );

        examination.setAlbumin(
                request.getAlbumin()
        );

        examination.setGlobulin(
                request.getGlobulin()
        );

        examination.setAlbuminGlobulinRatio(
                request.getAlbuminGlobulinRatio()
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

        LiverFunctionTestExamination updatedExamination =
                liverFunctionTestExaminationRepository.save(
                        examination
                );

        return liverFunctionTestExaminationMapper.toResponseDto(
                updatedExamination
        );
    }

    /**
     * Deletes a liver function test examination by its ID.
     *
     * @param id liver function test examination ID
     */
    @Override
    public void deleteLiverFunctionTestExamination(Long id) {

        LiverFunctionTestExamination examination =
                liverFunctionTestExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new LiverFunctionTestExaminationNotFoundException(
                                        id
                                )
                        );

        liverFunctionTestExaminationRepository.delete(
                examination
        );
    }
}