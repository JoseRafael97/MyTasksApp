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
import br.com.mytasks.controller.activity.activity.EditCategoryActivity;
import br.com.mytasks.controller.activity.adapters.AdapterCategory;
import br.com.mytasks.controller.activity.listener.RecyclerItemClickListener;
import br.com.mytasks.entities.Category;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.exception.IdDoesNotExistException;
import br.com.mytasks.exception.InvalidArgumentsException;
import br.com.mytasks.exception.VinculationException;
import br.com.mytasks.service.CategoryService;
import br.com.mytasks.util.AlertDialogAlternative;


public class CategoriesListFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener{

    private CategoryService service;
    private AdapterCategory adapterCategory;
    private RecyclerView recyclerView;
    private List<Category> categories;
    private ActionMode mActionMode;
    private Category category;

    public CategoriesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories_list, container, false);

            service = new CategoryService(getActivity());
            recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view_categories);



        return v;
    }

    @Override
    public void onItemClick(View childView, int position) {

        if (category !=null && category.selected) {
            category.selected = false;
            recyclerView.getAdapter().notifyDataSetChanged();
            mActionMode.finish();
            mActionMode = null;
        }
    }

    @Override
    public void onItemLongPress(View childView, int position) {
        if (mActionMode != null) {
            return;
        }

        mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(getActionModeCallback());
        categories.get(position).selected = true;
        category = categories.get(position);
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    private ActionMode.Callback getActionModeCallback() {
        return new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle("Category selected");
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

                                service.removeCategory(category.getId());
                                adapterCategory.removeCategory(category);
                                Toast.makeText(getContext(),"Removed category", Toast.LENGTH_LONG).show();

                            } catch (InvalidArgumentsException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();

                            } catch (VinculationException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();

                            } catch (IdDoesNotExistException e) {
                                e.printStackTrace();

                            } catch (ActivityManagerException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    mode.finish();

                }else if (item.getItemId() == R.id.item_edit_category) {
                    Intent intent = new Intent(getActivity(), EditCategoryActivity.class);
                    intent.putExtra("category", category);
                    startActivity(intent);
                    mode.finish();
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionMode = null;
                for (Category c : categories){
                    if (category.getId() == c.getId()){
                        c.selected = false;
                        recyclerView.getAdapter().notifyDataSetChanged();
                        break;
                    }
                }

                recyclerView.getAdapter().notifyDataSetChanged();

            }
        };

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            adapterCategory = new AdapterCategory(categories = service.listAllCategories(), getActivity());
            recyclerView.setAdapter(adapterCategory);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
        } catch (ActivityManagerException e) {
            e.printStackTrace();
        }


    }




}
