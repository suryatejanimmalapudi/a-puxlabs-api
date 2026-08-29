package com.apuxlabs.apuxlabs_api.exception;

public class LiverFunctionTestExaminationNotFoundException
        extends RuntimeException {

    public LiverFunctionTestExaminationNotFoundException(Long id) {
        super("Liver function test examination not found with id: " + id);
    }
}