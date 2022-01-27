package com.wolt.deliveryfeecalculator.services.impl;

import com.wolt.deliveryfeecalculator.services.CurrencyConverterServices;
import org.springframework.stereotype.Service;

/**
 * Currency Converter implemented methods for DeliveryFeeCalculator App
 *
 * @author Nicolás Aguilera Contreras
 */
@Service
public class CurrencyConverterServicesImpl implements CurrencyConverterServices {

    /**
     * Convert euros to cents
     *
     * @param euros euros to be converted
     * @return euros converted to cents
     */
    @Override
    public int convertEurosToCents(double euros) {
        double divider = 100;
        double cents = euros*divider;
        return (int) cents;
    }

    /**
     * Convert cents to euros
     *
     * @param cents cents to be converted
     * @return cents converted to euros
     */
    @Override
    public double convertCentsToEuros(int cents) {
        double divider = 100;
        double euros = cents/divider;
        return euros;
    }
}
