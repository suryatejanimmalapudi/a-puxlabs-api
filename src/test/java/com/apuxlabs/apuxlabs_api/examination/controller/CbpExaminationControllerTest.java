package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.CbpExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.CbpExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.CbpExaminationService;
import com.apuxlabs.apuxlabs_api.exception.CbpExaminationNotFoundException;
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

@WebMvcTest(CbpExaminationController.class)
class CbpExaminationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CbpExaminationService cbpExaminationService;


    /**
     * Verifies that a CBP examination is created successfully
     * for an existing registration.
     *
     * The service is mocked to return the created examination.
     * The controller should accept the JSON request and return
     * HTTP 201 Created with the CBP examination details.
     */
    @Test
    void shouldCreateCbpExamination() throws Exception {

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

        CbpExaminationResponseDto response =
                new CbpExaminationResponseDto();

        response.setId(10L);
        response.setRegistrationId(registrationId);

        response.setHaemoglobin(14.2);
        response.setTotalLeukocyteCount(7200);

        response.setNeutrophils(60.0);
        response.setLymphocytes(30.0);

        response.setRbcCount(4.8);
        response.setHct(44.0);

        response.setPlateletCount(250000);

        response.setRemarks(
                "Complete blood picture within normal limits"
        );

        response.setPathologistName(
                "Dr Radha"
        );

        when(cbpExaminationService.createCbpExamination(
                eq(registrationId),
                any(CbpExaminationRequestDto.class)
        )).thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/cbp-examinations/registration/{registrationId}",
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
                .andExpect(jsonPath("$.haemoglobin").value(14.2))
                .andExpect(jsonPath("$.totalLeukocyteCount").value(7200))
                .andExpect(jsonPath("$.neutrophils").value(60.0))
                .andExpect(jsonPath("$.lymphocytes").value(30.0))
                .andExpect(jsonPath("$.rbcCount").value(4.8))
                .andExpect(jsonPath("$.hct").value(44.0))
                .andExpect(jsonPath("$.plateletCount").value(250000))
                .andExpect(jsonPath("$.remarks")
                        .value(
                                "Complete blood picture within normal limits"
                        ))
                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Radha"));

        verify(cbpExaminationService, times(1))
                .createCbpExamination(
                        eq(registrationId),
                        any(CbpExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * a CBP examination is created for a registration
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenCreatingCbpForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        CbpExaminationRequestDto request =
                new CbpExaminationRequestDto();

        request.setHaemoglobin(14.2);

        when(cbpExaminationService.createCbpExamination(
                eq(registrationId),
                any(CbpExaminationRequestDto.class)
        )).thenThrow(
                new RegistrationNotFoundException(registrationId)
        );

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/cbp-examinations/registration/{registrationId}",
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
                                "/api/cbp-examinations/registration/999"
                        ));

        verify(cbpExaminationService, times(1))
                .createCbpExamination(
                        eq(registrationId),
                        any(CbpExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that a CBP examination is returned successfully
     * when the requested examination ID exists.
     */
    @Test
    void shouldGetCbpExaminationById()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        CbpExaminationResponseDto response =
                new CbpExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);

        response.setHaemoglobin(14.2);
        response.setTotalLeukocyteCount(7200);
        response.setNeutrophils(60.0);
        response.setLymphocytes(30.0);
        response.setPlateletCount(250000);

        when(cbpExaminationService
                .getCbpExaminationById(examinationId))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/cbp-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))
                .andExpect(jsonPath("$.haemoglobin").value(14.2))
                .andExpect(jsonPath("$.totalLeukocyteCount").value(7200))
                .andExpect(jsonPath("$.neutrophils").value(60.0))
                .andExpect(jsonPath("$.lymphocytes").value(30.0))
                .andExpect(jsonPath("$.plateletCount").value(250000));

        verify(cbpExaminationService, times(1))
                .getCbpExaminationById(examinationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when the requested CBP examination does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenCbpExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        when(cbpExaminationService
                .getCbpExaminationById(examinationId))
                .thenThrow(
                        new CbpExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/cbp-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "CBP examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/cbp-examinations/999"
                        ));

        verify(cbpExaminationService, times(1))
                .getCbpExaminationById(examinationId);
    }


    /**
     * Verifies that all CBP examinations associated
     * with a registration are returned successfully.
     */
    @Test
    void shouldGetCbpExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        CbpExaminationResponseDto response1 =
                new CbpExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setHaemoglobin(14.2);
        response1.setPlateletCount(250000);

        CbpExaminationResponseDto response2 =
                new CbpExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setHaemoglobin(13.8);
        response2.setPlateletCount(270000);

        when(cbpExaminationService
                .getCbpExaminationsByRegistrationId(
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
                                "/api/cbp-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].registrationId").value(1))
                .andExpect(jsonPath("$[0].haemoglobin").value(14.2))
                .andExpect(jsonPath("$[0].plateletCount").value(250000))

                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].registrationId").value(1))
                .andExpect(jsonPath("$[1].haemoglobin").value(13.8))
                .andExpect(jsonPath("$[1].plateletCount").value(270000));

        verify(cbpExaminationService, times(1))
                .getCbpExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an empty JSON array is returned successfully
     * when an existing registration has no CBP examinations.
     */
    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoCbpExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        when(cbpExaminationService
                .getCbpExaminationsByRegistrationId(
                        registrationId
                ))
                .thenReturn(List.of());

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/cbp-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(cbpExaminationService, times(1))
                .getCbpExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * CBP examinations are requested for a registration
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenRegistrationDoesNotExistWhileGettingCbpExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(cbpExaminationService
                .getCbpExaminationsByRegistrationId(
                        registrationId
                ))
                .thenThrow(
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/cbp-examinations/registration/{registrationId}",
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
                                "/api/cbp-examinations/registration/999"
                        ));

        verify(cbpExaminationService, times(1))
                .getCbpExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an existing CBP examination
     * is updated successfully.
     */
    @Test
    void shouldUpdateCbpExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        CbpExaminationRequestDto request =
                new CbpExaminationRequestDto();

        request.setHaemoglobin(13.5);
        request.setTotalLeukocyteCount(7800);

        request.setNeutrophils(62.0);
        request.setLymphocytes(28.0);
        request.setEosinophils(4.0);
        request.setMonocytes(5.0);
        request.setBasophils(1.0);

        request.setAbsoluteNeutrophils(4800);
        request.setAbsoluteLymphocytes(2200);
        request.setAbsoluteEosinophils(300);
        request.setAbsoluteMonocytes(400);

        request.setRbcCount(4.6);
        request.setHct(42.0);
        request.setMcv(90.0);
        request.setMch(29.0);
        request.setMchc(32.0);
        request.setRdwCv(13.0);
        request.setRdwSd(40.0);

        request.setPlateletCount(270000);
        request.setPct(0.24);
        request.setMpv(9.0);
        request.setPdw(12.0);
        request.setPLcr(25.0);
        request.setPLcc(65.0);

        request.setRemarks(
                "Updated CBP result"
        );

        request.setPathologistName(
                "Dr Anil Kumar"
        );

        CbpExaminationResponseDto response =
                new CbpExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);

        response.setHaemoglobin(13.5);
        response.setTotalLeukocyteCount(7800);

        response.setNeutrophils(62.0);
        response.setLymphocytes(28.0);

        response.setRbcCount(4.6);
        response.setHct(42.0);

        response.setPlateletCount(270000);

        response.setRemarks(
                "Updated CBP result"
        );

        response.setPathologistName(
                "Dr Anil Kumar"
        );

        when(cbpExaminationService.updateCbpExamination(
                eq(examinationId),
                any(CbpExaminationRequestDto.class)
        )).thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/cbp-examinations/{id}",
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
                .andExpect(jsonPath("$.haemoglobin").value(13.5))
                .andExpect(jsonPath("$.totalLeukocyteCount").value(7800))
                .andExpect(jsonPath("$.neutrophils").value(62.0))
                .andExpect(jsonPath("$.lymphocytes").value(28.0))
                .andExpect(jsonPath("$.rbcCount").value(4.6))
                .andExpect(jsonPath("$.hct").value(42.0))
                .andExpect(jsonPath("$.plateletCount").value(270000))
                .andExpect(jsonPath("$.remarks")
                        .value("Updated CBP result"))
                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Anil Kumar"));

        verify(cbpExaminationService, times(1))
                .updateCbpExamination(
                        eq(examinationId),
                        any(CbpExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to update a CBP examination
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenUpdatingCbpExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        CbpExaminationRequestDto request =
                new CbpExaminationRequestDto();

        request.setHaemoglobin(13.5);

        when(cbpExaminationService.updateCbpExamination(
                eq(examinationId),
                any(CbpExaminationRequestDto.class)
        )).thenThrow(
                new CbpExaminationNotFoundException(
                        examinationId
                )
        );

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/cbp-examinations/{id}",
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
                                "CBP examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/cbp-examinations/999"
                        ));

        verify(cbpExaminationService, times(1))
                .updateCbpExamination(
                        eq(examinationId),
                        any(CbpExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that an existing CBP examination
     * is deleted successfully.
     */
    @Test
    void shouldDeleteCbpExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        doNothing()
                .when(cbpExaminationService)
                .deleteCbpExamination(examinationId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/cbp-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNoContent());

        verify(cbpExaminationService, times(1))
                .deleteCbpExamination(examinationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to delete a CBP examination
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenDeletingCbpExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        doThrow(
                new CbpExaminationNotFoundException(
                        examinationId
                )
        )
                .when(cbpExaminationService)
                .deleteCbpExamination(examinationId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/cbp-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "CBP examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/cbp-examinations/999"
                        ));

        verify(cbpExaminationService, times(1))
                .deleteCbpExamination(examinationId);
    }
}