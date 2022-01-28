package com.wolt.deliveryfeecalculator.services;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorException;
import com.wolt.deliveryfeecalculator.model.Delivery;

/**
 * Delivery Services methods for DeliveryFeeCalculator App
 *
 * @author Nicolás Aguilera Contreras
 */
public interface DeliveryServices {

    /**
     * Gets the fee from a Delivery
     *
     * @param delivery delivery for which the fee will be calculated
     * @return the total delivery fee
     * @throws DeliveryFeeCalculatorException when something fails
     */
    int getDeliveryFee(Delivery delivery) throws DeliveryFeeCalculatorException;

    /**
     * Calculate the delivery surcharge due to the cart price
     *
     * @param delivery delivery whose surcharge will be calculated
     * @return the delivery surcharge due to the cart price
     * @throws DeliveryFeeCalculatorException when something fails
     */
    double calculateSurchargeByCartPrice(Delivery delivery) throws DeliveryFeeCalculatorException;

    /**
     * Calculate the delivery fee according to the distance it has to travel.
     *
     * @param delivery delivery for which the fee will be calculated
     * @return the delivery fee according to the distance it has to travel
     * @throws DeliveryFeeCalculatorException when something fails
     */
    double calculateFeeByDistance(Delivery delivery) throws DeliveryFeeCalculatorException;

    /**
     * Calculate the delivery surcharge due to the number of items
     *
     * @param delivery delivery whose surcharge will be calculated
     * @return the delivery surcharge due to the number of items
     * @throws DeliveryFeeCalculatorException when something fails
     */
    double calculateSurchargeByNumberOfItems(Delivery delivery) throws DeliveryFeeCalculatorException;
}
