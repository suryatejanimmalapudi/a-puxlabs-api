package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.EyeExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.EyeExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.EyeExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.EyeExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.EyeExaminationRepository;
import com.apuxlabs.apuxlabs_api.examination.service.EyeExaminationService;
import com.apuxlabs.apuxlabs_api.exception.EyeExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeExaminationServiceImpl implements EyeExaminationService {

    private final EyeExaminationRepository eyeExaminationRepository;
    private final EyeExaminationMapper eyeExaminationMapper;
    private final RegistrationRepository registrationRepository;

    public EyeExaminationServiceImpl(
            EyeExaminationRepository eyeExaminationRepository,
            EyeExaminationMapper eyeExaminationMapper,
            RegistrationRepository registrationRepository) {

        this.eyeExaminationRepository = eyeExaminationRepository;
        this.eyeExaminationMapper = eyeExaminationMapper;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public EyeExaminationResponseDto createEyeExamination(
            Long registrationId,
            EyeExaminationRequestDto request) {

        // Verify that the registration exists before creating
        // an eye examination for it.
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(registrationId)
                );

        // Convert the incoming request DTO into an EyeExamination entity.
        EyeExamination examination =
                eyeExaminationMapper.toEntity(request);

        // Link the examination to the existing registration.
        examination.setRegistration(registration);

        // Set application-managed timestamps.
        LocalDateTime now = LocalDateTime.now();
        examination.setCreatedAt(now);
        examination.setUpdatedAt(now);

        // Persist the eye examination.
        EyeExamination savedExamination =
                eyeExaminationRepository.save(examination);

        // Convert the saved entity into the API response DTO.
        return eyeExaminationMapper.toResponseDto(savedExamination);
    }

    @Override
    public EyeExaminationResponseDto getEyeExaminationById(Long id) {

        EyeExamination examination =
                eyeExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new EyeExaminationNotFoundException(id)               );

        return eyeExaminationMapper.toResponseDto(examination);
    }

    @Override
    public List<EyeExaminationResponseDto>
    getEyeExaminationsByRegistrationId(Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(registrationId)
                );

        List<EyeExamination> examinations =
                eyeExaminationRepository
                        .findAllByRegistrationId(registrationId);

        return examinations.stream()
                .map(eyeExaminationMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public EyeExaminationResponseDto updateEyeExamination(
            Long id,
            EyeExaminationRequestDto request) {

        // Find the existing eye examination before applying updates.
        EyeExamination examination =
                eyeExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                        new EyeExaminationNotFoundException(id)

                        );

        examination.setExaminationDate(request.getExaminationDate());

        // Right eye - distance vision
        examination.setRightDistanceSph(request.getRightDistanceSph());
        examination.setRightDistanceCyl(request.getRightDistanceCyl());
        examination.setRightDistanceAxis(request.getRightDistanceAxis());
        examination.setRightDistanceVa(request.getRightDistanceVa());

        // Left eye - distance vision
        examination.setLeftDistanceSph(request.getLeftDistanceSph());
        examination.setLeftDistanceCyl(request.getLeftDistanceCyl());
        examination.setLeftDistanceAxis(request.getLeftDistanceAxis());
        examination.setLeftDistanceVa(request.getLeftDistanceVa());

        // Right eye - near vision
        examination.setRightNearSph(request.getRightNearSph());
        examination.setRightNearCyl(request.getRightNearCyl());
        examination.setRightNearAxis(request.getRightNearAxis());
        examination.setRightNearVa(request.getRightNearVa());

        // Left eye - near vision
        examination.setLeftNearSph(request.getLeftNearSph());
        examination.setLeftNearCyl(request.getLeftNearCyl());
        examination.setLeftNearAxis(request.getLeftNearAxis());
        examination.setLeftNearVa(request.getLeftNearVa());

        examination.setColourVision(request.getColourVision());
        examination.setRemarks(request.getRemarks());
        examination.setOptometristName(request.getOptometristName());

        // Only updatedAt changes during an update.
        examination.setUpdatedAt(LocalDateTime.now());

        return eyeExaminationMapper.toResponseDto(examination);
    }

    @Override
    public void deleteEyeExamination(Long id) {

        EyeExamination examination =
                eyeExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new EyeExaminationNotFoundException(id)
                        );

        eyeExaminationRepository.delete(examination);
    }
}