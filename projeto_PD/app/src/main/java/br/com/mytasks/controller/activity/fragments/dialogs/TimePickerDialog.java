package br.com.mytasks.controller.activity.fragments.dialogs;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import br.com.mytasks.R;


public class TimePickerDialog extends DialogFragment {

    private Callback callback;
    private TimePicker timePicker;
    private Button btCancel;
    private Button btDone;
    private String time;

    public TimePickerDialog() {
        // Required empty public constructor
    }

    public static void show(FragmentManager fm, Callback callback) {
        FragmentTransaction ft = fm.beginTransaction();

        ft.addToBackStack(null);
        TimePickerDialog frag = new TimePickerDialog();
        frag.callback = callback;
        frag.show(ft, "edit");
    }


    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_time_picker_dialog, container, false);
        timePicker = (TimePicker) v.findViewById(R.id.time_picker_alternative);
        btCancel = (Button) v.findViewById(R.id.bt_cancel_time_dialog);
        btDone = (Button) v.findViewById(R.id.bt_done_time_dialog);

        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay+":"+minute;
                if (callback != null)
                    callback.SetHour(time);
            }
        });

        btCancel.setOnClickListener(btListener);
        btDone.setOnClickListener(btListener);
        return v;
    }





    public interface Callback {
        void SetHour(String hour);
    }

    private View.OnClickListener btListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bt_done_time_dialog:
                    if (callback != null) {
                        callback.SetHour(time);
                    }
                    dismiss();
                case R.id.bt_cancel_time_dialog:
                    dismiss();
            }
        }
    };


}
