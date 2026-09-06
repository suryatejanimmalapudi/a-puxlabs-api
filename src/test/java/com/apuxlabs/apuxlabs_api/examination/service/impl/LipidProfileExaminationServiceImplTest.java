package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.LipidProfileExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.LipidProfileExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.LipidProfileExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.LipidProfileExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.LipidProfileExaminationRepository;
import com.apuxlabs.apuxlabs_api.exception.LipidProfileExaminationNotFoundException;
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
class LipidProfileExaminationServiceImplTest {

    @Mock
    private LipidProfileExaminationRepository
            lipidProfileExaminationRepository;

    @Mock
    private LipidProfileExaminationMapper
            lipidProfileExaminationMapper;

    @Mock
    private RegistrationRepository
            registrationRepository;

    @InjectMocks
    private LipidProfileExaminationServiceImpl
            lipidProfileExaminationService;


    // =========================================================
    // CREATE - SUCCESS
    // =========================================================

    @Test
    void shouldCreateLipidProfileExamination() {

        // Arrange
        Long registrationId = 1L;

        LipidProfileExaminationRequestDto request =
                new LipidProfileExaminationRequestDto();

        request.setTotalCholesterol(197.14);
        request.setTriglycerides(146.19);
        request.setHdlCholesterol(47.04);
        request.setLdlCholesterol(120.86);
        request.setVldlCholesterol(29.24);
        request.setLdlHdlRatio(2.57);
        request.setTotalCholesterolHdlRatio(4.19);

        request.setRemarks(
                "Lipid profile reviewed"
        );

        request.setPathologistName(
                "Dr Radha"
        );

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        LipidProfileExamination examination =
                new LipidProfileExamination();

        LipidProfileExamination savedExamination =
                new LipidProfileExamination();

        savedExamination.setId(10L);
        savedExamination.setRegistration(registration);
        savedExamination.setTotalCholesterol(197.14);
        savedExamination.setTriglycerides(146.19);
        savedExamination.setHdlCholesterol(47.04);
        savedExamination.setLdlCholesterol(120.86);
        savedExamination.setVldlCholesterol(29.24);
        savedExamination.setLdlHdlRatio(2.57);
        savedExamination.setTotalCholesterolHdlRatio(4.19);

        LipidProfileExaminationResponseDto expectedResponse =
                new LipidProfileExaminationResponseDto();

        expectedResponse.setId(10L);
        expectedResponse.setRegistrationId(registrationId);
        expectedResponse.setTotalCholesterol(197.14);
        expectedResponse.setTriglycerides(146.19);
        expectedResponse.setHdlCholesterol(47.04);
        expectedResponse.setLdlCholesterol(120.86);
        expectedResponse.setVldlCholesterol(29.24);
        expectedResponse.setLdlHdlRatio(2.57);
        expectedResponse.setTotalCholesterolHdlRatio(4.19);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(lipidProfileExaminationMapper.toEntity(request))
                .thenReturn(examination);

        when(lipidProfileExaminationRepository.save(examination))
                .thenReturn(savedExamination);

        when(lipidProfileExaminationMapper
                .toResponseDto(savedExamination))
                .thenReturn(expectedResponse);

        // Act
        LipidProfileExaminationResponseDto actualResponse =
                lipidProfileExaminationService
                        .createLipidProfileExamination(
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
                197.14,
                actualResponse.getTotalCholesterol()
        );

        assertEquals(
                146.19,
                actualResponse.getTriglycerides()
        );

        assertEquals(
                47.04,
                actualResponse.getHdlCholesterol()
        );

        assertEquals(
                120.86,
                actualResponse.getLdlCholesterol()
        );

        assertEquals(
                registration,
                examination.getRegistration()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(lipidProfileExaminationMapper, times(1))
                .toEntity(request);

        verify(lipidProfileExaminationRepository, times(1))
                .save(examination);

        verify(lipidProfileExaminationMapper, times(1))
                .toResponseDto(savedExamination);
    }


    // =========================================================
    // CREATE - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFound() {

        // Arrange
        Long registrationId = 999L;

        LipidProfileExaminationRequestDto request =
                new LipidProfileExaminationRequestDto();

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> lipidProfileExaminationService
                                .createLipidProfileExamination(
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

        verify(lipidProfileExaminationMapper, never())
                .toEntity(
                        any(LipidProfileExaminationRequestDto.class)
                );

        verify(lipidProfileExaminationRepository, never())
                .save(
                        any(LipidProfileExamination.class)
                );
    }


    // =========================================================
    // GET BY ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetLipidProfileExaminationById() {

        // Arrange
        Long examinationId = 10L;

        Registration registration =
                new Registration();

        registration.setId(1L);

        LipidProfileExamination examination =
                new LipidProfileExamination();

        examination.setId(examinationId);
        examination.setRegistration(registration);
        examination.setTotalCholesterol(197.14);
        examination.setTriglycerides(146.19);
        examination.setHdlCholesterol(47.04);
        examination.setLdlCholesterol(120.86);
        examination.setVldlCholesterol(29.24);

        LipidProfileExaminationResponseDto expectedResponse =
                new LipidProfileExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setRegistrationId(1L);
        expectedResponse.setTotalCholesterol(197.14);
        expectedResponse.setTriglycerides(146.19);
        expectedResponse.setHdlCholesterol(47.04);
        expectedResponse.setLdlCholesterol(120.86);
        expectedResponse.setVldlCholesterol(29.24);

        when(lipidProfileExaminationRepository.findById(examinationId))
                .thenReturn(Optional.of(examination));

        when(lipidProfileExaminationMapper.toResponseDto(examination))
                .thenReturn(expectedResponse);

        // Act
        LipidProfileExaminationResponseDto actualResponse =
                lipidProfileExaminationService
                        .getLipidProfileExaminationById(
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
                197.14,
                actualResponse.getTotalCholesterol()
        );

        assertEquals(
                146.19,
                actualResponse.getTriglycerides()
        );

        assertEquals(
                47.04,
                actualResponse.getHdlCholesterol()
        );

        assertEquals(
                120.86,
                actualResponse.getLdlCholesterol()
        );

        verify(lipidProfileExaminationRepository, times(1))
                .findById(examinationId);

        verify(lipidProfileExaminationMapper, times(1))
                .toResponseDto(examination);
    }


    // =========================================================
    // GET BY ID - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenLipidProfileExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(lipidProfileExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        LipidProfileExaminationNotFoundException exception =
                assertThrows(
                        LipidProfileExaminationNotFoundException.class,
                        () -> lipidProfileExaminationService
                                .getLipidProfileExaminationById(
                                        examinationId
                                )
                );

        assertEquals(
                "Lipid profile examination not found with id: 999",
                exception.getMessage()
        );

        verify(lipidProfileExaminationRepository, times(1))
                .findById(examinationId);

        verify(lipidProfileExaminationMapper, never())
                .toResponseDto(
                        any(LipidProfileExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetLipidProfileExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        LipidProfileExamination examination1 =
                new LipidProfileExamination();

        examination1.setId(10L);
        examination1.setRegistration(registration);
        examination1.setTotalCholesterol(197.14);
        examination1.setLdlCholesterol(120.86);

        LipidProfileExamination examination2 =
                new LipidProfileExamination();

        examination2.setId(11L);
        examination2.setRegistration(registration);
        examination2.setTotalCholesterol(185.50);
        examination2.setLdlCholesterol(95.20);

        List<LipidProfileExamination> examinations =
                List.of(
                        examination1,
                        examination2
                );

        LipidProfileExaminationResponseDto response1 =
                new LipidProfileExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setTotalCholesterol(197.14);
        response1.setLdlCholesterol(120.86);

        LipidProfileExaminationResponseDto response2 =
                new LipidProfileExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setTotalCholesterol(185.50);
        response2.setLdlCholesterol(95.20);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(lipidProfileExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(examinations);

        when(lipidProfileExaminationMapper.toResponseDto(examination1))
                .thenReturn(response1);

        when(lipidProfileExaminationMapper.toResponseDto(examination2))
                .thenReturn(response2);

        // Act
        List<LipidProfileExaminationResponseDto> actualResponses =
                lipidProfileExaminationService
                        .getLipidProfileExaminationsByRegistrationId(
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
                197.14,
                actualResponses.get(0).getTotalCholesterol()
        );

        assertEquals(
                11L,
                actualResponses.get(1).getId()
        );

        assertEquals(
                185.50,
                actualResponses.get(1).getTotalCholesterol()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(lipidProfileExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(lipidProfileExaminationMapper, times(1))
                .toResponseDto(examination1);

        verify(lipidProfileExaminationMapper, times(1))
                .toResponseDto(examination2);
    }


    // =========================================================
    // GET BY REGISTRATION ID - EMPTY LIST
    // =========================================================

    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoLipidProfileExaminations() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(lipidProfileExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(List.of());

        // Act
        List<LipidProfileExaminationResponseDto> actualResponses =
                lipidProfileExaminationService
                        .getLipidProfileExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertTrue(
                actualResponses.isEmpty()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(lipidProfileExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(lipidProfileExaminationMapper, never())
                .toResponseDto(
                        any(LipidProfileExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFoundWhileGettingLipidProfileExaminations() {

        // Arrange
        Long registrationId = 999L;

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> lipidProfileExaminationService
                                .getLipidProfileExaminationsByRegistrationId(
                                        registrationId
                                )
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(lipidProfileExaminationRepository, never())
                .findAllByRegistrationId(
                        anyLong()
                );

        verify(lipidProfileExaminationMapper, never())
                .toResponseDto(
                        any(LipidProfileExamination.class)
                );
    }


    // =========================================================
    // UPDATE - SUCCESS
    // =========================================================

    @Test
    void shouldUpdateLipidProfileExamination() {

        // Arrange
        Long examinationId = 10L;

        LipidProfileExamination existingExamination =
                new LipidProfileExamination();

        existingExamination.setId(examinationId);
        existingExamination.setTotalCholesterol(197.14);
        existingExamination.setTriglycerides(146.19);
        existingExamination.setHdlCholesterol(47.04);
        existingExamination.setLdlCholesterol(120.86);
        existingExamination.setVldlCholesterol(29.24);
        existingExamination.setLdlHdlRatio(2.57);
        existingExamination.setTotalCholesterolHdlRatio(4.19);
        existingExamination.setRemarks("Initial result");
        existingExamination.setPathologistName("Dr Radha");

        LipidProfileExaminationRequestDto request =
                new LipidProfileExaminationRequestDto();

        request.setTotalCholesterol(190.50);
        request.setTriglycerides(140.20);
        request.setHdlCholesterol(50.00);
        request.setLdlCholesterol(110.00);
        request.setVldlCholesterol(28.04);
        request.setLdlHdlRatio(2.20);
        request.setTotalCholesterolHdlRatio(3.81);

        request.setRemarks(
                "Updated lipid profile"
        );

        request.setPathologistName(
                "Dr Anil Kumar"
        );

        LipidProfileExaminationResponseDto expectedResponse =
                new LipidProfileExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setTotalCholesterol(190.50);
        expectedResponse.setTriglycerides(140.20);
        expectedResponse.setHdlCholesterol(50.00);
        expectedResponse.setLdlCholesterol(110.00);
        expectedResponse.setVldlCholesterol(28.04);
        expectedResponse.setLdlHdlRatio(2.20);
        expectedResponse.setTotalCholesterolHdlRatio(3.81);
        expectedResponse.setRemarks("Updated lipid profile");
        expectedResponse.setPathologistName("Dr Anil Kumar");

        when(lipidProfileExaminationRepository.findById(examinationId))
                .thenReturn(
                        Optional.of(existingExamination)
                );

        when(lipidProfileExaminationRepository.save(existingExamination))
                .thenReturn(existingExamination);

        when(lipidProfileExaminationMapper
                .toResponseDto(existingExamination))
                .thenReturn(expectedResponse);

        // Act
        LipidProfileExaminationResponseDto actualResponse =
                lipidProfileExaminationService
                        .updateLipidProfileExamination(
                                examinationId,
                                request
                        );

        // Assert

        // Check actual entity was modified.
        assertEquals(
                190.50,
                existingExamination.getTotalCholesterol()
        );

        assertEquals(
                140.20,
                existingExamination.getTriglycerides()
        );

        assertEquals(
                50.00,
                existingExamination.getHdlCholesterol()
        );

        assertEquals(
                110.00,
                existingExamination.getLdlCholesterol()
        );

        assertEquals(
                28.04,
                existingExamination.getVldlCholesterol()
        );

        assertEquals(
                2.20,
                existingExamination.getLdlHdlRatio()
        );

        assertEquals(
                3.81,
                existingExamination.getTotalCholesterolHdlRatio()
        );

        assertEquals(
                "Updated lipid profile",
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
                190.50,
                actualResponse.getTotalCholesterol()
        );

        assertEquals(
                110.00,
                actualResponse.getLdlCholesterol()
        );

        assertEquals(
                "Updated lipid profile",
                actualResponse.getRemarks()
        );

        verify(lipidProfileExaminationRepository, times(1))
                .findById(examinationId);

        verify(lipidProfileExaminationRepository, times(1))
                .save(existingExamination);

        verify(lipidProfileExaminationMapper, times(1))
                .toResponseDto(existingExamination);
    }


    // =========================================================
    // UPDATE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenUpdatingLipidProfileExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        LipidProfileExaminationRequestDto request =
                new LipidProfileExaminationRequestDto();

        request.setTotalCholesterol(
                190.50
        );

        when(lipidProfileExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        LipidProfileExaminationNotFoundException exception =
                assertThrows(
                        LipidProfileExaminationNotFoundException.class,
                        () -> lipidProfileExaminationService
                                .updateLipidProfileExamination(
                                        examinationId,
                                        request
                                )
                );

        assertEquals(
                "Lipid profile examination not found with id: 999",
                exception.getMessage()
        );

        verify(lipidProfileExaminationRepository, times(1))
                .findById(examinationId);

        verify(lipidProfileExaminationRepository, never())
                .save(
                        any(LipidProfileExamination.class)
                );

        verify(lipidProfileExaminationMapper, never())
                .toResponseDto(
                        any(LipidProfileExamination.class)
                );
    }


    // =========================================================
    // DELETE - SUCCESS
    // =========================================================

    @Test
    void shouldDeleteLipidProfileExamination() {

        // Arrange
        Long examinationId = 10L;

        LipidProfileExamination examination =
                new LipidProfileExamination();

        examination.setId(examinationId);

        when(lipidProfileExaminationRepository.findById(examinationId))
                .thenReturn(
                        Optional.of(examination)
                );

        // Act
        lipidProfileExaminationService
                .deleteLipidProfileExamination(
                        examinationId
                );

        // Assert
        verify(lipidProfileExaminationRepository, times(1))
                .findById(examinationId);

        verify(lipidProfileExaminationRepository, times(1))
                .delete(examination);
    }


    // =========================================================
    // DELETE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenDeletingLipidProfileExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(lipidProfileExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        LipidProfileExaminationNotFoundException exception =
                assertThrows(
                        LipidProfileExaminationNotFoundException.class,
                        () -> lipidProfileExaminationService
                                .deleteLipidProfileExamination(
                                        examinationId
                                )
                );

        assertEquals(
                "Lipid profile examination not found with id: 999",
                exception.getMessage()
        );

        verify(lipidProfileExaminationRepository, times(1))
                .findById(examinationId);

        verify(lipidProfileExaminationRepository, never())
                .delete(
                        any(LipidProfileExamination.class)
                );
    }
}