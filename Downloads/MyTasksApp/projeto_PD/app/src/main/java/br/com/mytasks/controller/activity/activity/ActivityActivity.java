package br.com.mytasks.controller.activity.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;

import br.com.mytasks.R;
import br.com.mytasks.controller.activity.fragments.ActivityFragment;
import br.com.mytasks.controller.activity.fragments.dialogs.AboutDialog;
import br.com.mytasks.entities.Activity;


public class ActivityActivity extends BaseActivity implements View.OnClickListener {

    private Activity activity;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        setUpToolbar();

        activity = (Activity) getIntent().getSerializableExtra("activity");

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(activity.getName());

        int color = Color.parseColor(activity.getCategory().getColor());
        collapsingToolbarLayout.setBackgroundColor(color);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ActivityFragment fragment = new ActivityFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.activity_detail_fragment, fragment);
        fragmentTransaction.commit();

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_activity_detail);
        floatingActionButton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityCompat.finishAfterTransition(getActivity());
                return true;
            case R.id.action_about_menu_about:
                AboutDialog.showAbout(getSupportFragmentManager());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fab_activity_detail:
                Intent intent = new Intent(this, EditTaskActivity.class);
                intent.putExtra("activity", activity);
                startActivity(intent);
                break;
        }
    }
}

