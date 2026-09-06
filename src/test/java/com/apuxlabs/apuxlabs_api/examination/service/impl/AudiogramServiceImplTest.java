package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.AudiogramRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.AudiogramResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.Audiogram;
import com.apuxlabs.apuxlabs_api.examination.mapper.AudiogramMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.AudiogramRepository;
import com.apuxlabs.apuxlabs_api.exception.AudiogramNotFoundException;
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
class AudiogramServiceImplTest {

    @Mock
    private AudiogramRepository audiogramRepository;

    @Mock
    private AudiogramMapper audiogramMapper;

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private AudiogramServiceImpl audiogramService;


    // =========================================================
    // CREATE - SUCCESS
    // =========================================================

    @Test
    void shouldCreateAudiogram() {

        // Arrange
        Long registrationId = 1L;

        AudiogramRequestDto request =
                new AudiogramRequestDto();

        request.setRight125Hz(15);
        request.setRight250Hz(20);
        request.setRight500Hz(15);

        request.setLeft125Hz(20);
        request.setLeft250Hz(15);
        request.setLeft500Hz(20);

        request.setImpression(
                "Bilateral hearing sensitivity within normal limits"
        );

        request.setAudiologistName("Dr. Priya Sharma");

        Registration registration = new Registration();
        registration.setId(registrationId);

        Audiogram audiogram =
                new Audiogram();

        Audiogram savedAudiogram =
                new Audiogram();

        savedAudiogram.setId(10L);
        savedAudiogram.setRegistration(registration);

        AudiogramResponseDto expectedResponse =
                new AudiogramResponseDto();

        expectedResponse.setId(10L);
        expectedResponse.setRegistrationId(registrationId);

        expectedResponse.setRight125Hz(15);
        expectedResponse.setRight250Hz(20);
        expectedResponse.setRight500Hz(15);

        expectedResponse.setLeft125Hz(20);
        expectedResponse.setLeft250Hz(15);
        expectedResponse.setLeft500Hz(20);

        expectedResponse.setImpression(
                "Bilateral hearing sensitivity within normal limits"
        );

        expectedResponse.setAudiologistName(
                "Dr. Priya Sharma"
        );

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(audiogramMapper.toEntity(request))
                .thenReturn(audiogram);

        when(audiogramRepository.save(audiogram))
                .thenReturn(savedAudiogram);

        when(audiogramMapper.toResponseDto(savedAudiogram))
                .thenReturn(expectedResponse);

        // Act
        AudiogramResponseDto actualResponse =
                audiogramService.createAudiogram(
                        registrationId,
                        request
                );

        // Assert
        assertEquals(10L, actualResponse.getId());

        assertEquals(
                1L,
                actualResponse.getRegistrationId()
        );

        assertEquals(
                15,
                actualResponse.getRight125Hz()
        );

        assertEquals(
                20,
                actualResponse.getLeft125Hz()
        );

        assertEquals(
                "Bilateral hearing sensitivity within normal limits",
                actualResponse.getImpression()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(audiogramMapper, times(1))
                .toEntity(request);

        verify(audiogramRepository, times(1))
                .save(audiogram);

        verify(audiogramMapper, times(1))
                .toResponseDto(savedAudiogram);
    }


    // =========================================================
    // CREATE - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFound() {

        // Arrange
        Long registrationId = 999L;

        AudiogramRequestDto request =
                new AudiogramRequestDto();

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> audiogramService.createAudiogram(
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

        verify(audiogramMapper, never())
                .toEntity(any(AudiogramRequestDto.class));

        verify(audiogramRepository, never())
                .save(any(Audiogram.class));
    }


    // =========================================================
    // GET BY ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetAudiogramById() {

        // Arrange
        Long audiogramId = 10L;

        Registration registration = new Registration();
        registration.setId(1L);

        Audiogram audiogram =
                new Audiogram();

        audiogram.setId(audiogramId);
        audiogram.setRegistration(registration);

        audiogram.setRight125Hz(15);
        audiogram.setRight250Hz(20);
        audiogram.setLeft125Hz(20);
        audiogram.setLeft250Hz(15);

        audiogram.setImpression(
                "Normal hearing sensitivity"
        );

        AudiogramResponseDto expectedResponse =
                new AudiogramResponseDto();

        expectedResponse.setId(audiogramId);
        expectedResponse.setRegistrationId(1L);

        expectedResponse.setRight125Hz(15);
        expectedResponse.setRight250Hz(20);

        expectedResponse.setLeft125Hz(20);
        expectedResponse.setLeft250Hz(15);

        expectedResponse.setImpression(
                "Normal hearing sensitivity"
        );

        when(audiogramRepository.findById(audiogramId))
                .thenReturn(Optional.of(audiogram));

        when(audiogramMapper.toResponseDto(audiogram))
                .thenReturn(expectedResponse);

        // Act
        AudiogramResponseDto actualResponse =
                audiogramService.getAudiogramById(
                        audiogramId
                );

        // Assert
        assertEquals(10L, actualResponse.getId());

        assertEquals(
                1L,
                actualResponse.getRegistrationId()
        );

        assertEquals(
                15,
                actualResponse.getRight125Hz()
        );

        assertEquals(
                20,
                actualResponse.getLeft125Hz()
        );

        assertEquals(
                "Normal hearing sensitivity",
                actualResponse.getImpression()
        );

        verify(audiogramRepository, times(1))
                .findById(audiogramId);

        verify(audiogramMapper, times(1))
                .toResponseDto(audiogram);
    }


    // =========================================================
    // GET BY ID - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenAudiogramNotFound() {

        // Arrange
        Long audiogramId = 999L;

        when(audiogramRepository.findById(audiogramId))
                .thenReturn(Optional.empty());

        // Act + Assert
        AudiogramNotFoundException exception =
                assertThrows(
                        AudiogramNotFoundException.class,
                        () -> audiogramService
                                .getAudiogramById(
                                        audiogramId
                                )
                );

        assertEquals(
                "Audiogram not found with id: 999",
                exception.getMessage()
        );

        verify(audiogramRepository, times(1))
                .findById(audiogramId);

        verify(audiogramMapper, never())
                .toResponseDto(any(Audiogram.class));
    }


    // =========================================================
    // GET BY REGISTRATION ID - SUCCESS
    // =========================================================

    @Test
    void shouldGetAudiogramsByRegistrationId() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        Audiogram audiogram1 =
                new Audiogram();

        audiogram1.setId(10L);
        audiogram1.setRegistration(registration);

        Audiogram audiogram2 =
                new Audiogram();

        audiogram2.setId(11L);
        audiogram2.setRegistration(registration);

        List<Audiogram> audiograms =
                List.of(
                        audiogram1,
                        audiogram2
                );

        AudiogramResponseDto response1 =
                new AudiogramResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(
                registrationId
        );

        AudiogramResponseDto response2 =
                new AudiogramResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(
                registrationId
        );

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(audiogramRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(audiograms);

        when(audiogramMapper.toResponseDto(audiogram1))
                .thenReturn(response1);

        when(audiogramMapper.toResponseDto(audiogram2))
                .thenReturn(response2);

        // Act
        List<AudiogramResponseDto> actualResponses =
                audiogramService
                        .getAudiogramsByRegistrationId(
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
                11L,
                actualResponses.get(1).getId()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(audiogramRepository, times(1))
                .findAllByRegistrationId(
                        registrationId
                );

        verify(audiogramMapper, times(1))
                .toResponseDto(audiogram1);

        verify(audiogramMapper, times(1))
                .toResponseDto(audiogram2);
    }


    // =========================================================
    // GET BY REGISTRATION ID - EMPTY LIST
    // =========================================================

    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoAudiograms() {

        // Arrange
        Long registrationId = 1L;

        Registration registration =
                new Registration();

        registration.setId(registrationId);

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.of(registration));

        when(audiogramRepository
                .findAllByRegistrationId(registrationId))
                .thenReturn(List.of());

        // Act
        List<AudiogramResponseDto> actualResponses =
                audiogramService
                        .getAudiogramsByRegistrationId(
                                registrationId
                        );

        // Assert
        assertTrue(actualResponses.isEmpty());

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(audiogramRepository, times(1))
                .findAllByRegistrationId(
                        registrationId
                );

        verify(audiogramMapper, never())
                .toResponseDto(any(Audiogram.class));
    }


    // =========================================================
    // GET BY REGISTRATION ID - REGISTRATION NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenRegistrationNotFoundWhileGettingAudiograms() {

        // Arrange
        Long registrationId = 999L;

        when(registrationRepository.findById(registrationId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RegistrationNotFoundException exception =
                assertThrows(
                        RegistrationNotFoundException.class,
                        () -> audiogramService
                                .getAudiogramsByRegistrationId(
                                        registrationId
                                )
                );

        assertEquals(
                "Registration not found with id: 999",
                exception.getMessage()
        );

        verify(registrationRepository, times(1))
                .findById(registrationId);

        verify(audiogramRepository, never())
                .findAllByRegistrationId(anyLong());

        verify(audiogramMapper, never())
                .toResponseDto(any(Audiogram.class));
    }


    // =========================================================
    // UPDATE - SUCCESS
    // =========================================================

    @Test
    void shouldUpdateAudiogram() {

        // Arrange
        Long audiogramId = 10L;

        Audiogram existingAudiogram =
                new Audiogram();

        existingAudiogram.setId(audiogramId);

        existingAudiogram.setRight125Hz(15);
        existingAudiogram.setRight250Hz(20);

        existingAudiogram.setLeft125Hz(20);
        existingAudiogram.setLeft250Hz(15);

        existingAudiogram.setImpression(
                "Normal hearing"
        );

        AudiogramRequestDto request =
                new AudiogramRequestDto();

        request.setRight125Hz(25);
        request.setRight250Hz(30);

        request.setLeft125Hz(25);
        request.setLeft250Hz(30);

        request.setImpression(
                "Mild bilateral hearing loss"
        );

        request.setAudiologistName(
                "Dr. Anil Kumar"
        );

        AudiogramResponseDto expectedResponse =
                new AudiogramResponseDto();

        expectedResponse.setId(audiogramId);

        expectedResponse.setRight125Hz(25);
        expectedResponse.setRight250Hz(30);

        expectedResponse.setLeft125Hz(25);
        expectedResponse.setLeft250Hz(30);

        expectedResponse.setImpression(
                "Mild bilateral hearing loss"
        );

        when(audiogramRepository.findById(audiogramId))
                .thenReturn(Optional.of(existingAudiogram));

        when(audiogramMapper
                .toResponseDto(existingAudiogram))
                .thenReturn(expectedResponse);

        // Act
        AudiogramResponseDto actualResponse =
                audiogramService.updateAudiogram(
                        audiogramId,
                        request
                );

        // Assert

        // Check actual entity was modified.
        assertEquals(
                25,
                existingAudiogram.getRight125Hz()
        );

        assertEquals(
                30,
                existingAudiogram.getRight250Hz()
        );

        assertEquals(
                25,
                existingAudiogram.getLeft125Hz()
        );

        assertEquals(
                30,
                existingAudiogram.getLeft250Hz()
        );

        assertEquals(
                "Mild bilateral hearing loss",
                existingAudiogram.getImpression()
        );

        // Check returned DTO.
        assertEquals(
                10L,
                actualResponse.getId()
        );

        assertEquals(
                25,
                actualResponse.getRight125Hz()
        );

        assertEquals(
                "Mild bilateral hearing loss",
                actualResponse.getImpression()
        );

        verify(audiogramRepository, times(1))
                .findById(audiogramId);

        verify(audiogramMapper, times(1))
                .toResponseDto(existingAudiogram);
    }


    // =========================================================
    // UPDATE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenUpdatingAudiogramNotFound() {

        // Arrange
        Long audiogramId = 999L;

        AudiogramRequestDto request =
                new AudiogramRequestDto();

        request.setRight125Hz(25);
        request.setLeft125Hz(25);

        when(audiogramRepository.findById(audiogramId))
                .thenReturn(Optional.empty());

        // Act + Assert
        AudiogramNotFoundException exception =
                assertThrows(
                        AudiogramNotFoundException.class,
                        () -> audiogramService
                                .updateAudiogram(
                                        audiogramId,
                                        request
                                )
                );

        assertEquals(
                "Audiogram not found with id: 999",
                exception.getMessage()
        );

        verify(audiogramRepository, times(1))
                .findById(audiogramId);

        verify(audiogramMapper, never())
                .toResponseDto(any(Audiogram.class));
    }


    // =========================================================
    // DELETE - SUCCESS
    // =========================================================

    @Test
    void shouldDeleteAudiogram() {

        // Arrange
        Long audiogramId = 10L;

        Audiogram audiogram =
                new Audiogram();

        audiogram.setId(audiogramId);

        when(audiogramRepository.findById(audiogramId))
                .thenReturn(Optional.of(audiogram));

        // Act
        audiogramService.deleteAudiogram(
                audiogramId
        );

        // Assert
        verify(audiogramRepository, times(1))
                .findById(audiogramId);

        verify(audiogramRepository, times(1))
                .delete(audiogram);
    }


    // =========================================================
    // DELETE - NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenDeletingAudiogramNotFound() {

        // Arrange
        Long audiogramId = 999L;

        when(audiogramRepository.findById(audiogramId))
                .thenReturn(Optional.empty());

        // Act + Assert
        AudiogramNotFoundException exception =
                assertThrows(
                        AudiogramNotFoundException.class,
                        () -> audiogramService
                                .deleteAudiogram(
                                        audiogramId
                                )
                );

        assertEquals(
                "Audiogram not found with id: 999",
                exception.getMessage()
        );

        verify(audiogramRepository, times(1))
                .findById(audiogramId);

        verify(audiogramRepository, never())
                .delete(any(Audiogram.class));
    }
}