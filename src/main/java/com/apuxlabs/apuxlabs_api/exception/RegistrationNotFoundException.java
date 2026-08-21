package com.apuxlabs.apuxlabs_api.exception;

/**
 * Thrown when a requested registration does not exist.
 */
public class RegistrationNotFoundException extends RuntimeException {

    public RegistrationNotFoundException(Long id) {
        super("Registration not found with id: " + id);
    }
}