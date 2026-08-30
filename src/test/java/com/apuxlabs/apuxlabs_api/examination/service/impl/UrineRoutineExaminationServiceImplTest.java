package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.UrineRoutineExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.UrineRoutineExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.UrineRoutineExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.UrineRoutineExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.UrineRoutineExaminationRepository;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.UrineRoutineExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrineRoutineExaminationServiceImplTest {

    @Mock
    private UrineRoutineExaminationRepository
            urineRoutineExaminationRepository;

    @Mock
    private UrineRoutineExaminationMapper
            urineRoutineExaminationMapper;

    @Mock
    private RegistrationRepository
            registrationRepository;

    @InjectMocks
    private UrineRoutineExaminationServiceImpl
            urineRoutineExaminationService;


    // =========================================================
    // CREATE - SUCCESS
    // =========================================================

    @Test
    void shouldCreateUrineRoutineExamination() {

        // Arrange
        Long registrationId = 1L;

        LocalDateTime examinationDateTime =
                LocalDateTime.of(
                        2026,
                        7,
                        22,
                        10,
                        30
                );

        UrineRoutineExaminationRequestDto request =
                new UrineRoutineExaminationRequestDto();

        request.setExaminationDateTime(examinationDateTime);
        request.setSampleType("Urine");

        // Physical examination
        request.setColour("Yellow");
        request.setDeposit("NOT FOUND");
        request.setAppearance("Clear");
        request.setPh(7.0);
        request.setSpecificGravity(1.015);
        request.setQuantity("20ml");

        // Chemical examination
        request.setUrineProtein("Absent");
        request.setBileSalt("Absent");
        request.setUrineGlucose("Absent");
        request.setUrineKetones("Absent");
        request.setBilePigment("Absent");
        request.setOccultBlood("Absent");

        // Microscopic examination
        request.setRbcs("Absent");
        request.setPusCells("4-6/hpf");
        request.setEpithelialCells("1-2/hpf");
        request.setCrystals("Absent");
        request.setCasts("Absent");
        request.setAmorphousDeposit("Absent");
        request.setBacteria("Absent");
        request.setTrichomonasVaginalis("NOT SEEN");
        request.setYeastCells("Absent");

        request.setRemarks(
                "Urine routine examination reviewed"
        );

        request.setPathologistName(
                "Dr Radha"
        );

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        UrineRoutineExamination examination =
                new UrineRoutineExamination();

        UrineRoutineExamination savedExamination =
                new UrineRoutineExamination();

        savedExamination.setId(10L);
        savedExamination.setRegistration(registration);
        savedExamination.setExaminationDateTime(examinationDateTime);
        savedExamination.setSampleType("Urine");

        savedExamination.setColour("Yellow");
        savedExamination.setAppearance("Clear");
        savedExamination.setPh(7.0);
        savedExamination.setSpecificGravity(1.015);

        savedExamination.setUrineProtein("Absent");
        savedExamination.setUrineGlucose("Absent");

        savedExamination.setRbcs("Absent");
        savedExamination.setPusCells("4-6/hpf");
        savedExamination.setEpithelialCells("1-2/hpf");

        UrineRoutineExaminationResponseDto expectedResponse =
                new UrineRoutineExaminationResponseDto();

        expectedResponse.setId(10L);
        expectedResponse.setRegistrationId(registrationId);
        expectedResponse.setExaminationDateTime(examinationDateTime);
        expectedResponse.setSampleType("Urine");

        expectedResponse.setColour("Yellow");
        expectedResponse.setAppearance("Clear");
        expectedResponse.setPh(7.0);
        expectedResponse.setSpecificGravity(1.015);

        expectedResponse.setUrineProtein("Absent");
        expectedResponse.setUrineGlucose("Absent");

        expectedResponse.setRbcs("Absent");
        expectedResponse.setPusCells("4-6/hpf");
        expectedResponse.setEpithelialCells("1-2/hpf");

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(urineRoutineExaminationMapper.toEntity(request))
                .thenReturn(examination);

        when(urineRoutineExaminationRepository.save(examination))
                .thenReturn(savedExamination);

        when(urineRoutineExaminationMapper
                .toResponseDto(savedExamination))
                .thenReturn(expectedResponse);

        // Act
        UrineRoutineExaminationResponseDto actualResponse =
                urineRoutineExaminationService
                        .createUrineRoutineExamination(
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
                "Urine",
                actualResponse.getSampleType()
        );

        assertEquals(
                "Yellow",
                actualResponse.getColour()
        );

        assertEquals(
                "Clear",
                actualResponse.getAppearance()
        );

        assertEquals(
                7.0,
                actualResponse.getPh()
        );

        assertEquals(
                1.015,
                actualResponse.getSpecificGravity()
        );

        assertEquals(
                "Absent",
                actualResponse.getUrineProtein()
        );

        assertEquals(
                "4-6/hpf",
                actualResponse.getPusCells()
        );

        assertEquals(
                registration,
                examination.getRegistration()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(urineRoutineExaminationMapper, times(1))
                .toEntity(request);

        verify(urineRoutineExaminationRepository, times(1))
                .save(examination);

        verify(urineRoutineExaminationMapper, times(1))
                .toResponseDto(savedExamination);
    }


    // =========================================================
    // CREATE - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFound() {

        // Arrange
        Long registrationId = 999L;

        UrineRoutineExaminationRequestDto request =
                new UrineRoutineExaminationRequestDto();

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> urineRoutineExaminationService
                                .createUrineRoutineExamination(
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

        verify(urineRoutineExaminationMapper, never())
                .toEntity(
                        any(UrineRoutineExaminationRequestDto.class)
                );

        verify(urineRoutineExaminationRepository, never())
                .save(
                        any(UrineRoutineExamination.class)
                );
    }


    // =========================================================
    // GET BY ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetUrineRoutineExaminationById() {

        // Arrange
        Long examinationId = 10L;

        Registration registration =
                new Registration();

        registration.setId(1L);

        UrineRoutineExamination examination =
                new UrineRoutineExamination();

        examination.setId(examinationId);
        examination.setRegistration(registration);

        examination.setSampleType("Urine");

        examination.setColour("Yellow");
        examination.setDeposit("NOT FOUND");
        examination.setAppearance("Clear");

        examination.setPh(7.0);
        examination.setSpecificGravity(1.015);
        examination.setQuantity("20ml");

        examination.setUrineProtein("Absent");
        examination.setUrineGlucose("Absent");
        examination.setUrineKetones("Absent");

        examination.setRbcs("Absent");
        examination.setPusCells("4-6/hpf");
        examination.setEpithelialCells("1-2/hpf");

        UrineRoutineExaminationResponseDto expectedResponse =
                new UrineRoutineExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setRegistrationId(1L);

        expectedResponse.setSampleType("Urine");

        expectedResponse.setColour("Yellow");
        expectedResponse.setDeposit("NOT FOUND");
        expectedResponse.setAppearance("Clear");

        expectedResponse.setPh(7.0);
        expectedResponse.setSpecificGravity(1.015);
        expectedResponse.setQuantity("20ml");

        expectedResponse.setUrineProtein("Absent");
        expectedResponse.setUrineGlucose("Absent");
        expectedResponse.setUrineKetones("Absent");

        expectedResponse.setRbcs("Absent");
        expectedResponse.setPusCells("4-6/hpf");
        expectedResponse.setEpithelialCells("1-2/hpf");

        when(urineRoutineExaminationRepository
                .findById(examinationId))
                .thenReturn(Optional.of(examination));

        when(urineRoutineExaminationMapper
                .toResponseDto(examination))
                .thenReturn(expectedResponse);

        // Act
        UrineRoutineExaminationResponseDto actualResponse =
                urineRoutineExaminationService
                        .getUrineRoutineExaminationById(
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
                "Urine",
                actualResponse.getSampleType()
        );

        assertEquals(
                "Yellow",
                actualResponse.getColour()
        );

        assertEquals(
                "Clear",
                actualResponse.getAppearance()
        );

        assertEquals(
                7.0,
                actualResponse.getPh()
        );

        assertEquals(
                1.015,
                actualResponse.getSpecificGravity()
        );

        assertEquals(
                "Absent",
                actualResponse.getUrineProtein()
        );

        assertEquals(
                "4-6/hpf",
                actualResponse.getPusCells()
        );

        verify(urineRoutineExaminationRepository, times(1))
                .findById(examinationId);

        verify(urineRoutineExaminationMapper, times(1))
                .toResponseDto(examination);
    }


    // =========================================================
    // GET BY ID - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenUrineRoutineExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(urineRoutineExaminationRepository
                .findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        UrineRoutineExaminationNotFoundException exception =
                assertThrows(
                        UrineRoutineExaminationNotFoundException.class,
                        () -> urineRoutineExaminationService
                                .getUrineRoutineExaminationById(
                                        examinationId
                                )
                );

        assertEquals(
                "Urine routine examination not found with id: 999",
                exception.getMessage()
        );

        verify(urineRoutineExaminationRepository, times(1))
                .findById(examinationId);

        verify(urineRoutineExaminationMapper, never())
                .toResponseDto(
                        any(UrineRoutineExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetUrineRoutineExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        UrineRoutineExamination examination1 =
                new UrineRoutineExamination();

        examination1.setId(10L);
        examination1.setRegistration(registration);
        examination1.setColour("Yellow");
        examination1.setAppearance("Clear");
        examination1.setPh(7.0);

        UrineRoutineExamination examination2 =
                new UrineRoutineExamination();

        examination2.setId(11L);
        examination2.setRegistration(registration);
        examination2.setColour("Pale Yellow");
        examination2.setAppearance("Clear");
        examination2.setPh(6.5);

        List<UrineRoutineExamination> examinations =
                List.of(
                        examination1,
                        examination2
                );

        UrineRoutineExaminationResponseDto response1 =
                new UrineRoutineExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setColour("Yellow");
        response1.setAppearance("Clear");
        response1.setPh(7.0);

        UrineRoutineExaminationResponseDto response2 =
                new UrineRoutineExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setColour("Pale Yellow");
        response2.setAppearance("Clear");
        response2.setPh(6.5);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(urineRoutineExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(examinations);

        when(urineRoutineExaminationMapper
                .toResponseDto(examination1))
                .thenReturn(response1);

        when(urineRoutineExaminationMapper
                .toResponseDto(examination2))
                .thenReturn(response2);

        // Act
        List<UrineRoutineExaminationResponseDto> actualResponses =
                urineRoutineExaminationService
                        .getUrineRoutineExaminationsByRegistrationId(
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
                "Yellow",
                actualResponses.get(0).getColour()
        );

        assertEquals(
                11L,
                actualResponses.get(1).getId()
        );

        assertEquals(
                "Pale Yellow",
                actualResponses.get(1).getColour()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(urineRoutineExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(urineRoutineExaminationMapper, times(1))
                .toResponseDto(examination1);

        verify(urineRoutineExaminationMapper, times(1))
                .toResponseDto(examination2);
    }


    // =========================================================
    // GET BY REGISTRATION ID - EMPTY LIST
    // =========================================================

    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoUrineRoutineExaminations() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(urineRoutineExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(List.of());

        // Act
        List<UrineRoutineExaminationResponseDto> actualResponses =
                urineRoutineExaminationService
                        .getUrineRoutineExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertTrue(
                actualResponses.isEmpty()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(urineRoutineExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(urineRoutineExaminationMapper, never())
                .toResponseDto(
                        any(UrineRoutineExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFoundWhileGettingUrineRoutineExaminations() {

        // Arrange
        Long registrationId = 999L;

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> urineRoutineExaminationService
                                .getUrineRoutineExaminationsByRegistrationId(
                                        registrationId
                                )
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(urineRoutineExaminationRepository, never())
                .findAllByRegistrationId(
                        anyLong()
                );

        verify(urineRoutineExaminationMapper, never())
                .toResponseDto(
                        any(UrineRoutineExamination.class)
                );
    }


    // =========================================================
    // UPDATE - SUCCESS
    // =========================================================

    @Test
    void shouldUpdateUrineRoutineExamination() {

        // Arrange
        Long examinationId = 10L;

        LocalDateTime updatedExaminationDateTime =
                LocalDateTime.of(
                        2026,
                        8,
                        25,
                        15,
                        30
                );

        UrineRoutineExamination existingExamination =
                new UrineRoutineExamination();

        existingExamination.setId(examinationId);

        existingExamination.setSampleType(
                "Urine"
        );

        existingExamination.setColour(
                "Yellow"
        );

        existingExamination.setDeposit(
                "NOT FOUND"
        );

        existingExamination.setAppearance(
                "Clear"
        );

        existingExamination.setPh(
                7.0
        );

        existingExamination.setSpecificGravity(
                1.015
        );

        existingExamination.setQuantity(
                "20ml"
        );

        existingExamination.setUrineProtein(
                "Absent"
        );

        existingExamination.setBileSalt(
                "Absent"
        );

        existingExamination.setUrineGlucose(
                "Absent"
        );

        existingExamination.setUrineKetones(
                "Absent"
        );

        existingExamination.setBilePigment(
                "Absent"
        );

        existingExamination.setOccultBlood(
                "Absent"
        );

        existingExamination.setRbcs(
                "Absent"
        );

        existingExamination.setPusCells(
                "4-6/hpf"
        );

        existingExamination.setEpithelialCells(
                "1-2/hpf"
        );

        existingExamination.setCrystals(
                "Absent"
        );

        existingExamination.setCasts(
                "Absent"
        );

        existingExamination.setAmorphousDeposit(
                "Absent"
        );

        existingExamination.setBacteria(
                "Absent"
        );

        existingExamination.setTrichomonasVaginalis(
                "NOT SEEN"
        );

        existingExamination.setYeastCells(
                "Absent"
        );

        existingExamination.setRemarks(
                "Initial urine result"
        );

        existingExamination.setPathologistName(
                "Dr Radha"
        );


        UrineRoutineExaminationRequestDto request =
                new UrineRoutineExaminationRequestDto();

        request.setExaminationDateTime(
                updatedExaminationDateTime
        );

        request.setSampleType(
                "Urine"
        );

        // Physical examination
        request.setColour(
                "Pale Yellow"
        );

        request.setDeposit(
                "NOT FOUND"
        );

        request.setAppearance(
                "Clear"
        );

        request.setPh(
                6.5
        );

        request.setSpecificGravity(
                1.020
        );

        request.setQuantity(
                "25ml"
        );

        // Chemical examination
        request.setUrineProtein(
                "Absent"
        );

        request.setBileSalt(
                "Absent"
        );

        request.setUrineGlucose(
                "Absent"
        );

        request.setUrineKetones(
                "Absent"
        );

        request.setBilePigment(
                "Absent"
        );

        request.setOccultBlood(
                "Absent"
        );

        // Microscopic examination
        request.setRbcs(
                "Absent"
        );

        request.setPusCells(
                "2-3/hpf"
        );

        request.setEpithelialCells(
                "1-2/hpf"
        );

        request.setCrystals(
                "Absent"
        );

        request.setCasts(
                "Absent"
        );

        request.setAmorphousDeposit(
                "Absent"
        );

        request.setBacteria(
                "Absent"
        );

        request.setTrichomonasVaginalis(
                "NOT SEEN"
        );

        request.setYeastCells(
                "Absent"
        );

        request.setRemarks(
                "Updated urine routine result"
        );

        request.setPathologistName(
                "Dr Anil Kumar"
        );


        UrineRoutineExaminationResponseDto expectedResponse =
                new UrineRoutineExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setExaminationDateTime(
                updatedExaminationDateTime
        );

        expectedResponse.setSampleType(
                "Urine"
        );

        expectedResponse.setColour(
                "Pale Yellow"
        );

        expectedResponse.setAppearance(
                "Clear"
        );

        expectedResponse.setPh(
                6.5
        );

        expectedResponse.setSpecificGravity(
                1.020
        );

        expectedResponse.setPusCells(
                "2-3/hpf"
        );

        expectedResponse.setRemarks(
                "Updated urine routine result"
        );

        expectedResponse.setPathologistName(
                "Dr Anil Kumar"
        );


        when(urineRoutineExaminationRepository
                .findById(examinationId))
                .thenReturn(
                        Optional.of(existingExamination)
                );

        when(urineRoutineExaminationRepository
                .save(existingExamination))
                .thenReturn(existingExamination);

        when(urineRoutineExaminationMapper
                .toResponseDto(existingExamination))
                .thenReturn(expectedResponse);

        // Act
        UrineRoutineExaminationResponseDto actualResponse =
                urineRoutineExaminationService
                        .updateUrineRoutineExamination(
                                examinationId,
                                request
                        );

        // Assert

        assertEquals(
                updatedExaminationDateTime,
                existingExamination.getExaminationDateTime()
        );

        assertEquals(
                "Urine",
                existingExamination.getSampleType()
        );

        // Physical examination
        assertEquals(
                "Pale Yellow",
                existingExamination.getColour()
        );

        assertEquals(
                "NOT FOUND",
                existingExamination.getDeposit()
        );

        assertEquals(
                "Clear",
                existingExamination.getAppearance()
        );

        assertEquals(
                6.5,
                existingExamination.getPh()
        );

        assertEquals(
                1.020,
                existingExamination.getSpecificGravity()
        );

        assertEquals(
                "25ml",
                existingExamination.getQuantity()
        );

        // Chemical examination
        assertEquals(
                "Absent",
                existingExamination.getUrineProtein()
        );

        assertEquals(
                "Absent",
                existingExamination.getBileSalt()
        );

        assertEquals(
                "Absent",
                existingExamination.getUrineGlucose()
        );

        assertEquals(
                "Absent",
                existingExamination.getUrineKetones()
        );

        assertEquals(
                "Absent",
                existingExamination.getBilePigment()
        );

        assertEquals(
                "Absent",
                existingExamination.getOccultBlood()
        );

        // Microscopic examination
        assertEquals(
                "Absent",
                existingExamination.getRbcs()
        );

        assertEquals(
                "2-3/hpf",
                existingExamination.getPusCells()
        );

        assertEquals(
                "1-2/hpf",
                existingExamination.getEpithelialCells()
        );

        assertEquals(
                "Absent",
                existingExamination.getCrystals()
        );

        assertEquals(
                "Absent",
                existingExamination.getCasts()
        );

        assertEquals(
                "Absent",
                existingExamination.getAmorphousDeposit()
        );

        assertEquals(
                "Absent",
                existingExamination.getBacteria()
        );

        assertEquals(
                "NOT SEEN",
                existingExamination.getTrichomonasVaginalis()
        );

        assertEquals(
                "Absent",
                existingExamination.getYeastCells()
        );

        assertEquals(
                "Updated urine routine result",
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
                "Pale Yellow",
                actualResponse.getColour()
        );

        assertEquals(
                6.5,
                actualResponse.getPh()
        );

        assertEquals(
                1.020,
                actualResponse.getSpecificGravity()
        );

        assertEquals(
                "2-3/hpf",
                actualResponse.getPusCells()
        );

        assertEquals(
                "Updated urine routine result",
                actualResponse.getRemarks()
        );

        verify(urineRoutineExaminationRepository, times(1))
                .findById(examinationId);

        verify(urineRoutineExaminationRepository, times(1))
                .save(existingExamination);

        verify(urineRoutineExaminationMapper, times(1))
                .toResponseDto(existingExamination);
    }


    // =========================================================
    // UPDATE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenUpdatingUrineRoutineExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        UrineRoutineExaminationRequestDto request =
                new UrineRoutineExaminationRequestDto();

        request.setColour(
                "Pale Yellow"
        );

        when(urineRoutineExaminationRepository
                .findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        UrineRoutineExaminationNotFoundException exception =
                assertThrows(
                        UrineRoutineExaminationNotFoundException.class,
                        () -> urineRoutineExaminationService
                                .updateUrineRoutineExamination(
                                        examinationId,
                                        request
                                )
                );

        assertEquals(
                "Urine routine examination not found with id: 999",
                exception.getMessage()
        );

        verify(urineRoutineExaminationRepository, times(1))
                .findById(examinationId);

        verify(urineRoutineExaminationRepository, never())
                .save(
                        any(UrineRoutineExamination.class)
                );

        verify(urineRoutineExaminationMapper, never())
                .toResponseDto(
                        any(UrineRoutineExamination.class)
                );
    }


    // =========================================================
    // DELETE - SUCCESS
    // =========================================================

    @Test
    void shouldDeleteUrineRoutineExamination() {

        // Arrange
        Long examinationId = 10L;

        UrineRoutineExamination examination =
                new UrineRoutineExamination();

        examination.setId(examinationId);

        when(urineRoutineExaminationRepository
                .findById(examinationId))
                .thenReturn(
                        Optional.of(examination)
                );

        // Act
        urineRoutineExaminationService
                .deleteUrineRoutineExamination(
                        examinationId
                );

        // Assert
        verify(urineRoutineExaminationRepository, times(1))
                .findById(examinationId);

        verify(urineRoutineExaminationRepository, times(1))
                .delete(examination);
    }


    // =========================================================
    // DELETE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenDeletingUrineRoutineExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(urineRoutineExaminationRepository
                .findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        UrineRoutineExaminationNotFoundException exception =
                assertThrows(
                        UrineRoutineExaminationNotFoundException.class,
                        () -> urineRoutineExaminationService
                                .deleteUrineRoutineExamination(
                                        examinationId
                                )
                );

        assertEquals(
                "Urine routine examination not found with id: 999",
                exception.getMessage()
        );

        verify(urineRoutineExaminationRepository, times(1))
                .findById(examinationId);

        verify(urineRoutineExaminationRepository, never())
                .delete(
                        any(UrineRoutineExamination.class)
                );
    }
}