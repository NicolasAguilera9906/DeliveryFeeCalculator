package com.wolt.deliveryfeecalculator.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception Handler For Envirify App.
 *
 * @author Error 418
 */
@RestControllerAdvice
public class DeliveryFeeCalculationExceptionHandler {

    /**
     * Method that handles the exceptions of the Envirify Rest Controllers.
     *
     * @param e Exception Caused.
     * @return A Response Entity with teh Error Message.
     */
    @ExceptionHandler(DeliveryFeeCalculatorServicesException.class)
    private ResponseEntity<?> exceptionHandler(DeliveryFeeCalculatorException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}