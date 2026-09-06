package com.apuxlabs.apuxlabs_api.exception;

/**
 * Thrown when a requested blood glucose examination does not exist.
 */
public class BloodGlucoseExaminationNotFoundException
        extends RuntimeException {

    public BloodGlucoseExaminationNotFoundException(Long id) {
        super("Blood glucose examination not found with id: " + id);
    }
}