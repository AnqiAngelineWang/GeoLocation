package com.pnc.assessment.geolocapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.pnc.assessment.geolocapi.model.GeneralError;

@EnableWebMvc
@RestControllerAdvice
public class Handler {

    private ResponseEntity<GeneralError> buildError(HttpStatus status, String message) {
        GeneralError errorResponse = new GeneralError();
        errorResponse.setMessage(message);
        return ResponseEntity
            .status(status)
            .body(errorResponse);
    }

    @ExceptionHandler(GeolocException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<GeneralError> handleGeolocException(GeolocException e) {
        return buildError(HttpStatus.FORBIDDEN, "Fordibben geolocalisation");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GeneralError> handleMissingParameter(MethodArgumentNotValidException e) {
        return buildError(HttpStatus.BAD_REQUEST, "Missing or Invalid parameter: " + e.getBindingResult().getFieldErrors().get(0).getField());
    }
}
