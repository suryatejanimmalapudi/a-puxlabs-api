package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.RenalFunctionExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.RenalFunctionExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.RenalFunctionExamination;
import com.apuxlabs.apuxlabs_api.examination.enums.RenalFunctionTestType;
import com.apuxlabs.apuxlabs_api.examination.mapper.RenalFunctionExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.RenalFunctionExaminationRepository;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RenalFunctionExaminationNotFoundException;
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
class RenalFunctionExaminationServiceImplTest {

    @Mock
    private RenalFunctionExaminationRepository
            renalFunctionExaminationRepository;

    @Mock
    private RenalFunctionExaminationMapper
            renalFunctionExaminationMapper;

    @Mock
    private RegistrationRepository
            registrationRepository;

    @InjectMocks
    private RenalFunctionExaminationServiceImpl
            renalFunctionExaminationService;


    // =========================================================
    // CREATE - SUCCESS
    // =========================================================

    @Test
    void shouldCreateRenalFunctionExamination() {

        // Arrange
        Long registrationId = 1L;

        RenalFunctionExaminationRequestDto request =
                new RenalFunctionExaminationRequestDto();

        request.setTestType(
                RenalFunctionTestType.UREA
        );

        request.setResultValue(
                19.82
        );

        request.setSampleType(
                "Serum"
        );

        request.setReferenceRange(
                "7 - 40"
        );

        request.setUnit(
                "mg/dl"
        );

        request.setRemarks(
                "Within reference range"
        );

        request.setPathologistName(
                "Dr Radha"
        );

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        RenalFunctionExamination examination =
                new RenalFunctionExamination();

        RenalFunctionExamination savedExamination =
                new RenalFunctionExamination();

        savedExamination.setId(10L);
        savedExamination.setRegistration(registration);

        savedExamination.setTestType(
                RenalFunctionTestType.UREA
        );

        savedExamination.setResultValue(
                19.82
        );

        RenalFunctionExaminationResponseDto expectedResponse =
                new RenalFunctionExaminationResponseDto();

        expectedResponse.setId(10L);

        expectedResponse.setRegistrationId(
                registrationId
        );

        expectedResponse.setTestType(
                RenalFunctionTestType.UREA
        );

        expectedResponse.setResultValue(
                19.82
        );

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(renalFunctionExaminationMapper.toEntity(request))
                .thenReturn(examination);

        when(renalFunctionExaminationRepository.save(examination))
                .thenReturn(savedExamination);

        when(renalFunctionExaminationMapper
                .toResponseDto(savedExamination))
                .thenReturn(expectedResponse);

        // Act
        RenalFunctionExaminationResponseDto actualResponse =
                renalFunctionExaminationService
                        .createRenalFunctionExamination(
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
                RenalFunctionTestType.UREA,
                actualResponse.getTestType()
        );

        assertEquals(
                19.82,
                actualResponse.getResultValue()
        );

        assertEquals(
                registration,
                examination.getRegistration()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(renalFunctionExaminationMapper, times(1))
                .toEntity(request);

        verify(renalFunctionExaminationRepository, times(1))
                .save(examination);

        verify(renalFunctionExaminationMapper, times(1))
                .toResponseDto(savedExamination);
    }


    // =========================================================
    // CREATE - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFound() {

        // Arrange
        Long registrationId = 999L;

        RenalFunctionExaminationRequestDto request =
                new RenalFunctionExaminationRequestDto();

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> renalFunctionExaminationService
                                .createRenalFunctionExamination(
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

        verify(renalFunctionExaminationMapper, never())
                .toEntity(
                        any(RenalFunctionExaminationRequestDto.class)
                );

        verify(renalFunctionExaminationRepository, never())
                .save(
                        any(RenalFunctionExamination.class)
                );
    }


    // =========================================================
    // GET BY ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetRenalFunctionExaminationById() {

        // Arrange
        Long examinationId = 10L;

        Registration registration =
                new Registration();

        registration.setId(1L);

        RenalFunctionExamination examination =
                new RenalFunctionExamination();

        examination.setId(examinationId);
        examination.setRegistration(registration);

        examination.setTestType(
                RenalFunctionTestType.CREATININE
        );

        examination.setResultValue(
                0.73
        );

        RenalFunctionExaminationResponseDto expectedResponse =
                new RenalFunctionExaminationResponseDto();

        expectedResponse.setId(examinationId);

        expectedResponse.setRegistrationId(
                1L
        );

        expectedResponse.setTestType(
                RenalFunctionTestType.CREATININE
        );

        expectedResponse.setResultValue(
                0.73
        );

        when(renalFunctionExaminationRepository.findById(examinationId))
                .thenReturn(Optional.of(examination));

        when(renalFunctionExaminationMapper.toResponseDto(examination))
                .thenReturn(expectedResponse);

        // Act
        RenalFunctionExaminationResponseDto actualResponse =
                renalFunctionExaminationService
                        .getRenalFunctionExaminationById(
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
                RenalFunctionTestType.CREATININE,
                actualResponse.getTestType()
        );

        assertEquals(
                0.73,
                actualResponse.getResultValue()
        );

        verify(renalFunctionExaminationRepository, times(1))
                .findById(examinationId);

        verify(renalFunctionExaminationMapper, times(1))
                .toResponseDto(examination);
    }


    // =========================================================
    // GET BY ID - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRenalFunctionExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(renalFunctionExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RenalFunctionExaminationNotFoundException exception =
                assertThrows(
                        RenalFunctionExaminationNotFoundException.class,
                        () -> renalFunctionExaminationService
                                .getRenalFunctionExaminationById(
                                        examinationId
                                )
                );

        assertEquals(
                "Renal function examination not found with id: 999",
                exception.getMessage()
        );

        verify(renalFunctionExaminationRepository, times(1))
                .findById(examinationId);

        verify(renalFunctionExaminationMapper, never())
                .toResponseDto(
                        any(RenalFunctionExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetRenalFunctionExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        RenalFunctionExamination examination1 =
                new RenalFunctionExamination();

        examination1.setId(10L);
        examination1.setRegistration(registration);

        examination1.setTestType(
                RenalFunctionTestType.UREA
        );

        examination1.setResultValue(
                19.82
        );

        RenalFunctionExamination examination2 =
                new RenalFunctionExamination();

        examination2.setId(11L);
        examination2.setRegistration(registration);

        examination2.setTestType(
                RenalFunctionTestType.CREATININE
        );

        examination2.setResultValue(
                0.73
        );

        List<RenalFunctionExamination> examinations =
                List.of(
                        examination1,
                        examination2
                );

        RenalFunctionExaminationResponseDto response1 =
                new RenalFunctionExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setTestType(RenalFunctionTestType.UREA);
        response1.setResultValue(19.82);

        RenalFunctionExaminationResponseDto response2 =
                new RenalFunctionExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setTestType(RenalFunctionTestType.CREATININE);
        response2.setResultValue(0.73);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(renalFunctionExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(examinations);

        when(renalFunctionExaminationMapper.toResponseDto(examination1))
                .thenReturn(response1);

        when(renalFunctionExaminationMapper.toResponseDto(examination2))
                .thenReturn(response2);

        // Act
        List<RenalFunctionExaminationResponseDto> actualResponses =
                renalFunctionExaminationService
                        .getRenalFunctionExaminationsByRegistrationId(
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
                RenalFunctionTestType.UREA,
                actualResponses.get(0).getTestType()
        );

        assertEquals(
                11L,
                actualResponses.get(1).getId()
        );

        assertEquals(
                RenalFunctionTestType.CREATININE,
                actualResponses.get(1).getTestType()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(renalFunctionExaminationRepository, times(1))
                .findAllByRegistrationId(
                        registrationId
                );

        verify(renalFunctionExaminationMapper, times(1))
                .toResponseDto(examination1);

        verify(renalFunctionExaminationMapper, times(1))
                .toResponseDto(examination2);
    }


    // =========================================================
    // GET BY REGISTRATION ID - EMPTY LIST
    // =========================================================

    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoRenalFunctionExaminations() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(renalFunctionExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(List.of());

        // Act
        List<RenalFunctionExaminationResponseDto> actualResponses =
                renalFunctionExaminationService
                        .getRenalFunctionExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertTrue(
                actualResponses.isEmpty()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(renalFunctionExaminationRepository, times(1))
                .findAllByRegistrationId(
                        registrationId
                );

        verify(renalFunctionExaminationMapper, never())
                .toResponseDto(
                        any(RenalFunctionExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFoundWhileGettingRenalFunctionExaminations() {

        // Arrange
        Long registrationId = 999L;

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> renalFunctionExaminationService
                                .getRenalFunctionExaminationsByRegistrationId(
                                        registrationId
                                )
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(renalFunctionExaminationRepository, never())
                .findAllByRegistrationId(
                        anyLong()
                );

        verify(renalFunctionExaminationMapper, never())
                .toResponseDto(
                        any(RenalFunctionExamination.class)
                );
    }


    // =========================================================
    // GET UREA BY REGISTRATION ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetUreaExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        RenalFunctionExamination examination1 =
                new RenalFunctionExamination();

        examination1.setId(10L);
        examination1.setRegistration(registration);
        examination1.setTestType(RenalFunctionTestType.UREA);
        examination1.setResultValue(19.82);

        RenalFunctionExamination examination2 =
                new RenalFunctionExamination();

        examination2.setId(11L);
        examination2.setRegistration(registration);
        examination2.setTestType(RenalFunctionTestType.UREA);
        examination2.setResultValue(21.40);

        List<RenalFunctionExamination> examinations =
                List.of(
                        examination1,
                        examination2
                );

        RenalFunctionExaminationResponseDto response1 =
                new RenalFunctionExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setTestType(RenalFunctionTestType.UREA);
        response1.setResultValue(19.82);

        RenalFunctionExaminationResponseDto response2 =
                new RenalFunctionExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setTestType(RenalFunctionTestType.UREA);
        response2.setResultValue(21.40);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(renalFunctionExaminationRepository
                .findAllByRegistrationIdAndTestType(
                        registrationId,
                        RenalFunctionTestType.UREA
                ))
                .thenReturn(examinations);

        when(renalFunctionExaminationMapper.toResponseDto(examination1))
                .thenReturn(response1);

        when(renalFunctionExaminationMapper.toResponseDto(examination2))
                .thenReturn(response2);

        // Act
        List<RenalFunctionExaminationResponseDto> actualResponses =
                renalFunctionExaminationService
                        .getUreaExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertEquals(
                2,
                actualResponses.size()
        );

        assertEquals(
                RenalFunctionTestType.UREA,
                actualResponses.get(0).getTestType()
        );

        assertEquals(
                19.82,
                actualResponses.get(0).getResultValue()
        );

        assertEquals(
                RenalFunctionTestType.UREA,
                actualResponses.get(1).getTestType()
        );

        assertEquals(
                21.40,
                actualResponses.get(1).getResultValue()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(renalFunctionExaminationRepository, times(1))
                .findAllByRegistrationIdAndTestType(
                        registrationId,
                        RenalFunctionTestType.UREA
                );

        verify(renalFunctionExaminationMapper, times(1))
                .toResponseDto(examination1);

        verify(renalFunctionExaminationMapper, times(1))
                .toResponseDto(examination2);
    }


    // =========================================================
    // GET CREATININE BY REGISTRATION ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetCreatinineExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        RenalFunctionExamination examination =
                new RenalFunctionExamination();

        examination.setId(12L);
        examination.setRegistration(registration);
        examination.setTestType(RenalFunctionTestType.CREATININE);
        examination.setResultValue(0.73);

        RenalFunctionExaminationResponseDto expectedResponse =
                new RenalFunctionExaminationResponseDto();

        expectedResponse.setId(12L);
        expectedResponse.setRegistrationId(registrationId);
        expectedResponse.setTestType(RenalFunctionTestType.CREATININE);
        expectedResponse.setResultValue(0.73);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(renalFunctionExaminationRepository
                .findAllByRegistrationIdAndTestType(
                        registrationId,
                        RenalFunctionTestType.CREATININE
                ))
                .thenReturn(
                        List.of(examination)
                );

        when(renalFunctionExaminationMapper.toResponseDto(examination))
                .thenReturn(expectedResponse);

        // Act
        List<RenalFunctionExaminationResponseDto> actualResponses =
                renalFunctionExaminationService
                        .getCreatinineExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertEquals(
                1,
                actualResponses.size()
        );

        assertEquals(
                12L,
                actualResponses.get(0).getId()
        );

        assertEquals(
                RenalFunctionTestType.CREATININE,
                actualResponses.get(0).getTestType()
        );

        assertEquals(
                0.73,
                actualResponses.get(0).getResultValue()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(renalFunctionExaminationRepository, times(1))
                .findAllByRegistrationIdAndTestType(
                        registrationId,
                        RenalFunctionTestType.CREATININE
                );

        verify(renalFunctionExaminationMapper, times(1))
                .toResponseDto(examination);
    }


    // =========================================================
    // UPDATE - SUCCESS
    // =========================================================

    @Test
    void shouldUpdateRenalFunctionExamination() {

        // Arrange
        Long examinationId = 10L;

        RenalFunctionExamination existingExamination =
                new RenalFunctionExamination();

        existingExamination.setId(examinationId);

        existingExamination.setTestType(
                RenalFunctionTestType.UREA
        );

        existingExamination.setResultValue(
                19.82
        );

        existingExamination.setSampleType(
                "Serum"
        );

        existingExamination.setReferenceRange(
                "7 - 40"
        );

        existingExamination.setUnit(
                "mg/dl"
        );

        existingExamination.setRemarks(
                "Within reference range"
        );

        existingExamination.setPathologistName(
                "Dr Radha"
        );

        RenalFunctionExaminationRequestDto request =
                new RenalFunctionExaminationRequestDto();

        request.setTestType(
                RenalFunctionTestType.UREA
        );

        request.setResultValue(
                21.40
        );

        request.setSampleType(
                "Serum"
        );

        request.setReferenceRange(
                "7 - 40"
        );

        request.setUnit(
                "mg/dl"
        );

        request.setRemarks(
                "Updated result"
        );

        request.setPathologistName(
                "Dr Anil Kumar"
        );

        RenalFunctionExaminationResponseDto expectedResponse =
                new RenalFunctionExaminationResponseDto();

        expectedResponse.setId(examinationId);

        expectedResponse.setTestType(
                RenalFunctionTestType.UREA
        );

        expectedResponse.setResultValue(
                21.40
        );

        expectedResponse.setRemarks(
                "Updated result"
        );

        expectedResponse.setPathologistName(
                "Dr Anil Kumar"
        );

        when(renalFunctionExaminationRepository.findById(examinationId))
                .thenReturn(
                        Optional.of(existingExamination)
                );

        when(renalFunctionExaminationRepository.save(existingExamination))
                .thenReturn(existingExamination);

        when(renalFunctionExaminationMapper
                .toResponseDto(existingExamination))
                .thenReturn(expectedResponse);

        // Act
        RenalFunctionExaminationResponseDto actualResponse =
                renalFunctionExaminationService
                        .updateRenalFunctionExamination(
                                examinationId,
                                request
                        );

        // Assert

        // Check actual entity was modified.
        assertEquals(
                RenalFunctionTestType.UREA,
                existingExamination.getTestType()
        );

        assertEquals(
                21.40,
                existingExamination.getResultValue()
        );

        assertEquals(
                "Serum",
                existingExamination.getSampleType()
        );

        assertEquals(
                "7 - 40",
                existingExamination.getReferenceRange()
        );

        assertEquals(
                "mg/dl",
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
                21.40,
                actualResponse.getResultValue()
        );

        assertEquals(
                "Updated result",
                actualResponse.getRemarks()
        );

        verify(renalFunctionExaminationRepository, times(1))
                .findById(examinationId);

        verify(renalFunctionExaminationRepository, times(1))
                .save(existingExamination);

        verify(renalFunctionExaminationMapper, times(1))
                .toResponseDto(existingExamination);
    }


    // =========================================================
    // UPDATE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenUpdatingRenalFunctionExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        RenalFunctionExaminationRequestDto request =
                new RenalFunctionExaminationRequestDto();

        request.setTestType(
                RenalFunctionTestType.UREA
        );

        request.setResultValue(
                21.40
        );

        when(renalFunctionExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RenalFunctionExaminationNotFoundException exception =
                assertThrows(
                        RenalFunctionExaminationNotFoundException.class,
                        () -> renalFunctionExaminationService
                                .updateRenalFunctionExamination(
                                        examinationId,
                                        request
                                )
                );

        assertEquals(
                "Renal function examination not found with id: 999",
                exception.getMessage()
        );

        verify(renalFunctionExaminationRepository, times(1))
                .findById(examinationId);

        verify(renalFunctionExaminationRepository, never())
                .save(
                        any(RenalFunctionExamination.class)
                );

        verify(renalFunctionExaminationMapper, never())
                .toResponseDto(
                        any(RenalFunctionExamination.class)
                );
    }


    // =========================================================
    // DELETE - SUCCESS
    // =========================================================

    @Test
    void shouldDeleteRenalFunctionExamination() {

        // Arrange
        Long examinationId = 10L;

        RenalFunctionExamination examination =
                new RenalFunctionExamination();

        examination.setId(examinationId);

        when(renalFunctionExaminationRepository.findById(examinationId))
                .thenReturn(
                        Optional.of(examination)
                );

        // Act
        renalFunctionExaminationService
                .deleteRenalFunctionExamination(
                        examinationId
                );

        // Assert
        verify(renalFunctionExaminationRepository, times(1))
                .findById(examinationId);

        verify(renalFunctionExaminationRepository, times(1))
                .delete(examination);
    }


    // =========================================================
    // DELETE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenDeletingRenalFunctionExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(renalFunctionExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RenalFunctionExaminationNotFoundException exception =
                assertThrows(
                        RenalFunctionExaminationNotFoundException.class,
                        () -> renalFunctionExaminationService
                                .deleteRenalFunctionExamination(
                                        examinationId
                                )
                );

        assertEquals(
                "Renal function examination not found with id: 999",
                exception.getMessage()
        );

        verify(renalFunctionExaminationRepository, times(1))
                .findById(examinationId);

        verify(renalFunctionExaminationRepository, never())
                .delete(
                        any(RenalFunctionExamination.class)
                );
    }
}