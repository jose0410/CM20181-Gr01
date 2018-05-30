package co.edu.udea.compumovil.gr0120181.lab3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.SearchView.OnQueryTextListener;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, OnQueryTextListener, MenuItem.OnActionExpandListener {

    private FloatingActionButton fabDish, fabDrink;
    private FloatingActionMenu fabFloatingActionMenu;
    private NavigationView navigationView;
    private TextView nameDrawer, namePTextView;
    private FrameLayout frameLayout;
    private ImageView photoImageView, photoUserNavBar;
    private Toolbar toolbar;
    private String mail, name;
    private DrawerLayout drawer;
    private Intent intent;
    private JSONObject user;
    private Bundle bundle;
    private DishesFragment dishesFragment;
    private DrinksFragment drinksFragment;
    private static String URLphotoupdate = ":8080/api/updateuser/";
    private static String URL = "";
    public static final int IMAGE_GALLERY_REQUEST = 20;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_others, menu);
        menu.getItem(2).setVisible(false);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);

        searchItem.setOnActionExpandListener(this);

        return super.onCreateOptionsMenu(menu);
    }

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
        frameLayout = findViewById(R.id.menu);
        photoUserNavBar = findViewById(R.id.photoUserNavBar);


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        fabDish.setOnClickListener(this);
        fabDrink.setOnClickListener(this);
        View headerView = navigationView.getHeaderView(0);
        nameDrawer = headerView.findViewById(R.id.nameDrawer);

        if(getIntent().getExtras()!=null){

            Bundle bundle = getIntent().getExtras();
            intent = getIntent();
            URLphotoupdate = intent.getStringExtra("IP") + URLphotoupdate;

            try {
                user = new JSONObject(intent.getStringExtra("User"));
                nameDrawer.setText(user.getString("name"));

                URLphotoupdate += java.net.URLEncoder.encode(user.getString("user"), "UTF-8");
                if(URL.equals("")){
                    URL = intent.getStringExtra("IP") ;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        bundle = new Bundle();

        bundle.putString("IP",URL);



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
        FragmentTransaction ft;
        frameLayout.removeAllViews();

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
                dishesFragment =  new DishesFragment();
                dishesFragment.setArguments(bundle);
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.menu,dishesFragment);
                ft.commit();
                fabFloatingActionMenu.showMenu(true);
                fabDish.setVisibility(View.VISIBLE);
                fabDrink.setVisibility(View.GONE);
                break;

            case R.id.drinks:

                namePTextView.setVisibility(View.GONE);
                photoImageView.setVisibility(View.GONE);
                drinksFragment = new DrinksFragment();
                drinksFragment.setArguments(bundle);
                FragmentTransaction ftr1 = getSupportFragmentManager().beginTransaction();
                ftr1.replace(R.id.menu, drinksFragment);
                ftr1.commit();
                fabFloatingActionMenu.showMenu(true);
                fabDish.setVisibility(View.GONE);
                fabDrink.setVisibility(View.VISIBLE);
                break;

            case R.id.profile:

                namePTextView.setVisibility(View.VISIBLE);
                photoImageView.setVisibility(View.VISIBLE);

                try {
                    namePTextView.setText(user.getString("name"));
                    photoImageView.setImageBitmap(ImageCodeClass.decodeBase64(user.getString("picture")));
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.menu, new ProfileFragment(user.getString("email"), user.getString("user")));
                    ft.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                fabFloatingActionMenu.hideMenu(true);

                break;

            case R.id.conf:
                namePTextView.setVisibility(View.GONE);
                photoImageView.setVisibility(View.GONE);
                try {
                    ConfigFragment configFragment = new ConfigFragment(user);
                    configFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.menu, configFragment)
                            .commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.logout:
                namePTextView.setVisibility(View.GONE);
                photoImageView.setVisibility(View.GONE);

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
                FragmentTransaction ftr = getSupportFragmentManager().beginTransaction();
                ftr.replace(R.id.menu, new aboutFragment());
                ftr.commit();
                fabFloatingActionMenu.hideMenu(true);

                break;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        Intent intent;
        Bundle bundleP = new Bundle();
        switch (view.getId()) {
            case R.id.addDish:
                intent = new Intent(this, DishActivity.class);
                intent.putExtra("IP",URL);
                startActivityForResult(intent,1);
                break;
            case R.id.addDrink:
                intent = new Intent(this, DrinkActivity.class);
                intent.putExtra("IP",URL);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this,"ok",Toast.LENGTH_SHORT);
       /* if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            mail = bundle.getString(StatusContract.Column_User.MAIL);
            name = bundle.getString(StatusContract.Column_User.NAME);
            nameDrawer.setText(name);
        }*/

        if (requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK) {

            Bundle b = data.getExtras();

            Bitmap selectedImage = b.getParcelable("data");
            try {
                user.put("picture",ImageCodeClass.encodeToBase64(selectedImage));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URLphotoupdate, user, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (Objects.equals(response.getString("msg"), "Update successful")) {
                            Toast.makeText(getApplicationContext(), response.getString("msg"), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Can't Register", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                    volleyError.printStackTrace();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
            rQueue.add(request);
        }


    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        /*savedInstanceState.putString(StatusContract.Column_User.MAIL,mail);
        savedInstanceState.putString(StatusContract.Column_User.NAME,name);
*/
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.menu);

        if(fragment instanceof DishesFragment){
            ((DishesFragment) fragment).filter(s);
        }

        if(fragment instanceof DrinksFragment){
            ((DrinksFragment) fragment).filter(s);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.menu);

        if(fragment instanceof DishesFragment){
            ((DishesFragment) fragment).restart();
        }

        if(fragment instanceof DrinksFragment){
            ((DrinksFragment) fragment).restart();
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void photoSettings(View v) {
        ImageCodeClass.photoGallery(v, this, this);
    }

}
