package com.wolt.deliveryfeecalculator.services.impl;

import com.wolt.deliveryfeecalculator.model.Delivery;
import com.wolt.deliveryfeecalculator.services.CurrencyConverterServices;
import com.wolt.deliveryfeecalculator.services.DeliveryServices;
import com.wolt.deliveryfeecalculator.services.PriceServices;
import com.wolt.deliveryfeecalculator.services.TimeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
    public int getDeliveryPrice(Delivery delivery) throws Exception {
        double fees = 0;
        double cartValue = currencyConverterServices.convertCentsToEuros(delivery.getCartValue());
        fees += calculateSurchargeByCartPrice(cartValue);
        fees += calculateFeeByDistance(delivery.getDeliveryDistance());
        fees += calculateSurchargeByNumberOfItems(delivery.getNumberOfItems());
        if (isFridayBetween15and19(delivery.getTime())) {
            fees = fees * 1.1;
        }
        if(fees > 15){
            throw new Exception("The fee can not be greater than 15 euros");
        }
        if(cartValue >= 100){
            fees = 0;
        }
        return currencyConverterServices.convertEurosToCents(fees);
    }

    private boolean isFridayBetween15and19(String time) throws ParseException {
        Date date = timeServices.convertStringToDate(time);
        String dateTime = timeServices.extractTimeFromADate(date);
        boolean isFriday = timeServices.isADateOfTheWeek(6,date);
        boolean isBetween15and19 = timeServices.isBetweenTwoHours("15:00:00","19:00:00",dateTime);
        return (isFriday && isBetween15and19) ? true:false;
    }

    private double calculateSurchargeByNumberOfItems(int numberOfItems) {
        double surcharge = priceServices.calculateSurchargeByNumberOfItems(numberOfItems);
        return surcharge;
    }

    private double calculateFeeByDistance(int deliveryDistance) throws Exception {
        double fee = priceServices.calculateFeeByDistance(deliveryDistance);
        return fee;
    }

    private double calculateSurchargeByCartPrice(double cartValue){
        double surcharge = priceServices.calculateSurchargeByCartPrice(cartValue);
        return surcharge;
    }


}
