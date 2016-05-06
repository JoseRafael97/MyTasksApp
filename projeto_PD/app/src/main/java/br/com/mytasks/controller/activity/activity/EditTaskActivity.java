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
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Date;
import java.util.List;

import br.com.mytasks.R;
import br.com.mytasks.controller.activity.adapters.MultiSelectSpinner;
import br.com.mytasks.controller.activity.fragments.dialogs.TimePickerDialog;
import br.com.mytasks.entities.Activity;
import br.com.mytasks.entities.Event;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.exception.InvalidDateException;
import br.com.mytasks.service.ActivityService;
import br.com.mytasks.service.CategoryService;
import br.com.mytasks.service.EventService;
import br.com.mytasks.util.DateConversor;
import br.com.mytasks.util.GetListName;


import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;


public class EditTaskActivity extends BaseActivity {

    private CategoryService categoryService;
    private ActivityService activityService;
    private Spinner spinner;
    private EditText etName;
    private EditText etDeadline;
    private EditText etRepetitions;
    private EditText etPlannedHours;
    private EditText etWorkedHours;
    private ImageView btClean;
    private CheckBox checkBox;
    private CaldroidFragment dialogCaldroidFragment;
    private Activity activity;

    private ImageButton btImageAdd;
    private ImageButton btImageAddEvents;
    private MultiSelectSpinner spinnerEvents;
    private EventService eventService;



    public EditTaskActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_task);

        setUpToolbar();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Editing activity");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        activity = (Activity) getIntent().getSerializableExtra("activity");
        activityService = new ActivityService(getContext());
        categoryService = new CategoryService(getContext());
        eventService = new EventService(getContext());

        etName = (EditText) findViewById(R.id.et_name_activity);
        etDeadline = (EditText) findViewById(R.id.et_activity_deadline);
        etPlannedHours = (EditText) findViewById(R.id.et_activity_planned_hours);
        etRepetitions = (EditText) findViewById(R.id.et_activity_repetitions);
        etWorkedHours = (EditText) findViewById(R.id.et_activity_worked_hours);
        spinner = (Spinner) findViewById(R.id.spinner);
        btClean = (ImageView) findViewById(R.id.imageButton);
        checkBox = (CheckBox) findViewById(R.id.check_activity);

        btImageAdd = (ImageButton) findViewById(R.id.bt_image_add);
        btImageAddEvents = (ImageButton) findViewById(R.id.bt_image_add_events);
        spinnerEvents = (MultiSelectSpinner) findViewById(R.id.spinner_events);

        try {
            /*
            if(activity.getEvents() != null) {
                List<String> list = GetListName.listNameEvents(activity.getEvents());
                System.out.println(list);
                spinnerEvents.setSelection(list);
            }
            */
            spinnerEvents.setItems(GetListName.listNameEvents(eventService.findAll()));
        } catch (ActivityManagerException e) {
            e.printStackTrace();
        }

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
            }
        });

        etName.setText(activity.getName());
        etDeadline.setText(DateConversor.dateToStringConversor(activity.getDeadLine()));
        etPlannedHours.setText(DateConversor.hourStringConversor(activity.getPlannedHours()));
        etRepetitions.setText(Integer.toString(activity.getRepetitions()));
        checkBox.setSelected(activity.isFinished());


        btClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDeadline.setText("");
            }
        });

        etPlannedHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.show(getSupportFragmentManager(), new TimePickerDialog.Callback() {
                    @Override
                    public void SetHour(String hour) {
                        etPlannedHours.setText(hour);
                    }
                });

            }
        });

        etWorkedHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.show(getSupportFragmentManager(), new TimePickerDialog.Callback() {
                    @Override
                    public void SetHour(String hour) {
                        etWorkedHours.setText(hour);
                    }
                });
            }
        });

        try {
            ArrayAdapter<String> adapter = new ArrayAdapter(this, simple_spinner_item,
                    GetListName.listNameCategory(categoryService.listAllCategories()));
            spinner.setAdapter(adapter);
            adapter.setDropDownViewResource(simple_spinner_dropdown_item);
        } catch (ActivityManagerException e) {
            e.printStackTrace();
        }


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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create, menu);
        return true;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add_check:

                Date plannedHours = null;
                int repetitions = 0;
                Date workedHours = null;
                List<Event> eventList = eventService.listEventName(spinnerEvents.getSelectedStrings());


                if (etPlannedHours.getText().toString().trim().length() > 0) {
                    plannedHours = DateConversor.stringToHourConversor(etPlannedHours.getText().toString());
                }

                if (etRepetitions.getText().toString().trim().length() > 0) {
                    repetitions = Integer.parseInt(etRepetitions.getText().toString());
                }

                if (etWorkedHours.getText().toString().trim().length() > 0) {
                    workedHours = DateConversor.stringToHourConversor(etWorkedHours.getText().toString());
                }

                try {
                    activityService.updateActivity(activity.getId(), etName.getText().toString(), etDeadline.getText().toString(), plannedHours,
                            workedHours, categoryService.findCategoryByName(spinner.getSelectedItem().toString()),
                            repetitions, checkBox.isChecked(), eventList);

                    Toast.makeText(getActivity(), "Update activity success", Toast.LENGTH_LONG).show();

                } catch (ActivityManagerException e) {
                    etName.setError(e.getMessage());
                } catch (InvalidDateException e) {
                    etDeadline.setError(e.getMessage());
                }

                ActivityCompat.finishAfterTransition(getActivity());

                return true;

            case R.id.cancel:
                ActivityCompat.finishAfterTransition(getActivity());
                return true;
        }
        return true;
    }


}
