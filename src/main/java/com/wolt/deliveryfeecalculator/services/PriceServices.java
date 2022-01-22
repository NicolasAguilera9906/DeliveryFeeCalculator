package com.wolt.deliveryfeecalculator.services;

public interface PriceServices {

    public double calculateSurchargeByCartPrice(double cartValue);

    public double calculateFeeByDistance(int distance) throws Exception;

    public double calculateSurchargeByNumberOfItems(int numberOfItems);

}
