package br.com.mytasks.dao;

import android.content.Context;

import java.util.List;

import br.com.mytasks.entities.Category;
import br.com.mytasks.persistence.CategoryBD;
import br.com.mytasks.persistence.IManagerBD;
import br.com.mytasks.persistence.dataBase.DataBase;


public class CategoryDAO implements IDAO<Category> {

    private IManagerBD<Category> managerBD;

    public CategoryDAO (Context context)
    {
        managerBD = new CategoryBD(context);
    }

    @Override
    public void save(Category instance)  {
        managerBD.insert(instance);
    }

    @Override
    public void update(Category instance, long id)  {
        managerBD.update(instance, id);
    }

    @Override
    public Category findById(long id) {
        Category category = null;
        List<Category> categoryList = managerBD.findAll();

        for (int i = 0; i < categoryList.size(); i++) {
            if (id == categoryList.get(i).getId()) {
                category = categoryList.get(i);
                return category;
            }
        }
        return null;
    }

    @Override
    public List<Category> findAll()  {

        return managerBD.findAll();
    }

    @Override
    public void delete(long id)  {
        managerBD.delete(id);
    }

    public DataBase getDataBase(){
        return ((CategoryBD)managerBD).getDtb();
    }
}
