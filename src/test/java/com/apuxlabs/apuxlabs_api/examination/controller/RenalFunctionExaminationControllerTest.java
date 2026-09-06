package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.RenalFunctionExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.RenalFunctionExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.enums.RenalFunctionTestType;
import com.apuxlabs.apuxlabs_api.examination.service.RenalFunctionExaminationService;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RenalFunctionExaminationNotFoundException;

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

@WebMvcTest(RenalFunctionExaminationController.class)
class RenalFunctionExaminationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RenalFunctionExaminationService
            renalFunctionExaminationService;


    /**
     * Verifies that a renal function examination is created successfully
     * for an existing registration.
     *
     * The service is mocked to return the created examination.
     * The controller should accept the JSON request and return
     * HTTP 201 Created with the examination details.
     */
    @Test
    void shouldCreateRenalFunctionExamination() throws Exception {

        // Arrange
        Long registrationId = 1L;

        RenalFunctionExaminationRequestDto request =
                new RenalFunctionExaminationRequestDto();

        request.setTestType(RenalFunctionTestType.UREA);
        request.setResultValue(19.82);
        request.setSampleType("Serum");
        request.setReferenceRange("7 - 40");
        request.setUnit("mg/dl");
        request.setRemarks("Within reference range");
        request.setPathologistName("Dr Radha");

        RenalFunctionExaminationResponseDto response =
                new RenalFunctionExaminationResponseDto();

        response.setId(10L);
        response.setRegistrationId(registrationId);
        response.setTestType(RenalFunctionTestType.UREA);
        response.setResultValue(19.82);
        response.setSampleType("Serum");
        response.setReferenceRange("7 - 40");
        response.setUnit("mg/dl");
        response.setRemarks("Within reference range");
        response.setPathologistName("Dr Radha");

        when(renalFunctionExaminationService
                .createRenalFunctionExamination(
                        eq(registrationId),
                        any(RenalFunctionExaminationRequestDto.class)
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/renal-function-examinations/registration/{registrationId}",
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
                .andExpect(jsonPath("$.testType").value("UREA"))
                .andExpect(jsonPath("$.resultValue").value(19.82))
                .andExpect(jsonPath("$.sampleType").value("Serum"))
                .andExpect(jsonPath("$.referenceRange").value("7 - 40"))
                .andExpect(jsonPath("$.unit").value("mg/dl"))
                .andExpect(jsonPath("$.remarks")
                        .value("Within reference range"))
                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Radha"));

        verify(renalFunctionExaminationService, times(1))
                .createRenalFunctionExamination(
                        eq(registrationId),
                        any(RenalFunctionExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * a renal function examination is created for a registration
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenCreatingRenalFunctionForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        RenalFunctionExaminationRequestDto request =
                new RenalFunctionExaminationRequestDto();

        request.setTestType(RenalFunctionTestType.UREA);
        request.setResultValue(19.82);

        when(renalFunctionExaminationService
                .createRenalFunctionExamination(
                        eq(registrationId),
                        any(RenalFunctionExaminationRequestDto.class)
                ))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/renal-function-examinations/registration/{registrationId}",
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
                                "/api/renal-function-examinations/registration/999"
                        ));

        verify(renalFunctionExaminationService, times(1))
                .createRenalFunctionExamination(
                        eq(registrationId),
                        any(RenalFunctionExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that a renal function examination is returned
     * successfully when the examination ID exists.
     */
    @Test
    void shouldGetRenalFunctionExaminationById()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        RenalFunctionExaminationResponseDto response =
                new RenalFunctionExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);
        response.setTestType(RenalFunctionTestType.CREATININE);
        response.setResultValue(0.73);
        response.setSampleType("Serum");
        response.setReferenceRange("0.6 - 1.3");
        response.setUnit("mg/dl");

        when(renalFunctionExaminationService
                .getRenalFunctionExaminationById(examinationId))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/renal-function-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))
                .andExpect(jsonPath("$.testType")
                        .value("CREATININE"))
                .andExpect(jsonPath("$.resultValue").value(0.73))
                .andExpect(jsonPath("$.sampleType").value("Serum"))
                .andExpect(jsonPath("$.referenceRange")
                        .value("0.6 - 1.3"))
                .andExpect(jsonPath("$.unit").value("mg/dl"));

        verify(renalFunctionExaminationService, times(1))
                .getRenalFunctionExaminationById(examinationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * the requested renal function examination does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenRenalFunctionExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        when(renalFunctionExaminationService
                .getRenalFunctionExaminationById(examinationId))
                .thenThrow(
                        new RenalFunctionExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/renal-function-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Renal function examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/renal-function-examinations/999"
                        ));

        verify(renalFunctionExaminationService, times(1))
                .getRenalFunctionExaminationById(examinationId);
    }


    /**
     * Verifies that all renal function examinations associated
     * with a registration are returned successfully.
     */
    @Test
    void shouldGetRenalFunctionExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

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

        when(renalFunctionExaminationService
                .getRenalFunctionExaminationsByRegistrationId(
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
                                "/api/renal-function-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].registrationId").value(1))
                .andExpect(jsonPath("$[0].testType").value("UREA"))
                .andExpect(jsonPath("$[0].resultValue").value(19.82))

                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].registrationId").value(1))
                .andExpect(jsonPath("$[1].testType")
                        .value("CREATININE"))
                .andExpect(jsonPath("$[1].resultValue").value(0.73));

        verify(renalFunctionExaminationService, times(1))
                .getRenalFunctionExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an empty JSON array is returned successfully
     * when an existing registration has no renal function examinations.
     */
    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoRenalFunctionExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        when(renalFunctionExaminationService
                .getRenalFunctionExaminationsByRegistrationId(
                        registrationId
                ))
                .thenReturn(List.of());

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/renal-function-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(renalFunctionExaminationService, times(1))
                .getRenalFunctionExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * renal function examinations are requested for a registration
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenRegistrationDoesNotExistWhileGettingRenalFunctionExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(renalFunctionExaminationService
                .getRenalFunctionExaminationsByRegistrationId(
                        registrationId
                ))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/renal-function-examinations/registration/{registrationId}",
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
                                "/api/renal-function-examinations/registration/999"
                        ));

        verify(renalFunctionExaminationService, times(1))
                .getRenalFunctionExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that all Urea examinations associated
     * with a registration are returned successfully.
     */
    @Test
    void shouldGetUreaExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

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

        when(renalFunctionExaminationService
                .getUreaExaminationsByRegistrationId(
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
                                "/api/renal-function-examinations/registration/{registrationId}/urea",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].testType").value("UREA"))
                .andExpect(jsonPath("$[0].resultValue").value(19.82))

                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].testType").value("UREA"))
                .andExpect(jsonPath("$[1].resultValue").value(21.40));

        verify(renalFunctionExaminationService, times(1))
                .getUreaExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * Urea examinations are requested for a registration
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenGettingUreaForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(renalFunctionExaminationService
                .getUreaExaminationsByRegistrationId(
                        registrationId
                ))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/renal-function-examinations/registration/{registrationId}/urea",
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
                                "/api/renal-function-examinations/registration/999/urea"
                        ));

        verify(renalFunctionExaminationService, times(1))
                .getUreaExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that all Creatinine examinations associated
     * with a registration are returned successfully.
     */
    @Test
    void shouldGetCreatinineExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        RenalFunctionExaminationResponseDto response =
                new RenalFunctionExaminationResponseDto();

        response.setId(12L);
        response.setRegistrationId(registrationId);
        response.setTestType(RenalFunctionTestType.CREATININE);
        response.setResultValue(0.73);

        when(renalFunctionExaminationService
                .getCreatinineExaminationsByRegistrationId(
                        registrationId
                ))
                .thenReturn(
                        List.of(response)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/renal-function-examinations/registration/{registrationId}/creatinine",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(12))
                .andExpect(jsonPath("$[0].registrationId").value(1))
                .andExpect(jsonPath("$[0].testType")
                        .value("CREATININE"))
                .andExpect(jsonPath("$[0].resultValue").value(0.73));

        verify(renalFunctionExaminationService, times(1))
                .getCreatinineExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * Creatinine examinations are requested for a registration
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenGettingCreatinineForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(renalFunctionExaminationService
                .getCreatinineExaminationsByRegistrationId(
                        registrationId
                ))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/renal-function-examinations/registration/{registrationId}/creatinine",
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
                                "/api/renal-function-examinations/registration/999/creatinine"
                        ));

        verify(renalFunctionExaminationService, times(1))
                .getCreatinineExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an existing renal function examination
     * is updated successfully.
     */
    @Test
    void shouldUpdateRenalFunctionExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        RenalFunctionExaminationRequestDto request =
                new RenalFunctionExaminationRequestDto();

        request.setTestType(RenalFunctionTestType.UREA);
        request.setResultValue(21.40);
        request.setSampleType("Serum");
        request.setReferenceRange("7 - 40");
        request.setUnit("mg/dl");
        request.setRemarks("Updated result");
        request.setPathologistName("Dr Anil Kumar");

        RenalFunctionExaminationResponseDto response =
                new RenalFunctionExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);
        response.setTestType(RenalFunctionTestType.UREA);
        response.setResultValue(21.40);
        response.setSampleType("Serum");
        response.setReferenceRange("7 - 40");
        response.setUnit("mg/dl");
        response.setRemarks("Updated result");
        response.setPathologistName("Dr Anil Kumar");

        when(renalFunctionExaminationService
                .updateRenalFunctionExamination(
                        eq(examinationId),
                        any(RenalFunctionExaminationRequestDto.class)
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/renal-function-examinations/{id}",
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
                .andExpect(jsonPath("$.testType").value("UREA"))
                .andExpect(jsonPath("$.resultValue").value(21.40))
                .andExpect(jsonPath("$.sampleType").value("Serum"))
                .andExpect(jsonPath("$.referenceRange").value("7 - 40"))
                .andExpect(jsonPath("$.unit").value("mg/dl"))
                .andExpect(jsonPath("$.remarks")
                        .value("Updated result"))
                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Anil Kumar"));

        verify(renalFunctionExaminationService, times(1))
                .updateRenalFunctionExamination(
                        eq(examinationId),
                        any(RenalFunctionExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to update a renal function examination
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenUpdatingRenalFunctionExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        RenalFunctionExaminationRequestDto request =
                new RenalFunctionExaminationRequestDto();

        request.setTestType(RenalFunctionTestType.UREA);
        request.setResultValue(21.40);

        when(renalFunctionExaminationService
                .updateRenalFunctionExamination(
                        eq(examinationId),
                        any(RenalFunctionExaminationRequestDto.class)
                ))
                .thenThrow(
                        new RenalFunctionExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/renal-function-examinations/{id}",
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
                                "Renal function examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/renal-function-examinations/999"
                        ));

        verify(renalFunctionExaminationService, times(1))
                .updateRenalFunctionExamination(
                        eq(examinationId),
                        any(RenalFunctionExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that an existing renal function examination
     * is deleted successfully.
     */
    @Test
    void shouldDeleteRenalFunctionExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        doNothing()
                .when(renalFunctionExaminationService)
                .deleteRenalFunctionExamination(examinationId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/renal-function-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNoContent());

        verify(renalFunctionExaminationService, times(1))
                .deleteRenalFunctionExamination(examinationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to delete a renal function examination
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenDeletingRenalFunctionExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        doThrow(
                new RenalFunctionExaminationNotFoundException(
                        examinationId
                )
        )
                .when(renalFunctionExaminationService)
                .deleteRenalFunctionExamination(examinationId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/renal-function-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Renal function examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/renal-function-examinations/999"
                        ));

        verify(renalFunctionExaminationService, times(1))
                .deleteRenalFunctionExamination(examinationId);
    }
}