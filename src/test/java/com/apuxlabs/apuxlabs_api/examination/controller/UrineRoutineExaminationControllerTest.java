package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.UrineRoutineExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.UrineRoutineExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.UrineRoutineExaminationService;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.UrineRoutineExaminationNotFoundException;

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

@WebMvcTest(UrineRoutineExaminationController.class)
class UrineRoutineExaminationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UrineRoutineExaminationService
            urineRoutineExaminationService;


    /**
     * Verifies that a urine routine examination is created
     * successfully for an existing registration.
     *
     * The service is mocked to return the created examination.
     * The controller should accept the JSON request and return
     * HTTP 201 Created with the examination details.
     */
    @Test
    void shouldCreateUrineRoutineExamination()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        LocalDateTime examinationDateTime =
                LocalDateTime.of(
                        2026,
                        7,
                        22,
                        10,
                        30
                );

        UrineRoutineExaminationRequestDto request =
                new UrineRoutineExaminationRequestDto();

        request.setExaminationDateTime(
                examinationDateTime
        );

        request.setSampleType(
                "Urine"
        );

        // Physical examination
        request.setColour(
                "Yellow"
        );

        request.setDeposit(
                "NOT FOUND"
        );

        request.setAppearance(
                "Clear"
        );

        request.setPh(
                7.0
        );

        request.setSpecificGravity(
                1.015
        );

        request.setQuantity(
                "20ml"
        );

        // Chemical examination
        request.setUrineProtein(
                "Absent"
        );

        request.setBileSalt(
                "Absent"
        );

        request.setUrineGlucose(
                "Absent"
        );

        request.setUrineKetones(
                "Absent"
        );

        request.setBilePigment(
                "Absent"
        );

        request.setOccultBlood(
                "Absent"
        );

        // Microscopic examination
        request.setRbcs(
                "Absent"
        );

        request.setPusCells(
                "4-6/hpf"
        );

        request.setEpithelialCells(
                "1-2/hpf"
        );

        request.setCrystals(
                "Absent"
        );

        request.setCasts(
                "Absent"
        );

        request.setAmorphousDeposit(
                "Absent"
        );

        request.setBacteria(
                "Absent"
        );

        request.setTrichomonasVaginalis(
                "NOT SEEN"
        );

        request.setYeastCells(
                "Absent"
        );

        request.setRemarks(
                "Urine routine examination reviewed"
        );

        request.setPathologistName(
                "Dr Radha"
        );


        UrineRoutineExaminationResponseDto response =
                new UrineRoutineExaminationResponseDto();

        response.setId(
                10L
        );

        response.setRegistrationId(
                registrationId
        );

        response.setExaminationDateTime(
                examinationDateTime
        );

        response.setSampleType(
                "Urine"
        );

        response.setColour(
                "Yellow"
        );

        response.setDeposit(
                "NOT FOUND"
        );

        response.setAppearance(
                "Clear"
        );

        response.setPh(
                7.0
        );

        response.setSpecificGravity(
                1.015
        );

        response.setQuantity(
                "20ml"
        );

        response.setUrineProtein(
                "Absent"
        );

        response.setBileSalt(
                "Absent"
        );

        response.setUrineGlucose(
                "Absent"
        );

        response.setUrineKetones(
                "Absent"
        );

        response.setBilePigment(
                "Absent"
        );

        response.setOccultBlood(
                "Absent"
        );

        response.setRbcs(
                "Absent"
        );

        response.setPusCells(
                "4-6/hpf"
        );

        response.setEpithelialCells(
                "1-2/hpf"
        );

        response.setCrystals(
                "Absent"
        );

        response.setCasts(
                "Absent"
        );

        response.setAmorphousDeposit(
                "Absent"
        );

        response.setBacteria(
                "Absent"
        );

        response.setTrichomonasVaginalis(
                "NOT SEEN"
        );

        response.setYeastCells(
                "Absent"
        );

        response.setRemarks(
                "Urine routine examination reviewed"
        );

        response.setPathologistName(
                "Dr Radha"
        );

        when(urineRoutineExaminationService
                .createUrineRoutineExamination(
                        eq(registrationId),
                        any(UrineRoutineExaminationRequestDto.class)
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/urine-routine-examinations/registration/{registrationId}",
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

                .andExpect(jsonPath("$.id")
                        .value(10))

                .andExpect(jsonPath("$.registrationId")
                        .value(1))

                .andExpect(jsonPath("$.sampleType")
                        .value("Urine"))

                .andExpect(jsonPath("$.colour")
                        .value("Yellow"))

                .andExpect(jsonPath("$.deposit")
                        .value("NOT FOUND"))

                .andExpect(jsonPath("$.appearance")
                        .value("Clear"))

                .andExpect(jsonPath("$.ph")
                        .value(7.0))

                .andExpect(jsonPath("$.specificGravity")
                        .value(1.015))

                .andExpect(jsonPath("$.quantity")
                        .value("20ml"))

                .andExpect(jsonPath("$.urineProtein")
                        .value("Absent"))

                .andExpect(jsonPath("$.bileSalt")
                        .value("Absent"))

                .andExpect(jsonPath("$.urineGlucose")
                        .value("Absent"))

                .andExpect(jsonPath("$.urineKetones")
                        .value("Absent"))

                .andExpect(jsonPath("$.bilePigment")
                        .value("Absent"))

                .andExpect(jsonPath("$.occultBlood")
                        .value("Absent"))

                .andExpect(jsonPath("$.rbcs")
                        .value("Absent"))

                .andExpect(jsonPath("$.pusCells")
                        .value("4-6/hpf"))

                .andExpect(jsonPath("$.epithelialCells")
                        .value("1-2/hpf"))

                .andExpect(jsonPath("$.crystals")
                        .value("Absent"))

                .andExpect(jsonPath("$.casts")
                        .value("Absent"))

                .andExpect(jsonPath("$.amorphousDeposit")
                        .value("Absent"))

                .andExpect(jsonPath("$.bacteria")
                        .value("Absent"))

                .andExpect(jsonPath("$.trichomonasVaginalis")
                        .value("NOT SEEN"))

                .andExpect(jsonPath("$.yeastCells")
                        .value("Absent"))

                .andExpect(jsonPath("$.remarks")
                        .value(
                                "Urine routine examination reviewed"
                        ))

                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Radha"));

        verify(urineRoutineExaminationService, times(1))
                .createUrineRoutineExamination(
                        eq(registrationId),
                        any(UrineRoutineExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * a urine routine examination is created for
     * a registration that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenCreatingUrineRoutineForMissingRegistration()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        UrineRoutineExaminationRequestDto request =
                new UrineRoutineExaminationRequestDto();

        request.setColour(
                "Yellow"
        );

        when(urineRoutineExaminationService
                .createUrineRoutineExamination(
                        eq(registrationId),
                        any(UrineRoutineExaminationRequestDto.class)
                ))
                .thenThrow(
                        new RegistrationNotFoundException(
                                registrationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        post(
                                "/api/urine-routine-examinations/registration/{registrationId}",
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
                                "/api/urine-routine-examinations/registration/999"
                        ));

        verify(urineRoutineExaminationService, times(1))
                .createUrineRoutineExamination(
                        eq(registrationId),
                        any(UrineRoutineExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that a urine routine examination
     * is returned successfully when its ID exists.
     */
    @Test
    void shouldGetUrineRoutineExaminationById()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        UrineRoutineExaminationResponseDto response =
                new UrineRoutineExaminationResponseDto();

        response.setId(
                examinationId
        );

        response.setRegistrationId(
                1L
        );

        response.setSampleType(
                "Urine"
        );

        response.setColour(
                "Yellow"
        );

        response.setDeposit(
                "NOT FOUND"
        );

        response.setAppearance(
                "Clear"
        );

        response.setPh(
                7.0
        );

        response.setSpecificGravity(
                1.015
        );

        response.setQuantity(
                "20ml"
        );

        response.setUrineProtein(
                "Absent"
        );

        response.setUrineGlucose(
                "Absent"
        );

        response.setUrineKetones(
                "Absent"
        );

        response.setRbcs(
                "Absent"
        );

        response.setPusCells(
                "4-6/hpf"
        );

        response.setEpithelialCells(
                "1-2/hpf"
        );

        when(urineRoutineExaminationService
                .getUrineRoutineExaminationById(
                        examinationId
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/urine-routine-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id")
                        .value(10))

                .andExpect(jsonPath("$.registrationId")
                        .value(1))

                .andExpect(jsonPath("$.sampleType")
                        .value("Urine"))

                .andExpect(jsonPath("$.colour")
                        .value("Yellow"))

                .andExpect(jsonPath("$.deposit")
                        .value("NOT FOUND"))

                .andExpect(jsonPath("$.appearance")
                        .value("Clear"))

                .andExpect(jsonPath("$.ph")
                        .value(7.0))

                .andExpect(jsonPath("$.specificGravity")
                        .value(1.015))

                .andExpect(jsonPath("$.quantity")
                        .value("20ml"))

                .andExpect(jsonPath("$.urineProtein")
                        .value("Absent"))

                .andExpect(jsonPath("$.urineGlucose")
                        .value("Absent"))

                .andExpect(jsonPath("$.urineKetones")
                        .value("Absent"))

                .andExpect(jsonPath("$.rbcs")
                        .value("Absent"))

                .andExpect(jsonPath("$.pusCells")
                        .value("4-6/hpf"))

                .andExpect(jsonPath("$.epithelialCells")
                        .value("1-2/hpf"));

        verify(urineRoutineExaminationService, times(1))
                .getUrineRoutineExaminationById(
                        examinationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when the requested urine routine examination
     * does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenUrineRoutineExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        when(urineRoutineExaminationService
                .getUrineRoutineExaminationById(
                        examinationId
                ))
                .thenThrow(
                        new UrineRoutineExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/urine-routine-examinations/{id}",
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
                                "Urine routine examination not found with id: 999"
                        ))

                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/urine-routine-examinations/999"
                        ));

        verify(urineRoutineExaminationService, times(1))
                .getUrineRoutineExaminationById(
                        examinationId
                );
    }


    /**
     * Verifies that all urine routine examinations
     * associated with a registration are returned successfully.
     */
    @Test
    void shouldGetUrineRoutineExaminationsByRegistrationId()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        UrineRoutineExaminationResponseDto response1 =
                new UrineRoutineExaminationResponseDto();

        response1.setId(
                10L
        );

        response1.setRegistrationId(
                registrationId
        );

        response1.setColour(
                "Yellow"
        );

        response1.setAppearance(
                "Clear"
        );

        response1.setPh(
                7.0
        );

        response1.setSpecificGravity(
                1.015
        );


        UrineRoutineExaminationResponseDto response2 =
                new UrineRoutineExaminationResponseDto();

        response2.setId(
                11L
        );

        response2.setRegistrationId(
                registrationId
        );

        response2.setColour(
                "Pale Yellow"
        );

        response2.setAppearance(
                "Clear"
        );

        response2.setPh(
                6.5
        );

        response2.setSpecificGravity(
                1.020
        );


        when(urineRoutineExaminationService
                .getUrineRoutineExaminationsByRegistrationId(
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
                                "/api/urine-routine-examinations/registration/{registrationId}",
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

                .andExpect(jsonPath("$[0].colour")
                        .value("Yellow"))

                .andExpect(jsonPath("$[0].appearance")
                        .value("Clear"))

                .andExpect(jsonPath("$[0].ph")
                        .value(7.0))

                .andExpect(jsonPath("$[0].specificGravity")
                        .value(1.015))

                .andExpect(jsonPath("$[1].id")
                        .value(11))

                .andExpect(jsonPath("$[1].registrationId")
                        .value(1))

                .andExpect(jsonPath("$[1].colour")
                        .value("Pale Yellow"))

                .andExpect(jsonPath("$[1].appearance")
                        .value("Clear"))

                .andExpect(jsonPath("$[1].ph")
                        .value(6.5))

                .andExpect(jsonPath("$[1].specificGravity")
                        .value(1.020));

        verify(urineRoutineExaminationService, times(1))
                .getUrineRoutineExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an empty JSON array is returned successfully
     * when an existing registration has no urine routine
     * examinations.
     */
    @Test
    void shouldReturnEmptyListWhenRegistrationHasNoUrineRoutineExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 1L;

        when(urineRoutineExaminationService
                .getUrineRoutineExaminationsByRegistrationId(
                        registrationId
                ))
                .thenReturn(
                        List.of()
                );

        // Act + Assert
        mockMvc.perform(
                        get(
                                "/api/urine-routine-examinations/registration/{registrationId}",
                                registrationId
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(0));

        verify(urineRoutineExaminationService, times(1))
                .getUrineRoutineExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned
     * when urine routine examinations are requested
     * for a registration that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenRegistrationDoesNotExistWhileGettingUrineRoutineExaminations()
            throws Exception {

        // Arrange
        Long registrationId = 999L;

        when(urineRoutineExaminationService
                .getUrineRoutineExaminationsByRegistrationId(
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
                                "/api/urine-routine-examinations/registration/{registrationId}",
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
                                "/api/urine-routine-examinations/registration/999"
                        ));

        verify(urineRoutineExaminationService, times(1))
                .getUrineRoutineExaminationsByRegistrationId(
                        registrationId
                );
    }


    /**
     * Verifies that an existing urine routine examination
     * is updated successfully.
     */
    @Test
    void shouldUpdateUrineRoutineExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        LocalDateTime examinationDateTime =
                LocalDateTime.of(
                        2026,
                        8,
                        25,
                        15,
                        30
                );

        UrineRoutineExaminationRequestDto request =
                new UrineRoutineExaminationRequestDto();

        request.setExaminationDateTime(
                examinationDateTime
        );

        request.setSampleType(
                "Urine"
        );

        // Physical examination
        request.setColour(
                "Pale Yellow"
        );

        request.setDeposit(
                "NOT FOUND"
        );

        request.setAppearance(
                "Clear"
        );

        request.setPh(
                6.5
        );

        request.setSpecificGravity(
                1.020
        );

        request.setQuantity(
                "25ml"
        );

        // Chemical examination
        request.setUrineProtein(
                "Absent"
        );

        request.setBileSalt(
                "Absent"
        );

        request.setUrineGlucose(
                "Absent"
        );

        request.setUrineKetones(
                "Absent"
        );

        request.setBilePigment(
                "Absent"
        );

        request.setOccultBlood(
                "Absent"
        );

        // Microscopic examination
        request.setRbcs(
                "Absent"
        );

        request.setPusCells(
                "2-3/hpf"
        );

        request.setEpithelialCells(
                "1-2/hpf"
        );

        request.setCrystals(
                "Absent"
        );

        request.setCasts(
                "Absent"
        );

        request.setAmorphousDeposit(
                "Absent"
        );

        request.setBacteria(
                "Absent"
        );

        request.setTrichomonasVaginalis(
                "NOT SEEN"
        );

        request.setYeastCells(
                "Absent"
        );

        request.setRemarks(
                "Updated urine routine result"
        );

        request.setPathologistName(
                "Dr Anil Kumar"
        );


        UrineRoutineExaminationResponseDto response =
                new UrineRoutineExaminationResponseDto();

        response.setId(
                examinationId
        );

        response.setRegistrationId(
                1L
        );

        response.setExaminationDateTime(
                examinationDateTime
        );

        response.setSampleType(
                "Urine"
        );

        response.setColour(
                "Pale Yellow"
        );

        response.setDeposit(
                "NOT FOUND"
        );

        response.setAppearance(
                "Clear"
        );

        response.setPh(
                6.5
        );

        response.setSpecificGravity(
                1.020
        );

        response.setQuantity(
                "25ml"
        );

        response.setUrineProtein(
                "Absent"
        );

        response.setBileSalt(
                "Absent"
        );

        response.setUrineGlucose(
                "Absent"
        );

        response.setUrineKetones(
                "Absent"
        );

        response.setBilePigment(
                "Absent"
        );

        response.setOccultBlood(
                "Absent"
        );

        response.setRbcs(
                "Absent"
        );

        response.setPusCells(
                "2-3/hpf"
        );

        response.setEpithelialCells(
                "1-2/hpf"
        );

        response.setCrystals(
                "Absent"
        );

        response.setCasts(
                "Absent"
        );

        response.setAmorphousDeposit(
                "Absent"
        );

        response.setBacteria(
                "Absent"
        );

        response.setTrichomonasVaginalis(
                "NOT SEEN"
        );

        response.setYeastCells(
                "Absent"
        );

        response.setRemarks(
                "Updated urine routine result"
        );

        response.setPathologistName(
                "Dr Anil Kumar"
        );

        when(urineRoutineExaminationService
                .updateUrineRoutineExamination(
                        eq(examinationId),
                        any(UrineRoutineExaminationRequestDto.class)
                ))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/urine-routine-examinations/{id}",
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
                        .value("Urine"))

                .andExpect(jsonPath("$.colour")
                        .value("Pale Yellow"))

                .andExpect(jsonPath("$.deposit")
                        .value("NOT FOUND"))

                .andExpect(jsonPath("$.appearance")
                        .value("Clear"))

                .andExpect(jsonPath("$.ph")
                        .value(6.5))

                .andExpect(jsonPath("$.specificGravity")
                        .value(1.020))

                .andExpect(jsonPath("$.quantity")
                        .value("25ml"))

                .andExpect(jsonPath("$.urineProtein")
                        .value("Absent"))

                .andExpect(jsonPath("$.urineGlucose")
                        .value("Absent"))

                .andExpect(jsonPath("$.pusCells")
                        .value("2-3/hpf"))

                .andExpect(jsonPath("$.epithelialCells")
                        .value("1-2/hpf"))

                .andExpect(jsonPath("$.remarks")
                        .value(
                                "Updated urine routine result"
                        ))

                .andExpect(jsonPath("$.pathologistName")
                        .value("Dr Anil Kumar"));

        verify(urineRoutineExaminationService, times(1))
                .updateUrineRoutineExamination(
                        eq(examinationId),
                        any(UrineRoutineExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to update a urine routine examination
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenUpdatingUrineRoutineExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        UrineRoutineExaminationRequestDto request =
                new UrineRoutineExaminationRequestDto();

        request.setColour(
                "Pale Yellow"
        );

        when(urineRoutineExaminationService
                .updateUrineRoutineExamination(
                        eq(examinationId),
                        any(UrineRoutineExaminationRequestDto.class)
                ))
                .thenThrow(
                        new UrineRoutineExaminationNotFoundException(
                                examinationId
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        put(
                                "/api/urine-routine-examinations/{id}",
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
                                "Urine routine examination not found with id: 999"
                        ))

                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/urine-routine-examinations/999"
                        ));

        verify(urineRoutineExaminationService, times(1))
                .updateUrineRoutineExamination(
                        eq(examinationId),
                        any(UrineRoutineExaminationRequestDto.class)
                );
    }


    /**
     * Verifies that an existing urine routine examination
     * is deleted successfully.
     *
     * The service is mocked to complete without throwing
     * an exception. The controller should return
     * HTTP 204 No Content.
     */
    @Test
    void shouldDeleteUrineRoutineExamination()
            throws Exception {

        // Arrange
        Long examinationId = 10L;

        doNothing()
                .when(urineRoutineExaminationService)
                .deleteUrineRoutineExamination(
                        examinationId
                );

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/urine-routine-examinations/{id}",
                                examinationId
                        )
                )
                .andExpect(status().isNoContent());

        verify(urineRoutineExaminationService, times(1))
                .deleteUrineRoutineExamination(
                        examinationId
                );
    }


    /**
     * Verifies that HTTP 404 Not Found is returned when
     * attempting to delete a urine routine examination
     * that does not exist.
     */
    @Test
    void shouldReturnNotFoundWhenDeletingUrineRoutineExaminationDoesNotExist()
            throws Exception {

        // Arrange
        Long examinationId = 999L;

        doThrow(
                new UrineRoutineExaminationNotFoundException(
                        examinationId
                )
        )
                .when(urineRoutineExaminationService)
                .deleteUrineRoutineExamination(
                        examinationId
                );

        // Act + Assert
        mockMvc.perform(
                        delete(
                                "/api/urine-routine-examinations/{id}",
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
                                "Urine routine examination not found with id: 999"
                        ))

                .andExpect(jsonPath("$.path")
                        .value(
                                "/api/urine-routine-examinations/999"
                        ));

        verify(urineRoutineExaminationService, times(1))
                .deleteUrineRoutineExamination(
                        examinationId
                );
    }
}