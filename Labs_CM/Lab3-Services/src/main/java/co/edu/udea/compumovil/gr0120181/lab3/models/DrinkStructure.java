package co.edu.udea.compumovil.gr0120181.lab3.models;

import android.content.ContentValues;


public class DrinkStructure {

    private String name, price, ingredients, picture;


    public DrinkStructure(String name,  String price, String picture, String ingredients) {
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
