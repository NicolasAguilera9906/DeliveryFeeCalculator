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

    double calculateSurchargeByCartPrice(Delivery delivery) throws DeliveryFeeCalculatorException;

    double calculateFeeByDistance(Delivery delivery) throws DeliveryFeeCalculatorException;

    double calculateSurchargeByNumberOfItems(Delivery delivery) throws DeliveryFeeCalculatorException;
}
