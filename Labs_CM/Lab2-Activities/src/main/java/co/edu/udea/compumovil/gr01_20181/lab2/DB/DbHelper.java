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

import co.edu.udea.compumovil.gr01_20181.lab2.ImageCodeClass;
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
                + StatusContract.Column_User.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StatusContract.Column_User.MAIL + " TEXT NOT NULL,"
                + StatusContract.Column_User.NAME + " TEXT NOT NULL,"
                + StatusContract.Column_User.USER + " TEXT NOT NULL,"
                + StatusContract.Column_User.PASSWORD + " TEXT NOT NULL,"
                + StatusContract.Column_User.PICTURE + " TEXT,"
                + StatusContract.Column_User.STATE + " TEXT NOT NULL,"
                + "UNIQUE (" + StatusContract.Column_User.USER + ", "+ StatusContract.Column_User.ID + "))");

        Bitmap pic = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_camera);

        UserStructure user = new UserStructure("Pedro Perez", "pe", "1", "pedroperez@aaaa.com","1234");
        db.insert(StatusContract.TABLE_USER, null, user.toContentValues());

        db.execSQL("CREATE TABLE " + StatusContract.TABLE_DISH + " ("
                + StatusContract.Column_Dish.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StatusContract.Column_Dish.NAME + " TEXT NOT NULL,"
                + StatusContract.Column_Dish.TYPE + " TEXT NOT NULL,"
                + StatusContract.Column_Dish.PRICE + " TEXT NOT NULL,"
                + StatusContract.Column_Dish.DURATION + " TEXT NOT NULL,"
                + StatusContract.Column_Dish.INGREDIENTS + " TEXT,"
                + StatusContract.Column_Dish.PHOTO + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + StatusContract.TABLE_DRINK + " ("
                + StatusContract.Column_Dish.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StatusContract.Column_Drink.NAME + " TEXT NOT NULL,"
                + StatusContract.Column_Drink.PRICE + " TEXT NOT NULL,"
                + StatusContract.Column_Drink.INGREDIENTS + " TEXT,"
                + StatusContract.Column_Drink.PHOTO + " TEXT NOT NULL)");

        Bitmap imageBitmap;
        imageBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.papas);
        DishStructure dishStructure = new DishStructure("CLÁSICA","Mañana","12.000","15:00 min", ImageCodeClass.encodeToBase64(imageBitmap),"PAN AJONJOLÍ , 150 GRS DE CARNE 100% RES A LA PARRILLA, LECHUGA, TOMATE Y CEBOLLA. ");
        db.insert(StatusContract.TABLE_DISH, null, dishStructure.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.papas);
        dishStructure = new DishStructure("BURGER SALAD","Mañana","10.000","10:00 min",ImageCodeClass.encodeToBase64(imageBitmap),"Ensalada de lechugas frescas con carne a la parrilla, tomates marinados, cebollas de la casa, queso parmesano, cebolla puerro, champiñones y papas chips.");
        db.insert(StatusContract.TABLE_DISH, null, dishStructure.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.papas);
        dishStructure = new DishStructure("PAPAS AMERICANAS","Mañana","15.000","11:00 min",ImageCodeClass.encodeToBase64(imageBitmap),"Papas a la francesa, bañadas en queso cheddar fundido acompañadas de tomates marinados y tocineta finamente picados, cebollin y sour cream");
        db.insert(StatusContract.TABLE_DISH, null, dishStructure.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.papas);
        dishStructure = new DishStructure("BURGER","Afternoon","9.000","30:00 min",ImageCodeClass.encodeToBase64(imageBitmap),"Pan Integral, con queso cheddar fundido, cebolla, lechuga y papas fritas en ripio y con doble carne");
        db.insert(StatusContract.TABLE_DISH, null, dishStructure.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.papas);
        dishStructure = new DishStructure("PAPAS FRANCESAS","Evening","13.000","18:00 min",ImageCodeClass.encodeToBase64(imageBitmap),"Papas acompañadas de queso cheddar derretido y tocineta");
        db.insert(StatusContract.TABLE_DISH, null, dishStructure.toContentValues());





        imageBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.jugomaracuya);
        DrinkStructure drinkStructure = new DrinkStructure("JUGO MARACUYA","3.000",ImageCodeClass.encodeToBase64(imageBitmap),"Maracuya y 100gr de azucar");
        db.insert(StatusContract.TABLE_DRINK, null, drinkStructure.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.jugonaranja);
        drinkStructure = new DrinkStructure("JUGO NARANJA","2.000",ImageCodeClass.encodeToBase64(imageBitmap),"Naranja y 50gr de azucar");
        db.insert(StatusContract.TABLE_DRINK, null, drinkStructure.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.limonada);
        drinkStructure = new DrinkStructure("LIMONADA","3.000",ImageCodeClass.encodeToBase64(imageBitmap),"Limon, Coco y 100 gr de azucar con hielo");
        db.insert(StatusContract.TABLE_DRINK, null, drinkStructure.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.milo);
        drinkStructure = new DrinkStructure("MILO FRIO","2.000",ImageCodeClass.encodeToBase64(imageBitmap),"Sobre de Milo, Leche, Hielo y 150gr de azucar");
        db.insert(StatusContract.TABLE_DRINK, null, drinkStructure.toContentValues());

        imageBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.limonada);
        drinkStructure = new DrinkStructure("LIMONADA HIERBABUENA","5.000",ImageCodeClass.encodeToBase64(imageBitmap),"Hierba Buena, Limon, Hielo, y 200gr de azucar");
        db.insert(StatusContract.TABLE_DRINK, null, drinkStructure.toContentValues());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
