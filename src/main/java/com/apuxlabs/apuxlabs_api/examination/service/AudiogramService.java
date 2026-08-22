package com.apuxlabs.apuxlabs_api.examination.service;

import com.apuxlabs.apuxlabs_api.examination.dto.AudiogramRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.AudiogramResponseDto;

import java.util.List;

public interface AudiogramService {

    /**
     * Creates an audiogram for a registration.
     *
     * @param registrationId registration ID
     * @param request audiogram details
     * @return created audiogram
     */
    AudiogramResponseDto createAudiogram(
            Long registrationId,
            AudiogramRequestDto request
    );

    /**
     * Retrieves an audiogram by its ID.
     *
     * @param id audiogram ID
     * @return audiogram details
     */
    AudiogramResponseDto getAudiogramById(Long id);

    /**
     * Retrieves all audiograms for a registration.
     *
     * @param registrationId registration ID
     * @return list of audiograms
     */
    List<AudiogramResponseDto> getAudiogramsByRegistrationId(
            Long registrationId
    );

    /**
     * Updates an existing audiogram.
     *
     * @param id audiogram ID
     * @param request updated audiogram details
     * @return updated audiogram
     */
    AudiogramResponseDto updateAudiogram(
            Long id,
            AudiogramRequestDto request
    );

    /**
     * Deletes an audiogram by its ID.
     *
     * @param id audiogram ID
     */
    void deleteAudiogram(Long id);
}