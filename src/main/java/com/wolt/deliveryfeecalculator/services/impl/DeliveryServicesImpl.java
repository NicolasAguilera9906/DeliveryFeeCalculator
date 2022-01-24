package com.wolt.deliveryfeecalculator.services.impl;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorException;
import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorServicesException;
import com.wolt.deliveryfeecalculator.model.Delivery;
import com.wolt.deliveryfeecalculator.services.CurrencyConverterServices;
import com.wolt.deliveryfeecalculator.services.DeliveryServices;
import com.wolt.deliveryfeecalculator.services.PriceServices;
import com.wolt.deliveryfeecalculator.services.TimeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DeliveryServicesImpl implements DeliveryServices {

    @Autowired
    private PriceServices priceServices;

    @Autowired
    private CurrencyConverterServices currencyConverterServices;

    @Autowired
    private TimeServices timeServices;

    @Override
    public int getDeliveryFee(Delivery delivery) throws DeliveryFeeCalculatorException {
        int fees = 0;
        double cartValue = currencyConverterServices.convertCentsToEuros(delivery.getCartValue());
        if(cartValue >= 100){
            fees = 0;
        }
        else {
            fees = calculateDeliveryFee(delivery);
        }
        return fees;
    }

    private int calculateDeliveryFee(Delivery delivery) throws DeliveryFeeCalculatorException {
        double fees = 0;
        String stringDeliveryTime = delivery.getTime();
        Date deliveryTime = convertStringToDate(stringDeliveryTime);
        fees += calculateSurchargeByCartPrice(currencyConverterServices.convertCentsToEuros(delivery.getCartValue()));
        fees += calculateFeeByDistance(delivery.getDeliveryDistance());
        fees += calculateSurchargeByNumberOfItems(delivery.getNumberOfItems());
        if (isADateOfTheWeek(6,deliveryTime) && isBetweenTwoHours("15:00:00","19:00:00", delivery.getTime())) {
            fees = fees * 1.1;
        }
        if(fees > 15){
            DeliveryFeeCalculatorServicesException dFCServicesException =  new DeliveryFeeCalculatorServicesException("The delivery fee can never be more than 15€, including possible surcharges");
            throw new DeliveryFeeCalculatorException(dFCServicesException.getMessage() , dFCServicesException , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return currencyConverterServices.convertEurosToCents(fees);
    }

    private double calculateSurchargeByCartPrice(double cartValue) throws DeliveryFeeCalculatorException {
        double surcharge = 0;
        try {
            surcharge = priceServices.calculateSurchargeByCartPrice(cartValue);
        } catch (DeliveryFeeCalculatorServicesException e) {
            throw new DeliveryFeeCalculatorException(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return surcharge;
    }

    private double calculateFeeByDistance(int deliveryDistance) throws DeliveryFeeCalculatorException {
        double fee = 0;
        try {
            fee = priceServices.calculateFeeByDistance(deliveryDistance);
        } catch (DeliveryFeeCalculatorServicesException e) {
            throw new DeliveryFeeCalculatorException(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return fee;
    }

    private double calculateSurchargeByNumberOfItems(int numberOfItems) throws DeliveryFeeCalculatorException {
        double surcharge = 0;
        try {
            surcharge = priceServices.calculateSurchargeByNumberOfItems(numberOfItems);
        } catch (DeliveryFeeCalculatorServicesException e) {
            throw new DeliveryFeeCalculatorException(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return surcharge;
    }

    private Date convertStringToDate(String stringDeliveryTime) throws DeliveryFeeCalculatorException {
        try {
            return timeServices.convertStringToDate(stringDeliveryTime);
        } catch (DeliveryFeeCalculatorServicesException e) {
            throw new DeliveryFeeCalculatorException(e.getMessage(),e, HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isADateOfTheWeek(Integer numberOfDay,Date date) throws DeliveryFeeCalculatorException {
        return timeServices.isADateOfTheWeek(numberOfDay,date);
    }

    private boolean isBetweenTwoHours(String hour1, String hour2, String stringDate) throws DeliveryFeeCalculatorException {
        try {
            return timeServices.isBetweenTwoHours(hour1,hour2,stringDate);
        } catch (DeliveryFeeCalculatorServicesException e) {
            throw new DeliveryFeeCalculatorException(e.getMessage(),e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
