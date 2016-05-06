package br.com.mytasks.service;

import android.content.Context;

import java.util.List;

import br.com.mytasks.dao.CategoryDAO;
import br.com.mytasks.entities.Category;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.exception.IdDoesNotExistException;
import br.com.mytasks.exception.InvalidArgumentsException;
import br.com.mytasks.exception.VinculationException;
import br.com.mytasks.persistence.dataBase.DataBase;
import br.com.mytasks.validation.CategoryValidator;
import br.com.mytasks.validation.Validation;


public class CategoryService {

    private CategoryDAO categoryDao;
    private CategoryValidator categoryValidator;

    public CategoryService() {
    }

    public CategoryService(Context context) {
        categoryDao = new CategoryDAO(context);
        categoryValidator = new CategoryValidator(context);
    }

    /**
     * Cadastra uma nova categoria no banco de dados
     *
     * @param name Nome da categoria a ser inserida
     * @param color cor que será utilizada para representar a categoria
     * @throws ActivityManagerException
     */
    public void addCategory(String name, String color) throws ActivityManagerException {
        try {
            Validation.nameValidation(name);
            categoryValidator.containsCategory(name);
            categoryDao.save(new Category(name, color));
        } catch (InvalidArgumentsException e) {
            throw new ActivityManagerException(e.getMessage());
        }
    }

    /**
     * Atualiza as informações de uma categoria
     *
     * @param name Nome da categoria a ser atualizada
     * @param color cor que será utilizada para representar a categoria
     * @param id identificação única da categoria
     * @throws ActivityManagerException lançada quando houver um erro na atualização
     */
    public void updateCategory(String name, String color, long id) throws ActivityManagerException {
        try {
            Validation.nameValidation(name);
            categoryValidator.containsCategory(name);
        } catch (InvalidArgumentsException e) {
            throw new ActivityManagerException("Invalid name");
        }
        categoryDao.update(new Category(name, color), id);
    }

    /**
     * Lista todas as categorias existentes no banco
     *
     * @throws ActivityManagerException
     * @return uma lista contendo todas as categorias
     */
    public List<Category> listAllCategories() throws ActivityManagerException {
        return categoryDao.findAll();
    }

    /**
     * Lista as informações de uma categoria específica (caso esta exista)
     *
     * @param id Código de identificação único da categoria.     *
     * @throws InvalidArgumentsException lançada quando a identificação for inválida
     * @throws ActivityManagerException
     * @return A categoria
     */
    public Category findCategoryById(Long id) throws InvalidArgumentsException, ActivityManagerException {
        Validation.idValidation(id);
        Category category = categoryDao.findById(id);
        Validation.validateCategory(category);

        return category;
    }


    /**
     * Remove uma categoria previamente cadastrada
     *
     * @param id Código de identificação único da categoria.
     * @throws InvalidArgumentsException lançada quando o argumento é inválido;
     * @throws ActivityManagerException
     * @throws VinculationException
     * @throws IdDoesNotExistException lançada quando a identificação não existe
     */
    public void removeCategory(long id) throws InvalidArgumentsException, ActivityManagerException,
            VinculationException, IdDoesNotExistException {

        categoryValidator.containsCategory(id);
        categoryValidator.invalidRemove(findCategoryById(id));
        categoryDao.delete(id);
    }

    /**
     * Lista as informações de uma categoria escolhida pelo nome
     *
     * @param name nome da categoria pesquisada
     * @throws ActivityManagerException
     * @return a categoria
     *
     */
    public Category findCategoryByName(String name) throws ActivityManagerException {
        List<Category> listCategory = categoryDao.findAll();

        for (Category category : listCategory) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        throw new ActivityManagerException("Category not found");
    }

    /**
     * 'pega' o banco responsável por todas as operações e o retorna
     *
     * @return o banco
     */
    public DataBase getDataBase() {
        return categoryDao.getDataBase();
    }

}
