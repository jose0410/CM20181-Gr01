package co.edu.udea.compumovil.gr01_20181.labscm20181.views;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import co.edu.udea.compumovil.gr01_20181.labscm20181.R;
import co.edu.udea.compumovil.gr01_20181.labscm20181.views.MainActivity;

public class SplashActivity extends AppCompatActivity {
    long Delay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Timer RunSplash = new Timer();
        final TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

                finish();

            }
        };
        RunSplash.schedule(ShowSplash, Delay);
    }
}
