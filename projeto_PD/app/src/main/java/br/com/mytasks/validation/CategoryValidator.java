package br.com.mytasks.validation;

import android.content.Context;

import br.com.mytasks.dao.ActivityDAO;
import br.com.mytasks.dao.CategoryDAO;
import br.com.mytasks.entities.Activity;
import br.com.mytasks.entities.Category;
import br.com.mytasks.exception.IdDoesNotExistException;
import br.com.mytasks.exception.InvalidArgumentsException;
import br.com.mytasks.exception.VinculationException;


public class CategoryValidator {

    private CategoryDAO categoryDao;
    private ActivityDAO activityDAO;

    public CategoryValidator(Context context) {
        categoryDao = new CategoryDAO(context);
        activityDAO = new ActivityDAO(context);
    }

    /**
     * verifica se uma categoria já existe no banco de dados
     *
     * @param name nome da categoria
     */
    public void containsCategory(String name) throws InvalidArgumentsException {
        for (Category category: categoryDao.findAll()) {
            if (category.getName().equalsIgnoreCase(name)) {
                throw new InvalidArgumentsException("Category already exists.");
            }
        }
    }

    /**
     * Verifica se a categoria está associada a uma atividade
     * caso esteja, não é possível removê-la
     * @param category categoria a ser verificada
     * @return true, caso a categoria não esteja vinculada
     * @throws VinculationException excecao lancada quando uma categoria está linkada a uma atividade
     */
    public boolean invalidRemove(Category category) throws VinculationException  {
        for (Activity activity : activityDAO.findAll()){
            if (activity.getId() == category.getId()){
                throw new VinculationException("An activity is related to this category. You can't remove it.");
            }
        }
        return true;
    }


    /**
     * Verifica se uma categoria já existe
     * @param id código de identificação único de uma categoria
     * @throws IdDoesNotExistException excecao lancada quando não é possível encontrar a id da categoria no banco de dados
     */
    public void containsCategory(long id) throws IdDoesNotExistException {
        for (Category category: categoryDao.findAll()) {
            if (category.getId() == id) {
                return;
            }
        }
        throw new IdDoesNotExistException("Category already exists.");
    }


}