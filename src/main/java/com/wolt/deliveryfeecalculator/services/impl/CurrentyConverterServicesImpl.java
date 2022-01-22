package com.wolt.deliveryfeecalculator.services.impl;

import com.wolt.deliveryfeecalculator.services.CurrencyConverterServices;
import org.springframework.stereotype.Service;

@Service
public class CurrentyConverterServicesImpl implements CurrencyConverterServices {

    @Override
    public int convertEurosToCents(double euros) {
        double divider = 100;
        double cents = euros*divider;
        return (int) cents;
    }

    @Override
    public double convertCentsToEuros(int cents) {
        double divider = 100;
        double euros = cents/divider;
        return euros;
    }
}
