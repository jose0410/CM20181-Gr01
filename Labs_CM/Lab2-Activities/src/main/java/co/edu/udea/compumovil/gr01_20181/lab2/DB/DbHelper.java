package co.edu.udea.compumovil.gr01_20181.lab2.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr01_20181.lab2.R;

public class DbHelper extends SQLiteOpenHelper {

    private Context c;

    public DbHelper(Context context) {
        super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
        this.c = context.getApplicationContext();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + StatusContract.TABLE_USER + " ("
                + StatusContract.Column_User.MAIL + " TEXT NOT NULL,"
                + StatusContract.Column_User.NAME + " TEXT NOT NULL,"
                + StatusContract.Column_User.USER + " TEXT NOT NULL,"
                + StatusContract.Column_User.PASSWORD + " TEXT NOT NULL,"
                + StatusContract.Column_User.PICTURE + " TEXT,"
                + StatusContract.Column_User.STATE + " TEXT NOT NULL,"
                + "UNIQUE (" + StatusContract.Column_User.USER + "),"
                + "PRIMARY KEY (" + StatusContract.Column_User.MAIL + "))");
        Bitmap pic = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_camera);

        UserStructure user = new UserStructure("Pedro Perez", "pe", "1", "pedroperez@aaaa.com","1234");
        db.insert(StatusContract.TABLE_USER, null, user.toContentValues());

        db.execSQL("CREATE TABLE " + StatusContract.TABLE_DISH + " ("
                + StatusContract.Column_Dish.ID + " INT PRIMARY KEY,"
                + StatusContract.Column_Dish.NAME + " TEXT NOT NULL,"
                + StatusContract.Column_Dish.TYPE + " TEXT NOT NULL,"
                + StatusContract.Column_Dish.PRICE + " TEXT NOT NULL,"
                + StatusContract.Column_Dish.DURATION + " TEXT NOT NULL,"
                + StatusContract.Column_Dish.INGREDIENTS + " TEXT,"
                + StatusContract.Column_Dish.PHOTO + " TEXT NOT NULL)");

        pic = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_camera);

        DishStructure dish = new DishStructure("Bandeja","mañana","15000", "15:00 min","1234","Arroz, Frijoles, Huevo");
        db.insert(StatusContract.TABLE_DISH, null, dish.toContentValues());



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
