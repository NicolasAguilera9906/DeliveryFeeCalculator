package com.wolt.deliveryfeecalculator.services;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorServicesException;

import java.util.Date;

/**
 * Time services methods for DeliveryFeeCalculator App
 *
 * @author Nicolás Aguilera Contreras
 */
public interface TimeServices {

    /**
     * Convert String to Date
     * The String must be given in the following format : yyyy-MM-dd'T'HH:mm:ss'Z'
     *
     * @param stringDate the string to be converted to date
     * @return string converted to date
     * @throws DeliveryFeeCalculatorServicesException when something fails
     */
    public Date convertStringToDate(String stringDate) throws DeliveryFeeCalculatorServicesException;

    /**
     * Check if a date is a specific day of the week
     *
     * @param desiredDate the desired day of the week to be the date
     *        1 if it is Sunday
     *        2 if it is Monday
     *        3 if it is Tuesday
     *        4 if it is Wednesday
     *        5 if it is Thursday
     *        6 if it is Friday
     *        7 if it is Saturday
     * @param date the date to be verified as a specific day of the week
     * @return true if the date is the specific desired day of the week
     *         false if the date is not the specific desired day of the week
     */
    public boolean isADateOfTheWeek(int desiredDate, Date date);

    /**
     * Check if the time of a date is between two other times.
     * @param timeBefore Time before to be compared
     * @param timeAfter Time after to be compared
     * @param date The date to be compared with the two times
     * @return true if the time of the date is between the time before and the time after
     *         false if the time of the date is not between the time before and the time after
     * @throws DeliveryFeeCalculatorServicesException when something fails
     */
    boolean isBetweenTwoTimes(String timeBefore, String timeAfter, String date) throws DeliveryFeeCalculatorServicesException;
}
