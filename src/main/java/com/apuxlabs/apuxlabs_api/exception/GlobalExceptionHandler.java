package com.apuxlabs.apuxlabs_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles requests for registrations that do not exist.
     *
     * @param exception registration-not-found exception
     * @param request current HTTP request
     * @return standardized 404 error response
     */
    @ExceptionHandler(RegistrationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRegistrationNotFound(
            RegistrationNotFoundException exception,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    /**
     * Handles requests for physical examinations that do not exist.
     *
     * @param exception physical-examination-not-found exception
     * @param request current HTTP request
     * @return standardized 404 error response
     */
    @ExceptionHandler(PhysicalExaminationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePhysicalExaminationNotFound(
            PhysicalExaminationNotFoundException exception,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }
    /**
     * Handles requests for eye examinations that do not exist.
     *
     * @param exception eye-examination-not-found exception
     * @param request current HTTP request
     * @return standardized 404 error response
     */
    @ExceptionHandler(EyeExaminationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEyeExaminationNotFound(
            EyeExaminationNotFoundException exception,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    /**
     * Handles requests for audiograms that do not exist.
     *
     * @param exception audiogram-not-found exception
     * @param request current HTTP request
     * @return standardized 404 error response
     */
    @ExceptionHandler(AudiogramNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAudiogramNotFound(
            AudiogramNotFoundException exception,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }
}