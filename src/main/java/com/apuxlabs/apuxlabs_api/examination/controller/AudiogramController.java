package com.apuxlabs.apuxlabs_api.examination.controller;

import com.apuxlabs.apuxlabs_api.examination.dto.AudiogramRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.AudiogramResponseDto;
import com.apuxlabs.apuxlabs_api.examination.service.AudiogramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audiograms")
public class AudiogramController {

    private final AudiogramService audiogramService;

    public AudiogramController(AudiogramService audiogramService) {
        this.audiogramService = audiogramService;
    }

    /**
     * Creates a new audiogram for an existing registration.
     *
     * @param registrationId registration ID
     * @param request audiogram details
     * @return created audiogram
     */
    @PostMapping("/registration/{registrationId}")
    public ResponseEntity<AudiogramResponseDto> createAudiogram(
            @PathVariable Long registrationId,
            @RequestBody AudiogramRequestDto request) {

        AudiogramResponseDto response =
                audiogramService.createAudiogram(
                        registrationId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves an audiogram by its ID.
     *
     * @param id audiogram ID
     * @return audiogram details
     */
    @GetMapping("/{id}")
    public ResponseEntity<AudiogramResponseDto> getAudiogramById(
            @PathVariable Long id) {

        AudiogramResponseDto response =
                audiogramService.getAudiogramById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all audiograms associated with a registration.
     *
     * @param registrationId registration ID
     * @return list of audiograms
     */
    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<List<AudiogramResponseDto>>
    getAudiogramsByRegistrationId(
            @PathVariable Long registrationId) {

        List<AudiogramResponseDto> response =
                audiogramService
                        .getAudiogramsByRegistrationId(registrationId);

        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing audiogram.
     *
     * @param id audiogram ID
     * @param request updated audiogram details
     * @return updated audiogram
     */
    @PutMapping("/{id}")
    public ResponseEntity<AudiogramResponseDto> updateAudiogram(
            @PathVariable Long id,
            @RequestBody AudiogramRequestDto request) {

        AudiogramResponseDto response =
                audiogramService.updateAudiogram(id, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes an audiogram by its ID.
     *
     * @param id audiogram ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudiogram(
            @PathVariable Long id) {

        audiogramService.deleteAudiogram(id);

        return ResponseEntity.noContent().build();
    }
}