package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.BloodGlucoseExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.BloodGlucoseExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.enums.BloodGlucoseTestType;
import com.apuxlabs.apuxlabs_api.examination.service.BloodGlucoseExaminationService;
import com.apuxlabs.apuxlabs_api.exception.BloodGlucoseExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;

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

@WebMvcTest(BloodGlucoseExaminationController.class)
class BloodGlucoseExaminationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BloodGlucoseExaminationService
            bloodGlucoseExaminationService;


    /**
     * Verifies that a blood glucose examination is created successfully
     * for an existing registration.
     *
     * The service is mocked to return the created examination.
     * The controller should accept the JSON request and return
     * HTTP 201 Created with the examination details.
     */
    @Test
    void shouldCreateBloodGlucoseExamination() throws Exception {

        // Arrange
        Long registrationId = 1L;

        BloodGlucoseExaminationRequestDto request =
                new BloodGlucoseExaminationRequestDto();

        request.setTestType(BloodGlucoseTestType.FBS);
        request.setGlucoseValue(108.92);
        request.setSampleType("Fluoride plasma");
        request.setReferenceRange("70 - 110");
        request.setUnit("mg/dl");
        request.setRemarks("Normal");
        request.setPathologistName("Dr Radha");

        BloodGlucoseExaminationResponseDto response =
                new BloodGlucoseExaminationResponseDto();

        response.setId(10L);
        response.setRegistrationId(registrationId);
        response.setTestType(BloodGlucoseTestType.FBS);
        response.setGlucoseValue(108.92);
        response.setSampleType("Fluoride plasma");
        response.setReferenceRange("70 - 110");
        response.setUnit("mg/dl");
        response.setRemarks("Normal");
        response.setPathologistName("Dr Radha");

        when(bloodGlucoseExaminationService
                .createBloodGlucoseExamination(
                        eq(registrationId),
                        any(BloodGlucoseExaminationRequestDto.class)
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/blood-glucose-examinations/registration/{registrationId}",
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
                .andExpect(jsonPath("$.testType").value("FBS"))
                .andExpect(jsonPath("$.glucoseValue").value(108.92))
                .andExpect(jsonPath("$.sampleType")
                        .value("Fluoride plasma"))
                .andExpect(jsonPath("$.referenceRange")
                        .value("70 - 110"))
                .andExpect(jsonPath("$.unit").value("mg/dl"))
                .andExpect(jsonPath("$.remarks").value("Normal"))
                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Radha"));

        verify(bloodGlucoseExaminationService, times(1))
                .createBloodGlucoseExamination(
                        eq(registrationId),
                        any(BloodGlucoseExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * a blood glucose examination is created for a registration
     * that does not exist.
     *
     * The service is mocked to throw RegistrationNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenCreatingBloodGlucoseForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        BloodGlucoseExaminationRequestDto request =
                new BloodGlucoseExaminationRequestDto();

        request.setTestType(BloodGlucoseTestType.FBS);
        request.setGlucoseValue(108.92);

        when(bloodGlucoseExaminationService
                .createBloodGlucoseExamination(
                        eq(registrationId),
                        any(BloodGlucoseExaminationRequestDto.class)
                ))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/blood-glucose-examinations/registration/{registrationId}",
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
                                "/api/blood-glucose-examinations/registration/999"
                        ));

        verify(bloodGlucoseExaminationService, times(1))
                .createBloodGlucoseExamination(
                        eq(registrationId),
                        any(BloodGlucoseExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that a blood glucose examination is returned
     * successfully when the examination ID exists.
     */
    @Test
    void shouldGetBloodGlucoseExaminationById()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        BloodGlucoseExaminationResponseDto response =
                new BloodGlucoseExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);
        response.setTestType(BloodGlucoseTestType.FBS);
        response.setGlucoseValue(108.92);
        response.setSampleType("Fluoride plasma");
        response.setReferenceRange("70 - 110");
        response.setUnit("mg/dl");

        when(bloodGlucoseExaminationService
                .getBloodGlucoseExaminationById(examinationId))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/blood-glucose-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))
                .andExpect(jsonPath("$.testType").value("FBS"))
                .andExpect(jsonPath("$.glucoseValue").value(108.92))
                .andExpect(jsonPath("$.sampleType")
                        .value("Fluoride plasma"))
                .andExpect(jsonPath("$.referenceRange")
                        .value("70 - 110"))
                .andExpect(jsonPath("$.unit").value("mg/dl"));

        verify(bloodGlucoseExaminationService, times(1))
                .getBloodGlucoseExaminationById(examinationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when the requested blood glucose examination does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenBloodGlucoseExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        when(bloodGlucoseExaminationService
                .getBloodGlucoseExaminationById(examinationId))
                .thenThrow(
                        new BloodGlucoseExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/blood-glucose-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Blood glucose examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/blood-glucose-examinations/999"
                        ));

        verify(bloodGlucoseExaminationService, times(1))
                .getBloodGlucoseExaminationById(examinationId);
    }


    /**
     * Verifies that all blood glucose examinations associated
     * with a registration are returned successfully.
     */
    @Test
    void shouldGetBloodGlucoseExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        BloodGlucoseExaminationResponseDto response1 =
                new BloodGlucoseExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setTestType(BloodGlucoseTestType.FBS);
        response1.setGlucoseValue(108.92);

        BloodGlucoseExaminationResponseDto response2 =
                new BloodGlucoseExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setTestType(BloodGlucoseTestType.PLBS);
        response2.setGlucoseValue(126.73);

        when(bloodGlucoseExaminationService
                .getBloodGlucoseExaminationsByRegistrationId(
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
                                "/api/blood-glucose-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].registrationId").value(1))
                .andExpect(jsonPath("$[0].testType").value("FBS"))
                .andExpect(jsonPath("$[0].glucoseValue")
                        .value(108.92))

                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].registrationId").value(1))
                .andExpect(jsonPath("$[1].testType").value("PLBS"))
                .andExpect(jsonPath("$[1].glucoseValue")
                        .value(126.73));

        verify(bloodGlucoseExaminationService, times(1))
                .getBloodGlucoseExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an empty JSON array is returned successfully
     * when an existing registration has no blood glucose examinations.
     */
    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoBloodGlucoseExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        when(bloodGlucoseExaminationService
                .getBloodGlucoseExaminationsByRegistrationId(
                        registrationId
                ))
                .thenReturn(List.of());

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/blood-glucose-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(bloodGlucoseExaminationService, times(1))
                .getBloodGlucoseExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * blood glucose examinations are requested for a registration
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenRegistrationDoesNotExistWhileGettingBloodGlucoseExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(bloodGlucoseExaminationService
                .getBloodGlucoseExaminationsByRegistrationId(
                        registrationId
                ))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/blood-glucose-examinations/registration/{registrationId}",
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
                                "/api/blood-glucose-examinations/registration/999"
                        ));

        verify(bloodGlucoseExaminationService, times(1))
                .getBloodGlucoseExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that all FBS examinations associated
     * with a registration are returned successfully.
     */
    @Test
    void shouldGetFbsExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        BloodGlucoseExaminationResponseDto response1 =
                new BloodGlucoseExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setTestType(BloodGlucoseTestType.FBS);
        response1.setGlucoseValue(108.92);

        BloodGlucoseExaminationResponseDto response2 =
                new BloodGlucoseExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setTestType(BloodGlucoseTestType.FBS);
        response2.setGlucoseValue(105.50);

        when(bloodGlucoseExaminationService
                .getFbsExaminationsByRegistrationId(
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
                                "/api/blood-glucose-examinations/registration/{registrationId}/fbs",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].testType").value("FBS"))
                .andExpect(jsonPath("$[0].glucoseValue")
                        .value(108.92))

                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].testType").value("FBS"))
                .andExpect(jsonPath("$[1].glucoseValue")
                        .value(105.50));

        verify(bloodGlucoseExaminationService, times(1))
                .getFbsExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * FBS examinations are requested for a registration
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenGettingFbsForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(bloodGlucoseExaminationService
                .getFbsExaminationsByRegistrationId(
                        registrationId
                ))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/blood-glucose-examinations/registration/{registrationId}/fbs",
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
                                "/api/blood-glucose-examinations/registration/999/fbs"
                        ));

        verify(bloodGlucoseExaminationService, times(1))
                .getFbsExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that all PLBS examinations associated
     * with a registration are returned successfully.
     */
    @Test
    void shouldGetPlbsExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        BloodGlucoseExaminationResponseDto response =
                new BloodGlucoseExaminationResponseDto();

        response.setId(12L);
        response.setRegistrationId(registrationId);
        response.setTestType(BloodGlucoseTestType.PLBS);
        response.setGlucoseValue(126.73);

        when(bloodGlucoseExaminationService
                .getPlbsExaminationsByRegistrationId(
                        registrationId
                ))
                .thenReturn(
                        List.of(response)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/blood-glucose-examinations/registration/{registrationId}/plbs",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(12))
                .andExpect(jsonPath("$[0].registrationId").value(1))
                .andExpect(jsonPath("$[0].testType").value("PLBS"))
                .andExpect(jsonPath("$[0].glucoseValue")
                        .value(126.73));

        verify(bloodGlucoseExaminationService, times(1))
                .getPlbsExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * PLBS examinations are requested for a registration
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenGettingPlbsForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(bloodGlucoseExaminationService
                .getPlbsExaminationsByRegistrationId(
                        registrationId
                ))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/blood-glucose-examinations/registration/{registrationId}/plbs",
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
                                "/api/blood-glucose-examinations/registration/999/plbs"
                        ));

        verify(bloodGlucoseExaminationService, times(1))
                .getPlbsExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an existing blood glucose examination
     * is updated successfully.
     */
    @Test
    void shouldUpdateBloodGlucoseExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        BloodGlucoseExaminationRequestDto request =
                new BloodGlucoseExaminationRequestDto();

        request.setTestType(BloodGlucoseTestType.FBS);
        request.setGlucoseValue(110.50);
        request.setSampleType("Fluoride plasma");
        request.setReferenceRange("70 - 110");
        request.setUnit("mg/dl");
        request.setRemarks("Upper normal range");
        request.setPathologistName("Dr Anil Kumar");

        BloodGlucoseExaminationResponseDto response =
                new BloodGlucoseExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);
        response.setTestType(BloodGlucoseTestType.FBS);
        response.setGlucoseValue(110.50);
        response.setSampleType("Fluoride plasma");
        response.setReferenceRange("70 - 110");
        response.setUnit("mg/dl");
        response.setRemarks("Upper normal range");
        response.setPathologistName("Dr Anil Kumar");

        when(bloodGlucoseExaminationService
                .updateBloodGlucoseExamination(
                        eq(examinationId),
                        any(BloodGlucoseExaminationRequestDto.class)
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/blood-glucose-examinations/{id}",
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
                .andExpect(jsonPath("$.testType").value("FBS"))
                .andExpect(jsonPath("$.glucoseValue").value(110.50))
                .andExpect(jsonPath("$.sampleType")
                        .value("Fluoride plasma"))
                .andExpect(jsonPath("$.referenceRange")
                        .value("70 - 110"))
                .andExpect(jsonPath("$.unit").value("mg/dl"))
                .andExpect(jsonPath("$.remarks")
                        .value("Upper normal range"))
                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Anil Kumar"));

        verify(bloodGlucoseExaminationService, times(1))
                .updateBloodGlucoseExamination(
                        eq(examinationId),
                        any(BloodGlucoseExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to update a blood glucose examination
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenUpdatingBloodGlucoseExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        BloodGlucoseExaminationRequestDto request =
                new BloodGlucoseExaminationRequestDto();

        request.setTestType(BloodGlucoseTestType.FBS);
        request.setGlucoseValue(110.50);

        when(bloodGlucoseExaminationService
                .updateBloodGlucoseExamination(
                        eq(examinationId),
                        any(BloodGlucoseExaminationRequestDto.class)
                ))
                .thenThrow(
                        new BloodGlucoseExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/blood-glucose-examinations/{id}",
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
                                "Blood glucose examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/blood-glucose-examinations/999"
                        ));

        verify(bloodGlucoseExaminationService, times(1))
                .updateBloodGlucoseExamination(
                        eq(examinationId),
                        any(BloodGlucoseExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that an existing blood glucose examination
     * is deleted successfully.
     */
    @Test
    void shouldDeleteBloodGlucoseExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        doNothing()
                .when(bloodGlucoseExaminationService)
                .deleteBloodGlucoseExamination(examinationId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/blood-glucose-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNoContent());

        verify(bloodGlucoseExaminationService, times(1))
                .deleteBloodGlucoseExamination(examinationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to delete a blood glucose examination
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenDeletingBloodGlucoseExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        doThrow(
                new BloodGlucoseExaminationNotFoundException(
                        examinationId
                )
        )
                .when(bloodGlucoseExaminationService)
                .deleteBloodGlucoseExamination(examinationId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/blood-glucose-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Blood glucose examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/blood-glucose-examinations/999"
                        ));

        verify(bloodGlucoseExaminationService, times(1))
                .deleteBloodGlucoseExamination(examinationId);
    }
}