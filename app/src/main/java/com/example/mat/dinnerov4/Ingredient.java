package com.example.mat.dinnerov4;

/**
 * Created by mat on 14.02.2017.
 */

public class Ingredient {
    private int _id;
    private String _name;
    private String _description;
    private int _isVegan;
    private int _isVegetarion;
    private int _isGlutenFree;
    private boolean _isLactoseFree;
    private boolean _isSelected;

    public Ingredient(){
    }
    public void setVegetarian(int isVegetarion){ this._isVegetarion = isVegetarion;}
    public boolean isVegetarian(){return (this._isVegetarion != 0) ? true : false;}

    public void setGlutenFree(int isGlutenFree){ this._isGlutenFree = isGlutenFree;}
    public boolean isGlutenFree(){return (this._isGlutenFree != 0) ? true : false;}
    public void setSelected(boolean isChoosed){this._isSelected = isChoosed;}

    public boolean isSelected(){return this._isSelected;}

    public void setVegan(int isVegan){this._isVegan = isVegan;}

    public int isVegan(){return this._isVegan;}

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
