package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.EyeExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.EyeExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.EyeExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.EyeExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.EyeExaminationRepository;
import com.apuxlabs.apuxlabs_api.exception.EyeExaminationNotFoundException;
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
class EyeExaminationServiceImplTest {

    @Mock
    private EyeExaminationRepository eyeExaminationRepository;

    @Mock
    private EyeExaminationMapper eyeExaminationMapper;

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private EyeExaminationServiceImpl eyeExaminationService;

    /**
     * Verifies that an eye examination is created successfully
     * when the registration exists and valid examination data is provided.
     */
    @Test
    void shouldCreateEyeExamination() {

        // Arrange
        Long registrationId = 1L;

        EyeExaminationRequestDto request =
                new EyeExaminationRequestDto();

        request.setRightDistanceSph(-1.25);
        request.setLeftDistanceSph(-1.00);
        request.setRightDistanceVa("6/6");
        request.setLeftDistanceVa("6/6");
        request.setColourVision("Normal");

        Registration registration = new Registration();
        registration.setId(registrationId);

        EyeExamination examination =
                new EyeExamination();

        EyeExamination savedExamination =
                new EyeExamination();

        savedExamination.setId(10L);
        savedExamination.setRegistration(registration);

        EyeExaminationResponseDto expectedResponse =
                new EyeExaminationResponseDto();

        expectedResponse.setId(10L);
        expectedResponse.setRegistrationId(registrationId);
        expectedResponse.setRightDistanceSph(-1.25);
        expectedResponse.setLeftDistanceSph(-1.00);
        expectedResponse.setRightDistanceVa("6/6");
        expectedResponse.setLeftDistanceVa("6/6");
        expectedResponse.setColourVision("Normal");

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(eyeExaminationMapper.toEntity(request))
                .thenReturn(examination);

        when(eyeExaminationRepository.save(examination))
                .thenReturn(savedExamination);

        when(eyeExaminationMapper.toResponseDto(savedExamination))
                .thenReturn(expectedResponse);

        // Act
        EyeExaminationResponseDto actualResponse =
                eyeExaminationService.createEyeExamination(
                        registrationId,
                        request
                );

        // Assert
        assertEquals(10L, actualResponse.getId());
        assertEquals(1L, actualResponse.getRegistrationId());
        assertEquals(-1.25, actualResponse.getRightDistanceSph());
        assertEquals(-1.00, actualResponse.getLeftDistanceSph());
        assertEquals("6/6", actualResponse.getRightDistanceVa());
        assertEquals("6/6", actualResponse.getLeftDistanceVa());
        assertEquals("Normal", actualResponse.getColourVision());

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(eyeExaminationMapper, times(1))
                .toEntity(request);

        verify(eyeExaminationRepository, times(1))
                .save(examination);

        verify(eyeExaminationMapper, times(1))
                .toResponseDto(savedExamination);
    }

    @Test
    void shouldThrowExceptionWhenRegistrationNotFound() {

        // Arrange
        Long registrationId = 999L;

        EyeExaminationRequestDto request =
                new EyeExaminationRequestDto();

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> eyeExaminationService.createEyeExamination(
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

        verify(eyeExaminationMapper, never())
                .toEntity(any(EyeExaminationRequestDto.class));

        verify(eyeExaminationRepository, never())
                .save(any(EyeExamination.class));
    }

    @Test
    void shouldGetEyeExaminationById() {

        // Arrange
        Long examinationId = 10L;

        Registration registration = new Registration();
        registration.setId(1L);

        EyeExamination examination =
                new EyeExamination();

        examination.setId(examinationId);
        examination.setRegistration(registration);
        examination.setRightDistanceSph(-1.25);
        examination.setLeftDistanceSph(-1.00);
        examination.setRightDistanceVa("6/6");
        examination.setLeftDistanceVa("6/6");
        examination.setColourVision("Normal");

        EyeExaminationResponseDto expectedResponse =
                new EyeExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setRegistrationId(1L);
        expectedResponse.setRightDistanceSph(-1.25);
        expectedResponse.setLeftDistanceSph(-1.00);
        expectedResponse.setRightDistanceVa("6/6");
        expectedResponse.setLeftDistanceVa("6/6");
        expectedResponse.setColourVision("Normal");

        when(eyeExaminationRepository.findById(examinationId))
                .thenReturn(Optional.of(examination));

        when(eyeExaminationMapper.toResponseDto(examination))
                .thenReturn(expectedResponse);

        // Act
        EyeExaminationResponseDto actualResponse =
                eyeExaminationService.getEyeExaminationById(examinationId);

        // Assert
        assertEquals(10L, actualResponse.getId());
        assertEquals(1L, actualResponse.getRegistrationId());
        assertEquals(-1.25, actualResponse.getRightDistanceSph());
        assertEquals(-1.00, actualResponse.getLeftDistanceSph());
        assertEquals("6/6", actualResponse.getRightDistanceVa());
        assertEquals("6/6", actualResponse.getLeftDistanceVa());
        assertEquals("Normal", actualResponse.getColourVision());

        verify(eyeExaminationRepository, times(1))
                .findById(examinationId);

        verify(eyeExaminationMapper, times(1))
                .toResponseDto(examination);
    }

    @Test
    void shouldThrowExceptionWhenEyeExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(eyeExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        EyeExaminationNotFoundException exception =
                assertThrows(
                        EyeExaminationNotFoundException.class,
                        () -> eyeExaminationService
                                .getEyeExaminationById(examinationId)
                );

        assertEquals(
                "Eye examination not found with id: 999",
                exception.getMessage()
        );

        verify(eyeExaminationRepository, times(1))
                .findById(examinationId);

        verify(eyeExaminationMapper, never())
                .toResponseDto(any(EyeExamination.class));
    }

    // =========================================================
// GET BY REGISTRATION ID - SUCCESS
// =========================================================

    @Test
    void shouldGetEyeExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration = new Registration();
        registration.setId(registrationId);

        EyeExamination examination1 = new EyeExamination();
        examination1.setId(10L);
        examination1.setRegistration(registration);

        EyeExamination examination2 = new EyeExamination();
        examination2.setId(11L);
        examination2.setRegistration(registration);

        List<EyeExamination> examinations =
                List.of(examination1, examination2);

        EyeExaminationResponseDto response1 =
                new EyeExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);

        EyeExaminationResponseDto response2 =
                new EyeExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(eyeExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(examinations);

        when(eyeExaminationMapper.toResponseDto(examination1))
                .thenReturn(response1);

        when(eyeExaminationMapper.toResponseDto(examination2))
                .thenReturn(response2);

        // Act
        List<EyeExaminationResponseDto> actualResponses =
                eyeExaminationService
                        .getEyeExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertEquals(2, actualResponses.size());

        assertEquals(10L, actualResponses.get(0).getId());
        assertEquals(1L, actualResponses.get(0).getRegistrationId());

        assertEquals(11L, actualResponses.get(1).getId());
        assertEquals(1L, actualResponses.get(1).getRegistrationId());

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(eyeExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(eyeExaminationMapper, times(1))
                .toResponseDto(examination1);

        verify(eyeExaminationMapper, times(1))
                .toResponseDto(examination2);
    }


// =========================================================
// GET BY REGISTRATION ID - EMPTY LIST
// =========================================================

    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoEyeExaminations() {

        // Arrange
        Long registrationId = 1L;

        Registration registration = new Registration();
        registration.setId(registrationId);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(eyeExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(List.of());

        // Act
        List<EyeExaminationResponseDto> actualResponses =
                eyeExaminationService
                        .getEyeExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertTrue(actualResponses.isEmpty());

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(eyeExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(eyeExaminationMapper, never())
                .toResponseDto(any(EyeExamination.class));
    }


// =========================================================
// GET BY REGISTRATION ID - REGISTRATION NOT FOUND
// =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFoundWhileGettingEyeExaminations() {

        // Arrange
        Long registrationId = 999L;

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> eyeExaminationService
                                .getEyeExaminationsByRegistrationId(
                                        registrationId
                                )
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(eyeExaminationRepository, never())
                .findAllByRegistrationId(anyLong());

        verify(eyeExaminationMapper, never())
                .toResponseDto(any(EyeExamination.class));
    }


// =========================================================
// UPDATE - SUCCESS
// =========================================================

    @Test
    void shouldUpdateEyeExamination() {

        // Arrange
        Long examinationId = 10L;

        EyeExamination existingExamination =
                new EyeExamination();

        existingExamination.setId(examinationId);
        existingExamination.setRightDistanceSph(-1.00);
        existingExamination.setLeftDistanceSph(-0.75);
        existingExamination.setRightDistanceVa("6/9");
        existingExamination.setLeftDistanceVa("6/9");
        existingExamination.setColourVision("Normal");

        EyeExaminationRequestDto request =
                new EyeExaminationRequestDto();

        request.setRightDistanceSph(-1.50);
        request.setLeftDistanceSph(-1.25);
        request.setRightDistanceVa("6/6");
        request.setLeftDistanceVa("6/6");
        request.setColourVision("Normal");

        EyeExaminationResponseDto expectedResponse =
                new EyeExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setRightDistanceSph(-1.50);
        expectedResponse.setLeftDistanceSph(-1.25);
        expectedResponse.setRightDistanceVa("6/6");
        expectedResponse.setLeftDistanceVa("6/6");
        expectedResponse.setColourVision("Normal");

        when(eyeExaminationRepository.findById(examinationId))
                .thenReturn(Optional.of(existingExamination));

        when(eyeExaminationMapper.toResponseDto(existingExamination))
                .thenReturn(expectedResponse);

        // Act
        EyeExaminationResponseDto actualResponse =
                eyeExaminationService.updateEyeExamination(
                        examinationId,
                        request
                );

        // Assert

        // Check that the existing entity was actually modified.
        assertEquals(
                -1.50,
                existingExamination.getRightDistanceSph()
        );

        assertEquals(
                -1.25,
                existingExamination.getLeftDistanceSph()
        );

        assertEquals(
                "6/6",
                existingExamination.getRightDistanceVa()
        );

        assertEquals(
                "6/6",
                existingExamination.getLeftDistanceVa()
        );

        assertEquals(
                "Normal",
                existingExamination.getColourVision()
        );

        // Check returned DTO.
        assertEquals(10L, actualResponse.getId());

        assertEquals(
                -1.50,
                actualResponse.getRightDistanceSph()
        );

        assertEquals(
                -1.25,
                actualResponse.getLeftDistanceSph()
        );

        verify(eyeExaminationRepository, times(1))
                .findById(examinationId);

        verify(eyeExaminationMapper, times(1))
                .toResponseDto(existingExamination);
    }


// =========================================================
// UPDATE - NOT FOUND
// =========================================================

    @Test
    void shouldThrowExceptionWhenUpdatingEyeExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        EyeExaminationRequestDto request =
                new EyeExaminationRequestDto();

        request.setRightDistanceSph(-1.50);
        request.setLeftDistanceSph(-1.25);

        when(eyeExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        EyeExaminationNotFoundException exception =
                assertThrows(
                        EyeExaminationNotFoundException.class,
                        () -> eyeExaminationService
                                .updateEyeExamination(
                                        examinationId,
                                        request
                                )
                );

        assertEquals(
                "Eye examination not found with id: 999",
                exception.getMessage()
        );

        verify(eyeExaminationRepository, times(1))
                .findById(examinationId);

        verify(eyeExaminationMapper, never())
                .toResponseDto(any(EyeExamination.class));
    }


// =========================================================
// DELETE - SUCCESS
// =========================================================

    @Test
    void shouldDeleteEyeExamination() {

        // Arrange
        Long examinationId = 10L;

        EyeExamination examination =
                new EyeExamination();

        examination.setId(examinationId);

        when(eyeExaminationRepository.findById(examinationId))
                .thenReturn(Optional.of(examination));

        // Act
        eyeExaminationService.deleteEyeExamination(examinationId);

        // Assert
        verify(eyeExaminationRepository, times(1))
                .findById(examinationId);

        verify(eyeExaminationRepository, times(1))
                .delete(examination);
    }


// =========================================================
// DELETE - NOT FOUND
// =========================================================

    @Test
    void shouldThrowExceptionWhenDeletingEyeExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(eyeExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        EyeExaminationNotFoundException exception =
                assertThrows(
                        EyeExaminationNotFoundException.class,
                        () -> eyeExaminationService
                                .deleteEyeExamination(examinationId)
                );

        assertEquals(
                "Eye examination not found with id: 999",
                exception.getMessage()
        );

        verify(eyeExaminationRepository, times(1))
                .findById(examinationId);

        verify(eyeExaminationRepository, never())
                .delete(any(EyeExamination.class));
    }
}

