package co.edu.udea.compumovil.gr01_20181.lab2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import co.edu.udea.compumovil.gr01_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.HomeFragment;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FloatingActionButton fabDish, fabDrink;
    private FloatingActionMenu fabFloatingActionMenu;
    private NavigationView navigationView;
    private TextView nameDrawer, namePTextView;
    private ImageView photoImageView;
    private Toolbar toolbar;
    private String mail, name;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        fabDish = findViewById(R.id.addDish);
        fabDrink = findViewById(R.id.addDrink);
        fabFloatingActionMenu = findViewById(R.id.request);
        nameDrawer = findViewById(R.id.nameDrawer);
        photoImageView = findViewById(R.id.photoProfile);
        namePTextView = findViewById(R.id.nameProfile);
        photoImageView = findViewById(R.id.photoProfile);


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        fabDish.setOnClickListener(this);
        fabDrink.setOnClickListener(this);
        View headerView = navigationView.getHeaderView(0);
        nameDrawer = headerView.findViewById(R.id.nameDrawer);

        if(getIntent().getExtras()!=null){

            Bundle bundle = getIntent().getExtras();
            mail = bundle.getString(StatusContract.Column_User.MAIL);
            name = bundle.getString(StatusContract.Column_User.NAME);
            nameDrawer.setText(name);
        }

        FragmentTransaction ftr = getSupportFragmentManager().beginTransaction();
        ftr.replace(R.id.menu, new HomeFragment());
        ftr.addToBackStack("Home");
        ftr.commit();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DbHelper dbHelper;
        SQLiteDatabase db;
        FragmentTransaction ft;
        switch (item.getItemId()) {
            case R.id.menuP:
                namePTextView.setVisibility(View.GONE);
                photoImageView.setVisibility(View.GONE);
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.menu, new HomeFragment());
                ft.commit();
                fabFloatingActionMenu.showMenu(true);
                fabDish.setVisibility(View.VISIBLE);
                fabDrink.setVisibility(View.VISIBLE);
                break;

            case R.id.dishes:
                namePTextView.setVisibility(View.GONE);
                photoImageView.setVisibility(View.GONE);
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.menu, new DishesFragment());
                ft.commit();
                fabFloatingActionMenu.showMenu(true);
                fabDish.setVisibility(View.VISIBLE);
                fabDrink.setVisibility(View.GONE);
                break;

            case R.id.drinks:

                namePTextView.setVisibility(View.GONE);
                photoImageView.setVisibility(View.GONE);
                FragmentTransaction ftr1 = getSupportFragmentManager().beginTransaction();
                ftr1.replace(R.id.menu, new DrinksFragment());
                ftr1.commit();
                fabFloatingActionMenu.showMenu(true);
                fabDish.setVisibility(View.GONE);
                fabDrink.setVisibility(View.VISIBLE);
                break;

            case R.id.profile:
                dbHelper = new DbHelper(this);
                db = dbHelper.getReadableDatabase();

                 namePTextView.setVisibility(View.VISIBLE);
                photoImageView.setVisibility(View.VISIBLE);
                Cursor c = db.rawQuery("select " + StatusContract.Column_User.USER +
                        " , " + StatusContract.Column_User.NAME +
                        " , " + StatusContract.Column_User.PICTURE +
                        " from " + StatusContract.TABLE_USER +
                        " where " + StatusContract.Column_User.MAIL + " = '" + mail + "' ", null);
                c.moveToFirst();
                namePTextView.setText(c.getString(1));
                photoImageView.setImageBitmap(ImageCodeClass.decodeBase64(c.getString(2)));
                db.close();

                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.menu, new ProfileFragment(mail, c.getString(0)));
                ft.commit();
                fabFloatingActionMenu.hideMenu(true);

                break;

            case R.id.conf:
                namePTextView.setVisibility(View.GONE);
                photoImageView.setVisibility(View.GONE);
                startActivity(new Intent(this,AppPreferences.class));
                break;

            case R.id.logout:
                namePTextView.setVisibility(View.GONE);
                photoImageView.setVisibility(View.GONE);

                dbHelper = new DbHelper(getApplication().getApplicationContext());
                db = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(StatusContract.Column_User.STATE, "INACTIVO");
                db.updateWithOnConflict(StatusContract.TABLE_USER, contentValues,
                        StatusContract.Column_User.MAIL + "='" + mail + "'", null, SQLiteDatabase.CONFLICT_IGNORE);
                db.close();
                Intent other = new Intent(getApplication().getApplicationContext(), LoginActivity.class);
                Bundle bundleP = new Bundle();
                onSaveInstanceState(bundleP);
                other.putExtras(bundleP);
                finish();
                startActivity(other);


                break;

            case R.id.about:
                namePTextView.setVisibility(View.GONE);
                photoImageView.setVisibility(View.GONE);
                break;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        Intent intent;
        Bundle bun = new Bundle();
        switch (view.getId()) {
            case R.id.addDish:
                intent = new Intent(this, DishActivity.class);
                startActivity(intent);
                break;
            case R.id.addDrink:
                intent = new Intent(this, DrinkActivity.class);
                startActivity(intent);
                break;
        }
    }
}
