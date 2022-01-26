package com.wolt.deliveryfeecalculator.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Personalized Exception For Envirify App.
 *
 * @author Error 418
 */
public class DeliveryFeeCalculatorServicesException extends Exception {

    private final HttpStatus status;

    /**
     * Constructor For EnvirifyException.
     *
     * @param message The Error Message Of The Exception.
     * @param cause   The Cause Of The Error.
     * @param status  The HTTP Status Code Of The Error.
     */
    public DeliveryFeeCalculatorServicesException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    /**
     * Constructor For EnvirifyException.
     *
     * @param message The Error Message Of The Exception.
     * @param status  The HTTP Status Code Of The Error.
     */
    public DeliveryFeeCalculatorServicesException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }


    /**
     * Returns the HTTP Status Code Of The Exception.
     *
     * @return The HTTP Status Code Of The Exception.
     */
    public HttpStatus getStatus() {
        return status;
    }
}