package com.wolt.deliveryfeecalculator.services;

/**
 * Currency Converter methods for DeliveryFeeCalculator App
 *
 * @author Nicolás Aguilera Contreras
 */
public interface CurrencyConverterServices {

    /**
     * Convert euros to cents
     *
     * @param euros euros to be converted
     * @return euros converted to cents
     */
    public int convertEurosToCents(double euros);

    /**
     * Convert cents to euros
     *
     * @param cents cents to be converted
     * @return cents converted to euros
     */
    public double convertCentsToEuros(int cents);



}
