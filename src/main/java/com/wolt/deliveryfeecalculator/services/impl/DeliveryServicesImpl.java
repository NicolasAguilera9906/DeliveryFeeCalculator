package com.wolt.deliveryfeecalculator.services.impl;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorException;
import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorServicesException;
import com.wolt.deliveryfeecalculator.model.Delivery;
import com.wolt.deliveryfeecalculator.services.CurrencyConverterServices;
import com.wolt.deliveryfeecalculator.services.DeliveryServices;
import com.wolt.deliveryfeecalculator.services.FeeServices;
import com.wolt.deliveryfeecalculator.services.TimeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Delivery Services implemented methods for DeliveryFeeCalculator App
 *
 * @author Nicolás Aguilera Contreras
 */
@Service
public class DeliveryServicesImpl implements DeliveryServices {

    @Autowired
    private FeeServices feeServices;

    @Autowired
    private CurrencyConverterServices currencyConverterServices;

    @Autowired
    private TimeServices timeServices;

    /**
     * Gets the fee from a Delivery
     *
     * @param delivery delivery for which the fee will be calculated
     * @return the total delivery fee
     * @throws DeliveryFeeCalculatorException when something fails
     */
    @Override
    public int getDeliveryFee(Delivery delivery) throws DeliveryFeeCalculatorException {
        int fees = 0;
        double cartValue = currencyConverterServices.convertCentsToEuros(delivery.getCartValue());
        fees = calculateDeliveryFee(delivery);
        if(cartValue >= 100){
            fees = 0;
        }
        return fees;
    }

    /**
     * Calculate the delivery surcharge due to the cart price
     *
     * @param delivery delivery whose surcharge will be calculated
     * @return the delivery surcharge due to the cart price
     * @throws DeliveryFeeCalculatorException when something fails
     */
    @Override
    public double calculateSurchargeByCartPrice(Delivery delivery) throws DeliveryFeeCalculatorException {
        double cartValue = currencyConverterServices.convertCentsToEuros(delivery.getCartValue());
        double surcharge = 0;
        try {
            surcharge = feeServices.calculateSurchargeByCartPrice(cartValue);
        } catch (DeliveryFeeCalculatorServicesException e) {
            throw new DeliveryFeeCalculatorException(e.getMessage(), e, e.getStatus());
        }
        return surcharge;
    }

    /**
     * Calculate the delivery fee according to the distance it has to travel.
     *
     * @param delivery delivery for which the fee will be calculated
     * @return the delivery fee according to the distance it has to travel
     * @throws DeliveryFeeCalculatorException when something fails
     */
    @Override
    public double calculateFeeByDistance(Delivery delivery) throws DeliveryFeeCalculatorException {
        double fee = 0;
        try {
            fee = feeServices.calculateFeeByDistance(delivery.getDeliveryDistance());
        } catch (DeliveryFeeCalculatorServicesException e) {
            throw new DeliveryFeeCalculatorException(e.getMessage(), e, e.getStatus());
        }
        return fee;
    }

    /**
     * Calculate the delivery surcharge due to the number of items
     *
     * @param delivery delivery whose surcharge will be calculated
     * @return the delivery surcharge due to the number of items
     * @throws DeliveryFeeCalculatorException when something fails
     */
    @Override
    public double calculateSurchargeByNumberOfItems(Delivery delivery) throws DeliveryFeeCalculatorException {
        double surcharge = 0;
        try {
            surcharge = feeServices.calculateSurchargeByNumberOfItems(delivery.getNumberOfItems());
        } catch (DeliveryFeeCalculatorServicesException e) {
            throw new DeliveryFeeCalculatorException(e.getMessage(), e, e.getStatus());
        }
        return surcharge;
    }

    private int calculateDeliveryFee(Delivery delivery) throws DeliveryFeeCalculatorException {
        double fees = 0;
        String stringDeliveryTime = delivery.getTime();
        Date deliveryTime = convertStringToDate(stringDeliveryTime);
        fees += calculateSurchargeByCartPrice(delivery);
        fees += calculateFeeByDistance(delivery);
        fees += calculateSurchargeByNumberOfItems(delivery);
        if (isADateOfTheWeek(6,deliveryTime) && isBetweenTwoHours("15:00:00","19:00:00", delivery.getTime())) {
            fees = fees * 1.1;
        }
        if(fees > 15){
            DeliveryFeeCalculatorServicesException dFCServicesException =  new DeliveryFeeCalculatorServicesException("The delivery fee can never be more than 15€, including possible surcharges",HttpStatus.INTERNAL_SERVER_ERROR);
            throw new DeliveryFeeCalculatorException(dFCServicesException.getMessage() , dFCServicesException , dFCServicesException.getStatus());
        }
        return currencyConverterServices.convertEurosToCents(fees);
    }

    private Date convertStringToDate(String stringDeliveryTime) throws DeliveryFeeCalculatorException {
        try {
            return timeServices.convertStringToDate(stringDeliveryTime);
        } catch (DeliveryFeeCalculatorServicesException e) {
            throw new DeliveryFeeCalculatorException(e.getMessage(),e, e.getStatus());
        }
    }

    private boolean isADateOfTheWeek(Integer numberOfDay,Date date) throws DeliveryFeeCalculatorException {
        return timeServices.isADateOfTheWeek(numberOfDay,date);
    }

    private boolean isBetweenTwoHours(String time1, String time2, String stringDate) throws DeliveryFeeCalculatorException {
        try {
            return timeServices.isBetweenTwoTimes(time1,time2,stringDate);
        } catch (DeliveryFeeCalculatorServicesException e) {
            throw new DeliveryFeeCalculatorException(e.getMessage(),e, e.getStatus());
        }
    }



}
