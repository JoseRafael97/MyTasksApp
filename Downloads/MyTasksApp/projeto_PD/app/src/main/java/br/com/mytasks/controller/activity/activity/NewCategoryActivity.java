package br.com.mytasks.controller.activity.activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import br.com.mytasks.R;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.service.CategoryService;
import petrov.kristiyan.colorpicker.ColorPicker;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewCategoryActivity extends BaseActivity {

    private EditText etCategory;
    private CategoryService categoryService;
    private ImageButton btColor;
    private int selectColor;
    private String selectedColor;

    public NewCategoryActivity() {   }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_category);
        setUpToolbar();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(R.string.name_activity_new_category);
        }
        categoryService =  new CategoryService(getContext());
        etCategory = (EditText) findViewById(R.id.et_name_category);
        btColor = (ImageButton) findViewById(R.id.color_category);

        btColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(NewCategoryActivity.this);
                colorPicker.setFastChooser(new ColorPicker.OnFastChooseColorListener() {
                    @Override
                    public void setOnFastChooseColorListener(int position, int color) {
                        selectColor = color;

                        selectedColor = String.format("#%06X", (0xFFFFFF & selectColor));

                        GradientDrawable bgShape = (GradientDrawable) btColor.getBackground();
                        bgShape.setColor(selectColor);

                        colorPicker.dismissDialog();
                    }
                }).setDefaultColor(Color.parseColor("#f84c44")).setColumns(5).setDialogFullHeight().show();

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryService.getDataBase().close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add_check:
                String nameCategory = etCategory.getText().toString();

                try {
                    categoryService.addCategory(nameCategory, selectedColor);
                    Toast.makeText(getActivity(), R.string.tv_add_category_sucess,
                            Toast.LENGTH_LONG).show();

                    ActivityCompat.finishAfterTransition(getActivity());



                } catch (ActivityManagerException e) {
                    Toast.makeText(getActivity(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.cancel:
                ActivityCompat.finishAfterTransition(getActivity());
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create, menu);

        return true;
    }

}
