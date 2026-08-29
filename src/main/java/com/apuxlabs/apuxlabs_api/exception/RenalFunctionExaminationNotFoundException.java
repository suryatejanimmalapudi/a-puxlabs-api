package com.apuxlabs.apuxlabs_api.exception;

/**
 * Thrown when a requested renal function examination does not exist.
 */
public class RenalFunctionExaminationNotFoundException
        extends RuntimeException {

    public RenalFunctionExaminationNotFoundException(Long id) {
        super("Renal function examination not found with id: " + id);
    }
}