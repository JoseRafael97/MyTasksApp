package br.com.mytasks.controller.activity.activity;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import java.util.Date;

import br.com.mytasks.R;
import br.com.mytasks.controller.activity.fragments.dialogs.TimePickerDialog;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.service.EventService;
import br.com.mytasks.util.DateConversor;
import br.com.mytasks.util.DateFormatter;

public class NewEventActivity extends BaseActivity {

    private EditText etNameEvent;
    private EditText etInitialDate;
    private EditText etFinalDate;
    private EditText etInitialHour;
    private EditText etFinalHour;
    private EventService eventService;
    private CaldroidFragment dialogCaldroidFragment;
    private Date dateInitial;
    private Date dateFinal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        setUpToolbar();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("New Event");
        }

        eventService = new EventService(getContext());

        etNameEvent = (EditText) findViewById(R.id.et_name_event);
        etInitialDate = (EditText) findViewById(R.id.et_event_initial_date);
        etFinalDate = (EditText)findViewById(R.id.et_event_final_date);
        etInitialHour = (EditText) findViewById(R.id.et_initial_hour);
        etFinalHour = (EditText) findViewById(R.id.et_final_hour);

        etInitialDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", 4, 2016);
                dialogCaldroidFragment.show(getSupportFragmentManager(), "TAG");
                dialogCaldroidFragment.setCaldroidListener(listener);
            }
        });
        etFinalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", 4, 2016);
                dialogCaldroidFragment.show(getSupportFragmentManager(), "TAG");
                dialogCaldroidFragment.setCaldroidListener(listenerFinal);
            }
        });

        etInitialHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                TimePickerAux.timePickerDialog(etPlannedHours, getContext());
                */
                TimePickerDialog.show(getSupportFragmentManager(), new TimePickerDialog.Callback() {
                    @Override
                    public void SetHour(String hour) {
                        etInitialHour.setText(hour);
                    }
                });
            }
        });

        etFinalHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                TimePickerAux.timePickerDialog(etPlannedHours, getContext());
                */
                TimePickerDialog.show(getSupportFragmentManager(), new TimePickerDialog.Callback() {
                    @Override
                    public void SetHour(String hour) {
                        etFinalHour.setText(hour);
                    }
                });
            }
        });
    }

    final CaldroidListener listener = new CaldroidListener() {

        @Override
        public void onSelectDate(Date date, View view) {
            dateInitial = date;
            etInitialDate.setText(DateConversor.dateToStringConversor(date));
            dialogCaldroidFragment.dismiss();
        }


        @Override
        public void onLongClickDate(Date date, View view) {

        }
    };
    final CaldroidListener listenerFinal = new CaldroidListener() {

        @Override
        public void onSelectDate(Date date, View view) {
            dateFinal = date;
            etFinalDate.setText(DateConversor.dateToStringConversor(date));
            dialogCaldroidFragment.dismiss();
        }
        @Override
        public void onLongClickDate(Date date, View view) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel:
                ActivityCompat.finishAfterTransition(getActivity());
                finish();
                return true;
            case R.id.add_check:
                try {
                    String nameEvent = etNameEvent.getText().toString();
                    String initialDate = DateConversor.dateToStringConversor(dateInitial);
                    String initialHour = DateFormatter.hourFormatter(etInitialHour.getText().toString());
                    String finalDate = DateConversor.dateToStringConversor(dateFinal);
                    String finalHour = DateFormatter.hourFormatter(etFinalHour.getText().toString());

                    String iniDate = initialDate + " " + initialHour;
                    String finDate = finalDate + " " + finalHour;

                    eventService.addEvent(nameEvent, iniDate, finDate);
                    Toast.makeText(getContext(), "Event added", Toast.LENGTH_LONG).show();
                    ActivityCompat.finishAfterTransition(getActivity());

                } catch (ActivityManagerException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create, menu);

        return true;
    }
}
