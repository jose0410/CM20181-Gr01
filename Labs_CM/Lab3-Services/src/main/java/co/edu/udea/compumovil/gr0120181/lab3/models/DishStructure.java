package co.edu.udea.compumovil.gr0120181.lab3.models;

import android.content.ContentValues;


public class DishStructure {

    private String name, type, price, duration, ingredients, picture;


    public DishStructure(String name, String type, String price, String duration, String picture, String ingredients) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.duration = duration;
        this.picture = picture;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }




}
