package br.com.mytasks.controller.activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.mytasks.R;
import br.com.mytasks.controller.activity.adapters.AdapterEvent;
import br.com.mytasks.controller.activity.listener.RecyclerItemClickListener;
import br.com.mytasks.entities.Event;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.service.EventService;


public class EventsListFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener{

    private static EventService service;
    private AdapterEvent adapterEvent;
    private static RecyclerView recyclerView;
    private static List<Event> events;
    private Event event;
    private ActionMode mActionMode;


    public EventsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        service = new EventService(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.list_events);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            events = service.findAll();
        } catch (ActivityManagerException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
        adapterEvent = new AdapterEvent(events, getActivity());
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
