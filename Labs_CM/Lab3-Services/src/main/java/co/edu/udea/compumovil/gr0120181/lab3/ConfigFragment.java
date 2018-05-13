package co.edu.udea.compumovil.gr0120181.lab3;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.widget.Toast;



/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ConfigFragment extends PreferenceFragment {

    /*DbHelper dbHelper;

    public ConfigFragment(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }*/

    public ConfigFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        /*addPreferencesFromResource(R.xml.app_preferences);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT " + StatusContract.Column_User.USER +
                " , " + StatusContract.Column_User.NAME +
                " , " + StatusContract.Column_User.PICTURE +
                " , " + StatusContract.Column_User.MAIL +
                " FROM " + StatusContract.TABLE_USER +
                " WHERE " + StatusContract.Column_User.STATE + " = 'ACTIVO' ", null);
        c.moveToFirst();

        findPreference("user").setTitle(c.getString(0));
        findPreference("name").setTitle(c.getString(1));
        findPreference("mail").setTitle(c.getString(3));
        findPreference("photo").setLayoutResource(R.layout.profile_preference_photo);
        updateSettings(findPreference("user"));
        updateSettings(findPreference("name"));
        updateSettings(findPreference("mail"));
        updateSettings(findPreference("password"));*/


    }

    /*private  void updateSettings(Preference preference){
        preference.setOnPreferenceChangeListener(listener);
        listener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(
                preference.getContext()).getString(preference.getKey(),""));
    }*/

    /*private  Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if(preference instanceof EditTextPreference){
                ContentValues changes = new ContentValues();
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                if(preference.getKey().equals("user") && newValue != ""){
                    changes.put("user",newValue.toString());
                    findPreference("user").setTitle(newValue.toString());

                }

                if(preference.getKey().equals("name") && newValue != ""){
                    changes.put("name",newValue.toString());
                    findPreference("name").setTitle(newValue.toString());
                }

                if(preference.getKey().equals("mail") && newValue != ""){
                    changes.put("mail",newValue.toString());
                    findPreference("mail").setTitle(newValue.toString());
                }

                if(preference.getKey().equals("password") && newValue != ""){
                    changes.put("password",newValue.toString());
                }



                if(changes.size()>0) {
                    db.update(StatusContract.TABLE_USER, changes, StatusContract.Column_User.STATE + " = 'ACTIVO'", null);
                    changes = null;
                }
            }
            return false;
        }
    };*/




}
