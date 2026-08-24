package com.apuxlabs.apuxlabs_api.examination.repository;

import com.apuxlabs.apuxlabs_api.examination.entity.Audiogram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AudiogramRepository
        extends JpaRepository<Audiogram, Long> {

    /**
     * Retrieves all audiograms associated with a registration.
     *
     * @param registrationId registration ID
     * @return list of audiograms
     */
    List<Audiogram> findAllByRegistrationId(Long registrationId);
}