package com.example.kristine.eventastic.JavaClasses;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

//this class changes the format of a date
public class ChangeDateFormat {


    public static String changeIntoString(int iDate){
        String date = ""+iDate;
        String day= ""+date.charAt(6)+date.charAt(7);
        String month=""+date.charAt(4)+date.charAt(5);
        String year =""+date.charAt(0)+date.charAt(1)+date.charAt(2)+date.charAt(3);
        return ""+day+"."+month+"."+year;
    }

    private static Date getDateFromString(String dateString){
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {

            return new Date();
        }
    }

    public static int changeFirstIntoDateFormatAfterwardsIntoInteger(String date){
        Date dueDate = getDateFromString(date);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dueDate);
        String month;
        String day;
        if (cal.get(Calendar.MONTH) + 1 < 10) {
            month = "" + 0 + (cal.get(Calendar.MONTH) + 1);
        } else {
            month = "" + (cal.get(Calendar.MONTH) + 1);
        }
        if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
            day = "" + 0 + cal.get(Calendar.DAY_OF_MONTH);
        } else {
            day = "" + cal.get(Calendar.DAY_OF_MONTH);
        }
        return Integer.parseInt("" + cal.get(Calendar.YEAR) + month + day);

    }
}
