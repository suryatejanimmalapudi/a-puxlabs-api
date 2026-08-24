package com.apuxlabs.apuxlabs_api.registration.controller;

import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationRequestDto;
import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationResponseDto;
import com.apuxlabs.apuxlabs_api.registration.service.RegistrationService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegistrationService registrationService;


    /**
     * Verifies that a registration is created successfully
     * when valid registration data is provided.
     */
    @Test
    void shouldCreateRegistration() throws Exception {

        // Arrange
        RegistrationRequestDto request =
                new RegistrationRequestDto();

        request.setDesignation("Mr");
        request.setFirstName("Ravi");
        request.setLastName("Kumar");
        request.setEmail("ravi@example.com");
        request.setPhone("9999999999");
        request.setDispatchMethods(
                List.of("EMAIL", "WHATSAPP")
        );

        RegistrationResponseDto response =
                new RegistrationResponseDto();

        response.setId(10L);
        response.setDesignation("Mr");
        response.setFirstName("Ravi");
        response.setLastName("Kumar");
        response.setEmail("ravi@example.com");
        response.setPhone("9999999999");
        response.setStatus("ACTIVE");

        when(registrationService.createRegistration(
                any(RegistrationRequestDto.class)
        )).thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        post("/api/registrations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.firstName").value("Ravi"))
                .andExpect(jsonPath("$.lastName").value("Kumar"))
                .andExpect(jsonPath("$.email").value("ravi@example.com"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    /**
     * Verifies that a registration is returned successfully
     * when the requested registration ID exists.
     */

    @Test
    void shouldGetRegistrationById() throws Exception {

        // Arrange
        Long registrationId = 10L;

        RegistrationResponseDto response =
                new RegistrationResponseDto();

        response.setId(registrationId);
        response.setDesignation("Mr");
        response.setFirstName("Ravi");
        response.setLastName("Kumar");
        response.setEmail("ravi@example.com");
        response.setPhone("9999999999");
        response.setStatus("ACTIVE");

        when(registrationService.getRegistrationById(registrationId))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        get("/api/registrations/{id}", registrationId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.designation").value("Mr"))
                .andExpect(jsonPath("$.firstName").value("Ravi"))
                .andExpect(jsonPath("$.lastName").value("Kumar"))
                .andExpect(jsonPath("$.email").value("ravi@example.com"))
                .andExpect(jsonPath("$.phone").value("9999999999"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(registrationService, times(1))
                .getRegistrationById(registrationId);
    }

    /**
     * Verifies that all registrations are returned successfully.
     *
     * The service is mocked to return multiple registrations.
     * The controller should return HTTP 200 OK and a JSON array
     * containing all registration details.
     */

    @Test
    void shouldGetAllRegistrations() throws Exception {

        // Arrange
        RegistrationResponseDto response1 =
                new RegistrationResponseDto();

        response1.setId(10L);
        response1.setFirstName("Ravi");
        response1.setLastName("Kumar");
        response1.setStatus("ACTIVE");

        RegistrationResponseDto response2 =
                new RegistrationResponseDto();

        response2.setId(11L);
        response2.setFirstName("Priya");
        response2.setLastName("Sharma");
        response2.setStatus("ACTIVE");

        List<RegistrationResponseDto> responses =
                List.of(response1, response2);

        when(registrationService.getAllRegistrations())
                .thenReturn(responses);

        // Act + Assert
        mockMvc.perform(
                        get("/api/registrations")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].firstName").value("Ravi"))
                .andExpect(jsonPath("$[0].lastName").value("Kumar"))

                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].firstName").value("Priya"))
                .andExpect(jsonPath("$[1].lastName").value("Sharma"));

        verify(registrationService, times(1))
                .getAllRegistrations();
    }

    /**
     * Verifies that an empty list is returned successfully
     * when no registrations exist.
     *
     * The service is mocked to return an empty list.
     * The controller should return HTTP 200 OK and an empty JSON array.
     */
    @Test
    void shouldReturnEmptyListWhenNoRegistrationsExist() throws Exception {

        // Arrange
        when(registrationService.getAllRegistrations())
                .thenReturn(List.of());

        // Act + Assert
        mockMvc.perform(
                        get("/api/registrations")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(registrationService, times(1))
                .getAllRegistrations();
    }

    /**
     * Verifies that HTTP 404 Not Found is returned
     * when the requested registration does not exist.
     *
     * The service is mocked to throw RegistrationNotFoundException.
     * The GlobalExceptionHandler should handle the exception and return
     * a structured error response with status 404.
     */
    @Test
    void shouldReturnNotFoundWhenRegistrationDoesNotExist() throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(registrationService.getRegistrationById(registrationId))
                .thenThrow(new RegistrationNotFoundException(registrationId));

        // Act + Assert
        mockMvc.perform(
                        get("/api/registrations/{id}", registrationId)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value("Registration not found with id: 999"))
                .andExpect(jsonPath("$.path")
                        .value("/api/registrations/999"));

        verify(registrationService, times(1))
                .getRegistrationById(registrationId);
    }

    /**
     * Verifies that an existing registration is updated successfully.
     *
     * The service is mocked to return the updated registration.
     * The controller should accept the updated JSON request,
     * call the service with the correct registration ID,
     * and return HTTP 200 OK with the updated registration details.
     */
    @Test
    void shouldUpdateRegistration() throws Exception {

        // Arrange
        Long registrationId = 10L;

        RegistrationRequestDto request =
                new RegistrationRequestDto();

        request.setDesignation("Mr");
        request.setFirstName("Ravi");
        request.setLastName("Kumar");
        request.setEmail("ravi.updated@example.com");
        request.setPhone("8888888888");
        request.setDispatchMethods(
                List.of("EMAIL", "WHATSAPP")
        );

        RegistrationResponseDto response =
                new RegistrationResponseDto();

        response.setId(registrationId);
        response.setDesignation("Mr");
        response.setFirstName("Ravi");
        response.setLastName("Kumar");
        response.setEmail("ravi.updated@example.com");
        response.setPhone("8888888888");
        response.setStatus("ACTIVE");

        when(registrationService.updateRegistration(
                eq(registrationId),
                any(RegistrationRequestDto.class)
        )).thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        put("/api/registrations/{id}", registrationId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.firstName").value("Ravi"))
                .andExpect(jsonPath("$.lastName").value("Kumar"))
                .andExpect(jsonPath("$.email")
                        .value("ravi.updated@example.com"))
                .andExpect(jsonPath("$.phone")
                        .value("8888888888"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(registrationService, times(1))
                .updateRegistration(
                        eq(registrationId),
                        any(RegistrationRequestDto.class)
                );
    }

    /**
     * Verifies that HTTP 404 Not Found is returned
     * when attempting to update a registration that does not exist.
     *
     * The service is mocked to throw RegistrationNotFoundException.
     * The GlobalExceptionHandler should convert that exception into
     * a structured HTTP 404 error response.
     */
    @Test
    void shouldReturnNotFoundWhenUpdatingRegistrationDoesNotExist()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        RegistrationRequestDto request =
                new RegistrationRequestDto();

        request.setFirstName("Ravi");
        request.setLastName("Kumar");
        request.setEmail("ravi.updated@example.com");

        when(registrationService.updateRegistration(
                eq(registrationId),
                any(RegistrationRequestDto.class)
        )).thenThrow(
                new RegistrationNotFoundException(registrationId)
        );

        // Act + Assert
        mockMvc.perform(
                        put("/api/registrations/{id}", registrationId)
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
                        .value("/api/registrations/999"));

        verify(registrationService, times(1))
                .updateRegistration(
                        eq(registrationId),
                        any(RegistrationRequestDto.class)
                );
    }

    /**
     * Verifies that an existing registration is deleted successfully.
     *
     * The service is mocked to complete the delete operation without throwing
     * an exception. The controller should return HTTP 204 No Content.
     */
    @Test
    void shouldDeleteRegistration() throws Exception {

        // Arrange
        Long registrationId = 10L;

        doNothing()
                .when(registrationService)
                .deleteRegistration(registrationId);

        // Act + Assert
        mockMvc.perform(
                        delete("/api/registrations/{id}", registrationId)
                )
                .andExpect(status().isNoContent());

        verify(registrationService, times(1))
                .deleteRegistration(registrationId);
    }

    /**
     * Verifies that HTTP 404 Not Found is returned
     * when attempting to delete a registration that does not exist.
     *
     * The service is mocked to throw RegistrationNotFoundException.
     * The GlobalExceptionHandler should convert that exception into
     * a structured HTTP 404 error response.
     */
    @Test
    void shouldReturnNotFoundWhenDeletingRegistrationDoesNotExist()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        doThrow(new RegistrationNotFoundException(registrationId))
                .when(registrationService)
                .deleteRegistration(registrationId);

        // Act + Assert
        mockMvc.perform(
                        delete("/api/registrations/{id}", registrationId)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value("Registration not found with id: 999"))
                .andExpect(jsonPath("$.path")
                        .value("/api/registrations/999"));

        verify(registrationService, times(1))
                .deleteRegistration(registrationId);
    }


}