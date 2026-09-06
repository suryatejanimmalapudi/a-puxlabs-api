package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.SerumCholinesteraseExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.SerumCholinesteraseExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.SerumCholinesteraseExaminationService;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.SerumCholinesteraseExaminationNotFoundException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SerumCholinesteraseExaminationController.class)
class SerumCholinesteraseExaminationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SerumCholinesteraseExaminationService
            serumCholinesteraseExaminationService;


    /**
     * Verifies that a serum cholinesterase examination is created
     * successfully for an existing registration.
     *
     * The service is mocked to return the created examination.
     * The controller should accept the JSON request and return
     * HTTP 201 Created with the examination details.
     */
    @Test
    void shouldCreateSerumCholinesteraseExamination()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        SerumCholinesteraseExaminationRequestDto request =
                new SerumCholinesteraseExaminationRequestDto();

        request.setResultValue(8883.15);

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
                "Within reference range"
        );

        request.setPathologistName(
                "Dr Radha"
        );

        SerumCholinesteraseExaminationResponseDto response =
                new SerumCholinesteraseExaminationResponseDto();

        response.setId(10L);

        response.setRegistrationId(
                registrationId
        );

        response.setResultValue(
                8883.15
        );

        response.setMethod(
                "Butyrylthio Choline Method"
        );

        response.setSampleType(
                "Serum"
        );

        response.setReferenceRange(
                "4000 - 11500"
        );

        response.setUnit(
                "U/L"
        );

        response.setRemarks(
                "Within reference range"
        );

        response.setPathologistName(
                "Dr Radha"
        );

        when(serumCholinesteraseExaminationService
                .createSerumCholinesteraseExamination(
                        eq(registrationId),
                        any(SerumCholinesteraseExaminationRequestDto.class)
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/serum-cholinesterase-examinations/registration/{registrationId}",
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
                .andExpect(jsonPath("$.resultValue").value(8883.15))
                .andExpect(jsonPath("$.method")
                        .value("Butyrylthio Choline Method"))
                .andExpect(jsonPath("$.sampleType").value("Serum"))
                .andExpect(jsonPath("$.referenceRange")
                        .value("4000 - 11500"))
                .andExpect(jsonPath("$.unit").value("U/L"))
                .andExpect(jsonPath("$.remarks")
                        .value("Within reference range"))
                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Radha"));

        verify(serumCholinesteraseExaminationService, times(1))
                .createSerumCholinesteraseExamination(
                        eq(registrationId),
                        any(SerumCholinesteraseExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * a serum cholinesterase examination is created for
     * a registration that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenCreatingSerumCholinesteraseForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        SerumCholinesteraseExaminationRequestDto request =
                new SerumCholinesteraseExaminationRequestDto();

        request.setResultValue(
                8883.15
        );

        when(serumCholinesteraseExaminationService
                .createSerumCholinesteraseExamination(
                        eq(registrationId),
                        any(SerumCholinesteraseExaminationRequestDto.class)
                ))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/serum-cholinesterase-examinations/registration/{registrationId}",
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
                                "/api/serum-cholinesterase-examinations/registration/999"
                        ));

        verify(serumCholinesteraseExaminationService, times(1))
                .createSerumCholinesteraseExamination(
                        eq(registrationId),
                        any(SerumCholinesteraseExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that a serum cholinesterase examination is returned
     * successfully when the examination ID exists.
     */
    @Test
    void shouldGetSerumCholinesteraseExaminationById()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        SerumCholinesteraseExaminationResponseDto response =
                new SerumCholinesteraseExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);
        response.setResultValue(8883.15);

        response.setMethod(
                "Butyrylthio Choline Method"
        );

        response.setSampleType(
                "Serum"
        );

        response.setReferenceRange(
                "4000 - 11500"
        );

        response.setUnit(
                "U/L"
        );

        when(serumCholinesteraseExaminationService
                .getSerumCholinesteraseExaminationById(examinationId))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/serum-cholinesterase-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))
                .andExpect(jsonPath("$.resultValue").value(8883.15))
                .andExpect(jsonPath("$.method")
                        .value("Butyrylthio Choline Method"))
                .andExpect(jsonPath("$.sampleType").value("Serum"))
                .andExpect(jsonPath("$.referenceRange")
                        .value("4000 - 11500"))
                .andExpect(jsonPath("$.unit").value("U/L"));

        verify(serumCholinesteraseExaminationService, times(1))
                .getSerumCholinesteraseExaminationById(
                        examinationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * the requested serum cholinesterase examination does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenSerumCholinesteraseExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        when(serumCholinesteraseExaminationService
                .getSerumCholinesteraseExaminationById(examinationId))
                .thenThrow(
                        new SerumCholinesteraseExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/serum-cholinesterase-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Serum cholinesterase examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/serum-cholinesterase-examinations/999"
                        ));

        verify(serumCholinesteraseExaminationService, times(1))
                .getSerumCholinesteraseExaminationById(
                        examinationId
                );
    }


    /**
     * Verifies that all serum cholinesterase examinations
     * associated with a registration are returned successfully.
     */
    @Test
    void shouldGetSerumCholinesteraseExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        SerumCholinesteraseExaminationResponseDto response1 =
                new SerumCholinesteraseExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setResultValue(8883.15);

        SerumCholinesteraseExaminationResponseDto response2 =
                new SerumCholinesteraseExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setResultValue(9100.25);

        when(serumCholinesteraseExaminationService
                .getSerumCholinesteraseExaminationsByRegistrationId(
                        registrationId
                ))
                .thenReturn(
                        List.of(
                                response1,
                                response2
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/serum-cholinesterase-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].registrationId").value(1))
                .andExpect(jsonPath("$[0].resultValue")
                        .value(8883.15))

                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].registrationId").value(1))
                .andExpect(jsonPath("$[1].resultValue")
                        .value(9100.25));

        verify(serumCholinesteraseExaminationService, times(1))
                .getSerumCholinesteraseExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an empty JSON array is returned successfully
     * when an existing registration has no serum cholinesterase
     * examinations.
     */
    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoSerumCholinesteraseExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        when(serumCholinesteraseExaminationService
                .getSerumCholinesteraseExaminationsByRegistrationId(
                        registrationId
                ))
                .thenReturn(List.of());

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/serum-cholinesterase-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(serumCholinesteraseExaminationService, times(1))
                .getSerumCholinesteraseExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * serum cholinesterase examinations are requested for
     * a registration that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenRegistrationDoesNotExistWhileGettingSerumCholinesteraseExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(serumCholinesteraseExaminationService
                .getSerumCholinesteraseExaminationsByRegistrationId(
                        registrationId
                ))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/serum-cholinesterase-examinations/registration/{registrationId}",
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
                                "/api/serum-cholinesterase-examinations/registration/999"
                        ));

        verify(serumCholinesteraseExaminationService, times(1))
                .getSerumCholinesteraseExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an existing serum cholinesterase examination
     * is updated successfully.
     */
    @Test
    void shouldUpdateSerumCholinesteraseExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        SerumCholinesteraseExaminationRequestDto request =
                new SerumCholinesteraseExaminationRequestDto();

        request.setResultValue(
                9100.25
        );

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

        SerumCholinesteraseExaminationResponseDto response =
                new SerumCholinesteraseExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);
        response.setResultValue(9100.25);

        response.setMethod(
                "Butyrylthio Choline Method"
        );

        response.setSampleType(
                "Serum"
        );

        response.setReferenceRange(
                "4000 - 11500"
        );

        response.setUnit(
                "U/L"
        );

        response.setRemarks(
                "Updated result"
        );

        response.setPathologistName(
                "Dr Anil Kumar"
        );

        when(serumCholinesteraseExaminationService
                .updateSerumCholinesteraseExamination(
                        eq(examinationId),
                        any(SerumCholinesteraseExaminationRequestDto.class)
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/serum-cholinesterase-examinations/{id}",
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
                .andExpect(jsonPath("$.resultValue").value(9100.25))
                .andExpect(jsonPath("$.method")
                        .value("Butyrylthio Choline Method"))
                .andExpect(jsonPath("$.sampleType").value("Serum"))
                .andExpect(jsonPath("$.referenceRange")
                        .value("4000 - 11500"))
                .andExpect(jsonPath("$.unit").value("U/L"))
                .andExpect(jsonPath("$.remarks")
                        .value("Updated result"))
                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Anil Kumar"));

        verify(serumCholinesteraseExaminationService, times(1))
                .updateSerumCholinesteraseExamination(
                        eq(examinationId),
                        any(SerumCholinesteraseExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to update a serum cholinesterase examination
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenUpdatingSerumCholinesteraseExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        SerumCholinesteraseExaminationRequestDto request =
                new SerumCholinesteraseExaminationRequestDto();

        request.setResultValue(
                9100.25
        );

        when(serumCholinesteraseExaminationService
                .updateSerumCholinesteraseExamination(
                        eq(examinationId),
                        any(SerumCholinesteraseExaminationRequestDto.class)
                ))
                .thenThrow(
                        new SerumCholinesteraseExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/serum-cholinesterase-examinations/{id}",
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
                                "Serum cholinesterase examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/serum-cholinesterase-examinations/999"
                        ));

        verify(serumCholinesteraseExaminationService, times(1))
                .updateSerumCholinesteraseExamination(
                        eq(examinationId),
                        any(SerumCholinesteraseExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that an existing serum cholinesterase examination
     * is deleted successfully.
     */
    @Test
    void shouldDeleteSerumCholinesteraseExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        doNothing()
                .when(serumCholinesteraseExaminationService)
                .deleteSerumCholinesteraseExamination(
                        examinationId
                );

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/serum-cholinesterase-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNoContent());

        verify(serumCholinesteraseExaminationService, times(1))
                .deleteSerumCholinesteraseExamination(
                        examinationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to delete a serum cholinesterase examination
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenDeletingSerumCholinesteraseExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        doThrow(
                new SerumCholinesteraseExaminationNotFoundException(
                        examinationId
                )
        )
                .when(serumCholinesteraseExaminationService)
                .deleteSerumCholinesteraseExamination(
                        examinationId
                );

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/serum-cholinesterase-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Serum cholinesterase examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/serum-cholinesterase-examinations/999"
                        ));

        verify(serumCholinesteraseExaminationService, times(1))
                .deleteSerumCholinesteraseExamination(
                        examinationId
                );
    }
}