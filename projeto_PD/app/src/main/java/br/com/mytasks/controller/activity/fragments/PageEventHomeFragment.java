package br.com.mytasks.controller.activity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import br.com.mytasks.R;
import br.com.mytasks.controller.activity.adapters.AdapterEvent;
import br.com.mytasks.controller.activity.listener.RecyclerItemClickListener;
import br.com.mytasks.entities.Event;
import br.com.mytasks.service.EventService;


public class PageEventHomeFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {


    private static final String ARG_PAGE_NUMBER = "page_number";
    private static EventService service;
    private AdapterEvent adapterEvent;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static List<Event> events;
    private ActionMode actionMode;
    private Event event;


    public PageEventHomeFragment() {
        // Required empty public constructor
    }

    public static PageEventHomeFragment newInstance(int page, Context context) {
        service = new EventService(context);
        try {
            Date dateCurrent = new Date();
            dateCurrent.getTime();
            events = service.findAll();
        } catch (ActivityManagerException e) {
            e.printStackTrace();
        }
        PageEventHomeFragment fragment = new PageEventHomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list_events);

        service = new EventService(getContext());


        adapterEvent = new AdapterEvent(events, getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.list_events);
        recyclerView.setAdapter(adapterEvent);
        layoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));

        return view;
    }

    @Override
    public void onItemClick(View childView, int position) {
        event = events.get(position);

        recyclerView.getAdapter().notifyDataSetChanged();

        if(actionMode == null){
            //Intent intent = new Intent(getContext(), ActivityActivity.class);
            //intent.putExtra("event", event);
            //startActivity(intent);
            Toast.makeText(getContext(), "Em construção..", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onItemLongPress(View childView, int position) {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

    }



}
