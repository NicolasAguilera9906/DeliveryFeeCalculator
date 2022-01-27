package com.wolt.deliveryfeecalculator.services;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorServicesException;

public interface FeeServices {

    public double calculateSurchargeByCartPrice(double cartValue) throws DeliveryFeeCalculatorServicesException;

    public double calculateFeeByDistance(int distance) throws DeliveryFeeCalculatorServicesException;

    public double calculateSurchargeByNumberOfItems(int numberOfItems) throws DeliveryFeeCalculatorServicesException;

}
