package com.apuxlabs.apuxlabs_api.exception;

public class PhysicalExaminationNotFoundException extends RuntimeException
{

    public PhysicalExaminationNotFoundException(Long id)
    {
        super("Physical examination not found with id: " + id);
    }
}