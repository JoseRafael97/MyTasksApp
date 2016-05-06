package br.com.mytasks.validation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidation {

    public static boolean equalsDateForDay(Date date1, Date date2){


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateFormated1 =  sdf.format(date1);
        String dateFormated2 =  sdf.format(date2);

        return dateFormated1.equals(dateFormated2);
    }
}
