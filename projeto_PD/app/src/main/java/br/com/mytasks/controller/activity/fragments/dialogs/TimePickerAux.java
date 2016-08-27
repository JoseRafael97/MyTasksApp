package br.com.mytasks.controller.activity.fragments.dialogs;


import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;
import java.util.Calendar;


public class TimePickerAux {

    public TimePickerAux(){

    }

    public static void timePickerDialog(final EditText editText, Context context) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(android.widget.TimePicker view, int hourOfDay,
                                          int minute) {
                        editText.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
        tpd.show();
    }
}
