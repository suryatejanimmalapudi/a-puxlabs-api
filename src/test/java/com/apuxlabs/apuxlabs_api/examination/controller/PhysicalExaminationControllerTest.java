package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.exception.PhysicalExaminationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.examination.dto.PhysicalExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.PhysicalExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.PhysicalExaminationService;

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

@WebMvcTest(PhysicalExaminationController.class)
class PhysicalExaminationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PhysicalExaminationService physicalExaminationService;


    /**
     * Verifies that a physical examination is created successfully
     * for an existing registration.
     *
     * The service is mocked to return the newly created examination.
     * The controller should accept the JSON request and return
     * HTTP 201 Created with the created examination details.
     */
    @Test
    void shouldCreatePhysicalExamination() throws Exception {

        // Arrange
        Long registrationId = 1L;

        PhysicalExaminationRequestDto request =
                new PhysicalExaminationRequestDto();

        request.setExaminationDate(LocalDate.of(2026, 8, 21));
        request.setHeight(172.5);
        request.setWeight(68.0);
        request.setPulseRate(72);
        request.setBloodPressure("120/80");
        request.setTemperature(36.7);
        request.setPresentComplaints("No complaints");
        request.setCardiovascularSystem("Normal");

        PhysicalExaminationResponseDto response =
                new PhysicalExaminationResponseDto();

        response.setId(10L);
        response.setRegistrationId(registrationId);
        response.setExaminationDate(LocalDate.of(2026, 8, 21));
        response.setHeight(172.5);
        response.setWeight(68.0);
        response.setPulseRate(72);
        response.setBloodPressure("120/80");
        response.setTemperature(36.7);
        response.setPresentComplaints("No complaints");
        response.setCardiovascularSystem("Normal");

        when(physicalExaminationService.createPhysicalExamination(
                eq(registrationId),
                any(PhysicalExaminationRequestDto.class)
        )).thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/physical-examinations/registration/{registrationId}",
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
                .andExpect(jsonPath("$.height").value(172.5))
                .andExpect(jsonPath("$.weight").value(68.0))
                .andExpect(jsonPath("$.pulseRate").value(72))
                .andExpect(jsonPath("$.bloodPressure").value("120/80"))
                .andExpect(jsonPath("$.presentComplaints")
                        .value("No complaints"));

        verify(physicalExaminationService, times(1))
                .createPhysicalExamination(
                        eq(registrationId),
                        any(PhysicalExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that a physical examination is returned successfully
     * when the requested examination ID exists.
     *
     * The service is mocked to return an examination.
     * The controller should return HTTP 200 OK with its details.
     */
    @Test
    void shouldGetPhysicalExaminationById() throws Exception {

        // Arrange
        Long examinationId = 10L;

        PhysicalExaminationResponseDto response =
                new PhysicalExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);
        response.setHeight(172.5);
        response.setWeight(68.0);
        response.setPulseRate(72);
        response.setBloodPressure("120/80");

        when(physicalExaminationService
                .getPhysicalExaminationById(examinationId))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/physical-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))
                .andExpect(jsonPath("$.height").value(172.5))
                .andExpect(jsonPath("$.weight").value(68.0))
                .andExpect(jsonPath("$.pulseRate").value(72))
                .andExpect(jsonPath("$.bloodPressure").value("120/80"));

        verify(physicalExaminationService, times(1))
                .getPhysicalExaminationById(examinationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when the requested physical examination does not exist.
     *
     * The service is mocked to throw PhysicalExaminationNotFoundException.
     * The GlobalExceptionHandler should convert the exception into
     * a structured HTTP 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenPhysicalExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        when(physicalExaminationService
                .getPhysicalExaminationById(examinationId))
                .thenThrow(
                        new PhysicalExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/physical-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Physical examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value("/api/physical-examinations/999"));

        verify(physicalExaminationService, times(1))
                .getPhysicalExaminationById(examinationId);
    }


    /**
     * Verifies that all physical examinations associated with
     * a registration are returned successfully.
     *
     * The service is mocked to return multiple examination records.
     * The controller should return HTTP 200 OK with a JSON array
     * containing all examinations.
     */
    @Test
    void shouldGetPhysicalExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        PhysicalExaminationResponseDto response1 =
                new PhysicalExaminationResponseDto();

        response1.setId(10L);
        response1.setRegistrationId(registrationId);
        response1.setHeight(172.5);

        PhysicalExaminationResponseDto response2 =
                new PhysicalExaminationResponseDto();

        response2.setId(11L);
        response2.setRegistrationId(registrationId);
        response2.setHeight(175.0);

        when(physicalExaminationService
                .getPhysicalExaminationsByRegistrationId(
                        registrationId
                ))
                .thenReturn(
                        List.of(response1, response2)
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/physical-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].registrationId").value(1))
                .andExpect(jsonPath("$[0].height").value(172.5))

                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].registrationId").value(1))
                .andExpect(jsonPath("$[1].height").value(175.0));

        verify(physicalExaminationService, times(1))
                .getPhysicalExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an empty JSON array is returned successfully
     * when a valid registration has no physical examinations.
     *
     * The service is mocked to return an empty list.
     * The controller should return HTTP 200 OK rather than an error.
     */
    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoPhysicalExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        when(physicalExaminationService
                .getPhysicalExaminationsByRegistrationId(
                        registrationId
                ))
                .thenReturn(List.of());

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/physical-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(physicalExaminationService, times(1))
                .getPhysicalExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when physical examinations are requested for a registration
     * that does not exist.
     *
     * The service is mocked to throw RegistrationNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenRegistrationDoesNotExistWhileGettingPhysicalExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(physicalExaminationService
                .getPhysicalExaminationsByRegistrationId(
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
                                "/api/physical-examinations/registration/{registrationId}",
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
                                "/api/physical-examinations/registration/999"
                        ));

        verify(physicalExaminationService, times(1))
                .getPhysicalExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an existing physical examination
     * is updated successfully.
     *
     * The service is mocked to return the updated examination.
     * The controller should accept the JSON request and return
     * HTTP 200 OK with the updated values.
     */
    @Test
    void shouldUpdatePhysicalExamination() throws Exception {

        // Arrange
        Long examinationId = 10L;

        PhysicalExaminationRequestDto request =
                new PhysicalExaminationRequestDto();

        request.setExaminationDate(
                LocalDate.of(2026, 8, 21)
        );
        request.setHeight(175.0);
        request.setWeight(70.0);
        request.setPulseRate(76);
        request.setBloodPressure("125/82");
        request.setPresentComplaints("Mild headache");

        PhysicalExaminationResponseDto response =
                new PhysicalExaminationResponseDto();

        response.setId(examinationId);
        response.setRegistrationId(1L);
        response.setHeight(175.0);
        response.setWeight(70.0);
        response.setPulseRate(76);
        response.setBloodPressure("125/82");
        response.setPresentComplaints("Mild headache");

        when(physicalExaminationService.updatePhysicalExamination(
                eq(examinationId),
                any(PhysicalExaminationRequestDto.class)
        )).thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/physical-examinations/{id}",
                                examinationId
                        )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.registrationId").value(1))
                .andExpect(jsonPath("$.height").value(175.0))
                .andExpect(jsonPath("$.weight").value(70.0))
                .andExpect(jsonPath("$.pulseRate").value(76))
                .andExpect(jsonPath("$.bloodPressure")
                        .value("125/82"))
                .andExpect(jsonPath("$.presentComplaints")
                        .value("Mild headache"));

        verify(physicalExaminationService, times(1))
                .updatePhysicalExamination(
                        eq(examinationId),
                        any(PhysicalExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when attempting to update a physical examination
     * that does not exist.
     *
     * The service is mocked to throw PhysicalExaminationNotFoundException.
     * The GlobalExceptionHandler should return a structured 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenUpdatingPhysicalExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        PhysicalExaminationRequestDto request =
                new PhysicalExaminationRequestDto();

        request.setHeight(175.0);
        request.setWeight(70.0);

        when(physicalExaminationService.updatePhysicalExamination(
                eq(examinationId),
                any(PhysicalExaminationRequestDto.class)
        )).thenThrow(
                new PhysicalExaminationNotFoundException(
                        examinationId
                )
        );

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/physical-examinations/{id}",
                                examinationId
                        )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Physical examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value("/api/physical-examinations/999"));

        verify(physicalExaminationService, times(1))
                .updatePhysicalExamination(
                        eq(examinationId),
                        any(PhysicalExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that an existing physical examination
     * is deleted successfully.
     *
     * The service is mocked to complete without throwing an exception.
     * The controller should return HTTP 204 No Content.
     */
    @Test
    void shouldDeletePhysicalExamination() throws Exception {

        // Arrange
        Long examinationId = 10L;

        doNothing()
                .when(physicalExaminationService)
                .deletePhysicalExamination(examinationId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/physical-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNoContent());

        verify(physicalExaminationService, times(1))
                .deletePhysicalExamination(examinationId);
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when attempting to delete a physical examination
     * that does not exist.
     *
     * The service is mocked to throw PhysicalExaminationNotFoundException.
     * The GlobalExceptionHandler should convert the exception
     * into a structured HTTP 404 response.
     */
    @Test
    void shouldReturnNotFoundWhenDeletingPhysicalExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        doThrow(
                new PhysicalExaminationNotFoundException(
                        examinationId
                )
        )
                .when(physicalExaminationService)
                .deletePhysicalExamination(examinationId);

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/physical-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "Physical examination not found with id: 999"
                        ))
                .andExpect(jsonPath("$.path")
                        .value("/api/physical-examinations/999"));

        verify(physicalExaminationService, times(1))
                .deletePhysicalExamination(examinationId);
    }
}