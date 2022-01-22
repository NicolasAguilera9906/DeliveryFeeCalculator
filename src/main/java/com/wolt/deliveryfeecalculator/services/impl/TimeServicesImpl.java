package com.wolt.deliveryfeecalculator.services.impl;

import com.wolt.deliveryfeecalculator.services.TimeServices;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

@Service
public class TimeServicesImpl implements TimeServices {

    @Override
    public Date convertStringToDate(String stringDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = simpleDateFormat.parse( stringDate );
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
    public String extractTimeFromADate(Date date) {
        String hour = String.valueOf(date.getHours());
        String min = String.valueOf(date.getMinutes());
        String secs = String.valueOf(date.getSeconds());
        return hour+":"+min+":"+secs;
    }
    

    @Override
    public boolean isBetweenTwoHours(String hour1, String hour2, String date) throws ParseException {
        Date date1 = new SimpleDateFormat("HH:mm:ss").parse(hour1);
        Date date2 = new SimpleDateFormat("HH:mm:ss").parse(hour2);
        Date time =  new SimpleDateFormat("HH:mm:ss").parse(date);
        return (time.after(date2) || time.before(date1)) ? false:true;
    }
}
