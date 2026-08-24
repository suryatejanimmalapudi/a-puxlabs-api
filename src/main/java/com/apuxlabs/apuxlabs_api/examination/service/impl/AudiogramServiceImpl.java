package com.apuxlabs.apuxlabs_api.examination.service.impl;

import com.apuxlabs.apuxlabs_api.examination.dto.AudiogramRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.AudiogramResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.Audiogram;
import com.apuxlabs.apuxlabs_api.examination.mapper.AudiogramMapper;
import com.apuxlabs.apuxlabs_api.examination.repository.AudiogramRepository;
import com.apuxlabs.apuxlabs_api.examination.service.AudiogramService;
import com.apuxlabs.apuxlabs_api.exception.AudiogramNotFoundException;
import com.apuxlabs.apuxlabs_api.exception.RegistrationNotFoundException;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AudiogramServiceImpl implements AudiogramService {

    private final AudiogramRepository audiogramRepository;
    private final AudiogramMapper audiogramMapper;
    private final RegistrationRepository registrationRepository;

    public AudiogramServiceImpl(
            AudiogramRepository audiogramRepository,
            AudiogramMapper audiogramMapper,
            RegistrationRepository registrationRepository) {

        this.audiogramRepository = audiogramRepository;
        this.audiogramMapper = audiogramMapper;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public AudiogramResponseDto createAudiogram(
            Long registrationId,
            AudiogramRequestDto request) {

        // Verify that the registration exists.
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(registrationId)
                );

        // Convert request DTO to Audiogram entity.
        Audiogram audiogram =
                audiogramMapper.toEntity(request);

        // Link the audiogram to the registration.
        audiogram.setRegistration(registration);

        // Set timestamps.
        LocalDateTime now = LocalDateTime.now();
        audiogram.setCreatedAt(now);
        audiogram.setUpdatedAt(now);

        // Save to database.
        Audiogram savedAudiogram =
                audiogramRepository.save(audiogram);

        // Convert saved entity to response DTO.
        return audiogramMapper.toResponseDto(savedAudiogram);
    }

    @Override
    public AudiogramResponseDto getAudiogramById(Long id) {

        Audiogram audiogram =
                audiogramRepository.findById(id)
                        .orElseThrow(() ->
                                        new AudiogramNotFoundException(id)

                        );

        return audiogramMapper.toResponseDto(audiogram);
    }

    @Override
    public List<AudiogramResponseDto> getAudiogramsByRegistrationId(
            Long registrationId) {

        // Verify that the registration exists.
        registrationRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(registrationId)
                );

        // Fetch all audiograms linked to this registration.
        List<Audiogram> audiograms =
                audiogramRepository.findAllByRegistrationId(registrationId);

        // Convert entities into response DTOs.
        return audiograms.stream()
                .map(audiogramMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public AudiogramResponseDto updateAudiogram(
            Long id,
            AudiogramRequestDto request) {

        // Find the existing audiogram.
        Audiogram audiogram =
                audiogramRepository.findById(id)
                        .orElseThrow(() ->
                                new AudiogramNotFoundException(id)
                        );

        // Update examination date.
        audiogram.setExaminationDate(request.getExaminationDate());

        // Update right ear thresholds.
        audiogram.setRight125Hz(request.getRight125Hz());
        audiogram.setRight250Hz(request.getRight250Hz());
        audiogram.setRight500Hz(request.getRight500Hz());
        audiogram.setRight1000Hz(request.getRight1000Hz());
        audiogram.setRight2000Hz(request.getRight2000Hz());
        audiogram.setRight4000Hz(request.getRight4000Hz());
        audiogram.setRight8000Hz(request.getRight8000Hz());

        // Update left ear thresholds.
        audiogram.setLeft125Hz(request.getLeft125Hz());
        audiogram.setLeft250Hz(request.getLeft250Hz());
        audiogram.setLeft500Hz(request.getLeft500Hz());
        audiogram.setLeft1000Hz(request.getLeft1000Hz());
        audiogram.setLeft2000Hz(request.getLeft2000Hz());
        audiogram.setLeft4000Hz(request.getLeft4000Hz());
        audiogram.setLeft8000Hz(request.getLeft8000Hz());

        // Update interpretation details.
        audiogram.setImpression(request.getImpression());
        audiogram.setAudiologistName(request.getAudiologistName());

        // Only updatedAt changes during update.
        audiogram.setUpdatedAt(LocalDateTime.now());

        return audiogramMapper.toResponseDto(audiogram);
    }

    @Override
    public void deleteAudiogram(Long id) {

        Audiogram audiogram =
                audiogramRepository.findById(id)
                        .orElseThrow(() ->
                                new AudiogramNotFoundException(id)
                        );

        audiogramRepository.delete(audiogram);

    }
}