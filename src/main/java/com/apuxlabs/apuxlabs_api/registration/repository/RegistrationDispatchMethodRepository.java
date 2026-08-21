package com.apuxlabs.apuxlabs_api.registration.repository;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.entity.RegistrationDispatchMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationDispatchMethodRepository
        extends JpaRepository<RegistrationDispatchMethod, Long> {

    /**
     * Deletes all dispatch methods associated with a registration.
     *
     * @param registration parent registration
     */
    void deleteAllByRegistration(Registration registration);
}