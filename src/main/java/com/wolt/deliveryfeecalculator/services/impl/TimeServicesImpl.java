package com.wolt.deliveryfeecalculator.services.impl;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorServicesException;
import com.wolt.deliveryfeecalculator.services.TimeServices;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Time services implemented methods for DeliveryFeeCalculator App
 *
 * @author Nicolás Aguilera Contreras
 */
@Service
public class TimeServicesImpl implements TimeServices {

    /**
     * Convert String to Date
     * The String must be given in the following format : yyyy-MM-dd'T'HH:mm:ss'Z'
     *
     * @param stringDate the string to be converted to date
     * @return string converted to date
     * @throws DeliveryFeeCalculatorServicesException when something fails
     */
    @Override
    public Date convertStringToDate(String stringDate) throws DeliveryFeeCalculatorServicesException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse( stringDate );
        } catch (ParseException e) {
            throw new DeliveryFeeCalculatorServicesException("Date is in the wrong format", HttpStatus.BAD_REQUEST);
        }
        return date;
    }

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
    @Override
    public boolean isADateOfTheWeek(int desiredDate, Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == desiredDate) ? true:false;
    }


    /**
     * Check if the time of a date is between two other times.
     * @param timeBefore Time before to be compared
     * @param timeAfter Time after to be compared
     * @param date The date to be compared with the two times
     * @return true if the time of the date is between the time before and the time after
     *         false if the time of the date is not between the time before and the time after
     * @throws DeliveryFeeCalculatorServicesException when something fails
     */
    @Override
    public boolean isBetweenTwoTimes(String timeBefore, String timeAfter, String date) throws DeliveryFeeCalculatorServicesException {
        Date date1 = parseStringHourToDate(timeBefore);
        Date date2 = parseStringHourToDate(timeAfter);
        String stringDateOnlyWithTime = extractHourFromDate(date);
        Date dateOnlyWithTime = parseStringHourToDate(stringDateOnlyWithTime);
        return (dateOnlyWithTime.after(date2) || dateOnlyWithTime.before(date1)) ? false:true;
    }

    private String extractHourFromDate(String date) throws DeliveryFeeCalculatorServicesException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String hourFromDate = null;
        try {
            hourFromDate = dateFormat.format(convertStringToDate(date));
        } catch (DeliveryFeeCalculatorServicesException e) {
            throw new DeliveryFeeCalculatorServicesException("Date is in the wrong format",HttpStatus.BAD_REQUEST);
        }
        return hourFromDate;

    }

    private Date parseStringHourToDate(String hour) throws DeliveryFeeCalculatorServicesException {
        try {
            return new SimpleDateFormat("HH:mm:ss").parse(hour);
        } catch (ParseException e) {
            throw new DeliveryFeeCalculatorServicesException("Date is in the wrong format",HttpStatus.BAD_REQUEST);
        }
    }
}
