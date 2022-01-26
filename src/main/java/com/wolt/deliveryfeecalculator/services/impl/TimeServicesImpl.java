package com.wolt.deliveryfeecalculator.services.impl;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorServicesException;
import com.wolt.deliveryfeecalculator.services.TimeServices;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.text.*;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Service
public class TimeServicesImpl implements TimeServices {

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

    @Override
    public boolean isADateOfTheWeek(int desiredDate, Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == desiredDate) ? true:false;
    }


    @Override
    public boolean isBetweenTwoHours(String hour1, String hour2, String date) throws DeliveryFeeCalculatorServicesException {
        Date date1 = parseStringHourToDate(hour1);
        Date date2 = parseStringHourToDate(hour2);
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
