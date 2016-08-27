package br.com.mytasks.controller.activity.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Date;

import br.com.mytasks.R;
import br.com.mytasks.controller.activity.adapters.TabsAdapter;
import br.com.mytasks.controller.activity.fragments.PageHomeFragment;
import br.com.mytasks.controller.activity.fragments.dialogs.AboutDialog;
import br.com.mytasks.dao.EventDAO;
import br.com.mytasks.entities.Activity;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.service.ActivityService;
import br.com.mytasks.util.RepetitionsUtil;


public class MainActivity extends BaseActivity implements View.OnClickListener{

    private FloatingActionMenu fab;
    private TabsAdapter adapter;
    private ViewPager pager;
    private TabLayout tabLayout;
    private ActivityService service;
    private CaldroidFragment dialogCaldroidFragment;
    private  Date dateCurrent;
    private EventDAO eventDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //deleteDatabase("activityManager.db");

        setContentView(R.layout.activity_home_navigation);
        setUpToolbar();
        setupNavDrawer();
        setViewPager();

        fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {

            }
        });

        fab.showMenuButton(true);
        fab.setClosedOnTouchOutside(true);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);

        fab.setClosedOnTouchOutside(true);
    }

    /**
     * Define pageView na tela inicial
     */
    public void setViewPager(){
        dateCurrent = new Date();
        dateCurrent.getTime();
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new TabsAdapter(getSupportFragmentManager(), getContext(), dateCurrent);

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(fab.isOpened()){
            fab.close(true);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        service = new ActivityService(getContext());

        int id = item.getItemId();

        if (id == R.id.action_about) {
            AboutDialog.showAbout(getSupportFragmentManager());
            return true;

        } if (id == R.id.icon_calendar) {
            RepetitionsUtil r = new RepetitionsUtil();
            dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", 4, 2016);
            dialogCaldroidFragment.show(getSupportFragmentManager(), "TAG");
            dialogCaldroidFragment.setCaldroidListener(listener);
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.colorItemSelectPrimary));
            ColorDrawable dark = new ColorDrawable(getResources().getColor(R.color.colorItemRepetition));

            try {
                for (Activity activity : service.listAllActivities()){
                    dialogCaldroidFragment.setBackgroundDrawableForDate(blue,activity.getDeadLine());
                    if(activity.getRepetitions() > 0){
                        for (Activity activity1 : r.getActivitiesOfRepetitions(activity)){
                            dialogCaldroidFragment.setBackgroundDrawableForDate(dark,activity1.getDeadLine());
                        }
                    }

                }
            } catch (ActivityManagerException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
            }
            return true;

        }


        return super.onOptionsItemSelected(item);
    }

    final CaldroidListener listener = new CaldroidListener() {

        @Override
        public void onSelectDate(Date date, View view) {
            PageHomeFragment.newInstance(date, getContext());
            pager = (ViewPager) findViewById(R.id.viewPager);
            adapter = new TabsAdapter(getSupportFragmentManager(), getContext(), date);
            pager.setAdapter(adapter);
            tabLayout.setupWithViewPager(pager);
            dialogCaldroidFragment.dismiss();

        }

        @Override
        public void onLongClickDate(Date date, View view) {

        }
    };


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fab1:
                Intent intent2 = new Intent(this, NewEventActivity.class);
                startActivity(intent2);
                break;
            case R.id.fab2:
                Intent intent1 = new Intent(this, AddNewTaskActivity.class);
                startActivity(intent1);
                break;
            case  R.id.fab3:
                Intent intent = new Intent(this, NewCategoryActivity.class);
                startActivity(intent);
                break;
        }
    }
}
