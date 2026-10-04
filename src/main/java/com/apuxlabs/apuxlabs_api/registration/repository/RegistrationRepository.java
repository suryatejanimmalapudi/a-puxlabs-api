package com.apuxlabs.apuxlabs_api.registration.repository;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long>
{
    Optional<Registration> findByBarcode(String barcode);
}
