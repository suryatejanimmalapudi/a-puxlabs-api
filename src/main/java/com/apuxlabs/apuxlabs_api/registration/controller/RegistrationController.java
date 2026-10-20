package com.apuxlabs.apuxlabs_api.registration.controller;

import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationRequestDto;
import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationResponseDto;
import com.apuxlabs.apuxlabs_api.registration.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Creates a new registration.
     *
     * @param request registration details received from the client
     * @return newly created registration
     */
    @PostMapping
    public ResponseEntity<RegistrationResponseDto> createRegistration(
            @RequestBody RegistrationRequestDto request) {

        RegistrationResponseDto response =
                registrationService.createRegistration(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves a registration by its ID.
     *
     * @param id registration ID
     * @return registration details
     */
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationResponseDto> getRegistrationById(
            @PathVariable Long id) {

        RegistrationResponseDto response =
                registrationService.getRegistrationById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all registrations.
     *
     * @return list of all registrations
     */
    @GetMapping
    public ResponseEntity<?> getAllRegistrations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(defaultValue = "") String packageName) {

        if (page < 0 || size < 1 || size > 100) return ResponseEntity.badRequest().build();
        // Always bind typed timestamps. PostgreSQL cannot infer the type of a
        // null parameter used in an `IS NULL` predicate.
        LocalDateTime from = fromDate == null ? LocalDateTime.of(1970, 1, 1, 0, 0) : fromDate.atStartOfDay();
        LocalDateTime to = toDate == null ? LocalDateTime.of(9999, 12, 31, 0, 0) : toDate.plusDays(1).atStartOfDay();
        Page<RegistrationResponseDto> response = registrationService.searchRegistrations(search, from, to, packageName,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registrationDate")));
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing registration.
     *
     * @param id registration ID
     * @param request updated registration details
     * @return updated registration
     */
    @PutMapping("/{id}")
    public ResponseEntity<RegistrationResponseDto> updateRegistration(
            @PathVariable Long id,
            @RequestBody RegistrationRequestDto request) {

        RegistrationResponseDto response =
                registrationService.updateRegistration(id, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a registration by its ID.
     *
     * @param id registration ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(
            @PathVariable Long id) {

        registrationService.deleteRegistration(id);

        return ResponseEntity.noContent().build();
    }
}
