package co.edu.udea.compumovil.gr01_20181.lab2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    long Delay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new SplashFragment());
        ft.commit();
        getSupportActionBar().hide();

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
}
