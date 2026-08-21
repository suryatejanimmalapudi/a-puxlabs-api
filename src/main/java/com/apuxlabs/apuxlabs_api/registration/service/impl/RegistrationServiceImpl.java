package com.apuxlabs.apuxlabs_api.registration.service.impl;

import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
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
    private final RegistrationDispatchMethodRepository
            registrationDispatchMethodRepository;

    public RegistrationServiceImpl(
            RegistrationRepository registrationRepository,
            RegistrationMapper registrationMapper,
            RegistrationDispatchMethodRepository registrationDispatchMethodRepository)
    {

        this.registrationRepository = registrationRepository;
        this.registrationMapper = registrationMapper;
        this.registrationDispatchMethodRepository =
                registrationDispatchMethodRepository;
    }

    /**
     * Creates a new registration and persists it in the database.
     *
     * The mapper converts the incoming DTO into an entity.
     * Application-managed fields are then initialized before persistence.
     *
     * @param request registration details received from the client
     * @return newly created registration
     */
    @Override
    public RegistrationResponseDto createRegistration(
            RegistrationRequestDto request) {

        Registration registration =
                registrationMapper.toEntity(request);

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

        Registration savedRegistration =
                registrationRepository.save(registration);

        return registrationMapper.toResponseDto(savedRegistration);
    }

    @Override
    public RegistrationResponseDto getRegistrationById(Long id) {

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RegistrationNotFoundException(id));


        return registrationMapper.toResponseDto(registration);
    }
    @Override
    public List<RegistrationResponseDto> getAllRegistrations() {

        List<Registration> registrations =
                registrationRepository.findAll();

        return registrations.stream()
                .map(registrationMapper::toResponseDto)
                .toList();
    }
    /**
     * Updates an existing registration.
     *
     * The registration itself is already managed by the current transaction,
     * so there is no need to explicitly call save() after modifying it.
     *
     * Existing dispatch methods are deleted first and the new dispatch methods
     * are then attached to the registration.
     *
     * @param id registration ID
     * @param request updated registration details
     * @return updated registration
     */
    @Transactional
    @Override
    public RegistrationResponseDto updateRegistration(
            Long id,
            RegistrationRequestDto request) {

        // Find the existing registration.
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Registration not found with id: " + id
                        )
                );

        // Update registration fields.
        registration.setDesignation(request.getDesignation());
        registration.setFirstName(request.getFirstName());
        registration.setLastName(request.getLastName());
        registration.setDateOfBirth(request.getDateOfBirth());
        registration.setGender(request.getGender());
        registration.setPhone(request.getPhone());
        registration.setEmail(request.getEmail());
        registration.setReferringDoctorId(request.getReferringDoctorId());
        registration.setRateListId(request.getRateListId());
        registration.setUpdatedAt(LocalDateTime.now());

        // Delete existing dispatch-method records from the database.
        registrationDispatchMethodRepository
                .deleteAllByRegistration(registration);

        // Force Hibernate to execute the DELETE before we insert new records.
        registrationDispatchMethodRepository.flush();

        // Clear the old child entities from the parent's collection.
        registration.getDispatchMethods().clear();

        // Create the new dispatch-method entities.
        List<RegistrationDispatchMethod> newDispatchMethods =
                registrationMapper.toDispatchMethodEntities(
                        request.getDispatchMethods(),
                        registration
                );

        // Attach the new dispatch methods to the managed registration.
        registration.getDispatchMethods().addAll(newDispatchMethods);

        // The registration is already managed by Hibernate.
        // No registrationRepository.save() is required.
        return registrationMapper.toResponseDto(registration);
    }
    @Override
    public void deleteRegistration(Long id) {

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RegistrationNotFoundException(id));
        

        // Deleting the parent registration also deletes its
        // dispatch methods because of Cascade/relationship configuration.
        registrationRepository.delete(registration);
    }
}