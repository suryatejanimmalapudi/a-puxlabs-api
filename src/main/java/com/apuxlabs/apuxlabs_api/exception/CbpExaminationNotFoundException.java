package com.apuxlabs.apuxlabs_api.exception;

public class CbpExaminationNotFoundException extends RuntimeException {

    public CbpExaminationNotFoundException(Long id) {
        super("CBP examination not found with id: " + id);
    }
}