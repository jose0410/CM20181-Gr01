package co.edu.udea.compumovil.gr01_20181.lab2.DB;

import android.provider.BaseColumns;

public class StatusContract {
    public static final String DB_NAME = "lab2activities.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_USER= "usuario";
    public static final String TABLE_DISH="dish";
    public static final String TABLE_DRINK="drink";

    public class Column_User {
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String USER = "user";
        public static final String PASSWORD = "password";
        public static final String MAIL = "mail";
        public static final String PICTURE = "picture";
        public static final String STATE = "state";
        public static final String SESSION = "session";
    }
    public class Column_Dish {
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String TYPE = "type";
        public static final String PRICE = "price";
        public static final String DURATION = "duration";
        public static final String INGREDIENTS = "ingredients";
        public static final String PHOTO = "photo";
    }
    public class Column_Drink {
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String PRICE = "price";
        public static final String INGREDIENTS = "ingredients";
        public static final String PHOTO = "photo";
    }
}
