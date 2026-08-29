package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.SerumCholinesteraseExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.SerumCholinesteraseExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.SerumCholinesteraseExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.SerumCholinesteraseExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.SerumCholinesteraseExaminationRepository;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.SerumCholinesteraseExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SerumCholinesteraseExaminationServiceImplTest {

    @Mock
    private SerumCholinesteraseExaminationRepository
            serumCholinesteraseExaminationRepository;

    @Mock
    private SerumCholinesteraseExaminationMapper
            serumCholinesteraseExaminationMapper;

    @Mock
    private RegistrationRepository
            registrationRepository;

    @InjectMocks
    private SerumCholinesteraseExaminationServiceImpl
            serumCholinesteraseExaminationService;


    // =========================================================
    // CREATE - SUCCESS
    // =========================================================

    @Test
    void shouldCreateSerumCholinesteraseExamination() {

        // Arrange
        Long registrationId = 1L;

        SerumCholinesteraseExaminationRequestDto request =
                new SerumCholinesteraseExaminationRequestDto();

        request.setResultValue(8883.15);
        request.setMethod("Butyrylthio Choline Method");
        request.setSampleType("Serum");
        request.setReferenceRange("4000 - 11500");
        request.setUnit("U/L");
        request.setRemarks("Within reference range");
        request.setPathologistName("Dr Radha");

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        SerumCholinesteraseExamination examination =
                new SerumCholinesteraseExamination();

        SerumCholinesteraseExamination savedExamination =
                new SerumCholinesteraseExamination();

        savedExamination.setId(10L);
        savedExamination.setRegistration(registration);
        savedExamination.setResultValue(8883.15);
        savedExamination.setMethod(
                "Butyrylthio Choline Method"
        );

        SerumCholinesteraseExaminationResponseDto expectedResponse =
                new SerumCholinesteraseExaminationResponseDto();

        expectedResponse.setId(10L);
        expectedResponse.setRegistrationId(registrationId);
        expectedResponse.setResultValue(8883.15);
        expectedResponse.setMethod(
                "Butyrylthio Choline Method"
        );

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(serumCholinesteraseExaminationMapper.toEntity(request))
                .thenReturn(examination);

        when(serumCholinesteraseExaminationRepository.save(examination))
                .thenReturn(savedExamination);

        when(serumCholinesteraseExaminationMapper
                .toResponseDto(savedExamination))
                .thenReturn(expectedResponse);

        // Act
        SerumCholinesteraseExaminationResponseDto actualResponse =
                serumCholinesteraseExaminationService
                        .createSerumCholinesteraseExamination(
                                registrationId,
                                request
                        );

        // Assert
        assertEquals(
                10L,
                actualResponse.getId()
        );

        assertEquals(
                1L,
                actualResponse.getRegistrationId()
        );

        assertEquals(
                8883.15,
                actualResponse.getResultValue()
        );

        assertEquals(
                "Butyrylthio Choline Method",
                actualResponse.getMethod()
        );

        assertEquals(
                registration,
                examination.getRegistration()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(serumCholinesteraseExaminationMapper, times(1))
                .toEntity(request);

        verify(serumCholinesteraseExaminationRepository, times(1))
                .save(examination);

        verify(serumCholinesteraseExaminationMapper, times(1))
                .toResponseDto(savedExamination);
    }


    // =========================================================
    // CREATE - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFound() {

        // Arrange
        Long registrationId = 999L;

        SerumCholinesteraseExaminationRequestDto request =
                new SerumCholinesteraseExaminationRequestDto();

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> serumCholinesteraseExaminationService
                                .createSerumCholinesteraseExamination(
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

        verify(serumCholinesteraseExaminationMapper, never())
                .toEntity(
                        any(SerumCholinesteraseExaminationRequestDto.class)
                );

        verify(serumCholinesteraseExaminationRepository, never())
                .save(
                        any(SerumCholinesteraseExamination.class)
                );
    }


    // =========================================================
    // GET BY ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetSerumCholinesteraseExaminationById() {

        // Arrange
        Long examinationId = 10L;

        Registration registration =
                new Registration();

        registration.setId(1L);

        SerumCholinesteraseExamination examination =
                new SerumCholinesteraseExamination();

        examination.setId(examinationId);
        examination.setRegistration(registration);
        examination.setResultValue(8883.15);
        examination.setMethod(
                "Butyrylthio Choline Method"
        );

        SerumCholinesteraseExaminationResponseDto expectedResponse =
                new SerumCholinesteraseExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setRegistrationId(1L);
        expectedResponse.setResultValue(8883.15);
        expectedResponse.setMethod(
                "Butyrylthio Choline Method"
        );

        when(serumCholinesteraseExaminationRepository
                .findById(examinationId))
                .thenReturn(Optional.of(examination));

        when(serumCholinesteraseExaminationMapper
                .toResponseDto(examination))
                .thenReturn(expectedResponse);

        // Act
        SerumCholinesteraseExaminationResponseDto actualResponse =
                serumCholinesteraseExaminationService
                        .getSerumCholinesteraseExaminationById(
                                examinationId
                        );

        // Assert
        assertEquals(
                10L,
                actualResponse.getId()
        );

        assertEquals(
                1L,
                actualResponse.getRegistrationId()
        );

        assertEquals(
                8883.15,
                actualResponse.getResultValue()
        );

        assertEquals(
                "Butyrylthio Choline Method",
                actualResponse.getMethod()
        );

        verify(serumCholinesteraseExaminationRepository, times(1))
                .findById(examinationId);

        verify(serumCholinesteraseExaminationMapper, times(1))
                .toResponseDto(examination);
    }


    // =========================================================
    // GET BY ID - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenSerumCholinesteraseExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(serumCholinesteraseExaminationRepository
                .findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        SerumCholinesteraseExaminationNotFoundException exception =
                assertThrows(
                        SerumCholinesteraseExaminationNotFoundException.class,
                        () -> serumCholinesteraseExaminationService
                                .getSerumCholinesteraseExaminationById(
                                        examinationId
                                )
                );

        assertEquals(
                "Serum cholinesterase examination not found with id: 999",
                exception.getMessage()
        );

        verify(serumCholinesteraseExaminationRepository, times(1))
                .findById(examinationId);

        verify(serumCholinesteraseExaminationMapper, never())
                .toResponseDto(
                        any(SerumCholinesteraseExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetSerumCholinesteraseExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        SerumCholinesteraseExamination examination1 =
                new SerumCholinesteraseExamination();

        examination1.setId(10L);
        examination1.setRegistration(registration);
        examination1.setResultValue(8883.15);

        SerumCholinesteraseExamination examination2 =
                new SerumCholinesteraseExamination();

        examination2.setId(11L);
        examination2.setRegistration(registration);
        examination2.setResultValue(9000.00);

        List<SerumCholinesteraseExamination> examinations =
                List.of(
                        examination1,
                        examination2
                );

        SerumCholinesteraseExaminationResponseDto response1 =
                new SerumCholinesteraseExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setResultValue(8883.15);

        SerumCholinesteraseExaminationResponseDto response2 =
                new SerumCholinesteraseExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setResultValue(9000.00);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(serumCholinesteraseExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(examinations);

        when(serumCholinesteraseExaminationMapper
                .toResponseDto(examination1))
                .thenReturn(response1);

        when(serumCholinesteraseExaminationMapper
                .toResponseDto(examination2))
                .thenReturn(response2);

        // Act
        List<SerumCholinesteraseExaminationResponseDto> actualResponses =
                serumCholinesteraseExaminationService
                        .getSerumCholinesteraseExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertEquals(
                2,
                actualResponses.size()
        );

        assertEquals(
                10L,
                actualResponses.get(0).getId()
        );

        assertEquals(
                8883.15,
                actualResponses.get(0).getResultValue()
        );

        assertEquals(
                11L,
                actualResponses.get(1).getId()
        );

        assertEquals(
                9000.00,
                actualResponses.get(1).getResultValue()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(serumCholinesteraseExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(serumCholinesteraseExaminationMapper, times(1))
                .toResponseDto(examination1);

        verify(serumCholinesteraseExaminationMapper, times(1))
                .toResponseDto(examination2);
    }


    // =========================================================
    // GET BY REGISTRATION ID - EMPTY LIST
    // =========================================================

    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoSerumCholinesteraseExaminations() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(serumCholinesteraseExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(List.of());

        // Act
        List<SerumCholinesteraseExaminationResponseDto> actualResponses =
                serumCholinesteraseExaminationService
                        .getSerumCholinesteraseExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertTrue(
                actualResponses.isEmpty()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(serumCholinesteraseExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(serumCholinesteraseExaminationMapper, never())
                .toResponseDto(
                        any(SerumCholinesteraseExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFoundWhileGettingSerumCholinesteraseExaminations() {

        // Arrange
        Long registrationId = 999L;

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> serumCholinesteraseExaminationService
                                .getSerumCholinesteraseExaminationsByRegistrationId(
                                        registrationId
                                )
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(serumCholinesteraseExaminationRepository, never())
                .findAllByRegistrationId(
                        anyLong()
                );

        verify(serumCholinesteraseExaminationMapper, never())
                .toResponseDto(
                        any(SerumCholinesteraseExamination.class)
                );
    }


    // =========================================================
    // UPDATE - SUCCESS
    // =========================================================

    @Test
    void shouldUpdateSerumCholinesteraseExamination() {

        // Arrange
        Long examinationId = 10L;

        SerumCholinesteraseExamination existingExamination =
                new SerumCholinesteraseExamination();

        existingExamination.setId(examinationId);
        existingExamination.setResultValue(8883.15);

        existingExamination.setMethod(
                "Butyrylthio Choline Method"
        );

        existingExamination.setSampleType(
                "Serum"
        );

        existingExamination.setReferenceRange(
                "4000 - 11500"
        );

        existingExamination.setUnit(
                "U/L"
        );

        existingExamination.setRemarks(
                "Within reference range"
        );

        existingExamination.setPathologistName(
                "Dr Radha"
        );

        SerumCholinesteraseExaminationRequestDto request =
                new SerumCholinesteraseExaminationRequestDto();

        request.setResultValue(9100.25);

        request.setMethod(
                "Butyrylthio Choline Method"
        );

        request.setSampleType(
                "Serum"
        );

        request.setReferenceRange(
                "4000 - 11500"
        );

        request.setUnit(
                "U/L"
        );

        request.setRemarks(
                "Updated result"
        );

        request.setPathologistName(
                "Dr Anil Kumar"
        );

        SerumCholinesteraseExaminationResponseDto expectedResponse =
                new SerumCholinesteraseExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setResultValue(9100.25);
        expectedResponse.setRemarks("Updated result");
        expectedResponse.setPathologistName(
                "Dr Anil Kumar"
        );

        when(serumCholinesteraseExaminationRepository
                .findById(examinationId))
                .thenReturn(
                        Optional.of(existingExamination)
                );

        when(serumCholinesteraseExaminationRepository
                .save(existingExamination))
                .thenReturn(existingExamination);

        when(serumCholinesteraseExaminationMapper
                .toResponseDto(existingExamination))
                .thenReturn(expectedResponse);

        // Act
        SerumCholinesteraseExaminationResponseDto actualResponse =
                serumCholinesteraseExaminationService
                        .updateSerumCholinesteraseExamination(
                                examinationId,
                                request
                        );

        // Assert

        // Check actual entity was modified.
        assertEquals(
                9100.25,
                existingExamination.getResultValue()
        );

        assertEquals(
                "Butyrylthio Choline Method",
                existingExamination.getMethod()
        );

        assertEquals(
                "Serum",
                existingExamination.getSampleType()
        );

        assertEquals(
                "4000 - 11500",
                existingExamination.getReferenceRange()
        );

        assertEquals(
                "U/L",
                existingExamination.getUnit()
        );

        assertEquals(
                "Updated result",
                existingExamination.getRemarks()
        );

        assertEquals(
                "Dr Anil Kumar",
                existingExamination.getPathologistName()
        );

        // Check returned DTO.
        assertEquals(
                10L,
                actualResponse.getId()
        );

        assertEquals(
                9100.25,
                actualResponse.getResultValue()
        );

        assertEquals(
                "Updated result",
                actualResponse.getRemarks()
        );

        verify(serumCholinesteraseExaminationRepository, times(1))
                .findById(examinationId);

        verify(serumCholinesteraseExaminationRepository, times(1))
                .save(existingExamination);

        verify(serumCholinesteraseExaminationMapper, times(1))
                .toResponseDto(existingExamination);
    }


    // =========================================================
    // UPDATE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenUpdatingSerumCholinesteraseExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        SerumCholinesteraseExaminationRequestDto request =
                new SerumCholinesteraseExaminationRequestDto();

        request.setResultValue(9100.25);

        when(serumCholinesteraseExaminationRepository
                .findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        SerumCholinesteraseExaminationNotFoundException exception =
                assertThrows(
                        SerumCholinesteraseExaminationNotFoundException.class,
                        () -> serumCholinesteraseExaminationService
                                .updateSerumCholinesteraseExamination(
                                        examinationId,
                                        request
                                )
                );

        assertEquals(
                "Serum cholinesterase examination not found with id: 999",
                exception.getMessage()
        );

        verify(serumCholinesteraseExaminationRepository, times(1))
                .findById(examinationId);

        verify(serumCholinesteraseExaminationRepository, never())
                .save(
                        any(SerumCholinesteraseExamination.class)
                );

        verify(serumCholinesteraseExaminationMapper, never())
                .toResponseDto(
                        any(SerumCholinesteraseExamination.class)
                );
    }


    // =========================================================
    // DELETE - SUCCESS
    // =========================================================

    @Test
    void shouldDeleteSerumCholinesteraseExamination() {

        // Arrange
        Long examinationId = 10L;

        SerumCholinesteraseExamination examination =
                new SerumCholinesteraseExamination();

        examination.setId(examinationId);

        when(serumCholinesteraseExaminationRepository
                .findById(examinationId))
                .thenReturn(
                        Optional.of(examination)
                );

        // Act
        serumCholinesteraseExaminationService
                .deleteSerumCholinesteraseExamination(
                        examinationId
                );

        // Assert
        verify(serumCholinesteraseExaminationRepository, times(1))
                .findById(examinationId);

        verify(serumCholinesteraseExaminationRepository, times(1))
                .delete(examination);
    }


    // =========================================================
    // DELETE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenDeletingSerumCholinesteraseExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(serumCholinesteraseExaminationRepository
                .findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        SerumCholinesteraseExaminationNotFoundException exception =
                assertThrows(
                        SerumCholinesteraseExaminationNotFoundException.class,
                        () -> serumCholinesteraseExaminationService
                                .deleteSerumCholinesteraseExamination(
                                        examinationId
                                )
                );

        assertEquals(
                "Serum cholinesterase examination not found with id: 999",
                exception.getMessage()
        );

        verify(serumCholinesteraseExaminationRepository, times(1))
                .findById(examinationId);

        verify(serumCholinesteraseExaminationRepository, never())
                .delete(
                        any(SerumCholinesteraseExamination.class)
                );
    }
}