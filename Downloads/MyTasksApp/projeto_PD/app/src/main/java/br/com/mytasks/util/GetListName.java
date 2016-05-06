package br.com.mytasks.util;

import java.util.ArrayList;
import java.util.List;

import br.com.mytasks.entities.Category;
import br.com.mytasks.entities.Event;


public class GetListName {

    public static List<String> listNameCategory(List<Category> categories){
        List<String> c = new ArrayList<>();
        for (Category category : categories){
            c.add(category.getName());
        }
        return c;
    }

    public static  List<String> listNameEvents(List<Event> events){
        List<String> c = new ArrayList<>();
        for (Event event : events){
            c.add(event.getName());
        }
        return c;
    }
}
