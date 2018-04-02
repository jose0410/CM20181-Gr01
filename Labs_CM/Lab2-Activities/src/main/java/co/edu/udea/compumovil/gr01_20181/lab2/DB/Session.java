package co.edu.udea.compumovil.gr01_20181.lab2.DB;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import co.edu.udea.compumovil.gr01_20181.lab2.AppPreferences;

/**
 * Created by user on 1/04/2018.
 */

public class Session {
    private static DbHelper dbhelper; // static DbHelper
    private static Session session;
    protected Session() {
    }

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void initDbHelper(AppPreferences.SettingsFragment ctx){
        dbhelper = new DbHelper(ctx.getContext());
    }

    public DbHelper getDbHelper()
    {
        return dbhelper;
    }
}
