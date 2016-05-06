package br.com.mytasks.controller.activity.activity;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.com.mytasks.R;
import br.com.mytasks.entities.Category;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.service.CategoryService;


public class EditCategoryActivity extends BaseActivity {

    private Category category;
    private EditText etCategory;
    private CategoryService categoryService;

    public EditCategoryActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_category);

        setUpToolbar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Editing Activity");
        }
        category = (Category) getIntent().getSerializableExtra("category");
        etCategory = (EditText) findViewById(R.id.et_name_category);

        if (category != null) {
            etCategory.setText(category.getName());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.add_check:
                categoryService = new CategoryService(getContext());

                if (etCategory.getText().toString().trim().length() > 0) {
                    etCategory.setError("No informed name");
                }

                try {

                    categoryService.updateCategory(etCategory.getText().toString().trim(), "--",category.getId());
                    Toast.makeText(getActivity(), "Update category success", Toast.LENGTH_LONG).show();
                    ActivityCompat.finishAfterTransition(getActivity());


                } catch (ActivityManagerException e) {
                    e.printStackTrace();
                    etCategory.setError(e.getMessage());
                }
                return true;


            case R.id.cancel:
                ActivityCompat.finishAfterTransition(getActivity());
                return true;
        }

            return true;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create, menu);

        return true;
    }


}

