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

    /**
     * Handles the case when an ECG examination
     * cannot be found by its ID.
     *
     * @param exception ECG examination not found exception
     * @param request current HTTP request
     * @return structured 404 Not Found error response
     */
//    @ExceptionHandler(EcgExaminationNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleEcgExaminationNotFound(
//            EcgExaminationNotFoundException exception,
//            HttpServletRequest request) {
//
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.NOT_FOUND.value(),
//                HttpStatus.NOT_FOUND.getReasonPhrase(),
//                exception.getMessage(),
//                request.getRequestURI()
//        );
//
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(errorResponse);
//    }


    /**
     * Handles the case when a CBP examination
     * cannot be found by its ID.
     *
     * @param exception CBP examination not found exception
     * @param request current HTTP request
     * @return structured 404 Not Found error response
     */
    @ExceptionHandler(CbpExaminationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCbpExaminationNotFound(
            CbpExaminationNotFoundException exception,
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
     * Handles the case when a lipid profile examination
     * cannot be found by its ID.
     *
     * @param exception lipid profile examination not found exception
     * @param request current HTTP request
     * @return structured 404 Not Found error response
     */
    @ExceptionHandler(LipidProfileExaminationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLipidProfileExaminationNotFound(
            LipidProfileExaminationNotFoundException exception,
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
     * Handles the case when a liver function test examination
     * cannot be found by its ID.
     *
     * @param exception liver function test examination not found exception
     * @param request current HTTP request
     * @return structured 404 Not Found error response
     */
    @ExceptionHandler(LiverFunctionTestExaminationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLiverFunctionTestExaminationNotFound(
            LiverFunctionTestExaminationNotFoundException exception,
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
     * Handles the case when a urine routine examination
     * cannot be found by its ID.
     *
     * @param exception urine routine examination not found exception
     * @param request current HTTP request
     * @return structured 404 Not Found error response
     */
    @ExceptionHandler(UrineRoutineExaminationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUrineRoutineExaminationNotFound(
            UrineRoutineExaminationNotFoundException exception,
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
     * Handles requests for blood glucose examinations that do not exist.
     *
     * @param exception blood glucose examination not-found exception
     * @param request current HTTP request
     * @return standardized 404 error response
     */
    @ExceptionHandler(BloodGlucoseExaminationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBloodGlucoseExaminationNotFound(
            BloodGlucoseExaminationNotFoundException exception,
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
     * Handles requests for renal function examinations that do not exist.
     *
     * @param exception renal function examination not-found exception
     * @param request current HTTP request
     * @return standardized 404 error response
     */
    @ExceptionHandler(RenalFunctionExaminationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRenalFunctionExaminationNotFound(
            RenalFunctionExaminationNotFoundException exception,
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
     * Handles requests for serum cholinesterase examinations
     * that do not exist.
     *
     * @param exception serum cholinesterase examination not-found exception
     * @param request current HTTP request
     * @return standardized 404 error response
     */
    @ExceptionHandler(SerumCholinesteraseExaminationNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleSerumCholinesteraseExaminationNotFound(
            SerumCholinesteraseExaminationNotFoundException exception,
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