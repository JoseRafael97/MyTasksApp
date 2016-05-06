package br.com.mytasks.controller.activity.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import br.com.mytasks.R;
import br.edu.ifpb.mytasks.controller.activity.activity.ActivityActivity;
import br.edu.ifpb.mytasks.controller.activity.adapters.AdapterActivityHome;
import br.edu.ifpb.mytasks.controller.activity.listener.RecyclerItemClickListener;
import br.edu.ifpb.mytasks.entities.Activity;
import br.edu.ifpb.mytasks.exception.ActivityManagerException;
import br.edu.ifpb.mytasks.service.ActivityService;


public class PageHomeFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener{

    private static final String ARG_PAGE_NUMBER = "page_number";
    private static ActivityService service;
    private AdapterActivityHome adapterActivity;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static  List<Activity> activities;
    private static final int FLAG_POSITION_ACTVITIES = 0;
    private ActionMode actionMode;
    private Activity activity;


    public PageHomeFragment() {
    }

    public static PageHomeFragment newInstance(int page, Context context) {
        service = new ActivityService(context);
        try {
            Date dateCurrent = new Date();
            dateCurrent.getTime();
            activities = service.findByDate(dateCurrent);
        } catch (ActivityManagerException e) {
            e.printStackTrace();
        }
        PageHomeFragment fragment = new PageHomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }

    public static PageHomeFragment newInstance(Date date, Context context) {
        service = new ActivityService(context);
        try {
            activities = service.findByDate(date);
        } catch (ActivityManagerException e) {
            e.printStackTrace();
        }
        PageHomeFragment fragment = new PageHomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, FLAG_POSITION_ACTVITIES);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activity_list, container, false);

        service = new ActivityService(getContext());


        adapterActivity = new AdapterActivityHome(activities, getActivity());
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view_activities);
        recyclerView.setAdapter(adapterActivity);
        layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));

        return v;
    }

    @Override
    public void onItemClick(View childView, int position) {
        activity = activities.get(position);

        recyclerView.getAdapter().notifyDataSetChanged();

        if(actionMode == null){
            Intent intent = new Intent(getContext(), ActivityActivity.class);
            intent.putExtra("activity", activity);
            startActivity(intent);
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
