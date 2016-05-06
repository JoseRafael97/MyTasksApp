package br.com.mytasks.controller.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import java.util.Date;
import java.util.List;

import br.com.mytasks.R;
import br.com.mytasks.controller.activity.adapters.MultiSelectSpinner;
import br.com.mytasks.controller.activity.fragments.dialogs.TimePickerDialog;
import br.com.mytasks.entities.Category;
import br.com.mytasks.entities.Event;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.exception.InvalidArgumentsException;
import br.com.mytasks.exception.InvalidDateException;
import br.com.mytasks.service.ActivityService;
import br.com.mytasks.service.CategoryService;
import br.com.mytasks.service.EventService;
import br.com.mytasks.util.DateConversor;
import br.com.mytasks.util.GetListName;


public class AddNewTaskActivity extends BaseActivity {

    private CategoryService categoryService;
    private ActivityService activityService;
    private Spinner spinner;
    private EditText etName;
    private EditText etDeadline;
    private EditText etRepetitions;
    private EditText etPlannedHours;
    private ImageView btClean;
    private CheckBox checkBox;
    private CaldroidFragment dialogCaldroidFragment;
    private EditText etWorkedHours;
    private ImageView imageClock;
    private TextView textWorkedHours;
    private ImageButton btImageAdd;
    private ImageButton btImageAddEvents;
    private MultiSelectSpinner spinnerEvents;
    private EventService eventService;
    private List<Event> eventList;


    public AddNewTaskActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_task);
        setUpToolbar();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("New Activity");
        }



        etName = (EditText) findViewById(R.id.et_name_activity);
        etDeadline = (EditText) findViewById(R.id.et_activity_deadline);
        etPlannedHours = (EditText) findViewById(R.id.et_activity_planned_hours);
        etRepetitions = (EditText) findViewById(R.id.et_activity_repetitions);
        spinner = (Spinner) findViewById(R.id.spinner);
        btClean = (ImageView) findViewById(R.id.imageButton);
        checkBox = (CheckBox) findViewById(R.id.check_activity);
        etWorkedHours = (EditText) findViewById(R.id.et_activity_worked_hours);
        imageClock = (ImageView) findViewById(R.id.image_clock2);
        textWorkedHours = (TextView) findViewById(R.id.txt_worked_hours);
        btImageAdd = (ImageButton) findViewById(R.id.bt_image_add);
        btImageAddEvents = (ImageButton) findViewById(R.id.bt_image_add_events);
        spinnerEvents = (MultiSelectSpinner) findViewById(R.id.spinner_events);


        textWorkedHours.setVisibility(View.INVISIBLE);
        etWorkedHours.setVisibility(View.INVISIBLE);
        imageClock.setVisibility(View.INVISIBLE);

        btImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewCategoryActivity.class));
            }
        });

        btImageAddEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),NewEventActivity.class));
            }
        });

        etDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", 4, 2016);
                dialogCaldroidFragment.show(getSupportFragmentManager(), "TAG");
                dialogCaldroidFragment.setCaldroidListener(listener);

                /*
                DateTimeDialogFragement newFragment = new DateTimeDialogFragement();
                newFragment.show(getSupportFragmentManager(), "timePicker");
                */

            }
        });

        etPlannedHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                TimePickerAux.timePickerDialog(etPlannedHours, getContext());
                */
                TimePickerDialog.show(getSupportFragmentManager(), new TimePickerDialog.Callback() {
                    @Override
                    public void SetHour(String hour) {
                        etPlannedHours.setText(hour);
                    }
                });
            }
        });


        btClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDeadline.setText("");
            }
        });



    }

    final CaldroidListener listener = new CaldroidListener() {

        @Override
        public void onSelectDate(Date date, View view) {
            etDeadline.setText(DateConversor.dateToStringConversor(date));
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
                    int repetitions = 0;
                    Date plannedHoursTime = null;
                    List<Event> eventList = eventService.listEventName(spinnerEvents.getSelectedStrings());
                    String category = spinner.getSelectedItem().toString();
                    Category category1 = categoryService.findCategoryByName(category);

                    if (etPlannedHours.getText().toString().trim().length() > 0) {
                        plannedHoursTime = DateConversor.stringToHourConversor(etPlannedHours.getText().toString());
                    }

                    if (etRepetitions.getText().toString().trim().length() > 0) {
                        repetitions = Integer.parseInt(etRepetitions.getText().toString());
                    }

                    activityService.addActivity(etName.getText().toString(), etDeadline.getText().toString(),
                            plannedHoursTime, category1, repetitions, checkBox.isChecked(), eventList);
                    Toast.makeText(getContext(), "Activity added", Toast.LENGTH_LONG).show();

                    ActivityCompat.finishAfterTransition(getActivity());

                } catch (ActivityManagerException e) {
                    etName.setError(e.getMessage());
                } catch (InvalidArgumentsException e) {
                    e.printStackTrace();
                    if (e.getMessage().equals("Invalid Hour.")) {
                        etPlannedHours.setError(e.getMessage());

                    } else if (e.getMessage().equals("Invalid name.")) {
                        etName.setError(e.getMessage());
                    } else {
                        etRepetitions.setError(e.getMessage());
                    }
                } catch (InvalidDateException e) {
                    e.printStackTrace();
                    etDeadline.setError(e.getMessage());
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

    @Override
    public void onStart() {
        super.onStart();

        activityService = new ActivityService(getContext());
        categoryService = new CategoryService(getContext());
        eventService = new EventService(getContext());
        List<String> eventsName = null;
        try {
            eventsName = GetListName.listNameEvents(eventService.findAll());
        } catch (ActivityManagerException e) {
            e.printStackTrace();
        }

        try {
            ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                    GetListName.listNameCategory(categoryService.listAllCategories()));
            spinner.setAdapter(adapter);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        } catch (ActivityManagerException e) {
            e.printStackTrace();
        }

        spinnerEvents.setItems(eventsName);


    }

}
