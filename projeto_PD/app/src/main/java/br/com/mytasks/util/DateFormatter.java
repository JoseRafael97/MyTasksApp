package br.com.mytasks.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {


    public static String dateFormatter(String date) {
        String dateFormatted = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1 = sdf.parse(date);
            dateFormatted = sdf.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormatted;
    }

    public static String hourFormatter(String hour) {
        String hourFormatted = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date hour1 = sdf.parse(hour);
            hourFormatted = sdf.format(hour1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hourFormatted;
    }


}
