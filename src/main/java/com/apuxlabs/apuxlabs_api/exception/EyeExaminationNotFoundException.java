package com.apuxlabs.apuxlabs_api.exception;

/**
 * Thrown when a requested eye examination does not exist.
 */
public class EyeExaminationNotFoundException extends RuntimeException
{

    public EyeExaminationNotFoundException(Long id)
    {
        super("Eye examination not found with id: " + id);
    }
}