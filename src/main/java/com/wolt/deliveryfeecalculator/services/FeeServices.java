package com.wolt.deliveryfeecalculator.services;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorServicesException;

/**
 * Fee services methods for DeliveryFeeCalculator App
 *
 * @author Nicolás Aguilera Contreras
 */
public interface FeeServices {

    /**
     * Calculate the surcharge depending on the value of the cart
     * If the cart value is less than 10 €, a small order surcharge is added to the delivery price
     * The surcharge is the difference between the cart value and 10 €
     *
     * @param cartValue The value of the cart in euros
     * @return additional surcharge depending on the value of the cart
     * @throws DeliveryFeeCalculatorServicesException when the cart value is less than 0
     */
    public double calculateSurchargeByCartPrice(double cartValue) throws DeliveryFeeCalculatorServicesException;

    /**
     * Calculate the surcharge depending on the number of Items
     * If the number of items is five or more, an additional 50 cent surcharge is added for each item above four
     *
     * @param numberOfItems The number of items in the delivery
     * @return additional surcharge depending on the number of items
     * @throws DeliveryFeeCalculatorServicesException when the number of items is less than 0
     */
    public double calculateSurchargeByNumberOfItems(int numberOfItems) throws DeliveryFeeCalculatorServicesException;

    /**
     * Calculate the surcharge depending on a distance
     * The fee for the first 1000 meters (=1km) is 2€.
     * If distance is longer than that, 1€ is added for every additional 500 meters.
     * Even if the distance would be shorter than 500 meters, the minimum fee is always 1€.
     *
     * @param distance the distance over which the fee is to be calculated
     * @return the surcharge depending on a distance
     * @throws DeliveryFeeCalculatorServicesException when something fails
     */
    public double calculateFeeByDistance(int distance) throws DeliveryFeeCalculatorServicesException;

}
