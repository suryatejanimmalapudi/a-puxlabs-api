package com.apuxlabs.apuxlabs_api.registration.service.impl;

import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationRequestDto;
import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationResponseDto;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.entity.RegistrationDispatchMethod;
import com.apuxlabs.apuxlabs_api.registration.mapper.RegistrationMapper;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationDispatchMethodRepository;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private RegistrationMapper registrationMapper;

    @Mock
    private RegistrationDispatchMethodRepository
            registrationDispatchMethodRepository;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    void shouldCreateRegistration() {

        // Arrange
        RegistrationRequestDto request =
                new RegistrationRequestDto();

        request.setFirstName("Ravi");
        request.setLastName("Kumar");
        request.setEmail("ravi@example.com");

        request.setDispatchMethods(
                List.of("EMAIL", "WHATSAPP")
        );

        Registration registration =
                new Registration();

        RegistrationDispatchMethod email =
                new RegistrationDispatchMethod();

        email.setDispatchMethod("EMAIL");

        RegistrationDispatchMethod whatsapp =
                new RegistrationDispatchMethod();

        whatsapp.setDispatchMethod("WHATSAPP");

        List<RegistrationDispatchMethod> dispatchMethods =
                List.of(email, whatsapp);

        Registration savedRegistration =
                new Registration();

        savedRegistration.setId(10L);
        savedRegistration.setFirstName("Ravi");
        savedRegistration.setLastName("Kumar");
        savedRegistration.setEmail("ravi@example.com");

        RegistrationResponseDto expectedResponse =
                new RegistrationResponseDto();

        expectedResponse.setId(10L);
        expectedResponse.setFirstName("Ravi");
        expectedResponse.setLastName("Kumar");
        expectedResponse.setEmail("ravi@example.com");
        expectedResponse.setStatus("ACTIVE");

        when(registrationMapper.toEntity(request))
                .thenReturn(registration);

        when(registrationMapper.toDispatchMethodEntities(
                request.getDispatchMethods(),
                registration
        )).thenReturn(dispatchMethods);

        when(registrationRepository.save(registration))
                .thenReturn(savedRegistration);

        when(registrationMapper.toResponseDto(savedRegistration))
                .thenReturn(expectedResponse);

        // Act
        RegistrationResponseDto actualResponse =
                registrationService.createRegistration(request);

        // Assert
        assertEquals(10L, actualResponse.getId());
        assertEquals("Ravi", actualResponse.getFirstName());
        assertEquals("Kumar", actualResponse.getLastName());
        assertEquals("ravi@example.com", actualResponse.getEmail());
        assertEquals("ACTIVE", actualResponse.getStatus());

        // Verify fields managed by the service itself.
        assertEquals("ACTIVE", registration.getStatus());

        assertNotNull(registration.getRegistrationDate());
        assertNotNull(registration.getCreatedAt());
        assertNotNull(registration.getUpdatedAt());

        assertEquals(
                dispatchMethods,
                registration.getDispatchMethods()
        );

        verify(registrationMapper, times(1))
                .toEntity(request);

        verify(registrationMapper, times(1))
                .toDispatchMethodEntities(
                        request.getDispatchMethods(),
                        registration
                );

        verify(registrationRepository, times(1))
                .save(registration);

        verify(registrationMapper, times(1))
                .toResponseDto(savedRegistration);
    }

    @Test
    void shouldGetRegistrationById() {

        // Arrange
        Long registrationId = 10L;

        Registration registration = new Registration();
        registration.setId(registrationId);
        registration.setFirstName("Ravi");
        registration.setLastName("Kumar");
        registration.setEmail("ravi@example.com");

        RegistrationResponseDto expectedResponse =
                new RegistrationResponseDto();

        expectedResponse.setId(registrationId);
        expectedResponse.setFirstName("Ravi");
        expectedResponse.setLastName("Kumar");
        expectedResponse.setEmail("ravi@example.com");

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(registrationMapper.toResponseDto(registration))
                .thenReturn(expectedResponse);

        // Act
        RegistrationResponseDto actualResponse =
                registrationService.getRegistrationById(registrationId);

        // Assert
        assertEquals(10L, actualResponse.getId());
        assertEquals("Ravi", actualResponse.getFirstName());
        assertEquals("Kumar", actualResponse.getLastName());
        assertEquals("ravi@example.com", actualResponse.getEmail());

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(registrationMapper, times(1))
                .toResponseDto(registration);
    }


    @Test
    void shouldThrowExceptionWhenRegistrationNotFoundById() {

        // Arrange
        Long registrationId = 999L;

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> registrationService
                                .getRegistrationById(registrationId)
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(registrationMapper, never())
                .toResponseDto(any(Registration.class));
    }

    @Test
    void shouldGetAllRegistrations() {

        // Arrange
        Registration registration1 = new Registration();
        registration1.setId(10L);

        Registration registration2 = new Registration();
        registration2.setId(11L);

        List<Registration> registrations =
                List.of(registration1, registration2);

        RegistrationResponseDto response1 =
                new RegistrationResponseDto();
        response1.setId(10L);

        RegistrationResponseDto response2 =
                new RegistrationResponseDto();
        response2.setId(11L);

        when(registrationRepository.findAll())
                .thenReturn(registrations);

        when(registrationMapper.toResponseDto(registration1))
                .thenReturn(response1);

        when(registrationMapper.toResponseDto(registration2))
                .thenReturn(response2);

        // Act
        List<RegistrationResponseDto> actualResponses =
                registrationService.getAllRegistrations();

        // Assert
        assertEquals(2, actualResponses.size());
        assertEquals(10L, actualResponses.get(0).getId());
        assertEquals(11L, actualResponses.get(1).getId());

        verify(registrationRepository, times(1))
                .findAll();

        verify(registrationMapper, times(1))
                .toResponseDto(registration1);

        verify(registrationMapper, times(1))
                .toResponseDto(registration2);
    }

    @Test
    void shouldReturnEmptyListWhenNoRegistrationsExist() {

        // Arrange
        when(registrationRepository.findAll())
                .thenReturn(List.of());

        // Act
        List<RegistrationResponseDto> actualResponses =
                registrationService.getAllRegistrations();

        // Assert
        assertTrue(actualResponses.isEmpty());

        verify(registrationRepository, times(1))
                .findAll();

        verify(registrationMapper, never())
                .toResponseDto(any(Registration.class));
    }

    @Test
    void shouldUpdateRegistration() {

        // Arrange
        Long registrationId = 10L;

        Registration registration = new Registration();
        registration.setId(registrationId);
        registration.setFirstName("Old");
        registration.setLastName("Name");

        RegistrationDispatchMethod oldDispatch =
                new RegistrationDispatchMethod();

        oldDispatch.setDispatchMethod("EMAIL");

        registration.getDispatchMethods().add(oldDispatch);

        RegistrationRequestDto request =
                new RegistrationRequestDto();

        request.setDesignation("Mr");
        request.setFirstName("Ravi");
        request.setLastName("Kumar");
        request.setPhone("9999999999");
        request.setEmail("ravi@example.com");
        request.setReferringDoctorId(101L);
        request.setRateListId(201L);
        request.setDispatchMethods(
                List.of("WHATSAPP", "SMS")
        );

        RegistrationDispatchMethod whatsapp =
                new RegistrationDispatchMethod();
        whatsapp.setDispatchMethod("WHATSAPP");

        RegistrationDispatchMethod sms =
                new RegistrationDispatchMethod();
        sms.setDispatchMethod("SMS");

        List<RegistrationDispatchMethod> newDispatchMethods =
                List.of(whatsapp, sms);

        RegistrationResponseDto expectedResponse =
                new RegistrationResponseDto();

        expectedResponse.setId(registrationId);
        expectedResponse.setFirstName("Ravi");
        expectedResponse.setLastName("Kumar");
        expectedResponse.setEmail("ravi@example.com");

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(registrationMapper.toDispatchMethodEntities(
                request.getDispatchMethods(),
                registration
        )).thenReturn(newDispatchMethods);

        when(registrationMapper.toResponseDto(registration))
                .thenReturn(expectedResponse);

        // Act
        RegistrationResponseDto actualResponse =
                registrationService.updateRegistration(
                        registrationId,
                        request
                );

        // Assert
        assertEquals("Ravi", registration.getFirstName());
        assertEquals("Kumar", registration.getLastName());
        assertEquals("9999999999", registration.getPhone());
        assertEquals("ravi@example.com", registration.getEmail());

        assertNotNull(registration.getUpdatedAt());

        assertEquals(2, registration.getDispatchMethods().size());
        assertEquals(
                "WHATSAPP",
                registration.getDispatchMethods().get(0).getDispatchMethod()
        );
        assertEquals(
                "SMS",
                registration.getDispatchMethods().get(1).getDispatchMethod()
        );

        assertEquals(registrationId, actualResponse.getId());
        assertEquals("Ravi", actualResponse.getFirstName());

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(registrationDispatchMethodRepository, times(1))
                .deleteAllByRegistration(registration);

        verify(registrationDispatchMethodRepository, times(1))
                .flush();

        verify(registrationMapper, times(1))
                .toDispatchMethodEntities(
                        request.getDispatchMethods(),
                        registration
                );

        verify(registrationMapper, times(1))
                .toResponseDto(registration);

        verify(registrationRepository, never())
                .save(any(Registration.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingRegistrationNotFound() {

        // Arrange
        Long registrationId = 999L;

        RegistrationRequestDto request =
                new RegistrationRequestDto();

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> registrationService.updateRegistration(
                                registrationId,
                                request
                        )
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(registrationDispatchMethodRepository, never())
                .deleteAllByRegistration(any(Registration.class));

        verify(registrationDispatchMethodRepository, never())
                .flush();

        verify(registrationMapper, never())
                .toDispatchMethodEntities(
                        anyList(),
                        any(Registration.class)
                );

        verify(registrationMapper, never())
                .toResponseDto(any(Registration.class));
    }

    @Test
    void shouldDeleteRegistration() {

        // Arrange
        Long registrationId = 10L;

        Registration registration = new Registration();
        registration.setId(registrationId);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        // Act
        registrationService.deleteRegistration(registrationId);

        // Assert
        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(registrationRepository, times(1))
                .delete(registration);
    }

    @Test
    void shouldThrowExceptionWhenDeletingRegistrationNotFound() {

        // Arrange
        Long registrationId = 999L;

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> registrationService
                                .deleteRegistration(registrationId)
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(registrationRepository, never())
                .delete(any(Registration.class));
    }
}