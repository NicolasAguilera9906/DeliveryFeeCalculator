package com.wolt.deliveryfeecalculator.services.impl;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorServicesException;
import com.wolt.deliveryfeecalculator.services.CurrencyConverterServices;
import com.wolt.deliveryfeecalculator.services.FeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Fee services implemented methods for DeliveryFeeCalculator App
 *
 * @author Nicolás Aguilera Contreras
 */
@Service
public class FeeServicesImpl implements FeeServices {

    @Autowired
    private CurrencyConverterServices currencyConverterServices;

    /**
     * Calculate the surcharge depending on the value of the cart
     * If the cart value is less than 10 €, a small order surcharge is added to the delivery price
     *
     * @param cartValue The value of the cart in euros
     * @return additional surcharge depending on the value of the cart
     * @throws DeliveryFeeCalculatorServicesException when the cart value is less than 0
     */
    @Override
    public double calculateSurchargeByCartPrice(double cartValue) throws DeliveryFeeCalculatorServicesException {
        if(cartValue < 0){
            throw new DeliveryFeeCalculatorServicesException("The cart value cannot be less than 0", HttpStatus.BAD_REQUEST);
        }
        double surcharge = 0;
        if (cartValue < 10) {
            double subtrahend = 10;
            double difference = Math.abs(cartValue - subtrahend);
            surcharge = difference;
        }
        surcharge = Math.round(surcharge * 100.0) / 100.0;
        return surcharge;
    }


    /**
     * Calculate the surcharge depending on the number of Items
     * If the number of items is five or more, an additional 50 cent surcharge is added for each item above four
     *
     * @param numberOfItems The number of items in the delivery
     * @return additional surcharge depending on the number of items
     * @throws DeliveryFeeCalculatorServicesException when the number of items is less than 0
     */
    @Override
    public double calculateSurchargeByNumberOfItems(int numberOfItems) throws DeliveryFeeCalculatorServicesException {
        if(numberOfItems<0){
            throw new DeliveryFeeCalculatorServicesException("The number of items cannot be less than 0",HttpStatus.BAD_REQUEST);
        }
        double extraPaymentInEuros = currencyConverterServices.convertCentsToEuros(50);
        double surcharge = (numberOfItems >=5) ? (numberOfItems-4)*extraPaymentInEuros : 0 ;
        return surcharge;
    }

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
    @Override
    public double calculateFeeByDistance(int distance) throws DeliveryFeeCalculatorServicesException {
        double fee = 0;
        if (distance < 0){
            throw new DeliveryFeeCalculatorServicesException("Distance cannot be less than 0",HttpStatus.BAD_REQUEST);
        }
        else if(distance <= 1000){
            fee = 2;
        }
        else if(distance > 1000){
            fee = ((distance % 500 == 0) ? distance / 500 : (distance / 500) + 1);
        }
        return fee;
    }


}
