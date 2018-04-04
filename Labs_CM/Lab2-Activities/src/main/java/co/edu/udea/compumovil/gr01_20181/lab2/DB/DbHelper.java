package co.edu.udea.compumovil.gr01_20181.lab2.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
                + StatusContract.Column_User.SESSION + " TEXT NOT NULL,"
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



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
