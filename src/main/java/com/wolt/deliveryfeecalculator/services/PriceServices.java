package com.wolt.deliveryfeecalculator.services;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorServicesException;

public interface PriceServices {

    public double calculateSurchargeByCartPrice(double cartValue) throws DeliveryFeeCalculatorServicesException;

    public double calculateFeeByDistance(int distance) throws DeliveryFeeCalculatorServicesException;

    public double calculateSurchargeByNumberOfItems(int numberOfItems) throws DeliveryFeeCalculatorServicesException;

}
