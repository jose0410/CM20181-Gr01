package co.edu.udea.compumovil.gr0120181.lab3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;


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

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, new LoginFragment());
                    ft.commit();


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
