package com.apuxlabs.apuxlabs_api.exception;

public class LipidProfileExaminationNotFoundException
        extends RuntimeException {

    public LipidProfileExaminationNotFoundException(Long id) {
        super("Lipid profile examination not found with id: " + id);
    }
}