package com.apuxlabs.apuxlabs_api.examination.mapper;

import com.apuxlabs.apuxlabs_api.examination.dto.AudiogramRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.AudiogramResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.Audiogram;
import org.springframework.stereotype.Component;

@Component
public class AudiogramMapper {

    /**
     * Converts an incoming Audiogram request DTO into an Audiogram entity.
     *
     * Registration, ID, and timestamps are handled separately
     * by the service/database layer.
     *
     * @param dto incoming audiogram request
     * @return Audiogram entity
     */
    public Audiogram toEntity(AudiogramRequestDto dto) {

        Audiogram audiogram = new Audiogram();

        audiogram.setExaminationDate(dto.getExaminationDate());

        audiogram.setRight125Hz(dto.getRight125Hz());
        audiogram.setRight250Hz(dto.getRight250Hz());
        audiogram.setRight500Hz(dto.getRight500Hz());
        audiogram.setRight1000Hz(dto.getRight1000Hz());
        audiogram.setRight2000Hz(dto.getRight2000Hz());
        audiogram.setRight4000Hz(dto.getRight4000Hz());
        audiogram.setRight8000Hz(dto.getRight8000Hz());

        audiogram.setLeft125Hz(dto.getLeft125Hz());
        audiogram.setLeft250Hz(dto.getLeft250Hz());
        audiogram.setLeft500Hz(dto.getLeft500Hz());
        audiogram.setLeft1000Hz(dto.getLeft1000Hz());
        audiogram.setLeft2000Hz(dto.getLeft2000Hz());
        audiogram.setLeft4000Hz(dto.getLeft4000Hz());
        audiogram.setLeft8000Hz(dto.getLeft8000Hz());

        audiogram.setImpression(dto.getImpression());
        audiogram.setAudiologistName(dto.getAudiologistName());

        return audiogram;
    }

    /**
     * Converts a persisted Audiogram entity into an API response DTO.
     *
     * @param audiogram persisted Audiogram entity
     * @return Audiogram response DTO
     */
    public AudiogramResponseDto toResponseDto(Audiogram audiogram) {

        AudiogramResponseDto response = new AudiogramResponseDto();

        response.setId(audiogram.getId());

        if (audiogram.getRegistration() != null) {
            response.setRegistrationId(
                    audiogram.getRegistration().getId()
            );
        }

        response.setExaminationDate(audiogram.getExaminationDate());

        response.setRight125Hz(audiogram.getRight125Hz());
        response.setRight250Hz(audiogram.getRight250Hz());
        response.setRight500Hz(audiogram.getRight500Hz());
        response.setRight1000Hz(audiogram.getRight1000Hz());
        response.setRight2000Hz(audiogram.getRight2000Hz());
        response.setRight4000Hz(audiogram.getRight4000Hz());
        response.setRight8000Hz(audiogram.getRight8000Hz());

        response.setLeft125Hz(audiogram.getLeft125Hz());
        response.setLeft250Hz(audiogram.getLeft250Hz());
        response.setLeft500Hz(audiogram.getLeft500Hz());
        response.setLeft1000Hz(audiogram.getLeft1000Hz());
        response.setLeft2000Hz(audiogram.getLeft2000Hz());
        response.setLeft4000Hz(audiogram.getLeft4000Hz());
        response.setLeft8000Hz(audiogram.getLeft8000Hz());

        response.setImpression(audiogram.getImpression());
        response.setAudiologistName(audiogram.getAudiologistName());

        response.setCreatedAt(audiogram.getCreatedAt());
        response.setUpdatedAt(audiogram.getUpdatedAt());

        return response;
    }
}