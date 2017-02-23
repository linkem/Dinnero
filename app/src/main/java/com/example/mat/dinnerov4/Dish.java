package com.example.mat.dinnerov4;

import android.graphics.Bitmap;

/**
 * Created by mat on 14.02.2017.
 */

public class Dish {
    private int _id;
    private String _name;
    private String _description;
    private Bitmap _image;

    public Dish(){
    }

    public void setImage(Bitmap image){
        this._image = image;
    }
    public Bitmap getImage(){return this._image;}
    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String _description) {
        this._description = _description;
    }
}