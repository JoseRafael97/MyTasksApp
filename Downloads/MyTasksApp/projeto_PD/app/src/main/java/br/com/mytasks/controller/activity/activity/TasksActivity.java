package br.com.mytasks.controller.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import br.com.mytasks.R;
import br.com.mytasks.controller.activity.fragments.ActivitiesListFragment;

public class TasksActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);


        setUpToolbar();
        if (getSupportActionBar()!= null) {
            getSupportActionBar().setTitle(R.string.menu_activities);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ActivitiesListFragment fragment = new ActivitiesListFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.activity_fragment, fragment);
        fragmentTransaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item_category:
                Intent intent1 = new Intent(this, AddNewTaskActivity.class);
                startActivity(intent1);
                finish();
                return true;

            case android.R.id.home:
                ActivityCompat.finishAfterTransition(getActivity());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
