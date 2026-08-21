package com.apuxlabs.apuxlabs_api.registration.repository;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long>
{
}