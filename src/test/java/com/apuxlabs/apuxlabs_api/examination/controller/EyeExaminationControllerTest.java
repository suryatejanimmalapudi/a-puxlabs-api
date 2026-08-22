package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.EyeExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.EyeExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.EyeExaminationService;
import com.apuxlabs.apuxlabs_api.exception.EyeExaminationNotFoundException;
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

@WebMvcTest(EyeExaminationController.class)
class EyeExaminationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EyeExaminationService eyeExaminationService;


    /**
     * Verifies that an eye examination is created successfully
     * for an existing registration.
     *
     * The service is mocked to return the created examination.
     * The controller should accept the JSON request and return
     * HTTP 201 Created with the eye examination details.
     */
    @Test
    void shouldCreateEyeExamination() throws Exception {

        // Arrange
        Long registrationId = 1L;

        EyeExaminationRequestDto request =
                new EyeExaminationRequestDto();

        request.setExaminationDate(LocalDate.of(2026, 8, 21));
        request.setRightDistanceSph(-1.25);
        request.setRightDistanceCyl(-0.50);
        request.setRightDistanceAxis(90);
        request.setRightDistanceVa("6/6");

        request.setLeftDistanceSph(-1.00);
        request.setLeftDistanceCyl(-0.25);
        request.setLeftDistanceAxis(80);
        request.setLeftDistanceVa("6/6");

        request.setColourVision("Normal");
        request.setRemarks("No abnormalities detected");
        request.setOptometristName("Dr. Priya");

        EyeExaminationResponseDto response =
                new EyeExaminationResponseDto();

        response.setId(10L);
        response.setRegistrationId(registrationId);
        response.setExaminationDate(LocalDate.of(2026, 8, 21));
        response.setRightDistanceSph(-1.25);
        response.setRightDistanceCyl(-0.50);
        response.setRightDistanceAxis(90);
        response.setRightDistanceVa("6/6");

        response.setLeftDistanceSph(-1.00);
        response.setLeftDistanceCyl(-0.25);
        response.setLeftDistanceAxis(80);
        response.setLeftDistanceVa("6/6");

        response.setColourVision("Normal");
        response.setRemarks("No abnormalities detected");
        response.setOptometristName("Dr. Priya");

        when(eyeExaminationService.createEyeExamination(
                eq(registrationId),
                any(EyeExaminationRequestDto.class)
        )).thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/eye-examinations/registration/{registrationId}",
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
                .andExpect(jsonPath("$.rightDistanceSph").value(-1.25))
                .andExpect(jsonPath("$.rightDistanceCyl").value(-0.50))
                .andExpect(jsonPath("$.rightDistanceAxis").value(90))
                .andExpect(jsonPath("$.rightDistanceVa").value("6/6"))
                .andExpect(jsonPath("$.leftDistanceSph").value(-1.00))
                .andExpect(jsonPath("$.colourVision").value("Normal"))
                .andExpect(jsonPath("$.optometristName")
                        .value("Dr. Priya"));

        verify(eyeExaminationService, times(1))
                .createEyeExamination(
                        eq(registrationId),
                        any(EyeExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * an eye examination is created for a registration that does not exist.
     *
     * The service is mocked to throw RegistrationNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenCreatingEyeExaminationForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        EyeExaminationRequestDto request =
                new EyeExaminationRequestDto();

        request.setRightDistanceSph(-1.25);

        when(eyeExaminationService.createEyeExamination(
                eq(registrationId),
                any(EyeExaminationRequestDto.class)
        )).thenThrow(
                new RegistrationNotFoundException(registrationId)
        );

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/eye-examinations/registration/{registrationId}",
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
                        .value("Registration not found with id: 999"))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/eye-examinations/registration/999"
                        ));

        verify(eyeExaminationService, times(1))
                .createEyeExamination(
                        eq(registrationId),
                        any(EyeExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that an eye examination is returned successfully
     * when the requested examination ID exists.
     *
     * The service is mocked to return an eye examination.
     * The controller should return HTTP 200 OK with its details.
     */
    @Test
    void shouldGetEyeExaminationById() throws Exception {

        // Arrange
        Long examinationId = 10L;

        EyeExaminationResponseDto response =
                new EyeExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);
        response.setRightDistanceSph(-1.25);
        response.setLeftDistanceSph(-1.00);
        response.setRightDistanceVa("6/6");
        response.setLeftDistanceVa("6/6");
        response.setColourVision("Normal");

        when(eyeExaminationService
                .getEyeExaminationById(examinationId))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/eye-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))
                .andExpect(jsonPath("$.rightDistanceSph").value(-1.25))
                .andExpect(jsonPath("$.leftDistanceSph").value(-1.00))
                .andExpect(jsonPath("$.rightDistanceVa").value("6/6"))
                .andExpect(jsonPath("$.leftDistanceVa").value("6/6"))
                .andExpect(jsonPath("$.colourVision").value("Normal"));

        verify(eyeExaminationService, times(1))
                .getEyeExaminationById(examinationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when the requested eye examination does not exist.
     *
     * The service is mocked to throw EyeExaminationNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenEyeExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        when(eyeExaminationService
                .getEyeExaminationById(examinationId))
                .thenThrow(
                        new EyeExaminationNotFoundException(examinationId)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/eye-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Eye examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value("/api/eye-examinations/999"));

        verify(eyeExaminationService, times(1))
                .getEyeExaminationById(examinationId);
    }


    /**
     * Verifies that all eye examinations associated with
     * a registration are returned successfully.
     *
     * The service is mocked to return multiple examinations.
     * The controller should return HTTP 200 OK with a JSON array.
     */
    @Test
    void shouldGetEyeExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        EyeExaminationResponseDto response1 =
                new EyeExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setRightDistanceVa("6/6");

        EyeExaminationResponseDto response2 =
                new EyeExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setRightDistanceVa("6/9");

        when(eyeExaminationService
                .getEyeExaminationsByRegistrationId(registrationId))
                .thenReturn(
                        List.of(response1, response2)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/eye-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].registrationId").value(1))
                .andExpect(jsonPath("$[0].rightDistanceVa")
                        .value("6/6"))

                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].registrationId").value(1))
                .andExpect(jsonPath("$[1].rightDistanceVa")
                        .value("6/9"));

        verify(eyeExaminationService, times(1))
                .getEyeExaminationsByRegistrationId(registrationId);
    }


    /**
     * Verifies that an empty JSON array is returned successfully
     * when an existing registration has no eye examinations.
     *
     * The service is mocked to return an empty list.
     * The controller should return HTTP 200 OK.
     */
    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoEyeExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        when(eyeExaminationService
                .getEyeExaminationsByRegistrationId(registrationId))
                .thenReturn(List.of());

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/eye-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(eyeExaminationService, times(1))
                .getEyeExaminationsByRegistrationId(registrationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * eye examinations are requested for a registration that does not exist.
     *
     * The service is mocked to throw RegistrationNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenRegistrationDoesNotExistWhileGettingEyeExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(eyeExaminationService
                .getEyeExaminationsByRegistrationId(registrationId))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/eye-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value("Registration not found with id: 999"))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/eye-examinations/registration/999"
                        ));

        verify(eyeExaminationService, times(1))
                .getEyeExaminationsByRegistrationId(registrationId);
    }


    /**
     * Verifies that an existing eye examination is updated successfully.
     *
     * The service is mocked to return the updated examination.
     * The controller should accept the updated JSON request and return
     * HTTP 200 OK with the modified examination details.
     */
    @Test
    void shouldUpdateEyeExamination() throws Exception {

        // Arrange
        Long examinationId = 10L;

        EyeExaminationRequestDto request =
                new EyeExaminationRequestDto();

        request.setRightDistanceSph(-1.50);
        request.setRightDistanceCyl(-0.75);
        request.setRightDistanceAxis(95);
        request.setRightDistanceVa("6/9");

        request.setLeftDistanceSph(-1.25);
        request.setLeftDistanceCyl(-0.50);
        request.setLeftDistanceAxis(85);
        request.setLeftDistanceVa("6/9");

        request.setColourVision("Normal");
        request.setRemarks("Updated prescription");
        request.setOptometristName("Dr. Anil");

        EyeExaminationResponseDto response =
                new EyeExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);

        response.setRightDistanceSph(-1.50);
        response.setRightDistanceCyl(-0.75);
        response.setRightDistanceAxis(95);
        response.setRightDistanceVa("6/9");

        response.setLeftDistanceSph(-1.25);
        response.setLeftDistanceCyl(-0.50);
        response.setLeftDistanceAxis(85);
        response.setLeftDistanceVa("6/9");

        response.setColourVision("Normal");
        response.setRemarks("Updated prescription");
        response.setOptometristName("Dr. Anil");

        when(eyeExaminationService.updateEyeExamination(
                eq(examinationId),
                any(EyeExaminationRequestDto.class)
        )).thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/eye-examinations/{id}",
                                examinationId
                        )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))
                .andExpect(jsonPath("$.rightDistanceSph").value(-1.50))
                .andExpect(jsonPath("$.rightDistanceCyl").value(-0.75))
                .andExpect(jsonPath("$.rightDistanceAxis").value(95))
                .andExpect(jsonPath("$.rightDistanceVa").value("6/9"))
                .andExpect(jsonPath("$.leftDistanceSph").value(-1.25))
                .andExpect(jsonPath("$.remarks")
                        .value("Updated prescription"))
                .andExpect(jsonPath("$.optometristName")
                        .value("Dr. Anil"));

        verify(eyeExaminationService, times(1))
                .updateEyeExamination(
                        eq(examinationId),
                        any(EyeExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to update an eye examination that does not exist.
     *
     * The service is mocked to throw EyeExaminationNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenUpdatingEyeExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        EyeExaminationRequestDto request =
                new EyeExaminationRequestDto();

        request.setRightDistanceSph(-1.50);

        when(eyeExaminationService.updateEyeExamination(
                eq(examinationId),
                any(EyeExaminationRequestDto.class)
        )).thenThrow(
                new EyeExaminationNotFoundException(examinationId)
        );

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/eye-examinations/{id}",
                                examinationId
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
                                "Eye examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value("/api/eye-examinations/999"));

        verify(eyeExaminationService, times(1))
                .updateEyeExamination(
                        eq(examinationId),
                        any(EyeExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that an existing eye examination is deleted successfully.
     *
     * The service is mocked to complete without throwing an exception.
     * The controller should return HTTP 204 No Content.
     */
    @Test
    void shouldDeleteEyeExamination() throws Exception {

        // Arrange
        Long examinationId = 10L;

        doNothing()
                .when(eyeExaminationService)
                .deleteEyeExamination(examinationId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/eye-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNoContent());

        verify(eyeExaminationService, times(1))
                .deleteEyeExamination(examinationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to delete an eye examination that does not exist.
     *
     * The service is mocked to throw EyeExaminationNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenDeletingEyeExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        doThrow(
                new EyeExaminationNotFoundException(examinationId)
        )
                .when(eyeExaminationService)
                .deleteEyeExamination(examinationId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/eye-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Eye examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value("/api/eye-examinations/999"));

        verify(eyeExaminationService, times(1))
                .deleteEyeExamination(examinationId);
    }
}