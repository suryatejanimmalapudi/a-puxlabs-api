package com.apuxlabs.apuxlabs_api.exception;

public class UrineRoutineExaminationNotFoundException
        extends RuntimeException {

    public UrineRoutineExaminationNotFoundException(Long id) {
        super("Urine routine examination not found with id: " + id);
    }
}