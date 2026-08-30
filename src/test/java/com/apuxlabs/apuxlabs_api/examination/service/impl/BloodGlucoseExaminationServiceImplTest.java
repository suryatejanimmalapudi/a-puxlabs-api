package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.BloodGlucoseExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.BloodGlucoseExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.BloodGlucoseExamination;
import com.apuxlabs.apuxlabs_api.examination.enums.BloodGlucoseTestType;
import com.apuxlabs.apuxlabs_api.examination.mapper.BloodGlucoseExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.BloodGlucoseExaminationRepository;
import com.apuxlabs.apuxlabs_api.exception.BloodGlucoseExaminationNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BloodGlucoseExaminationServiceImplTest {

    @Mock
    private BloodGlucoseExaminationRepository
            bloodGlucoseExaminationRepository;

    @Mock
    private BloodGlucoseExaminationMapper
            bloodGlucoseExaminationMapper;

    @Mock
    private RegistrationRepository
            registrationRepository;

    @InjectMocks
    private BloodGlucoseExaminationServiceImpl
            bloodGlucoseExaminationService;


    // =========================================================
    // CREATE - SUCCESS
    // =========================================================

    @Test
    void shouldCreateBloodGlucoseExamination() {

        // Arrange
        Long registrationId = 1L;

        BloodGlucoseExaminationRequestDto request =
                new BloodGlucoseExaminationRequestDto();

        request.setTestType(
                BloodGlucoseTestType.FBS
        );

        request.setGlucoseValue(
                108.92
        );

        request.setSampleType(
                "Fluoride plasma"
        );

        request.setReferenceRange(
                "70 - 110"
        );

        request.setUnit(
                "mg/dl"
        );

        request.setRemarks(
                "Normal"
        );

        request.setPathologistName(
                "Dr Radha"
        );

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        BloodGlucoseExamination examination =
                new BloodGlucoseExamination();

        BloodGlucoseExamination savedExamination =
                new BloodGlucoseExamination();

        savedExamination.setId(10L);
        savedExamination.setRegistration(registration);

        savedExamination.setTestType(
                BloodGlucoseTestType.FBS
        );

        savedExamination.setGlucoseValue(
                108.92
        );

        BloodGlucoseExaminationResponseDto expectedResponse =
                new BloodGlucoseExaminationResponseDto();

        expectedResponse.setId(10L);

        expectedResponse.setRegistrationId(
                registrationId
        );

        expectedResponse.setTestType(
                BloodGlucoseTestType.FBS
        );

        expectedResponse.setGlucoseValue(
                108.92
        );

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(bloodGlucoseExaminationMapper.toEntity(request))
                .thenReturn(examination);

        when(bloodGlucoseExaminationRepository.save(examination))
                .thenReturn(savedExamination);

        when(bloodGlucoseExaminationMapper
                .toResponseDto(savedExamination))
                .thenReturn(expectedResponse);

        // Act
        BloodGlucoseExaminationResponseDto actualResponse =
                bloodGlucoseExaminationService
                        .createBloodGlucoseExamination(
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
                BloodGlucoseTestType.FBS,
                actualResponse.getTestType()
        );

        assertEquals(
                108.92,
                actualResponse.getGlucoseValue()
        );

        assertEquals(
                registration,
                examination.getRegistration()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(bloodGlucoseExaminationMapper, times(1))
                .toEntity(request);

        verify(bloodGlucoseExaminationRepository, times(1))
                .save(examination);

        verify(bloodGlucoseExaminationMapper, times(1))
                .toResponseDto(savedExamination);
    }


    // =========================================================
    // CREATE - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFound() {

        // Arrange
        Long registrationId = 999L;

        BloodGlucoseExaminationRequestDto request =
                new BloodGlucoseExaminationRequestDto();

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> bloodGlucoseExaminationService
                                .createBloodGlucoseExamination(
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

        verify(bloodGlucoseExaminationMapper, never())
                .toEntity(
                        any(BloodGlucoseExaminationRequestDto.class)
                );

        verify(bloodGlucoseExaminationRepository, never())
                .save(
                        any(BloodGlucoseExamination.class)
                );
    }


    // =========================================================
    // GET BY ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetBloodGlucoseExaminationById() {

        // Arrange
        Long examinationId = 10L;

        Registration registration =
                new Registration();

        registration.setId(1L);

        BloodGlucoseExamination examination =
                new BloodGlucoseExamination();

        examination.setId(examinationId);
        examination.setRegistration(registration);

        examination.setTestType(
                BloodGlucoseTestType.FBS
        );

        examination.setGlucoseValue(
                108.92
        );

        BloodGlucoseExaminationResponseDto expectedResponse =
                new BloodGlucoseExaminationResponseDto();

        expectedResponse.setId(examinationId);

        expectedResponse.setRegistrationId(
                1L
        );

        expectedResponse.setTestType(
                BloodGlucoseTestType.FBS
        );

        expectedResponse.setGlucoseValue(
                108.92
        );

        when(bloodGlucoseExaminationRepository.findById(examinationId))
                .thenReturn(Optional.of(examination));

        when(bloodGlucoseExaminationMapper.toResponseDto(examination))
                .thenReturn(expectedResponse);

        // Act
        BloodGlucoseExaminationResponseDto actualResponse =
                bloodGlucoseExaminationService
                        .getBloodGlucoseExaminationById(
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
                BloodGlucoseTestType.FBS,
                actualResponse.getTestType()
        );

        assertEquals(
                108.92,
                actualResponse.getGlucoseValue()
        );

        verify(bloodGlucoseExaminationRepository, times(1))
                .findById(examinationId);

        verify(bloodGlucoseExaminationMapper, times(1))
                .toResponseDto(examination);
    }


    // =========================================================
    // GET BY ID - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenBloodGlucoseExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(bloodGlucoseExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        BloodGlucoseExaminationNotFoundException exception =
                assertThrows(
                        BloodGlucoseExaminationNotFoundException.class,
                        () -> bloodGlucoseExaminationService
                                .getBloodGlucoseExaminationById(
                                        examinationId
                                )
                );

        assertEquals(
                "Blood glucose examination not found with id: 999",
                exception.getMessage()
        );

        verify(bloodGlucoseExaminationRepository, times(1))
                .findById(examinationId);

        verify(bloodGlucoseExaminationMapper, never())
                .toResponseDto(
                        any(BloodGlucoseExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetBloodGlucoseExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        BloodGlucoseExamination examination1 =
                new BloodGlucoseExamination();

        examination1.setId(10L);
        examination1.setRegistration(registration);

        examination1.setTestType(
                BloodGlucoseTestType.FBS
        );

        BloodGlucoseExamination examination2 =
                new BloodGlucoseExamination();

        examination2.setId(11L);
        examination2.setRegistration(registration);

        examination2.setTestType(
                BloodGlucoseTestType.PLBS
        );

        List<BloodGlucoseExamination> examinations =
                List.of(
                        examination1,
                        examination2
                );

        BloodGlucoseExaminationResponseDto response1 =
                new BloodGlucoseExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);

        response1.setTestType(
                BloodGlucoseTestType.FBS
        );

        BloodGlucoseExaminationResponseDto response2 =
                new BloodGlucoseExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);

        response2.setTestType(
                BloodGlucoseTestType.PLBS
        );

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(bloodGlucoseExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(examinations);

        when(bloodGlucoseExaminationMapper.toResponseDto(examination1))
                .thenReturn(response1);

        when(bloodGlucoseExaminationMapper.toResponseDto(examination2))
                .thenReturn(response2);

        // Act
        List<BloodGlucoseExaminationResponseDto> actualResponses =
                bloodGlucoseExaminationService
                        .getBloodGlucoseExaminationsByRegistrationId(
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
                BloodGlucoseTestType.FBS,
                actualResponses.get(0).getTestType()
        );

        assertEquals(
                11L,
                actualResponses.get(1).getId()
        );

        assertEquals(
                BloodGlucoseTestType.PLBS,
                actualResponses.get(1).getTestType()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(bloodGlucoseExaminationRepository, times(1))
                .findAllByRegistrationId(
                        registrationId
                );

        verify(bloodGlucoseExaminationMapper, times(1))
                .toResponseDto(examination1);

        verify(bloodGlucoseExaminationMapper, times(1))
                .toResponseDto(examination2);
    }


    // =========================================================
    // GET BY REGISTRATION ID - EMPTY LIST
    // =========================================================

    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoBloodGlucoseExaminations() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(bloodGlucoseExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(List.of());

        // Act
        List<BloodGlucoseExaminationResponseDto> actualResponses =
                bloodGlucoseExaminationService
                        .getBloodGlucoseExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertTrue(
                actualResponses.isEmpty()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(bloodGlucoseExaminationRepository, times(1))
                .findAllByRegistrationId(
                        registrationId
                );

        verify(bloodGlucoseExaminationMapper, never())
                .toResponseDto(
                        any(BloodGlucoseExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFoundWhileGettingBloodGlucoseExaminations() {

        // Arrange
        Long registrationId = 999L;

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> bloodGlucoseExaminationService
                                .getBloodGlucoseExaminationsByRegistrationId(
                                        registrationId
                                )
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(bloodGlucoseExaminationRepository, never())
                .findAllByRegistrationId(
                        anyLong()
                );

        verify(bloodGlucoseExaminationMapper, never())
                .toResponseDto(
                        any(BloodGlucoseExamination.class)
                );
    }


    // =========================================================
    // GET FBS BY REGISTRATION ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetFbsExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        BloodGlucoseExamination examination1 =
                new BloodGlucoseExamination();

        examination1.setId(10L);
        examination1.setRegistration(registration);

        examination1.setTestType(
                BloodGlucoseTestType.FBS
        );

        examination1.setGlucoseValue(
                108.92
        );

        BloodGlucoseExamination examination2 =
                new BloodGlucoseExamination();

        examination2.setId(11L);
        examination2.setRegistration(registration);

        examination2.setTestType(
                BloodGlucoseTestType.FBS
        );

        examination2.setGlucoseValue(
                105.50
        );

        List<BloodGlucoseExamination> examinations =
                List.of(
                        examination1,
                        examination2
                );

        BloodGlucoseExaminationResponseDto response1 =
                new BloodGlucoseExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);

        response1.setTestType(
                BloodGlucoseTestType.FBS
        );

        response1.setGlucoseValue(
                108.92
        );

        BloodGlucoseExaminationResponseDto response2 =
                new BloodGlucoseExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);

        response2.setTestType(
                BloodGlucoseTestType.FBS
        );

        response2.setGlucoseValue(
                105.50
        );

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(bloodGlucoseExaminationRepository
                .findAllByRegistrationIdAndTestType(
                        registrationId,
                        BloodGlucoseTestType.FBS
                ))
                .thenReturn(examinations);

        when(bloodGlucoseExaminationMapper.toResponseDto(examination1))
                .thenReturn(response1);

        when(bloodGlucoseExaminationMapper.toResponseDto(examination2))
                .thenReturn(response2);

        // Act
        List<BloodGlucoseExaminationResponseDto> actualResponses =
                bloodGlucoseExaminationService
                        .getFbsExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertEquals(
                2,
                actualResponses.size()
        );

        assertEquals(
                BloodGlucoseTestType.FBS,
                actualResponses.get(0).getTestType()
        );

        assertEquals(
                108.92,
                actualResponses.get(0).getGlucoseValue()
        );

        assertEquals(
                BloodGlucoseTestType.FBS,
                actualResponses.get(1).getTestType()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(bloodGlucoseExaminationRepository, times(1))
                .findAllByRegistrationIdAndTestType(
                        registrationId,
                        BloodGlucoseTestType.FBS
                );

        verify(bloodGlucoseExaminationMapper, times(1))
                .toResponseDto(examination1);

        verify(bloodGlucoseExaminationMapper, times(1))
                .toResponseDto(examination2);
    }


    // =========================================================
    // GET PLBS BY REGISTRATION ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetPlbsExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        BloodGlucoseExamination examination =
                new BloodGlucoseExamination();

        examination.setId(12L);
        examination.setRegistration(registration);

        examination.setTestType(
                BloodGlucoseTestType.PLBS
        );

        examination.setGlucoseValue(
                126.73
        );

        BloodGlucoseExaminationResponseDto expectedResponse =
                new BloodGlucoseExaminationResponseDto();

        expectedResponse.setId(12L);

        expectedResponse.setRegistrationId(
                registrationId
        );

        expectedResponse.setTestType(
                BloodGlucoseTestType.PLBS
        );

        expectedResponse.setGlucoseValue(
                126.73
        );

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(bloodGlucoseExaminationRepository
                .findAllByRegistrationIdAndTestType(
                        registrationId,
                        BloodGlucoseTestType.PLBS
                ))
                .thenReturn(
                        List.of(examination)
                );

        when(bloodGlucoseExaminationMapper.toResponseDto(examination))
                .thenReturn(expectedResponse);

        // Act
        List<BloodGlucoseExaminationResponseDto> actualResponses =
                bloodGlucoseExaminationService
                        .getPlbsExaminationsByRegistrationId(
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
                BloodGlucoseTestType.PLBS,
                actualResponses.get(0).getTestType()
        );

        assertEquals(
                126.73,
                actualResponses.get(0).getGlucoseValue()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(bloodGlucoseExaminationRepository, times(1))
                .findAllByRegistrationIdAndTestType(
                        registrationId,
                        BloodGlucoseTestType.PLBS
                );

        verify(bloodGlucoseExaminationMapper, times(1))
                .toResponseDto(examination);
    }


    // =========================================================
    // UPDATE - SUCCESS
    // =========================================================

    @Test
    void shouldUpdateBloodGlucoseExamination() {

        // Arrange
        Long examinationId = 10L;

        BloodGlucoseExamination existingExamination =
                new BloodGlucoseExamination();

        existingExamination.setId(examinationId);

        existingExamination.setTestType(
                BloodGlucoseTestType.FBS
        );

        existingExamination.setGlucoseValue(
                108.92
        );

        existingExamination.setSampleType(
                "Fluoride plasma"
        );

        existingExamination.setReferenceRange(
                "70 - 110"
        );

        existingExamination.setUnit(
                "mg/dl"
        );

        existingExamination.setRemarks(
                "Normal"
        );

        existingExamination.setPathologistName(
                "Dr Radha"
        );

        BloodGlucoseExaminationRequestDto request =
                new BloodGlucoseExaminationRequestDto();

        request.setTestType(
                BloodGlucoseTestType.FBS
        );

        request.setGlucoseValue(
                110.50
        );

        request.setSampleType(
                "Fluoride plasma"
        );

        request.setReferenceRange(
                "70 - 110"
        );

        request.setUnit(
                "mg/dl"
        );

        request.setRemarks(
                "Upper normal range"
        );

        request.setPathologistName(
                "Dr Anil Kumar"
        );

        BloodGlucoseExaminationResponseDto expectedResponse =
                new BloodGlucoseExaminationResponseDto();

        expectedResponse.setId(examinationId);

        expectedResponse.setTestType(
                BloodGlucoseTestType.FBS
        );

        expectedResponse.setGlucoseValue(
                110.50
        );

        expectedResponse.setRemarks(
                "Upper normal range"
        );

        expectedResponse.setPathologistName(
                "Dr Anil Kumar"
        );

        when(bloodGlucoseExaminationRepository.findById(examinationId))
                .thenReturn(
                        Optional.of(existingExamination)
                );

        when(bloodGlucoseExaminationRepository.save(existingExamination))
                .thenReturn(existingExamination);

        when(bloodGlucoseExaminationMapper
                .toResponseDto(existingExamination))
                .thenReturn(expectedResponse);

        // Act
        BloodGlucoseExaminationResponseDto actualResponse =
                bloodGlucoseExaminationService
                        .updateBloodGlucoseExamination(
                                examinationId,
                                request
                        );

        // Assert

        // Check actual entity was modified.
        assertEquals(
                BloodGlucoseTestType.FBS,
                existingExamination.getTestType()
        );

        assertEquals(
                110.50,
                existingExamination.getGlucoseValue()
        );

        assertEquals(
                "Fluoride plasma",
                existingExamination.getSampleType()
        );

        assertEquals(
                "70 - 110",
                existingExamination.getReferenceRange()
        );

        assertEquals(
                "mg/dl",
                existingExamination.getUnit()
        );

        assertEquals(
                "Upper normal range",
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
                110.50,
                actualResponse.getGlucoseValue()
        );

        assertEquals(
                "Upper normal range",
                actualResponse.getRemarks()
        );

        verify(bloodGlucoseExaminationRepository, times(1))
                .findById(examinationId);

        verify(bloodGlucoseExaminationRepository, times(1))
                .save(existingExamination);

        verify(bloodGlucoseExaminationMapper, times(1))
                .toResponseDto(existingExamination);
    }


    // =========================================================
    // UPDATE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenUpdatingBloodGlucoseExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        BloodGlucoseExaminationRequestDto request =
                new BloodGlucoseExaminationRequestDto();

        request.setTestType(
                BloodGlucoseTestType.FBS
        );

        request.setGlucoseValue(
                110.50
        );

        when(bloodGlucoseExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        BloodGlucoseExaminationNotFoundException exception =
                assertThrows(
                        BloodGlucoseExaminationNotFoundException.class,
                        () -> bloodGlucoseExaminationService
                                .updateBloodGlucoseExamination(
                                        examinationId,
                                        request
                                )
                );

        assertEquals(
                "Blood glucose examination not found with id: 999",
                exception.getMessage()
        );

        verify(bloodGlucoseExaminationRepository, times(1))
                .findById(examinationId);

        verify(bloodGlucoseExaminationRepository, never())
                .save(
                        any(BloodGlucoseExamination.class)
                );

        verify(bloodGlucoseExaminationMapper, never())
                .toResponseDto(
                        any(BloodGlucoseExamination.class)
                );
    }


    // =========================================================
    // DELETE - SUCCESS
    // =========================================================

    @Test
    void shouldDeleteBloodGlucoseExamination() {

        // Arrange
        Long examinationId = 10L;

        BloodGlucoseExamination examination =
                new BloodGlucoseExamination();

        examination.setId(examinationId);

        when(bloodGlucoseExaminationRepository.findById(examinationId))
                .thenReturn(
                        Optional.of(examination)
                );

        // Act
        bloodGlucoseExaminationService
                .deleteBloodGlucoseExamination(
                        examinationId
                );

        // Assert
        verify(bloodGlucoseExaminationRepository, times(1))
                .findById(examinationId);

        verify(bloodGlucoseExaminationRepository, times(1))
                .delete(examination);
    }


    // =========================================================
    // DELETE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenDeletingBloodGlucoseExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(bloodGlucoseExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        BloodGlucoseExaminationNotFoundException exception =
                assertThrows(
                        BloodGlucoseExaminationNotFoundException.class,
                        () -> bloodGlucoseExaminationService
                                .deleteBloodGlucoseExamination(
                                        examinationId
                                )
                );

        assertEquals(
                "Blood glucose examination not found with id: 999",
                exception.getMessage()
        );

        verify(bloodGlucoseExaminationRepository, times(1))
                .findById(examinationId);

        verify(bloodGlucoseExaminationRepository, never())
                .delete(
                        any(BloodGlucoseExamination.class)
                );
    }
}