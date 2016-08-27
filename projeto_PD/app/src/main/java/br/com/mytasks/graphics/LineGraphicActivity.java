package br.com.mytasks.graphics;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import br.com.mytasks.R;
import br.com.mytasks.controller.activity.activity.BaseActivity;
import br.com.mytasks.util.GetWorkedHours;


public class LineGraphicActivity extends BaseActivity {

    private LineChart lineChart;
    private GetWorkedHours getWorkedHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graphic);
        setUpToolbar();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(R.string.graphic_activity_title);
        }

        lineChart = (LineChart) findViewById(R.id.line_chart);
        getWorkedHours = new GetWorkedHours(getContext());


        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(getWorkedHours.getAllWorkedHoursPerMonth(3), 0));
        entries.add(new Entry(getWorkedHours.getAllWorkedHoursPerMonth(4), 1));
        entries.add(new Entry(getWorkedHours.getAllWorkedHoursPerMonth(5), 2));
        entries.add(new Entry(getWorkedHours.getAllWorkedHoursPerMonth(6), 3));
        entries.add(new Entry(getWorkedHours.getAllWorkedHoursPerMonth(7), 4));


        LineDataSet dataset = new LineDataSet(entries, "Number of worked hours per month");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");

        LineData data = new LineData(labels, dataset);
        lineChart.setData(data);

        lineChart.setDescription("Worked Hours per Month");
    }
}
