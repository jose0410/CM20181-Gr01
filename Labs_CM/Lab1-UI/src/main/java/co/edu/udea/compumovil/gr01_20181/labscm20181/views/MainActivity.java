package co.edu.udea.compumovil.gr01_20181.labscm20181.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;

import co.edu.udea.compumovil.gr01_20181.labscm20181.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionMenu materialDesignFAM;
    private FloatingActionButton fabDish, fabDrink;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        materialDesignFAM = findViewById(R.id.request);
        fabDish = findViewById(R.id.addDish);
        fabDrink = findViewById(R.id.addDrink);

        fabDish.setOnClickListener(this);
        fabDrink.setOnClickListener(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch(view.getId()){
            case R.id.addDish:
                intent = new Intent(this,Dish.class);
                startActivity(intent);
                break;
            case R.id.addDrink:
                intent = new Intent(this,Drink.class);
                startActivity(intent);
                break;
        }
    }


}
