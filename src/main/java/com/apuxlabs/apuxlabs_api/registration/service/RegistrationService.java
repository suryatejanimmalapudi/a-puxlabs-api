package com.apuxlabs.apuxlabs_api.registration.service;

import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationRequestDto;
import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationResponseDto;

import java.util.List;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;

public interface RegistrationService {

    /**
     * Creates a new registration.
     *
     * @param request registration details received from the client
     * @return newly created registration
     */
    RegistrationResponseDto createRegistration(RegistrationRequestDto request);

    /**
     * Retrieves a registration by its ID.
     *
     * @param id registration ID
     * @return registration details
     */
    RegistrationResponseDto getRegistrationById(Long id);

    /**
     * Retrieves all registrations.
     *
     * @return list of all registrations
     */
    List<RegistrationResponseDto> getAllRegistrations();
    Page<RegistrationResponseDto> searchRegistrations(String search, LocalDateTime fromDate, LocalDateTime toDate, String packageName, org.springframework.data.domain.Pageable pageable);

    /**
     * Updates an existing registration.
     *
     * @param id registration ID
     * @param request updated registration details
     * @return updated registration
     */
    RegistrationResponseDto updateRegistration(
            Long id,
            RegistrationRequestDto request
    );

    /**
     * Deletes a registration by its ID.
     *
     * @param id registration ID
     */
    void deleteRegistration(Long id);
}
