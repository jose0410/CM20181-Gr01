package co.edu.udea.compumovil.gr01_20181.lab2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import co.edu.udea.compumovil.gr01_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.Session;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.UserStructure;


public class AppPreferences extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.add(android.R.id.content, settingsFragment, "SETTINGS_FRAGMENT");
        fragmentTransaction.commit();


    }


    public static class SettingsFragment extends PreferenceFragment{

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.app_preferences);
            Session.getInstance().initDbHelper(this);
            bindSummaryValue(findPreference("user"));
            bindSummaryValue(findPreference("mail"));
            bindSummaryValue(findPreference("password"));
        }

    }

    private static void bindSummaryValue(Preference preference){
        preference.setOnPreferenceChangeListener(listener);
        listener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(
                preference.getContext()).getString(preference.getKey(),""));
    }

    private static Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if(preference instanceof EditTextPreference){
                ContentValues changes = new ContentValues();
                DbHelper dbHelper = Session.getInstance().getDbHelper();
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                if(preference.getKey().equals("user") && newValue != ""){
                    changes.put("user",newValue.toString());
                }

                if(preference.getKey().equals("mail") && newValue != ""){
                    changes.put("mail",newValue.toString());
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
    };

}

