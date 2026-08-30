package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.LiverFunctionTestExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.LiverFunctionTestExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.LiverFunctionTestExaminationService;
import com.apuxlabs.apuxlabs_api.exception.LiverFunctionTestExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LiverFunctionTestExaminationController.class)
class LiverFunctionTestExaminationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LiverFunctionTestExaminationService
            liverFunctionTestExaminationService;


    /**
     * Verifies that a liver function test examination
     * is created successfully for an existing registration.
     *
     * The service is mocked to return the created examination.
     * The controller should accept the JSON request and return
     * HTTP 201 Created with the examination details.
     */
    @Test
    void shouldCreateLiverFunctionTestExamination()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        LiverFunctionTestExaminationRequestDto request =
                new LiverFunctionTestExaminationRequestDto();

        request.setExaminationDateTime(
                LocalDateTime.of(
                        2026,
                        7,
                        22,
                        10,
                        30
                )
        );

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

        LiverFunctionTestExaminationResponseDto response =
                new LiverFunctionTestExaminationResponseDto();

        response.setId(10L);
        response.setRegistrationId(registrationId);

        response.setSampleType("Serum");

        response.setBilirubinTotal(0.44);
        response.setBilirubinDirect(0.15);
        response.setBilirubinIndirect(0.29);

        response.setSgpt(38.89);
        response.setSgot(31.73);
        response.setSgotSgptRatio(0.82);

        response.setAlkalinePhosphatase(75.76);
        response.setGammaGlutamylTransferase(36.0);

        response.setTotalProteins(7.12);
        response.setAlbumin(4.95);
        response.setGlobulin(2.17);
        response.setAlbuminGlobulinRatio(2.28);

        response.setRemarks(
                "Liver function test reviewed"
        );

        response.setPathologistName(
                "Dr Radha"
        );

        when(liverFunctionTestExaminationService
                .createLiverFunctionTestExamination(
                        eq(registrationId),
                        any(
                                LiverFunctionTestExaminationRequestDto.class
                        )
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/liver-function-test-examinations/registration/{registrationId}",
                                registrationId
                        )
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))

                .andExpect(jsonPath("$.sampleType")
                        .value("Serum"))

                .andExpect(jsonPath("$.bilirubinTotal")
                        .value(0.44))

                .andExpect(jsonPath("$.bilirubinDirect")
                        .value(0.15))

                .andExpect(jsonPath("$.bilirubinIndirect")
                        .value(0.29))

                .andExpect(jsonPath("$.sgpt")
                        .value(38.89))

                .andExpect(jsonPath("$.sgot")
                        .value(31.73))

                .andExpect(jsonPath("$.sgotSgptRatio")
                        .value(0.82))

                .andExpect(jsonPath("$.alkalinePhosphatase")
                        .value(75.76))

                .andExpect(jsonPath("$.gammaGlutamylTransferase")
                        .value(36.0))

                .andExpect(jsonPath("$.totalProteins")
                        .value(7.12))

                .andExpect(jsonPath("$.albumin")
                        .value(4.95))

                .andExpect(jsonPath("$.globulin")
                        .value(2.17))

                .andExpect(jsonPath("$.albuminGlobulinRatio")
                        .value(2.28))

                .andExpect(jsonPath("$.remarks")
                        .value(
                                "Liver function test reviewed"
                        ))

                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Radha"));

        verify(liverFunctionTestExaminationService, times(1))
                .createLiverFunctionTestExamination(
                        eq(registrationId),
                        any(
                                LiverFunctionTestExaminationRequestDto.class
                        )
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * a liver function test examination is created
     * for a registration that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenCreatingLiverFunctionTestForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        LiverFunctionTestExaminationRequestDto request =
                new LiverFunctionTestExaminationRequestDto();

        request.setSampleType("Serum");
        request.setBilirubinTotal(0.44);

        when(liverFunctionTestExaminationService
                .createLiverFunctionTestExamination(
                        eq(registrationId),
                        any(
                                LiverFunctionTestExaminationRequestDto.class
                        )
                ))
                .thenThrow(
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/liver-function-test-examinations/registration/{registrationId}",
                                registrationId
                        )
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$.status")
                        .value(404))

                .andExpect(jsonPath("$.error")
                        .value("Not Found"))

                .andExpect(jsonPath("$.message")
                        .value(
                                "Registration not found with id: 999"
                        ))

                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/liver-function-test-examinations/registration/999"
                        ));

        verify(liverFunctionTestExaminationService, times(1))
                .createLiverFunctionTestExamination(
                        eq(registrationId),
                        any(
                                LiverFunctionTestExaminationRequestDto.class
                        )
                );
    }


    /**
     * Verifies that a liver function test examination
     * is returned successfully when its ID exists.
     */
    @Test
    void shouldGetLiverFunctionTestExaminationById()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        LiverFunctionTestExaminationResponseDto response =
                new LiverFunctionTestExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);

        response.setSampleType("Serum");

        response.setBilirubinTotal(0.44);
        response.setBilirubinDirect(0.15);
        response.setBilirubinIndirect(0.29);

        response.setSgpt(38.89);
        response.setSgot(31.73);

        response.setAlkalinePhosphatase(75.76);
        response.setGammaGlutamylTransferase(36.0);

        response.setTotalProteins(7.12);
        response.setAlbumin(4.95);
        response.setGlobulin(2.17);

        when(liverFunctionTestExaminationService
                .getLiverFunctionTestExaminationById(
                        examinationId
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/liver-function-test-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id")
                        .value(10))

                .andExpect(jsonPath("$.registrationId")
                        .value(1))

                .andExpect(jsonPath("$.sampleType")
                        .value("Serum"))

                .andExpect(jsonPath("$.bilirubinTotal")
                        .value(0.44))

                .andExpect(jsonPath("$.bilirubinDirect")
                        .value(0.15))

                .andExpect(jsonPath("$.bilirubinIndirect")
                        .value(0.29))

                .andExpect(jsonPath("$.sgpt")
                        .value(38.89))

                .andExpect(jsonPath("$.sgot")
                        .value(31.73))

                .andExpect(jsonPath("$.alkalinePhosphatase")
                        .value(75.76))

                .andExpect(jsonPath("$.gammaGlutamylTransferase")
                        .value(36.0))

                .andExpect(jsonPath("$.totalProteins")
                        .value(7.12))

                .andExpect(jsonPath("$.albumin")
                        .value(4.95))

                .andExpect(jsonPath("$.globulin")
                        .value(2.17));

        verify(liverFunctionTestExaminationService, times(1))
                .getLiverFunctionTestExaminationById(
                        examinationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when the requested liver function test examination
     * does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenLiverFunctionTestExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        when(liverFunctionTestExaminationService
                .getLiverFunctionTestExaminationById(
                        examinationId
                ))
                .thenThrow(
                        new LiverFunctionTestExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/liver-function-test-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$.status")
                        .value(404))

                .andExpect(jsonPath("$.error")
                        .value("Not Found"))

                .andExpect(jsonPath("$.message")
                        .value(
                                "Liver function test examination not found with id: 999"
                        ))

                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/liver-function-test-examinations/999"
                        ));

        verify(liverFunctionTestExaminationService, times(1))
                .getLiverFunctionTestExaminationById(
                        examinationId
                );
    }


    /**
     * Verifies that all liver function test examinations
     * associated with a registration are returned successfully.
     */
    @Test
    void shouldGetLiverFunctionTestExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

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

        when(liverFunctionTestExaminationService
                .getLiverFunctionTestExaminationsByRegistrationId(
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
                                "/api/liver-function-test-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.length()")
                        .value(2))

                .andExpect(jsonPath("$[0].id")
                        .value(10))

                .andExpect(jsonPath("$[0].registrationId")
                        .value(1))

                .andExpect(jsonPath("$[0].bilirubinTotal")
                        .value(0.44))

                .andExpect(jsonPath("$[0].sgpt")
                        .value(38.89))

                .andExpect(jsonPath("$[0].sgot")
                        .value(31.73))

                .andExpect(jsonPath("$[1].id")
                        .value(11))

                .andExpect(jsonPath("$[1].registrationId")
                        .value(1))

                .andExpect(jsonPath("$[1].bilirubinTotal")
                        .value(0.50))

                .andExpect(jsonPath("$[1].sgpt")
                        .value(40.20))

                .andExpect(jsonPath("$[1].sgot")
                        .value(32.10));

        verify(liverFunctionTestExaminationService, times(1))
                .getLiverFunctionTestExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an empty JSON array is returned
     * when an existing registration has no
     * liver function test examinations.
     */
    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoLiverFunctionTestExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        when(liverFunctionTestExaminationService
                .getLiverFunctionTestExaminationsByRegistrationId(
                        registrationId
                ))
                .thenReturn(List.of());

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/liver-function-test-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(0));

        verify(liverFunctionTestExaminationService, times(1))
                .getLiverFunctionTestExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when liver function test examinations are requested
     * for a registration that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenRegistrationDoesNotExistWhileGettingLiverFunctionTestExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(liverFunctionTestExaminationService
                .getLiverFunctionTestExaminationsByRegistrationId(
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
                                "/api/liver-function-test-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$.status")
                        .value(404))

                .andExpect(jsonPath("$.error")
                        .value("Not Found"))

                .andExpect(jsonPath("$.message")
                        .value(
                                "Registration not found with id: 999"
                        ))

                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/liver-function-test-examinations/registration/999"
                        ));

        verify(liverFunctionTestExaminationService, times(1))
                .getLiverFunctionTestExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an existing liver function test
     * examination is updated successfully.
     */
    @Test
    void shouldUpdateLiverFunctionTestExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        LiverFunctionTestExaminationRequestDto request =
                new LiverFunctionTestExaminationRequestDto();

        request.setExaminationDateTime(
                LocalDateTime.of(
                        2026,
                        8,
                        25,
                        14,
                        30
                )
        );

        request.setSampleType("Serum");

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

        LiverFunctionTestExaminationResponseDto response =
                new LiverFunctionTestExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);

        response.setSampleType("Serum");

        response.setBilirubinTotal(0.50);
        response.setBilirubinDirect(0.18);
        response.setBilirubinIndirect(0.32);

        response.setSgpt(40.20);
        response.setSgot(32.10);
        response.setSgotSgptRatio(0.80);

        response.setAlkalinePhosphatase(78.50);
        response.setGammaGlutamylTransferase(38.0);

        response.setTotalProteins(7.30);
        response.setAlbumin(4.90);
        response.setGlobulin(2.40);
        response.setAlbuminGlobulinRatio(2.04);

        response.setRemarks(
                "Updated liver function test"
        );

        response.setPathologistName(
                "Dr Anil Kumar"
        );

        when(liverFunctionTestExaminationService
                .updateLiverFunctionTestExamination(
                        eq(examinationId),
                        any(
                                LiverFunctionTestExaminationRequestDto.class
                        )
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/liver-function-test-examinations/{id}",
                                examinationId
                        )
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id")
                        .value(10))

                .andExpect(jsonPath("$.registrationId")
                        .value(1))

                .andExpect(jsonPath("$.sampleType")
                        .value("Serum"))

                .andExpect(jsonPath("$.bilirubinTotal")
                        .value(0.50))

                .andExpect(jsonPath("$.bilirubinDirect")
                        .value(0.18))

                .andExpect(jsonPath("$.bilirubinIndirect")
                        .value(0.32))

                .andExpect(jsonPath("$.sgpt")
                        .value(40.20))

                .andExpect(jsonPath("$.sgot")
                        .value(32.10))

                .andExpect(jsonPath("$.sgotSgptRatio")
                        .value(0.80))

                .andExpect(jsonPath("$.alkalinePhosphatase")
                        .value(78.50))

                .andExpect(jsonPath("$.gammaGlutamylTransferase")
                        .value(38.0))

                .andExpect(jsonPath("$.totalProteins")
                        .value(7.30))

                .andExpect(jsonPath("$.albumin")
                        .value(4.90))

                .andExpect(jsonPath("$.globulin")
                        .value(2.40))

                .andExpect(jsonPath("$.albuminGlobulinRatio")
                        .value(2.04))

                .andExpect(jsonPath("$.remarks")
                        .value(
                                "Updated liver function test"
                        ))

                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Anil Kumar"));

        verify(liverFunctionTestExaminationService, times(1))
                .updateLiverFunctionTestExamination(
                        eq(examinationId),
                        any(
                                LiverFunctionTestExaminationRequestDto.class
                        )
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when attempting to update a liver function test
     * examination that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenUpdatingLiverFunctionTestExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        LiverFunctionTestExaminationRequestDto request =
                new LiverFunctionTestExaminationRequestDto();

        request.setBilirubinTotal(0.50);

        when(liverFunctionTestExaminationService
                .updateLiverFunctionTestExamination(
                        eq(examinationId),
                        any(
                                LiverFunctionTestExaminationRequestDto.class
                        )
                ))
                .thenThrow(
                        new LiverFunctionTestExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/liver-function-test-examinations/{id}",
                                examinationId
                        )
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$.status")
                        .value(404))

                .andExpect(jsonPath("$.error")
                        .value("Not Found"))

                .andExpect(jsonPath("$.message")
                        .value(
                                "Liver function test examination not found with id: 999"
                        ))

                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/liver-function-test-examinations/999"
                        ));

        verify(liverFunctionTestExaminationService, times(1))
                .updateLiverFunctionTestExamination(
                        eq(examinationId),
                        any(
                                LiverFunctionTestExaminationRequestDto.class
                        )
                );
    }


    /**
     * Verifies that an existing liver function test
     * examination is deleted successfully.
     */
    @Test
    void shouldDeleteLiverFunctionTestExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        doNothing()
                .when(liverFunctionTestExaminationService)
                .deleteLiverFunctionTestExamination(
                        examinationId
                );

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/liver-function-test-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNoContent());

        verify(liverFunctionTestExaminationService, times(1))
                .deleteLiverFunctionTestExamination(
                        examinationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when attempting to delete a liver function test
     * examination that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenDeletingLiverFunctionTestExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        doThrow(
                new LiverFunctionTestExaminationNotFoundException(
                        examinationId
                )
        )
                .when(liverFunctionTestExaminationService)
                .deleteLiverFunctionTestExamination(
                        examinationId
                );

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/liver-function-test-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$.status")
                        .value(404))

                .andExpect(jsonPath("$.error")
                        .value("Not Found"))

                .andExpect(jsonPath("$.message")
                        .value(
                                "Liver function test examination not found with id: 999"
                        ))

                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/liver-function-test-examinations/999"
                        ));

        verify(liverFunctionTestExaminationService, times(1))
                .deleteLiverFunctionTestExamination(
                        examinationId
                );
    }
}