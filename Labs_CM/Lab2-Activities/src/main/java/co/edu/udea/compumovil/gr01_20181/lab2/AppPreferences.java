package co.edu.udea.compumovil.gr01_20181.lab2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import co.edu.udea.compumovil.gr01_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.Session;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;


public class AppPreferences extends AppCompatActivity {

    private ImageView photoSettings;
    private String photo;
    public static final int IMAGE_GALLERY_REQUEST = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        photoSettings = findViewById(R.id.photoUser);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.add(android.R.id.content, settingsFragment, "SETTINGS_FRAGMENT");
        fragmentTransaction.commit();


    }

    public void photoSettings(View v) {
        ImageCodeClass.photoGallery(v,this, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK) {

            Bundle b = data.getExtras();

            Bitmap selectedImage = b.getParcelable("data");

            ContentValues changes = new ContentValues();
            changes.put("picture",ImageCodeClass.encodeToBase64(selectedImage));
            DbHelper dbHelper = Session.getInstance().getDbHelper();
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.update(StatusContract.TABLE_USER, changes, StatusContract.Column_User.STATE + " = 'ACTIVO'", null);


        }
    }


    public static class SettingsFragment extends PreferenceFragment{

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.app_preferences);
            Session.getInstance().initDbHelper(this);
            updateSettings(findPreference("user"));
            updateSettings(findPreference("mail"));
            updateSettings(findPreference("password"));
        }

    }

    private static void updateSettings(Preference preference){
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

