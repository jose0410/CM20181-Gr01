package co.edu.udea.compumovil.gr01_20181.lab2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import co.edu.udea.compumovil.gr01_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;

public class LoginActivity extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    long Delay = 1000;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new SplashFragment());
        ft.commit();

        Timer RunSplash = new Timer();
        final TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                DbHelper dbHelper = new DbHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor c = db.rawQuery("select "+ StatusContract.Column_User.MAIL +
                        " from " + StatusContract.TABLE_USER +
                        " where " + StatusContract.Column_User.STATE + " = 'ACTIVO' " , null);


                if(c.moveToFirst()){
                    mail = c.getString(0);
                    Intent other = new Intent(getApplicationContext(), MainActivity.class);
                    Bundle bundleP = new Bundle();
                    bundleP.putString(StatusContract.Column_User.MAIL,mail);
                    other.putExtras(bundleP);
                    finish();
                    startActivity(other);


                }else{

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, new LoginFragment());
                    ft.commit();

                }
            }
        };

        // Start the timer
        RunSplash.schedule(ShowSplash, Delay);

    }

    public void photoGallery(View v) {
        ImageCodeClass.photoGallery(v,this, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK) {

            Bundle b = data.getExtras();

            Bitmap selectedImage = b.getParcelable("data");
            LogupFragment frag = (LogupFragment) getSupportFragmentManager().findFragmentByTag("registrarUsuario");
            frag.setPhotoBitmap(selectedImage);

        }
    }

}
