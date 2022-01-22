package com.wolt.deliveryfeecalculator.services;

import java.text.ParseException;
import java.util.Date;

public interface TimeServices {

    public Date convertStringToDate(String stringDate) throws ParseException;

    public boolean isADateOfTheWeek(int desiredDate, Date date);

    public String extractTimeFromADate(Date date);

    public boolean isBetweenTwoHours(String hour1, String hour2, String date) throws ParseException;
}
