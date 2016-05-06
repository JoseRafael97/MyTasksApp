package br.com.mytasks.controller.activity.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.mytasks.R;
import br.com.mytasks.controller.activity.activity.ActivityActivity;
import br.com.mytasks.controller.activity.activity.EditTaskActivity;
import br.com.mytasks.controller.activity.adapters.AdapterActivity;
import br.com.mytasks.controller.activity.listener.RecyclerItemClickListener;
import br.com.mytasks.entities.Activity;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.exception.IdDoesNotExistException;
import br.com.mytasks.service.ActivityService;
import br.com.mytasks.util.AlertDialogAlternative;


public class ActivitiesListFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {

    private static ActivityService service;
    private AdapterActivity adapterActivity;
    private static RecyclerView recyclerView;
    private static List<Activity> activities;
    private Activity activity;
    private ActionMode mActionMode;


    public ActivitiesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activity_list, container, false);
        service = new ActivityService(getContext());
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view_activities);

        return v;
    }

    @Override
    public void onItemClick(View childView, int position) {
        int flag = 0;
        if (activity != null && activity.selected) {
            flag = 1;
            activity.selected = false;
            recyclerView.getAdapter().notifyDataSetChanged();
            mActionMode.finish();
            mActionMode = null;
        }
        activity = activities.get(position);

        if(flag == 0){
            flag = 0;
            Intent intent = new Intent(getContext(), ActivityActivity.class);
            intent.putExtra("activity", activity);
            startActivity(intent);
        }
    }

    @Override
    public void onItemLongPress(View childView, int position) {
        if (mActionMode != null) {
            return;
        }

        mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(getActionModeCallback());
        activities.get(position).selected = true;
        activity = activities.get(position);
        recyclerView.getAdapter().notifyDataSetChanged();

    }


    private ActionMode.Callback getActionModeCallback() {
        return new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle("Activity selected");
                mode.getMenuInflater().inflate(R.menu.menu_flag_editing_remove, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                if (item.getItemId() == R.id.item_delete) {

                    AlertDialogAlternative.createAlertDialog(getContext(), "Do you really want to remove?",
                            new AlertDialogAlternative.Callback() {
                                @Override
                                public void onClickYes() {
                                    try {
                                        service.removeActivity(activity.getId());
                                        adapterActivity.removeActivity(activity);
                                        Toast.makeText(getContext(), "Removed activity", Toast.LENGTH_LONG).show();


                                    } catch (IdDoesNotExistException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                    mode.finish();
                } else if (item.getItemId() == R.id.item_edit_category) {
                    Intent intent = new Intent(getActivity(), EditTaskActivity.class);
                    intent.putExtra("activity", activity);
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionMode = null;
                for (Activity a : activities) {
                    if (activity.getId() == a.getId()) {
                        a.selected = false;
                        break;
                    }
                }

                recyclerView.getAdapter().notifyDataSetChanged();

            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            activities = service.listAllActivities();
            adapterActivity = new AdapterActivity(activities, getActivity());
            recyclerView.setAdapter(adapterActivity);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
        } catch (ActivityManagerException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

}
