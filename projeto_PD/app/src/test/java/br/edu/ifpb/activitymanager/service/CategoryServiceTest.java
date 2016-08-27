package br.edu.ifpb.activitymanager.service;

import org.junit.Test;

import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.service.CategoryService;


public class CategoryServiceTest {

    @Test(expected = ActivityManagerException.class)
    public void testAddEmptyCategory() throws Exception {

        CategoryService categoryService = new CategoryService();
        categoryService.addCategory("", "");

    }

    @Test(expected = ActivityManagerException.class)
    public void testAddNullCategory() throws Exception {

        CategoryService categoryService = new CategoryService();
        categoryService.addCategory(null, null);

    }

    @Test(expected = ActivityManagerException.class)
    public void testUpdateCategory() throws Exception {
        CategoryService categoryService = new CategoryService();
        categoryService.updateCategory("", "", 666L);
    }

    @Test(expected = ActivityManagerException.class)
    public void testUpdateNullCategory() throws Exception {
        CategoryService categoryService = new CategoryService();
        categoryService.updateCategory(null, null, 666L);
    }

    @Test(expected = NullPointerException.class)
    public void testListAllCategories() throws Exception {
        CategoryService categoryService = new CategoryService();
        categoryService.listAllCategories();
    }

    @Test(expected = Exception.class)
    public void testFindCategoryById() throws Exception {
        CategoryService categoryService = new CategoryService();
        categoryService.findCategoryById(666L);
    }

    @Test(expected = Exception.class)
    public void testRemoveCategory() throws Exception {
        CategoryService categoryService = new CategoryService();
        categoryService.removeCategory(666L);
    }
}