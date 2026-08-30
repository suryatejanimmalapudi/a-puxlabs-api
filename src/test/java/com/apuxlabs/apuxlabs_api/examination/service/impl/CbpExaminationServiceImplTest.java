package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.CbpExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.CbpExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.CbpExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.CbpExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.CbpExaminationRepository;
import com.apuxlabs.apuxlabs_api.exception.CbpExaminationNotFoundException;
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
class CbpExaminationServiceImplTest {

    @Mock
    private CbpExaminationRepository cbpExaminationRepository;

    @Mock
    private CbpExaminationMapper cbpExaminationMapper;

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private CbpExaminationServiceImpl cbpExaminationService;


    // =========================================================
    // CREATE - SUCCESS
    // =========================================================

    @Test
    void shouldCreateCbpExamination() {

        // Arrange
        Long registrationId = 1L;

        CbpExaminationRequestDto request =
                new CbpExaminationRequestDto();

        request.setHaemoglobin(14.2);
        request.setTotalLeukocyteCount(7200);

        request.setNeutrophils(60.0);
        request.setLymphocytes(30.0);
        request.setEosinophils(4.0);
        request.setMonocytes(5.0);
        request.setBasophils(1.0);

        request.setRbcCount(4.8);
        request.setHct(44.0);
        request.setMcv(91.0);
        request.setMch(29.5);
        request.setMchc(32.5);

        request.setPlateletCount(250000);

        request.setRemarks(
                "Complete blood picture within normal limits"
        );

        request.setPathologistName(
                "Dr Radha"
        );

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        CbpExamination examination =
                new CbpExamination();

        CbpExamination savedExamination =
                new CbpExamination();

        savedExamination.setId(10L);
        savedExamination.setRegistration(registration);
        savedExamination.setHaemoglobin(14.2);
        savedExamination.setTotalLeukocyteCount(7200);
        savedExamination.setPlateletCount(250000);

        CbpExaminationResponseDto expectedResponse =
                new CbpExaminationResponseDto();

        expectedResponse.setId(10L);
        expectedResponse.setRegistrationId(registrationId);
        expectedResponse.setHaemoglobin(14.2);
        expectedResponse.setTotalLeukocyteCount(7200);
        expectedResponse.setPlateletCount(250000);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(cbpExaminationMapper.toEntity(request))
                .thenReturn(examination);

        when(cbpExaminationRepository.save(examination))
                .thenReturn(savedExamination);

        when(cbpExaminationMapper.toResponseDto(savedExamination))
                .thenReturn(expectedResponse);

        // Act
        CbpExaminationResponseDto actualResponse =
                cbpExaminationService.createCbpExamination(
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
                14.2,
                actualResponse.getHaemoglobin()
        );

        assertEquals(
                7200,
                actualResponse.getTotalLeukocyteCount()
        );

        assertEquals(
                250000,
                actualResponse.getPlateletCount()
        );

        assertEquals(
                registration,
                examination.getRegistration()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(cbpExaminationMapper, times(1))
                .toEntity(request);

        verify(cbpExaminationRepository, times(1))
                .save(examination);

        verify(cbpExaminationMapper, times(1))
                .toResponseDto(savedExamination);
    }


    // =========================================================
    // CREATE - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFound() {

        // Arrange
        Long registrationId = 999L;

        CbpExaminationRequestDto request =
                new CbpExaminationRequestDto();

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> cbpExaminationService
                                .createCbpExamination(
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

        verify(cbpExaminationMapper, never())
                .toEntity(
                        any(CbpExaminationRequestDto.class)
                );

        verify(cbpExaminationRepository, never())
                .save(
                        any(CbpExamination.class)
                );
    }


    // =========================================================
    // GET BY ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetCbpExaminationById() {

        // Arrange
        Long examinationId = 10L;

        Registration registration =
                new Registration();

        registration.setId(1L);

        CbpExamination examination =
                new CbpExamination();

        examination.setId(examinationId);
        examination.setRegistration(registration);
        examination.setHaemoglobin(14.2);
        examination.setTotalLeukocyteCount(7200);
        examination.setPlateletCount(250000);

        CbpExaminationResponseDto expectedResponse =
                new CbpExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setRegistrationId(1L);
        expectedResponse.setHaemoglobin(14.2);
        expectedResponse.setTotalLeukocyteCount(7200);
        expectedResponse.setPlateletCount(250000);

        when(cbpExaminationRepository.findById(examinationId))
                .thenReturn(Optional.of(examination));

        when(cbpExaminationMapper.toResponseDto(examination))
                .thenReturn(expectedResponse);

        // Act
        CbpExaminationResponseDto actualResponse =
                cbpExaminationService
                        .getCbpExaminationById(
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
                14.2,
                actualResponse.getHaemoglobin()
        );

        assertEquals(
                7200,
                actualResponse.getTotalLeukocyteCount()
        );

        assertEquals(
                250000,
                actualResponse.getPlateletCount()
        );

        verify(cbpExaminationRepository, times(1))
                .findById(examinationId);

        verify(cbpExaminationMapper, times(1))
                .toResponseDto(examination);
    }


    // =========================================================
    // GET BY ID - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenCbpExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(cbpExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        CbpExaminationNotFoundException exception =
                assertThrows(
                        CbpExaminationNotFoundException.class,
                        () -> cbpExaminationService
                                .getCbpExaminationById(
                                        examinationId
                                )
                );

        assertEquals(
                "CBP examination not found with id: 999",
                exception.getMessage()
        );

        verify(cbpExaminationRepository, times(1))
                .findById(examinationId);

        verify(cbpExaminationMapper, never())
                .toResponseDto(
                        any(CbpExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetCbpExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        CbpExamination examination1 =
                new CbpExamination();

        examination1.setId(10L);
        examination1.setRegistration(registration);
        examination1.setHaemoglobin(14.2);

        CbpExamination examination2 =
                new CbpExamination();

        examination2.setId(11L);
        examination2.setRegistration(registration);
        examination2.setHaemoglobin(13.8);

        List<CbpExamination> examinations =
                List.of(
                        examination1,
                        examination2
                );

        CbpExaminationResponseDto response1 =
                new CbpExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setHaemoglobin(14.2);

        CbpExaminationResponseDto response2 =
                new CbpExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setHaemoglobin(13.8);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(cbpExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(examinations);

        when(cbpExaminationMapper.toResponseDto(examination1))
                .thenReturn(response1);

        when(cbpExaminationMapper.toResponseDto(examination2))
                .thenReturn(response2);

        // Act
        List<CbpExaminationResponseDto> actualResponses =
                cbpExaminationService
                        .getCbpExaminationsByRegistrationId(
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
                14.2,
                actualResponses.get(0).getHaemoglobin()
        );

        assertEquals(
                11L,
                actualResponses.get(1).getId()
        );

        assertEquals(
                13.8,
                actualResponses.get(1).getHaemoglobin()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(cbpExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(cbpExaminationMapper, times(1))
                .toResponseDto(examination1);

        verify(cbpExaminationMapper, times(1))
                .toResponseDto(examination2);
    }


    // =========================================================
    // GET BY REGISTRATION ID - EMPTY LIST
    // =========================================================

    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoCbpExaminations() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(cbpExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(List.of());

        // Act
        List<CbpExaminationResponseDto> actualResponses =
                cbpExaminationService
                        .getCbpExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertTrue(
                actualResponses.isEmpty()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(cbpExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(cbpExaminationMapper, never())
                .toResponseDto(
                        any(CbpExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFoundWhileGettingCbpExaminations() {

        // Arrange
        Long registrationId = 999L;

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> cbpExaminationService
                                .getCbpExaminationsByRegistrationId(
                                        registrationId
                                )
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(cbpExaminationRepository, never())
                .findAllByRegistrationId(
                        anyLong()
                );

        verify(cbpExaminationMapper, never())
                .toResponseDto(
                        any(CbpExamination.class)
                );
    }


    // =========================================================
    // UPDATE - SUCCESS
    // =========================================================

    @Test
    void shouldUpdateCbpExamination() {

        // Arrange
        Long examinationId = 10L;

        CbpExamination existingExamination =
                new CbpExamination();

        existingExamination.setId(examinationId);

        existingExamination.setHaemoglobin(
                14.2
        );

        existingExamination.setTotalLeukocyteCount(
                7200
        );

        existingExamination.setNeutrophils(
                60.0
        );

        existingExamination.setLymphocytes(
                30.0
        );

        existingExamination.setRbcCount(
                4.8
        );

        existingExamination.setPlateletCount(
                250000
        );

        existingExamination.setRemarks(
                "Normal"
        );

        existingExamination.setPathologistName(
                "Dr Radha"
        );

        CbpExaminationRequestDto request =
                new CbpExaminationRequestDto();

        request.setHaemoglobin(
                13.5
        );

        request.setTotalLeukocyteCount(
                7800
        );

        request.setNeutrophils(
                62.0
        );

        request.setLymphocytes(
                28.0
        );

        request.setEosinophils(
                4.0
        );

        request.setMonocytes(
                5.0
        );

        request.setBasophils(
                1.0
        );

        request.setAbsoluteNeutrophils(
                4800
        );

        request.setAbsoluteLymphocytes(
                2200
        );

        request.setAbsoluteEosinophils(
                300
        );

        request.setAbsoluteMonocytes(
                400
        );

        request.setRbcCount(
                4.6
        );

        request.setHct(
                42.0
        );

        request.setMcv(
                90.0
        );

        request.setMch(
                29.0
        );

        request.setMchc(
                32.0
        );

        request.setRdwCv(
                13.0
        );

        request.setRdwSd(
                40.0
        );

        request.setPlateletCount(
                270000
        );

        request.setPct(
                0.24
        );

        request.setMpv(
                9.0
        );

        request.setPdw(
                12.0
        );

        request.setPLcr(
                25.0
        );

        request.setPLcc(
                65.0
        );

        request.setRemarks(
                "Updated CBP result"
        );

        request.setPathologistName(
                "Dr Anil Kumar"
        );

        CbpExaminationResponseDto expectedResponse =
                new CbpExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setHaemoglobin(13.5);
        expectedResponse.setTotalLeukocyteCount(7800);
        expectedResponse.setPlateletCount(270000);
        expectedResponse.setRemarks(
                "Updated CBP result"
        );

        when(cbpExaminationRepository.findById(examinationId))
                .thenReturn(
                        Optional.of(existingExamination)
                );

        when(cbpExaminationRepository.save(existingExamination))
                .thenReturn(existingExamination);

        when(cbpExaminationMapper
                .toResponseDto(existingExamination))
                .thenReturn(expectedResponse);

        // Act
        CbpExaminationResponseDto actualResponse =
                cbpExaminationService
                        .updateCbpExamination(
                                examinationId,
                                request
                        );

        // Assert

        // Basic haematology
        assertEquals(
                13.5,
                existingExamination.getHaemoglobin()
        );

        assertEquals(
                7800,
                existingExamination.getTotalLeukocyteCount()
        );

        // Differential leukocyte count
        assertEquals(
                62.0,
                existingExamination.getNeutrophils()
        );

        assertEquals(
                28.0,
                existingExamination.getLymphocytes()
        );

        assertEquals(
                4.0,
                existingExamination.getEosinophils()
        );

        // RBC indices
        assertEquals(
                4.6,
                existingExamination.getRbcCount()
        );

        assertEquals(
                42.0,
                existingExamination.getHct()
        );

        assertEquals(
                90.0,
                existingExamination.getMcv()
        );

        assertEquals(
                29.0,
                existingExamination.getMch()
        );

        assertEquals(
                32.0,
                existingExamination.getMchc()
        );

        // Platelet indices
        assertEquals(
                270000,
                existingExamination.getPlateletCount()
        );

        assertEquals(
                9.0,
                existingExamination.getMpv()
        );

        assertEquals(
                12.0,
                existingExamination.getPdw()
        );

        assertEquals(
                25.0,
                existingExamination.getPLcr()
        );

        assertEquals(
                65.0,
                existingExamination.getPLcc()
        );

        assertEquals(
                "Updated CBP result",
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
                13.5,
                actualResponse.getHaemoglobin()
        );

        assertEquals(
                7800,
                actualResponse.getTotalLeukocyteCount()
        );

        assertEquals(
                270000,
                actualResponse.getPlateletCount()
        );

        assertEquals(
                "Updated CBP result",
                actualResponse.getRemarks()
        );

        verify(cbpExaminationRepository, times(1))
                .findById(examinationId);

        verify(cbpExaminationRepository, times(1))
                .save(existingExamination);

        verify(cbpExaminationMapper, times(1))
                .toResponseDto(existingExamination);
    }


    // =========================================================
    // UPDATE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenUpdatingCbpExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        CbpExaminationRequestDto request =
                new CbpExaminationRequestDto();

        request.setHaemoglobin(
                13.5
        );

        when(cbpExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        CbpExaminationNotFoundException exception =
                assertThrows(
                        CbpExaminationNotFoundException.class,
                        () -> cbpExaminationService
                                .updateCbpExamination(
                                        examinationId,
                                        request
                                )
                );

        assertEquals(
                "CBP examination not found with id: 999",
                exception.getMessage()
        );

        verify(cbpExaminationRepository, times(1))
                .findById(examinationId);

        verify(cbpExaminationRepository, never())
                .save(
                        any(CbpExamination.class)
                );

        verify(cbpExaminationMapper, never())
                .toResponseDto(
                        any(CbpExamination.class)
                );
    }


    // =========================================================
    // DELETE - SUCCESS
    // =========================================================

    @Test
    void shouldDeleteCbpExamination() {

        // Arrange
        Long examinationId = 10L;

        CbpExamination examination =
                new CbpExamination();

        examination.setId(examinationId);

        when(cbpExaminationRepository.findById(examinationId))
                .thenReturn(
                        Optional.of(examination)
                );

        // Act
        cbpExaminationService.deleteCbpExamination(
                examinationId
        );

        // Assert
        verify(cbpExaminationRepository, times(1))
                .findById(examinationId);

        verify(cbpExaminationRepository, times(1))
                .delete(examination);
    }


    // =========================================================
    // DELETE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenDeletingCbpExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(cbpExaminationRepository.findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        CbpExaminationNotFoundException exception =
                assertThrows(
                        CbpExaminationNotFoundException.class,
                        () -> cbpExaminationService
                                .deleteCbpExamination(
                                        examinationId
                                )
                );

        assertEquals(
                "CBP examination not found with id: 999",
                exception.getMessage()
        );

        verify(cbpExaminationRepository, times(1))
                .findById(examinationId);

        verify(cbpExaminationRepository, never())
                .delete(
                        any(CbpExamination.class)
                );
    }
}