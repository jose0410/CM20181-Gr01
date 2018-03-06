package co.edu.udea.compumovil.gr01_20181.lab2.DB;

import android.content.ContentValues;


public class UserStructure {

    private String name, user, password, mail, state, picture;


    public UserStructure(String name, String user, String password, String mail, String picture) {
        this.name = name;
        this.user = user;
        this.password = password;
        this.mail = mail;
        this.picture = picture;
        this.state = "INACTIVO";
    }
    
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(StatusContract.Column_User.USER, user);
        values.put(StatusContract.Column_User.NAME, name);
        values.put(StatusContract.Column_User.PASSWORD, password);
        values.put(StatusContract.Column_User.MAIL, mail);
        values.put(StatusContract.Column_User.PICTURE, picture);
        values.put(StatusContract.Column_User.STATE, state);
        return values;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public String getPicture() {
        return picture;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
