package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.LiverFunctionTestExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.LiverFunctionTestExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.LiverFunctionTestExamination;
import com.apuxlabs.apuxlabs_api.examination.mapper.LiverFunctionTestExaminationMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.LiverFunctionTestExaminationRepository;
import com.apuxlabs.apuxlabs_api.exception.LiverFunctionTestExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
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
class LiverFunctionTestExaminationServiceImplTest {

    @Mock
    private LiverFunctionTestExaminationRepository
            liverFunctionTestExaminationRepository;

    @Mock
    private LiverFunctionTestExaminationMapper
            liverFunctionTestExaminationMapper;

    @Mock
    private RegistrationRepository
            registrationRepository;

    @InjectMocks
    private LiverFunctionTestExaminationServiceImpl
            liverFunctionTestExaminationService;


    // =========================================================
    // CREATE - SUCCESS
    // =========================================================

    @Test
    void shouldCreateLiverFunctionTestExamination() {

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

        LiverFunctionTestExaminationRequestDto request =
                new LiverFunctionTestExaminationRequestDto();

        request.setExaminationDateTime(examinationDateTime);
        request.setSampleType("Serum");

        request.setBilirubinTotal(0.44);
        request.setBilirubinDirect(0.15);
        request.setBilirubinIndirect(0.29);

        request.setSgpt(38.89);
        request.setSgot(31.73);
        request.setSgotSgptRatio(0.82);

        request.setAlkalinePhosphatase(75.76);
        request.setGammaGlutamylTransferase(36.0);

        request.setTotalProteins(7.12);
        request.setAlbumin(4.95);
        request.setGlobulin(2.17);
        request.setAlbuminGlobulinRatio(2.28);

        request.setRemarks(
                "Liver function test reviewed"
        );

        request.setPathologistName(
                "Dr Radha"
        );

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        LiverFunctionTestExamination examination =
                new LiverFunctionTestExamination();

        LiverFunctionTestExamination savedExamination =
                new LiverFunctionTestExamination();

        savedExamination.setId(10L);
        savedExamination.setRegistration(registration);
        savedExamination.setExaminationDateTime(examinationDateTime);
        savedExamination.setSampleType("Serum");

        savedExamination.setBilirubinTotal(0.44);
        savedExamination.setBilirubinDirect(0.15);
        savedExamination.setBilirubinIndirect(0.29);

        savedExamination.setSgpt(38.89);
        savedExamination.setSgot(31.73);
        savedExamination.setSgotSgptRatio(0.82);

        savedExamination.setAlkalinePhosphatase(75.76);
        savedExamination.setGammaGlutamylTransferase(36.0);

        savedExamination.setTotalProteins(7.12);
        savedExamination.setAlbumin(4.95);
        savedExamination.setGlobulin(2.17);
        savedExamination.setAlbuminGlobulinRatio(2.28);

        LiverFunctionTestExaminationResponseDto expectedResponse =
                new LiverFunctionTestExaminationResponseDto();

        expectedResponse.setId(10L);
        expectedResponse.setRegistrationId(registrationId);
        expectedResponse.setExaminationDateTime(examinationDateTime);
        expectedResponse.setSampleType("Serum");

        expectedResponse.setBilirubinTotal(0.44);
        expectedResponse.setBilirubinDirect(0.15);
        expectedResponse.setBilirubinIndirect(0.29);

        expectedResponse.setSgpt(38.89);
        expectedResponse.setSgot(31.73);
        expectedResponse.setSgotSgptRatio(0.82);

        expectedResponse.setAlkalinePhosphatase(75.76);
        expectedResponse.setGammaGlutamylTransferase(36.0);

        expectedResponse.setTotalProteins(7.12);
        expectedResponse.setAlbumin(4.95);
        expectedResponse.setGlobulin(2.17);
        expectedResponse.setAlbuminGlobulinRatio(2.28);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(liverFunctionTestExaminationMapper.toEntity(request))
                .thenReturn(examination);

        when(liverFunctionTestExaminationRepository.save(examination))
                .thenReturn(savedExamination);

        when(liverFunctionTestExaminationMapper
                .toResponseDto(savedExamination))
                .thenReturn(expectedResponse);

        // Act
        LiverFunctionTestExaminationResponseDto actualResponse =
                liverFunctionTestExaminationService
                        .createLiverFunctionTestExamination(
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
                "Serum",
                actualResponse.getSampleType()
        );

        assertEquals(
                0.44,
                actualResponse.getBilirubinTotal()
        );

        assertEquals(
                38.89,
                actualResponse.getSgpt()
        );

        assertEquals(
                31.73,
                actualResponse.getSgot()
        );

        assertEquals(
                75.76,
                actualResponse.getAlkalinePhosphatase()
        );

        assertEquals(
                7.12,
                actualResponse.getTotalProteins()
        );

        assertEquals(
                4.95,
                actualResponse.getAlbumin()
        );

        assertEquals(
                registration,
                examination.getRegistration()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(liverFunctionTestExaminationMapper, times(1))
                .toEntity(request);

        verify(liverFunctionTestExaminationRepository, times(1))
                .save(examination);

        verify(liverFunctionTestExaminationMapper, times(1))
                .toResponseDto(savedExamination);
    }


    // =========================================================
    // CREATE - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFound() {

        // Arrange
        Long registrationId = 999L;

        LiverFunctionTestExaminationRequestDto request =
                new LiverFunctionTestExaminationRequestDto();

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> liverFunctionTestExaminationService
                                .createLiverFunctionTestExamination(
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

        verify(liverFunctionTestExaminationMapper, never())
                .toEntity(
                        any(
                                LiverFunctionTestExaminationRequestDto.class
                        )
                );

        verify(liverFunctionTestExaminationRepository, never())
                .save(
                        any(LiverFunctionTestExamination.class)
                );
    }


    // =========================================================
    // GET BY ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetLiverFunctionTestExaminationById() {

        // Arrange
        Long examinationId = 10L;

        Registration registration =
                new Registration();

        registration.setId(1L);

        LiverFunctionTestExamination examination =
                new LiverFunctionTestExamination();

        examination.setId(examinationId);
        examination.setRegistration(registration);

        examination.setSampleType("Serum");
        examination.setBilirubinTotal(0.44);
        examination.setBilirubinDirect(0.15);
        examination.setBilirubinIndirect(0.29);

        examination.setSgpt(38.89);
        examination.setSgot(31.73);
        examination.setSgotSgptRatio(0.82);

        examination.setAlkalinePhosphatase(75.76);
        examination.setGammaGlutamylTransferase(36.0);

        examination.setTotalProteins(7.12);
        examination.setAlbumin(4.95);
        examination.setGlobulin(2.17);
        examination.setAlbuminGlobulinRatio(2.28);

        LiverFunctionTestExaminationResponseDto expectedResponse =
                new LiverFunctionTestExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setRegistrationId(1L);

        expectedResponse.setSampleType("Serum");
        expectedResponse.setBilirubinTotal(0.44);

        expectedResponse.setSgpt(38.89);
        expectedResponse.setSgot(31.73);

        expectedResponse.setAlkalinePhosphatase(75.76);

        expectedResponse.setTotalProteins(7.12);
        expectedResponse.setAlbumin(4.95);
        expectedResponse.setGlobulin(2.17);

        when(liverFunctionTestExaminationRepository
                .findById(examinationId))
                .thenReturn(Optional.of(examination));

        when(liverFunctionTestExaminationMapper
                .toResponseDto(examination))
                .thenReturn(expectedResponse);

        // Act
        LiverFunctionTestExaminationResponseDto actualResponse =
                liverFunctionTestExaminationService
                        .getLiverFunctionTestExaminationById(
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
                0.44,
                actualResponse.getBilirubinTotal()
        );

        assertEquals(
                38.89,
                actualResponse.getSgpt()
        );

        assertEquals(
                31.73,
                actualResponse.getSgot()
        );

        assertEquals(
                75.76,
                actualResponse.getAlkalinePhosphatase()
        );

        assertEquals(
                7.12,
                actualResponse.getTotalProteins()
        );

        verify(liverFunctionTestExaminationRepository, times(1))
                .findById(examinationId);

        verify(liverFunctionTestExaminationMapper, times(1))
                .toResponseDto(examination);
    }


    // =========================================================
    // GET BY ID - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenLiverFunctionTestExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(liverFunctionTestExaminationRepository
                .findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        LiverFunctionTestExaminationNotFoundException exception =
                assertThrows(
                        LiverFunctionTestExaminationNotFoundException.class,
                        () -> liverFunctionTestExaminationService
                                .getLiverFunctionTestExaminationById(
                                        examinationId
                                )
                );

        assertEquals(
                "Liver function test examination not found with id: 999",
                exception.getMessage()
        );

        verify(liverFunctionTestExaminationRepository, times(1))
                .findById(examinationId);

        verify(liverFunctionTestExaminationMapper, never())
                .toResponseDto(
                        any(LiverFunctionTestExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetLiverFunctionTestExaminationsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        LiverFunctionTestExamination examination1 =
                new LiverFunctionTestExamination();

        examination1.setId(10L);
        examination1.setRegistration(registration);
        examination1.setBilirubinTotal(0.44);
        examination1.setSgpt(38.89);
        examination1.setSgot(31.73);

        LiverFunctionTestExamination examination2 =
                new LiverFunctionTestExamination();

        examination2.setId(11L);
        examination2.setRegistration(registration);
        examination2.setBilirubinTotal(0.50);
        examination2.setSgpt(40.20);
        examination2.setSgot(32.10);

        List<LiverFunctionTestExamination> examinations =
                List.of(
                        examination1,
                        examination2
                );

        LiverFunctionTestExaminationResponseDto response1 =
                new LiverFunctionTestExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setBilirubinTotal(0.44);
        response1.setSgpt(38.89);
        response1.setSgot(31.73);

        LiverFunctionTestExaminationResponseDto response2 =
                new LiverFunctionTestExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setBilirubinTotal(0.50);
        response2.setSgpt(40.20);
        response2.setSgot(32.10);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(liverFunctionTestExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(examinations);

        when(liverFunctionTestExaminationMapper
                .toResponseDto(examination1))
                .thenReturn(response1);

        when(liverFunctionTestExaminationMapper
                .toResponseDto(examination2))
                .thenReturn(response2);

        // Act
        List<LiverFunctionTestExaminationResponseDto> actualResponses =
                liverFunctionTestExaminationService
                        .getLiverFunctionTestExaminationsByRegistrationId(
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
                0.44,
                actualResponses.get(0).getBilirubinTotal()
        );

        assertEquals(
                11L,
                actualResponses.get(1).getId()
        );

        assertEquals(
                0.50,
                actualResponses.get(1).getBilirubinTotal()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(liverFunctionTestExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(liverFunctionTestExaminationMapper, times(1))
                .toResponseDto(examination1);

        verify(liverFunctionTestExaminationMapper, times(1))
                .toResponseDto(examination2);
    }


    // =========================================================
    // GET BY REGISTRATION ID - EMPTY LIST
    // =========================================================

    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoLiverFunctionTestExaminations() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(liverFunctionTestExaminationRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(List.of());

        // Act
        List<LiverFunctionTestExaminationResponseDto> actualResponses =
                liverFunctionTestExaminationService
                        .getLiverFunctionTestExaminationsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertTrue(
                actualResponses.isEmpty()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(liverFunctionTestExaminationRepository, times(1))
                .findAllByRegistrationId(registrationId);

        verify(liverFunctionTestExaminationMapper, never())
                .toResponseDto(
                        any(LiverFunctionTestExamination.class)
                );
    }


    // =========================================================
    // GET BY REGISTRATION ID - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFoundWhileGettingLiverFunctionTestExaminations() {

        // Arrange
        Long registrationId = 999L;

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> liverFunctionTestExaminationService
                                .getLiverFunctionTestExaminationsByRegistrationId(
                                        registrationId
                                )
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(liverFunctionTestExaminationRepository, never())
                .findAllByRegistrationId(
                        anyLong()
                );

        verify(liverFunctionTestExaminationMapper, never())
                .toResponseDto(
                        any(LiverFunctionTestExamination.class)
                );
    }


    // =========================================================
    // UPDATE - SUCCESS
    // =========================================================

    @Test
    void shouldUpdateLiverFunctionTestExamination() {

        // Arrange
        Long examinationId = 10L;

        LocalDateTime updatedExaminationDateTime =
                LocalDateTime.of(
                        2026,
                        8,
                        25,
                        14,
                        30
                );

        LiverFunctionTestExamination existingExamination =
                new LiverFunctionTestExamination();

        existingExamination.setId(examinationId);
        existingExamination.setSampleType("Serum");

        existingExamination.setBilirubinTotal(0.44);
        existingExamination.setBilirubinDirect(0.15);
        existingExamination.setBilirubinIndirect(0.29);

        existingExamination.setSgpt(38.89);
        existingExamination.setSgot(31.73);
        existingExamination.setSgotSgptRatio(0.82);

        existingExamination.setAlkalinePhosphatase(75.76);
        existingExamination.setGammaGlutamylTransferase(36.0);

        existingExamination.setTotalProteins(7.12);
        existingExamination.setAlbumin(4.95);
        existingExamination.setGlobulin(2.17);
        existingExamination.setAlbuminGlobulinRatio(2.28);

        existingExamination.setRemarks(
                "Initial LFT result"
        );

        existingExamination.setPathologistName(
                "Dr Radha"
        );

        LiverFunctionTestExaminationRequestDto request =
                new LiverFunctionTestExaminationRequestDto();

        request.setExaminationDateTime(
                updatedExaminationDateTime
        );

        request.setSampleType(
                "Serum"
        );

        request.setBilirubinTotal(0.50);
        request.setBilirubinDirect(0.18);
        request.setBilirubinIndirect(0.32);

        request.setSgpt(40.20);
        request.setSgot(32.10);
        request.setSgotSgptRatio(0.80);

        request.setAlkalinePhosphatase(78.50);
        request.setGammaGlutamylTransferase(38.0);

        request.setTotalProteins(7.30);
        request.setAlbumin(4.90);
        request.setGlobulin(2.40);
        request.setAlbuminGlobulinRatio(2.04);

        request.setRemarks(
                "Updated liver function test"
        );

        request.setPathologistName(
                "Dr Anil Kumar"
        );

        LiverFunctionTestExaminationResponseDto expectedResponse =
                new LiverFunctionTestExaminationResponseDto();

        expectedResponse.setId(examinationId);
        expectedResponse.setExaminationDateTime(
                updatedExaminationDateTime
        );

        expectedResponse.setSampleType("Serum");

        expectedResponse.setBilirubinTotal(0.50);
        expectedResponse.setBilirubinDirect(0.18);
        expectedResponse.setBilirubinIndirect(0.32);

        expectedResponse.setSgpt(40.20);
        expectedResponse.setSgot(32.10);
        expectedResponse.setSgotSgptRatio(0.80);

        expectedResponse.setAlkalinePhosphatase(78.50);
        expectedResponse.setGammaGlutamylTransferase(38.0);

        expectedResponse.setTotalProteins(7.30);
        expectedResponse.setAlbumin(4.90);
        expectedResponse.setGlobulin(2.40);
        expectedResponse.setAlbuminGlobulinRatio(2.04);

        expectedResponse.setRemarks(
                "Updated liver function test"
        );

        expectedResponse.setPathologistName(
                "Dr Anil Kumar"
        );

        when(liverFunctionTestExaminationRepository
                .findById(examinationId))
                .thenReturn(
                        Optional.of(existingExamination)
                );

        when(liverFunctionTestExaminationRepository
                .save(existingExamination))
                .thenReturn(existingExamination);

        when(liverFunctionTestExaminationMapper
                .toResponseDto(existingExamination))
                .thenReturn(expectedResponse);

        // Act
        LiverFunctionTestExaminationResponseDto actualResponse =
                liverFunctionTestExaminationService
                        .updateLiverFunctionTestExamination(
                                examinationId,
                                request
                        );

        // Assert

        assertEquals(
                updatedExaminationDateTime,
                existingExamination.getExaminationDateTime()
        );

        assertEquals(
                "Serum",
                existingExamination.getSampleType()
        );

        // Bilirubin values
        assertEquals(
                0.50,
                existingExamination.getBilirubinTotal()
        );

        assertEquals(
                0.18,
                existingExamination.getBilirubinDirect()
        );

        assertEquals(
                0.32,
                existingExamination.getBilirubinIndirect()
        );

        // Liver enzymes
        assertEquals(
                40.20,
                existingExamination.getSgpt()
        );

        assertEquals(
                32.10,
                existingExamination.getSgot()
        );

        assertEquals(
                0.80,
                existingExamination.getSgotSgptRatio()
        );

        assertEquals(
                78.50,
                existingExamination.getAlkalinePhosphatase()
        );

        assertEquals(
                38.0,
                existingExamination.getGammaGlutamylTransferase()
        );

        // Protein values
        assertEquals(
                7.30,
                existingExamination.getTotalProteins()
        );

        assertEquals(
                4.90,
                existingExamination.getAlbumin()
        );

        assertEquals(
                2.40,
                existingExamination.getGlobulin()
        );

        assertEquals(
                2.04,
                existingExamination.getAlbuminGlobulinRatio()
        );

        assertEquals(
                "Updated liver function test",
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
                0.50,
                actualResponse.getBilirubinTotal()
        );

        assertEquals(
                40.20,
                actualResponse.getSgpt()
        );

        assertEquals(
                32.10,
                actualResponse.getSgot()
        );

        assertEquals(
                7.30,
                actualResponse.getTotalProteins()
        );

        assertEquals(
                "Updated liver function test",
                actualResponse.getRemarks()
        );

        verify(liverFunctionTestExaminationRepository, times(1))
                .findById(examinationId);

        verify(liverFunctionTestExaminationRepository, times(1))
                .save(existingExamination);

        verify(liverFunctionTestExaminationMapper, times(1))
                .toResponseDto(existingExamination);
    }


    // =========================================================
    // UPDATE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenUpdatingLiverFunctionTestExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        LiverFunctionTestExaminationRequestDto request =
                new LiverFunctionTestExaminationRequestDto();

        request.setBilirubinTotal(
                0.50
        );

        when(liverFunctionTestExaminationRepository
                .findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        LiverFunctionTestExaminationNotFoundException exception =
                assertThrows(
                        LiverFunctionTestExaminationNotFoundException.class,
                        () -> liverFunctionTestExaminationService
                                .updateLiverFunctionTestExamination(
                                        examinationId,
                                        request
                                )
                );

        assertEquals(
                "Liver function test examination not found with id: 999",
                exception.getMessage()
        );

        verify(liverFunctionTestExaminationRepository, times(1))
                .findById(examinationId);

        verify(liverFunctionTestExaminationRepository, never())
                .save(
                        any(LiverFunctionTestExamination.class)
                );

        verify(liverFunctionTestExaminationMapper, never())
                .toResponseDto(
                        any(LiverFunctionTestExamination.class)
                );
    }


    // =========================================================
    // DELETE - SUCCESS
    // =========================================================

    @Test
    void shouldDeleteLiverFunctionTestExamination() {

        // Arrange
        Long examinationId = 10L;

        LiverFunctionTestExamination examination =
                new LiverFunctionTestExamination();

        examination.setId(examinationId);

        when(liverFunctionTestExaminationRepository
                .findById(examinationId))
                .thenReturn(
                        Optional.of(examination)
                );

        // Act
        liverFunctionTestExaminationService
                .deleteLiverFunctionTestExamination(
                        examinationId
                );

        // Assert
        verify(liverFunctionTestExaminationRepository, times(1))
                .findById(examinationId);

        verify(liverFunctionTestExaminationRepository, times(1))
                .delete(examination);
    }


    // =========================================================
    // DELETE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenDeletingLiverFunctionTestExaminationNotFound() {

        // Arrange
        Long examinationId = 999L;

        when(liverFunctionTestExaminationRepository
                .findById(examinationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        LiverFunctionTestExaminationNotFoundException exception =
                assertThrows(
                        LiverFunctionTestExaminationNotFoundException.class,
                        () -> liverFunctionTestExaminationService
                                .deleteLiverFunctionTestExamination(
                                        examinationId
                                )
                );

        assertEquals(
                "Liver function test examination not found with id: 999",
                exception.getMessage()
        );

        verify(liverFunctionTestExaminationRepository, times(1))
                .findById(examinationId);

        verify(liverFunctionTestExaminationRepository, never())
                .delete(
                        any(LiverFunctionTestExamination.class)
                );
    }
}