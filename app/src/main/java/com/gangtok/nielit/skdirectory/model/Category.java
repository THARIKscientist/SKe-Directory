package com.gangtok.nielit.skdirectory.model;

/**
 * Created by NIELIT on 11-08-2016.
 */
public class Category {
    private String name;
    private int id;
    private String mCategories;
    private boolean mHasImage = false;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    public Category()
    {

    }

    public Category(String categories, int imageResourceId )
    {

        mCategories=categories;
        mImageResourceId=imageResourceId;

        mHasImage = true;
    }



    public Category(int id, String name ) {

        this.id = id;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCategories()
    {
        return mCategories;
    }

    public int getImageResourceId() {  return mImageResourceId;                           }

    public boolean hasImage()       {       return mImageResourceId!=NO_IMAGE_PROVIDED;   }



}
