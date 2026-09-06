package com.apuxlabs.apuxlabs_api.exception;

/**
 * Thrown when a requested audiogram does not exist.
 */
public class AudiogramNotFoundException extends RuntimeException {

    public AudiogramNotFoundException(Long id) {
        super("Audiogram not found with id: " + id);
    }
}