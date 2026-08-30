package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.LipidProfileExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.LipidProfileExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.LipidProfileExaminationService;
import com.apuxlabs.apuxlabs_api.exception.LipidProfileExaminationNotFoundException;
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

@WebMvcTest(LipidProfileExaminationController.class)
class LipidProfileExaminationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LipidProfileExaminationService
            lipidProfileExaminationService;


    /**
     * Verifies that a lipid profile examination is created
     * successfully for an existing registration.
     */
    @Test
    void shouldCreateLipidProfileExamination()
            throws Exception {

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
        request.setRemarks("Lipid profile reviewed");
        request.setPathologistName("Dr Radha");

        LipidProfileExaminationResponseDto response =
                new LipidProfileExaminationResponseDto();

        response.setId(10L);
        response.setRegistrationId(registrationId);

        response.setTotalCholesterol(197.14);
        response.setTriglycerides(146.19);
        response.setHdlCholesterol(47.04);
        response.setLdlCholesterol(120.86);
        response.setVldlCholesterol(29.24);
        response.setLdlHdlRatio(2.57);
        response.setTotalCholesterolHdlRatio(4.19);

        response.setRemarks("Lipid profile reviewed");
        response.setPathologistName("Dr Radha");

        when(lipidProfileExaminationService
                .createLipidProfileExamination(
                        eq(registrationId),
                        any(LipidProfileExaminationRequestDto.class)
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/lipid-profile-examinations/registration/{registrationId}",
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
                .andExpect(jsonPath("$.totalCholesterol").value(197.14))
                .andExpect(jsonPath("$.triglycerides").value(146.19))
                .andExpect(jsonPath("$.hdlCholesterol").value(47.04))
                .andExpect(jsonPath("$.ldlCholesterol").value(120.86))
                .andExpect(jsonPath("$.vldlCholesterol").value(29.24))
                .andExpect(jsonPath("$.ldlHdlRatio").value(2.57))
                .andExpect(jsonPath("$.totalCholesterolHdlRatio")
                        .value(4.19))
                .andExpect(jsonPath("$.remarks")
                        .value("Lipid profile reviewed"))
                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Radha"));

        verify(lipidProfileExaminationService, times(1))
                .createLipidProfileExamination(
                        eq(registrationId),
                        any(LipidProfileExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * a lipid profile examination is created for a registration
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenCreatingLipidProfileForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        LipidProfileExaminationRequestDto request =
                new LipidProfileExaminationRequestDto();

        request.setTotalCholesterol(197.14);

        when(lipidProfileExaminationService
                .createLipidProfileExamination(
                        eq(registrationId),
                        any(LipidProfileExaminationRequestDto.class)
                ))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/lipid-profile-examinations/registration/{registrationId}",
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
                                "/api/lipid-profile-examinations/registration/999"
                        ));

        verify(lipidProfileExaminationService, times(1))
                .createLipidProfileExamination(
                        eq(registrationId),
                        any(LipidProfileExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that a lipid profile examination is returned
     * successfully when its ID exists.
     */
    @Test
    void shouldGetLipidProfileExaminationById()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        LipidProfileExaminationResponseDto response =
                new LipidProfileExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);

        response.setTotalCholesterol(197.14);
        response.setTriglycerides(146.19);
        response.setHdlCholesterol(47.04);
        response.setLdlCholesterol(120.86);
        response.setVldlCholesterol(29.24);

        when(lipidProfileExaminationService
                .getLipidProfileExaminationById(examinationId))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/lipid-profile-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))
                .andExpect(jsonPath("$.totalCholesterol").value(197.14))
                .andExpect(jsonPath("$.triglycerides").value(146.19))
                .andExpect(jsonPath("$.hdlCholesterol").value(47.04))
                .andExpect(jsonPath("$.ldlCholesterol").value(120.86))
                .andExpect(jsonPath("$.vldlCholesterol").value(29.24));

        verify(lipidProfileExaminationService, times(1))
                .getLipidProfileExaminationById(examinationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * the requested lipid profile examination does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenLipidProfileExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        when(lipidProfileExaminationService
                .getLipidProfileExaminationById(examinationId))
                .thenThrow(
                        new LipidProfileExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/lipid-profile-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Lipid profile examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/lipid-profile-examinations/999"
                        ));

        verify(lipidProfileExaminationService, times(1))
                .getLipidProfileExaminationById(examinationId);
    }


    /**
     * Verifies that all lipid profile examinations associated
     * with a registration are returned successfully.
     */
    @Test
    void shouldGetLipidProfileExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

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

        when(lipidProfileExaminationService
                .getLipidProfileExaminationsByRegistrationId(
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
                                "/api/lipid-profile-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].registrationId").value(1))
                .andExpect(jsonPath("$[0].totalCholesterol")
                        .value(197.14))
                .andExpect(jsonPath("$[0].ldlCholesterol")
                        .value(120.86))

                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].registrationId").value(1))
                .andExpect(jsonPath("$[1].totalCholesterol")
                        .value(185.50))
                .andExpect(jsonPath("$[1].ldlCholesterol")
                        .value(95.20));

        verify(lipidProfileExaminationService, times(1))
                .getLipidProfileExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an empty JSON array is returned successfully
     * when an existing registration has no lipid profile examinations.
     */
    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoLipidProfileExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        when(lipidProfileExaminationService
                .getLipidProfileExaminationsByRegistrationId(
                        registrationId
                ))
                .thenReturn(List.of());

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/lipid-profile-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(lipidProfileExaminationService, times(1))
                .getLipidProfileExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * lipid profile examinations are requested for a registration
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenRegistrationDoesNotExistWhileGettingLipidProfileExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(lipidProfileExaminationService
                .getLipidProfileExaminationsByRegistrationId(
                        registrationId
                ))
                .thenThrow(
                        new RegistrationNotFoundException(registrationId)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/lipid-profile-examinations/registration/{registrationId}",
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
                                "/api/lipid-profile-examinations/registration/999"
                        ));

        verify(lipidProfileExaminationService, times(1))
                .getLipidProfileExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an existing lipid profile examination
     * is updated successfully.
     */
    @Test
    void shouldUpdateLipidProfileExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

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

        LipidProfileExaminationResponseDto response =
                new LipidProfileExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);

        response.setTotalCholesterol(190.50);
        response.setTriglycerides(140.20);
        response.setHdlCholesterol(50.00);
        response.setLdlCholesterol(110.00);
        response.setVldlCholesterol(28.04);
        response.setLdlHdlRatio(2.20);
        response.setTotalCholesterolHdlRatio(3.81);

        response.setRemarks(
                "Updated lipid profile"
        );

        response.setPathologistName(
                "Dr Anil Kumar"
        );

        when(lipidProfileExaminationService
                .updateLipidProfileExamination(
                        eq(examinationId),
                        any(LipidProfileExaminationRequestDto.class)
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/lipid-profile-examinations/{id}",
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
                .andExpect(jsonPath("$.totalCholesterol")
                        .value(190.50))
                .andExpect(jsonPath("$.triglycerides")
                        .value(140.20))
                .andExpect(jsonPath("$.hdlCholesterol")
                        .value(50.00))
                .andExpect(jsonPath("$.ldlCholesterol")
                        .value(110.00))
                .andExpect(jsonPath("$.vldlCholesterol")
                        .value(28.04))
                .andExpect(jsonPath("$.ldlHdlRatio")
                        .value(2.20))
                .andExpect(jsonPath("$.totalCholesterolHdlRatio")
                        .value(3.81))
                .andExpect(jsonPath("$.remarks")
                        .value("Updated lipid profile"))
                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Anil Kumar"));

        verify(lipidProfileExaminationService, times(1))
                .updateLipidProfileExamination(
                        eq(examinationId),
                        any(LipidProfileExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to update a lipid profile examination
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenUpdatingLipidProfileExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        LipidProfileExaminationRequestDto request =
                new LipidProfileExaminationRequestDto();

        request.setTotalCholesterol(
                190.50
        );

        when(lipidProfileExaminationService
                .updateLipidProfileExamination(
                        eq(examinationId),
                        any(LipidProfileExaminationRequestDto.class)
                ))
                .thenThrow(
                        new LipidProfileExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/lipid-profile-examinations/{id}",
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
                                "Lipid profile examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/lipid-profile-examinations/999"
                        ));

        verify(lipidProfileExaminationService, times(1))
                .updateLipidProfileExamination(
                        eq(examinationId),
                        any(LipidProfileExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that an existing lipid profile examination
     * is deleted successfully.
     */
    @Test
    void shouldDeleteLipidProfileExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        doNothing()
                .when(lipidProfileExaminationService)
                .deleteLipidProfileExamination(examinationId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/lipid-profile-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNoContent());

        verify(lipidProfileExaminationService, times(1))
                .deleteLipidProfileExamination(examinationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to delete a lipid profile examination
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenDeletingLipidProfileExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        doThrow(
                new LipidProfileExaminationNotFoundException(
                        examinationId
                )
        )
                .when(lipidProfileExaminationService)
                .deleteLipidProfileExamination(examinationId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/lipid-profile-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Lipid profile examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/lipid-profile-examinations/999"
                        ));

        verify(lipidProfileExaminationService, times(1))
                .deleteLipidProfileExamination(examinationId);
    }
}