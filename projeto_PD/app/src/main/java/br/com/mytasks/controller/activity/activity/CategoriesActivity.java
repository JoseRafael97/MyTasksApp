package br.com.mytasks.controller.activity.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import br.com.mytasks.R;
import br.com.mytasks.controller.activity.fragments.CategoriesListFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesActivity extends BaseActivity {

    private CategoriesListFragment fragment;


    public CategoriesActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        setUpToolbar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.menu_category);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        fragment = new CategoriesListFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_categories, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category, menu);

        return true;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add_item_category:
                startActivity(new Intent(this, NewCategoryActivity.class));
                return true;

            case android.R.id.home:
                ActivityCompat.finishAfterTransition(getActivity());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        fragment.onStart();
        Log.d("Ciclo", "Fragment: Metodo onStart() chamado");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("Ciclo", "Fragment: Metodo onResume() chamado");
    }


}
