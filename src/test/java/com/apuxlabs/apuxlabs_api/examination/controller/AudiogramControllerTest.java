package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.AudiogramRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.AudiogramResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.AudiogramService;
import com.apuxlabs.apuxlabs_api.exception.AudiogramNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AudiogramController.class)
class AudiogramControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AudiogramService audiogramService;


    /**
     * Verifies that an audiogram is created successfully
     * for an existing registration.
     *
     * The service is mocked to return the newly created audiogram.
     * The controller should accept the JSON request and return
     * HTTP 201 Created with the created audiogram details.
     */
    @Test
    void shouldCreateAudiogram() throws Exception {

        // Arrange
        Long registrationId = 1L;

        AudiogramRequestDto request =
                new AudiogramRequestDto();

        request.setExaminationDate(LocalDate.of(2026, 8, 21));

        request.setRight125Hz(15);
        request.setRight250Hz(20);
        request.setRight500Hz(15);
        request.setRight1000Hz(20);
        request.setRight2000Hz(15);
        request.setRight4000Hz(20);
        request.setRight8000Hz(25);

        request.setLeft125Hz(20);
        request.setLeft250Hz(15);
        request.setLeft500Hz(20);
        request.setLeft1000Hz(15);
        request.setLeft2000Hz(20);
        request.setLeft4000Hz(25);
        request.setLeft8000Hz(20);

        request.setImpression(
                "Bilateral hearing sensitivity within normal limits"
        );

        request.setAudiologistName("Dr. Priya Sharma");

        AudiogramResponseDto response =
                new AudiogramResponseDto();

        response.setId(10L);
        response.setRegistrationId(registrationId);
        response.setExaminationDate(LocalDate.of(2026, 8, 21));

        response.setRight125Hz(15);
        response.setRight250Hz(20);
        response.setRight500Hz(15);

        response.setLeft125Hz(20);
        response.setLeft250Hz(15);
        response.setLeft500Hz(20);

        response.setImpression(
                "Bilateral hearing sensitivity within normal limits"
        );

        response.setAudiologistName("Dr. Priya Sharma");

        when(audiogramService.createAudiogram(
                eq(registrationId),
                any(AudiogramRequestDto.class)
        )).thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/audiograms/registration/{registrationId}",
                                registrationId
                        )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))
                .andExpect(jsonPath("$.right125Hz").value(15))
                .andExpect(jsonPath("$.right250Hz").value(20))
                .andExpect(jsonPath("$.left125Hz").value(20))
                .andExpect(jsonPath("$.left250Hz").value(15))
                .andExpect(jsonPath("$.impression")
                        .value(
                                "Bilateral hearing sensitivity within normal limits"
                        ))
                .andExpect(jsonPath("$.audiologistName")
                        .value("Dr. Priya Sharma"));

        verify(audiogramService, times(1))
                .createAudiogram(
                        eq(registrationId),
                        any(AudiogramRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when creating an audiogram for a registration that does not exist.
     *
     * The service is mocked to throw RegistrationNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenCreatingAudiogramForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        AudiogramRequestDto request =
                new AudiogramRequestDto();

        request.setRight125Hz(15);

        when(audiogramService.createAudiogram(
                eq(registrationId),
                any(AudiogramRequestDto.class)
        )).thenThrow(
                new RegistrationNotFoundException(registrationId)
        );

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/audiograms/registration/{registrationId}",
                                registrationId
                        )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Registration not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/audiograms/registration/999"
                        ));

        verify(audiogramService, times(1))
                .createAudiogram(
                        eq(registrationId),
                        any(AudiogramRequestDto.class)
                );
    }


    /**
     * Verifies that an audiogram is returned successfully
     * when the requested audiogram ID exists.
     *
     * The service is mocked to return an audiogram.
     * The controller should return HTTP 200 OK with its details.
     */
    @Test
    void shouldGetAudiogramById() throws Exception {

        // Arrange
        Long audiogramId = 10L;

        AudiogramResponseDto response =
                new AudiogramResponseDto();

        response.setId(audiogramId);
        response.setRegistrationId(1L);
        response.setRight125Hz(15);
        response.setRight250Hz(20);
        response.setLeft125Hz(20);
        response.setLeft250Hz(15);
        response.setImpression("Normal hearing sensitivity");

        when(audiogramService.getAudiogramById(audiogramId))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/audiograms/{id}",
                                audiogramId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))
                .andExpect(jsonPath("$.right125Hz").value(15))
                .andExpect(jsonPath("$.right250Hz").value(20))
                .andExpect(jsonPath("$.left125Hz").value(20))
                .andExpect(jsonPath("$.left250Hz").value(15))
                .andExpect(jsonPath("$.impression")
                        .value("Normal hearing sensitivity"));

        verify(audiogramService, times(1))
                .getAudiogramById(audiogramId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when the requested audiogram does not exist.
     *
     * The service is mocked to throw AudiogramNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenAudiogramDoesNotExist()
            throws Exception {

        // Arrange
        Long audiogramId = 999L;

        when(audiogramService.getAudiogramById(audiogramId))
                .thenThrow(
                        new AudiogramNotFoundException(audiogramId)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/audiograms/{id}",
                                audiogramId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Audiogram not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value("/api/audiograms/999"));

        verify(audiogramService, times(1))
                .getAudiogramById(audiogramId);
    }


    /**
     * Verifies that all audiograms associated with
     * a registration are returned successfully.
     *
     * The service is mocked to return multiple audiograms.
     * The controller should return HTTP 200 OK with a JSON array.
     */
    @Test
    void shouldGetAudiogramsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        AudiogramResponseDto response1 =
                new AudiogramResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setRight125Hz(15);

        AudiogramResponseDto response2 =
                new AudiogramResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setRight125Hz(25);

        when(audiogramService
                .getAudiogramsByRegistrationId(registrationId))
                .thenReturn(
                        List.of(response1, response2)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/audiograms/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].registrationId").value(1))
                .andExpect(jsonPath("$[0].right125Hz").value(15))

                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].registrationId").value(1))
                .andExpect(jsonPath("$[1].right125Hz").value(25));

        verify(audiogramService, times(1))
                .getAudiogramsByRegistrationId(registrationId);
    }


    /**
     * Verifies that an empty JSON array is returned successfully
     * when an existing registration has no audiograms.
     *
     * The service is mocked to return an empty list.
     * The controller should return HTTP 200 OK.
     */
    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoAudiograms()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        when(audiogramService
                .getAudiogramsByRegistrationId(registrationId))
                .thenReturn(List.of());

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/audiograms/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(audiogramService, times(1))
                .getAudiogramsByRegistrationId(registrationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when audiograms are requested for a registration that does not exist.
     *
     * The service is mocked to throw RegistrationNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenRegistrationDoesNotExistWhileGettingAudiograms()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(audiogramService
                .getAudiogramsByRegistrationId(registrationId))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/audiograms/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Registration not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/audiograms/registration/999"
                        ));

        verify(audiogramService, times(1))
                .getAudiogramsByRegistrationId(registrationId);
    }


    /**
     * Verifies that an existing audiogram is updated successfully.
     *
     * The service is mocked to return the updated audiogram.
     * The controller should accept the JSON request and return
     * HTTP 200 OK with the updated audiogram details.
     */
    @Test
    void shouldUpdateAudiogram() throws Exception {

        // Arrange
        Long audiogramId = 10L;

        AudiogramRequestDto request =
                new AudiogramRequestDto();

        request.setRight125Hz(25);
        request.setRight250Hz(30);
        request.setRight500Hz(25);

        request.setLeft125Hz(20);
        request.setLeft250Hz(25);
        request.setLeft500Hz(30);

        request.setImpression(
                "Mild bilateral hearing loss"
        );

        request.setAudiologistName(
                "Dr. Anil Kumar"
        );

        AudiogramResponseDto response =
                new AudiogramResponseDto();

        response.setId(audiogramId);
        response.setRegistrationId(1L);

        response.setRight125Hz(25);
        response.setRight250Hz(30);
        response.setRight500Hz(25);

        response.setLeft125Hz(20);
        response.setLeft250Hz(25);
        response.setLeft500Hz(30);

        response.setImpression(
                "Mild bilateral hearing loss"
        );

        response.setAudiologistName(
                "Dr. Anil Kumar"
        );

        when(audiogramService.updateAudiogram(
                eq(audiogramId),
                any(AudiogramRequestDto.class)
        )).thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/audiograms/{id}",
                                audiogramId
                        )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))
                .andExpect(jsonPath("$.right125Hz").value(25))
                .andExpect(jsonPath("$.right250Hz").value(30))
                .andExpect(jsonPath("$.left125Hz").value(20))
                .andExpect(jsonPath("$.left250Hz").value(25))
                .andExpect(jsonPath("$.impression")
                        .value(
                                "Mild bilateral hearing loss"
                        ))
                .andExpect(jsonPath("$.audiologistName")
                        .value("Dr. Anil Kumar"));

        verify(audiogramService, times(1))
                .updateAudiogram(
                        eq(audiogramId),
                        any(AudiogramRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when attempting to update an audiogram that does not exist.
     *
     * The service is mocked to throw AudiogramNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenUpdatingAudiogramDoesNotExist()
            throws Exception {

        // Arrange
        Long audiogramId = 999L;

        AudiogramRequestDto request =
                new AudiogramRequestDto();

        request.setRight125Hz(25);

        when(audiogramService.updateAudiogram(
                eq(audiogramId),
                any(AudiogramRequestDto.class)
        )).thenThrow(
                new AudiogramNotFoundException(audiogramId)
        );

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/audiograms/{id}",
                                audiogramId
                        )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Audiogram not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value("/api/audiograms/999"));

        verify(audiogramService, times(1))
                .updateAudiogram(
                        eq(audiogramId),
                        any(AudiogramRequestDto.class)
                );
    }


    /**
     * Verifies that an existing audiogram is deleted successfully.
     *
     * The service is mocked to complete without throwing an exception.
     * The controller should return HTTP 204 No Content.
     */
    @Test
    void shouldDeleteAudiogram() throws Exception {

        // Arrange
        Long audiogramId = 10L;

        doNothing()
                .when(audiogramService)
                .deleteAudiogram(audiogramId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/audiograms/{id}",
                                audiogramId
                        )
                )
                .andExpect(status().isNoContent());

        verify(audiogramService, times(1))
                .deleteAudiogram(audiogramId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when attempting to delete an audiogram that does not exist.
     *
     * The service is mocked to throw AudiogramNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenDeletingAudiogramDoesNotExist()
            throws Exception {

        // Arrange
        Long audiogramId = 999L;

        doThrow(
                new AudiogramNotFoundException(audiogramId)
        )
                .when(audiogramService)
                .deleteAudiogram(audiogramId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/audiograms/{id}",
                                audiogramId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Audiogram not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value("/api/audiograms/999"));

        verify(audiogramService, times(1))
                .deleteAudiogram(audiogramId);
    }
}