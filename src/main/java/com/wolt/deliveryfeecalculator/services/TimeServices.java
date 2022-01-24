package com.wolt.deliveryfeecalculator.services;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorServicesException;

import java.util.Date;

public interface TimeServices {

    public Date convertStringToDate(String stringDate) throws DeliveryFeeCalculatorServicesException;

    public boolean isADateOfTheWeek(int desiredDate, Date date);

    public boolean isBetweenTwoHours(String hour1, String hour2, String date) throws DeliveryFeeCalculatorServicesException;
}
