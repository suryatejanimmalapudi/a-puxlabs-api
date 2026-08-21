package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.PhysicalExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.PhysicalExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.PhysicalExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.PhysicalExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.PhysicalExaminationRepository;
import com.apuxlabs.apuxlabs_api.exception.PhysicalExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
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
class PhysicalExaminationServiceImplTest {

    @Mock
    private PhysicalExaminationRepository physicalExaminationRepository;

    @Mock
    private PhysicalExaminationMapper physicalExaminationMapper;

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private PhysicalExaminationServiceImpl physicalExaminationService;

    /**
     * Verifies that a physical examination is created successfully
     * when the registration exists and valid examination data is provided.
     */
    @Test
    void shouldCreatePhysicalExamination() {

        // Arrange
        Long registrationId = 1L;

        PhysicalExaminationRequestDto request =
                new PhysicalExaminationRequestDto();

        request.setHeight(172.5);
        request.setWeight(68.0);
        request.setPulseRate(72);
        request.setBloodPressure("120/80");

        Registration registration = new Registration();
        registration.setId(registrationId);

        PhysicalExamination examination =
                new PhysicalExamination();

        PhysicalExamination savedExamination =
                new PhysicalExamination();

        savedExamination.setId(10L);
        savedExamination.setRegistration(registration);

        PhysicalExaminationResponseDto expectedResponse =
                new PhysicalExaminationResponseDto();

        expectedResponse.setId(10L);
        expectedResponse.setRegistrationId(registrationId);
        expectedResponse.setHeight(172.5);
        expectedResponse.setWeight(68.0);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(physicalExaminationMapper.toEntity(request))
                .thenReturn(examination);

        when(physicalExaminationRepository.save(examination))
                .thenReturn(savedExamination);

        when(physicalExaminationMapper.toResponseDto(savedExamination))
                .thenReturn(expectedResponse);

        // Act
        PhysicalExaminationResponseDto actualResponse =
                physicalExaminationService.createPhysicalExamination(
                        registrationId,
                        request
                );

        // Assert
        assertEquals(10L, actualResponse.getId());
        assertEquals(1L, actualResponse.getRegistrationId());
        assertEquals(172.5, actualResponse.getHeight());
        assertEquals(68.0, actualResponse.getWeight());

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(physicalExaminationMapper, times(1))
                .toEntity(request);

        verify(physicalExaminationRepository, times(1))
                .save(examination);

        verify(physicalExaminationMapper, times(1))
                .toResponseDto(savedExamination);
    }

    @Test
    void shouldThrowExceptionWhenRegistrationNotFound() {

        // Arrange
        Long registrationId = 999L;

        PhysicalExaminationRequestDto request =
                new PhysicalExaminationRequestDto();

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> physicalExaminationService
                                .createPhysicalExamination(
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

        verify(physicalExaminationRepository, never())
                .save(any(PhysicalExamination.class));
    }

    @Test
    void shouldGetPhysicalExaminationById() {

        // Arrange
        Long examinationId = 10L;

        Registration registration = new Registration();
        registration.setId(1L);

        PhysicalExamination examination =
                new PhysicalExamination();

        examination.setId(examinationId);
        examination.setRegistration(registration);
        examination.setHeight(172.5);
        examination.setWeight(68.0);

        PhysicalExaminationResponseDto expectedResponse =
                new PhysicalExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setRegistrationId(1L);
        expectedResponse.setHeight(172.5);
        expectedResponse.setWeight(68.0);

        when(physicalExaminationRepository.findById(examinationId))
                .thenReturn(Optional.of(examination));

        when(physicalExaminationMapper.toResponseDto(examination))
                .thenReturn(expectedResponse);

        // Act
        PhysicalExaminationResponseDto actualResponse =
                physicalExaminationService
                        .getPhysicalExaminationById(examinationId);

        // Assert
        assertEquals(10L, actualResponse.getId());
        assertEquals(1L, actualResponse.getRegistrationId());
        assertEquals(172.5, actualResponse.getHeight());
        assertEquals(68.0, actualResponse.getWeight());

        verify(physicalExaminationRepository, times(1))
                .findById(examinationId);

        verify(physicalExaminationMapper, times(1))
                .toResponseDto(examination);
    }

    @Test
    void shouldThrowExceptionWhenPhysicalExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(physicalExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        PhysicalExaminationNotFoundException exception =
                assertThrows(
                        PhysicalExaminationNotFoundException.class,
                        () -> physicalExaminationService
                                .getPhysicalExaminationById(examinationId)
                );

        assertEquals(
                "Physical examination not found with id: 999",
                exception.getMessage()
        );

        verify(physicalExaminationRepository, times(1))
                .findById(examinationId);

        verify(physicalExaminationMapper, never())
                .toResponseDto(any(PhysicalExamination.class));
    }

    @Test
    void shouldGetPhysicalExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration = new Registration();
        registration.setId(registrationId);

        PhysicalExamination examination1 =
                new PhysicalExamination();

        examination1.setId(10L);
        examination1.setRegistration(registration);
        examination1.setHeight(172.5);

        PhysicalExamination examination2 =
                new PhysicalExamination();

        examination2.setId(11L);
        examination2.setRegistration(registration);
        examination2.setHeight(175.0);

        List<PhysicalExamination> examinations =
                List.of(examination1, examination2);

        PhysicalExaminationResponseDto response1 =
                new PhysicalExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setHeight(172.5);

        PhysicalExaminationResponseDto response2 =
                new PhysicalExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setHeight(175.0);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(physicalExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(examinations);

        when(physicalExaminationMapper.toResponseDto(examination1))
                .thenReturn(response1);

        when(physicalExaminationMapper.toResponseDto(examination2))
                .thenReturn(response2);

        // Act
        List<PhysicalExaminationResponseDto> actualResponses =
                physicalExaminationService
                        .getPhysicalExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertEquals(2, actualResponses.size());

        assertEquals(10L, actualResponses.get(0).getId());
        assertEquals(172.5, actualResponses.get(0).getHeight());

        assertEquals(11L, actualResponses.get(1).getId());
        assertEquals(175.0, actualResponses.get(1).getHeight());

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(physicalExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(physicalExaminationMapper, times(1))
                .toResponseDto(examination1);

        verify(physicalExaminationMapper, times(1))
                .toResponseDto(examination2);
    }

    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoPhysicalExaminations() {

        // Arrange
        Long registrationId = 1L;

        Registration registration = new Registration();
        registration.setId(registrationId);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(physicalExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(List.of());

        // Act
        List<PhysicalExaminationResponseDto> actualResponses =
                physicalExaminationService
                        .getPhysicalExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertTrue(actualResponses.isEmpty());

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(physicalExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(physicalExaminationMapper, never())
                .toResponseDto(any(PhysicalExamination.class));
    }

    @Test
    void shouldThrowExceptionWhenRegistrationNotFoundWhileGettingPhysicalExaminations() {

        // Arrange
        Long registrationId = 999L;

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> physicalExaminationService
                                .getPhysicalExaminationsByRegistrationId(
                                        registrationId
                                )
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(physicalExaminationRepository, never())
                .findAllByRegistrationId(anyLong());

        verify(physicalExaminationMapper, never())
                .toResponseDto(any(PhysicalExamination.class));
    }


    @Test
    void shouldUpdatePhysicalExamination() {

        // Arrange
        Long examinationId = 10L;

        PhysicalExamination existingExamination =
                new PhysicalExamination();

        existingExamination.setId(examinationId);
        existingExamination.setHeight(170.0);
        existingExamination.setWeight(65.0);
        existingExamination.setPulseRate(70);
        existingExamination.setBloodPressure("118/78");

        PhysicalExaminationRequestDto request =
                new PhysicalExaminationRequestDto();

        request.setHeight(175.0);
        request.setWeight(70.0);
        request.setPulseRate(76);
        request.setBloodPressure("125/82");
        request.setPresentComplaints("Mild headache");

        PhysicalExaminationResponseDto expectedResponse =
                new PhysicalExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setHeight(175.0);
        expectedResponse.setWeight(70.0);
        expectedResponse.setPulseRate(76);
        expectedResponse.setBloodPressure("125/82");
        expectedResponse.setPresentComplaints("Mild headache");

        when(physicalExaminationRepository.findById(examinationId))
                .thenReturn(Optional.of(existingExamination));

        when(physicalExaminationMapper.toResponseDto(existingExamination))
                .thenReturn(expectedResponse);

        // Act
        PhysicalExaminationResponseDto actualResponse =
                physicalExaminationService.updatePhysicalExamination(
                        examinationId,
                        request
                );

        // Assert
        assertEquals(175.0, existingExamination.getHeight());
        assertEquals(70.0, existingExamination.getWeight());
        assertEquals(76, existingExamination.getPulseRate());
        assertEquals("125/82", existingExamination.getBloodPressure());
        assertEquals(
                "Mild headache",
                existingExamination.getPresentComplaints()
        );

        assertEquals(examinationId, actualResponse.getId());
        assertEquals(175.0, actualResponse.getHeight());
        assertEquals(70.0, actualResponse.getWeight());

        verify(physicalExaminationRepository, times(1))
                .findById(examinationId);

        verify(physicalExaminationMapper, times(1))
                .toResponseDto(existingExamination);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingPhysicalExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        PhysicalExaminationRequestDto request =
                new PhysicalExaminationRequestDto();

        request.setHeight(175.0);
        request.setWeight(70.0);
        request.setPulseRate(76);
        request.setBloodPressure("125/82");

        when(physicalExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        PhysicalExaminationNotFoundException exception =
                assertThrows(
                        PhysicalExaminationNotFoundException.class,
                        () -> physicalExaminationService
                                .updatePhysicalExamination(
                                        examinationId,
                                        request
                                )
                );

        assertEquals(
                "Physical examination not found with id: 999",
                exception.getMessage()
        );

        verify(physicalExaminationRepository, times(1))
                .findById(examinationId);

        verify(physicalExaminationMapper, never())
                .toResponseDto(any(PhysicalExamination.class));
    }

    @Test
    void shouldDeletePhysicalExamination() {

        // Arrange
        Long examinationId = 10L;

        PhysicalExamination examination =
                new PhysicalExamination();

        examination.setId(examinationId);

        when(physicalExaminationRepository.findById(examinationId))
                .thenReturn(Optional.of(examination));

        // Act
        physicalExaminationService
                .deletePhysicalExamination(examinationId);

        // Assert
        verify(physicalExaminationRepository, times(1))
                .findById(examinationId);

        verify(physicalExaminationRepository, times(1))
                .delete(examination);
    }

    @Test
    void shouldThrowExceptionWhenDeletingPhysicalExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(physicalExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        PhysicalExaminationNotFoundException exception =
                assertThrows(
                        PhysicalExaminationNotFoundException.class,
                        () -> physicalExaminationService
                                .deletePhysicalExamination(examinationId)
                );

        assertEquals(
                "Physical examination not found with id: 999",
                exception.getMessage()
        );

        verify(physicalExaminationRepository, times(1))
                .findById(examinationId);

        verify(physicalExaminationRepository, never())
                .delete(any(PhysicalExamination.class));
    }
}