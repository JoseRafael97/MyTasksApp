package br.com.mytasks.controller.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import br.com.mytasks.R;
import br.com.mytasks.controller.activity.adapters.AdapterEvent;
import br.com.mytasks.controller.activity.listener.RecyclerItemClickListener;
import br.com.mytasks.entities.Activity;
import br.com.mytasks.entities.Event;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.service.EventService;
import br.com.mytasks.util.DateConversor;
import br.com.mytasks.util.StatusActivityUtil;


public class ActivityFragment extends Fragment implements Serializable, RecyclerItemClickListener.OnItemClickListener{

    private TextView categoryName;
    private TextView deadLine;
    private TextView plannedOfHours;
    private TextView repetions;
    private ImageView imageView;
    private Activity activity;
    private static EventService service;
    private AdapterEvent adapterEvent;
    private static RecyclerView recyclerView;
    private List<Event> events;

    public ActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_activity, container, false);
        setHasOptionsMenu(true);
        service = new EventService(getContext());
        recyclerView = (RecyclerView) v.findViewById(R.id.list_events_activity_detail);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryName = (TextView) getView().findViewById(R.id.tv_category_home);
        deadLine = (TextView) getView().findViewById(R.id.tv_deadline_home_detail);
        plannedOfHours = (TextView) getView().findViewById(R.id.tv_planned_hours_home);
        repetions = (TextView) getView().findViewById(R.id.tv_repetitions_home);
        imageView = (ImageView) getView().findViewById(R.id.iv_activity_home);
    }

    @Override
    public void onStart() {
        super.onStart();

        Intent i = getActivity().getIntent();
        activity = (Activity) i.getSerializableExtra("activity");

        categoryName.setText(activity.getCategory().getName());
        deadLine.setText(DateConversor.dateAndHourToStringConversor(activity.getDeadLine()));
        plannedOfHours.setText(DateConversor.hourStringConversor(activity.getPlannedHours()));
        repetions.setText(Integer.toString(activity.getRepetitions()));

        StatusActivityUtil statusActivityUtil = new StatusActivityUtil();
        try {
            if (statusActivityUtil.getAtivitiesStatus(activity) == 1) {
                imageView.setImageResource(R.drawable.ic_circle_done);
            } else if (statusActivityUtil.getAtivitiesStatus(activity) == -1) {
                imageView.setImageResource(R.drawable.ic_circle_fail);
            } else {
                imageView.setImageResource(R.drawable.ic_circle_current);
            }
        } catch (ActivityManagerException e) {
            e.printStackTrace();
        }

        adapterEvent = new AdapterEvent(activity.getEvents(), getActivity());
        recyclerView.setAdapter(adapterEvent);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
    }

    @Override
    public void onItemClick(View childView, int position) {

    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}
