package br.com.mytasks.entities;


import java.io.Serializable;

public class Category implements Serializable{

    private long id;
    private String name;
    private String color;
    public boolean selected;


    public Category(long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
