package com.apuxlabs.apuxlabs_api.registration.mapper;

import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationRequestDto;
import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationResponseDto;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.entity.RegistrationDispatchMethod;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RegistrationMapper
{
    /**
     * Converts the incoming registration request DTO into a Registration entity.
     *
     * This method maps only the fields supplied by the client.
     * Database-generated fields and application-managed fields such as
     * ID, status, and timestamps are handled separately by the service/database.
     *
     * @param dto incoming registration request
     * @return Registration entity ready for business processing and persistence
     */
    public Registration toEntity(RegistrationRequestDto dto) {

        Registration registration = new Registration();

        registration.setDesignation(dto.getDesignation());
        registration.setFirstName(dto.getFirstName());
        registration.setLastName(dto.getLastName());
        registration.setDateOfBirth(dto.getDateOfBirth());
        registration.setGender(dto.getGender());
        registration.setPhone(dto.getPhone());
        registration.setEmail(dto.getEmail());
        registration.setReferringDoctorId(dto.getReferringDoctorId());
        registration.setRateListId(dto.getRateListId());

        return registration;
    }

    /**
     * Converts a Registration entity into a response DTO.
     *
     * This keeps the JPA entity separate from the API response and allows
     * us to control exactly which registration fields are exposed to clients.
     *
     * @param registration persisted Registration entity
     * @return Registration response DTO
     */
    public RegistrationResponseDto toResponseDto(Registration registration) {

        RegistrationResponseDto response = new RegistrationResponseDto();

        response.setId(registration.getId());
        response.setDesignation(registration.getDesignation());
        response.setFirstName(registration.getFirstName());
        response.setLastName(registration.getLastName());
        response.setDateOfBirth(registration.getDateOfBirth());
        response.setGender(registration.getGender());
        response.setPhone(registration.getPhone());
        response.setEmail(registration.getEmail());
        response.setReferringDoctorId(registration.getReferringDoctorId());
        response.setRateListId(registration.getRateListId());
        response.setRegistrationDate(registration.getRegistrationDate());
        response.setStatus(registration.getStatus());
        response.setCreatedAt(registration.getCreatedAt());
        response.setUpdatedAt(registration.getUpdatedAt());

        response.setDispatchMethods(
                toDispatchMethodStrings(registration.getDispatchMethods())
        );

        return response;
    }

    /**
     * Converts dispatch method values received from the client into
     * RegistrationDispatchMethod entities.
     *
     * Each dispatch method becomes a separate child entity because the
     * database stores dispatch methods in the registration_dispatch_method table.
     *
     * @param dispatchMethods dispatch methods received from the registration request
     * @param registration parent Registration entity
     * @return list of RegistrationDispatchMethod entities
     */
    public List<RegistrationDispatchMethod> toDispatchMethodEntities(
            List<String> dispatchMethods,
            Registration registration) {

        if (dispatchMethods == null || dispatchMethods.isEmpty()) {
            return new ArrayList<>();
        }

        return dispatchMethods.stream()
                .map(method -> {
                    RegistrationDispatchMethod dispatchMethod =
                            new RegistrationDispatchMethod();

                    dispatchMethod.setRegistration(registration);
                    dispatchMethod.setDispatchMethod(method);
                    dispatchMethod.setCreatedAt(LocalDateTime.now());

                    return dispatchMethod;
                })
                .toList();
    }

    /**
     * Converts dispatch method entities into simple string values
     * for the registration response DTO.
     *
     * @param dispatchMethods dispatch method entities associated with a registration
     * @return list of dispatch method names
     */
    public List<String> toDispatchMethodStrings(
            List<RegistrationDispatchMethod> dispatchMethods) {

        if (dispatchMethods == null || dispatchMethods.isEmpty()) {
            return new ArrayList<>();
        }

        return dispatchMethods.stream()
                .map(RegistrationDispatchMethod::getDispatchMethod)
                .toList();
    }
}
