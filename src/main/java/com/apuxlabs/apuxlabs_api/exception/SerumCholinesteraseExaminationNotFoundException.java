package com.apuxlabs.apuxlabs_api.exception;

/**
 * Thrown when a requested serum cholinesterase examination does not exist.
 */
public class SerumCholinesteraseExaminationNotFoundException
        extends RuntimeException {

    public SerumCholinesteraseExaminationNotFoundException(Long id) {
        super("Serum cholinesterase examination not found with id: " + id);
    }
}