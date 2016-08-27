package br.com.mytasks.controller.activity.fragments.dialogs;


import java.util.Calendar;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
/**
 * A simple {@link Fragment} subclass.
 */
public class DateTimeDialogFragement extends DialogFragment implements OnTimeChangedListener, OnDateChangedListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int monthOfYear = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        DateTimeDialog myDialog = new DateTimeDialog(getActivity());
        myDialog.setTimeListener(this);
        myDialog.setDateListener(year, monthOfYear, dayOfMonth, this);
        return myDialog;
    }


    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub

    }


}
