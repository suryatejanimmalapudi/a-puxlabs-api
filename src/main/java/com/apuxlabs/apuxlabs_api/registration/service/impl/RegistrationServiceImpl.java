package com.apuxlabs.apuxlabs_api.registration.service.impl;

import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationRequestDto;
import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationResponseDto;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.entity.RegistrationDispatchMethod;
import com.apuxlabs.apuxlabs_api.registration.mapper.RegistrationMapper;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationDispatchMethodRepository;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;
import com.apuxlabs.apuxlabs_api.registration.service.RegistrationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;
    private final RegistrationDispatchMethodRepository registrationDispatchMethodRepository;

    public RegistrationServiceImpl(
            RegistrationRepository registrationRepository,
            RegistrationMapper registrationMapper,
            RegistrationDispatchMethodRepository registrationDispatchMethodRepository) {

        this.registrationRepository = registrationRepository;
        this.registrationMapper = registrationMapper;
        this.registrationDispatchMethodRepository = registrationDispatchMethodRepository;
    }

    @Override
    public RegistrationResponseDto createRegistration(RegistrationRequestDto request) {

        Registration registration = registrationMapper.toEntity(request);

        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus("ACTIVE");
        registration.setCreatedAt(LocalDateTime.now());
        registration.setUpdatedAt(LocalDateTime.now());

        registration.setDispatchMethods(
                registrationMapper.toDispatchMethodEntities(
                        request.getDispatchMethods(),
                        registration
                )
        );

        Registration savedRegistration = registrationRepository.save(registration);

        return registrationMapper.toResponseDto(savedRegistration);
    }

    @Override
    public RegistrationResponseDto getRegistrationById(Long id) {

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with ID: " + id));

        return registrationMapper.toResponseDto(registration);
    }

    @Override
    public List<RegistrationResponseDto> getAllRegistrations() {

        List<Registration> registrations = registrationRepository.findAll();

        return registrations.stream()
                .map(registrationMapper::toResponseDto)
                .toList();
    }

    @Transactional
    @Override
    public RegistrationResponseDto updateRegistration(Long id, RegistrationRequestDto request) {

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with ID: " + id));

        registration.setFirstName(request.getFirstName());
        registration.setLastName(request.getLastName());
        registration.setDateOfBirth(request.getDateOfBirth());
        registration.setGender(request.getGender());
        registration.setPhone(request.getPhone());
        registration.setEmail(request.getEmail());
        registration.setReferringDoctorId(request.getReferringDoctorId());
        registration.setRateListId(request.getRateListId());
        registration.setUpdatedAt(LocalDateTime.now());

        registrationDispatchMethodRepository.deleteAllByRegistration(registration);
        registrationDispatchMethodRepository.flush();

        registration.getDispatchMethods().clear();

        List<RegistrationDispatchMethod> newDispatchMethods =
                registrationMapper.toDispatchMethodEntities(
                        request.getDispatchMethods(),
                        registration
                );

        registration.getDispatchMethods().addAll(newDispatchMethods);

        return registrationMapper.toResponseDto(registration);
    }

    @Override
    public void deleteRegistration(Long id) {

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with ID: " + id));

        registrationRepository.delete(registration);
    }
}