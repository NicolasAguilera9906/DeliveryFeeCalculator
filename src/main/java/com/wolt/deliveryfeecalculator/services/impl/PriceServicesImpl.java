package com.wolt.deliveryfeecalculator.services.impl;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorServicesException;
import com.wolt.deliveryfeecalculator.services.CurrencyConverterServices;
import com.wolt.deliveryfeecalculator.services.PriceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PriceServicesImpl implements PriceServices {

    @Autowired
    private CurrencyConverterServices currencyConverterServices;

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


    @Override
    public double calculateSurchargeByNumberOfItems(int numberOfItems) throws DeliveryFeeCalculatorServicesException {
        if(numberOfItems<0){
            throw new DeliveryFeeCalculatorServicesException("The number of items cannot be less than 0",HttpStatus.BAD_REQUEST);
        }
        double extraPaymentInEuros = currencyConverterServices.convertCentsToEuros(50);
        double surcharge = (numberOfItems >=5) ? (numberOfItems-4)*extraPaymentInEuros : 0 ;
        return surcharge;
    }

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
