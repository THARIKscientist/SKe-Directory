package com.gangtok.nielit.skdirectory.model;

/**
 * Created by NgocTri on 11/7/2015.
 */
public class Department {

    private String name;
    private int id;
    private int category_id;
    public Department(int id, String name, int category_id ) {

        this.id = id;
        this.name = name;
        this.category_id=category_id;

    }
    public Department(int id, String name) {

        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getCategoryId() {
        return category_id;
    }

    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
