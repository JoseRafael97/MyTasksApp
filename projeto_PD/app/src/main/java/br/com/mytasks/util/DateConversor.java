package br.com.mytasks.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.mytasks.exception.InvalidDateException;


public class DateConversor {


    public static Date stringToDateConversor(String data) throws InvalidDateException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        sdf.setLenient(false);
        try {
            date = sdf.parse(data);
            //if (date.before(new Date())) {
              //  throw new InvalidDateException("Invalid date, make sure you enter the correct");
            //}
        } catch (ParseException e) {
            throw new InvalidDateException("Invalid date.");
        }
        return date;
    }

    public static Date stringToDateAndHourConversor(String data) throws InvalidDateException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date;
        sdf.setLenient(false);
        try {
            date = sdf.parse(data);
        } catch (ParseException e) {
            throw new InvalidDateException("Invalid date.");
        }
        return date;
    }

    public static String dateAndHourToStringConversor(Date date) {
        if (date != null) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.format(date);
        }
        return "";
    }

    public static String dateToStringConversor(Date date) {
        if (date != null) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(date);
        }
        return "";
    }

    public static String hourStringConversor(Date date){
        if (date != null){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(date);
        }
        return "";
    }

    public static Date stringToHourConversor(String d){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date;
        sdf.setLenient(false);
        try {
            date = sdf.parse(d);
            return date;
        } catch (ParseException ignored) {
        }
        return  null;
    }

}
