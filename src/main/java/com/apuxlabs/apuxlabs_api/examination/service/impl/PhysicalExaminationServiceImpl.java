package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.PhysicalExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.PhysicalExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.PhysicalExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.PhysicalExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.PhysicalExaminationRepository;
import com.apuxlabs.apuxlabs_api.examination.service.PhysicalExaminationService;
import com.apuxlabs.apuxlabs_api.exception.PhysicalExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhysicalExaminationServiceImpl
        implements PhysicalExaminationService {

    private final PhysicalExaminationRepository physicalExaminationRepository;
    private final PhysicalExaminationMapper physicalExaminationMapper;
    private final RegistrationRepository registrationRepository;

    public PhysicalExaminationServiceImpl(
            PhysicalExaminationRepository physicalExaminationRepository,
            PhysicalExaminationMapper physicalExaminationMapper,
            RegistrationRepository registrationRepository) {

        this.physicalExaminationRepository =
                physicalExaminationRepository;

        this.physicalExaminationMapper =
                physicalExaminationMapper;

        this.registrationRepository =
                registrationRepository;
    }

    @Override
    public PhysicalExaminationResponseDto createPhysicalExamination(
            Long registrationId,
            PhysicalExaminationRequestDto request) {

        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(registrationId)
                );

        PhysicalExamination examination =
                physicalExaminationMapper.toEntity(request);

        examination.setRegistration(registration);
        examination.setCreatedAt(LocalDateTime.now());
        examination.setUpdatedAt(LocalDateTime.now());

        PhysicalExamination savedExamination =
                physicalExaminationRepository.save(examination);

        return physicalExaminationMapper.toResponseDto(savedExamination);
    }

    @Override
    public PhysicalExaminationResponseDto getPhysicalExaminationById(Long id) {

        PhysicalExamination examination =
                physicalExaminationRepository.findById(id)
                        .orElseThrow(() ->
                new PhysicalExaminationNotFoundException(id)
        );

        return physicalExaminationMapper.toResponseDto(examination);
    }


    @Override
    public List<PhysicalExaminationResponseDto>
    getPhysicalExaminationsByRegistrationId(Long registrationId) {

        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(registrationId)
                );

        List<PhysicalExamination> examinations =
                physicalExaminationRepository
                        .findAllByRegistrationId(registrationId);

        return examinations.stream()
                .map(physicalExaminationMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public PhysicalExaminationResponseDto updatePhysicalExamination(
            Long id,
            PhysicalExaminationRequestDto request) {

        PhysicalExamination examination =
                physicalExaminationRepository.findById(id)
                        .orElseThrow(() ->
                new PhysicalExaminationNotFoundException(id)
        );


        examination.setExaminationDate(request.getExaminationDate());
        examination.setHeight(request.getHeight());
        examination.setWeight(request.getWeight());
        examination.setPulseRate(request.getPulseRate());
        examination.setBloodPressure(request.getBloodPressure());
        examination.setTemperature(request.getTemperature());
        examination.setPresentComplaints(request.getPresentComplaints());
        examination.setPastHistory(request.getPastHistory());
        examination.setCardiovascularSystem(
                request.getCardiovascularSystem()
        );
        examination.setRespiratorySystem(
                request.getRespiratorySystem()
        );
        examination.setAbdomen(request.getAbdomen());
        examination.setCentralNervousSystem(
                request.getCentralNervousSystem()
        );
        examination.setSkinExamination(
                request.getSkinExamination()
        );
        examination.setEpilepsy(request.getEpilepsy());
        examination.setMedicalCertificate(
                request.getMedicalCertificate()
        );
        examination.setDoctorName(request.getDoctorName());
        examination.setDoctorRegistrationNumber(
                request.getDoctorRegistrationNumber()
        );

        examination.setUpdatedAt(LocalDateTime.now());

        return physicalExaminationMapper.toResponseDto(examination);
    }

    @Override
    public void deletePhysicalExamination(Long id) {

        PhysicalExamination examination =
                physicalExaminationRepository.findById(id)
                        .orElseThrow(() ->
                                new PhysicalExaminationNotFoundException(id)
                        );

        physicalExaminationRepository.delete(examination);
    }

}