package co.edu.udea.compumovil.gr0120181.lab3;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Random;


public class DrinkActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int EXTRAS_CODE = 1;

    private static String URL = "http://192.168.0.11:8080/api/drink";
    private ImageView photoDrinkImageView;
    private EditText nameDrinkEditText;
    private Toolbar myToolbar;
    private EditText priceDrinkEditText;
    private EditText ingredientsDrinkEditText;
    private Button save;
    private Uri imageUri;
    private String mail, name;
    private Bitmap photoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        imageUri =  null;
        myToolbar = findViewById(R.id.toolbar_drink);
        photoDrinkImageView = findViewById(R.id.photoDrink);
        nameDrinkEditText = findViewById(R.id.nameDrink);
        priceDrinkEditText = findViewById(R.id.priceDrink);
        ingredientsDrinkEditText = findViewById(R.id.ingredientsDrink);
        save = findViewById(R.id.savebtn);

        save.setOnClickListener(this);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            /*mail = bundle.getString(StatusContract.Column_User.MAIL);
            name = bundle.getString(StatusContract.Column_User.NAME);*/
            Intent intent = new Intent();
            Bundle bundleP = new Bundle();
            onSaveInstanceState(bundleP);
            intent.putExtras(bundleP);
            setResult(EXTRAS_CODE, intent);
        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.savebtn && !check()) {
            String name, price, ingredients, photo;
            name = nameDrinkEditText.getText().toString();
            price = priceDrinkEditText.getText().toString();
            ingredients = ingredientsDrinkEditText.getText().toString();
            photo = ImageCodeClass.encodeToBase64(photoBitmap);

            /*DrinkStructure drinkDb = new DrinkStructure(name, price, photo, ingredients);
            Snackbar.make(view, R.string.logupS, Snackbar.LENGTH_SHORT).show();
            db.insert(StatusContract.TABLE_DRINK, null, drinkDb.toContentValues());*/

            JSONObject requestInfo = new JSONObject();
            try {
                Random randomGenerator = new Random();
                requestInfo.put("id",randomGenerator.nextInt(1000000000));
                requestInfo.put("name",name);
                requestInfo.put("price",price);
                requestInfo.put("ingredients", ingredients);
                requestInfo.put("picture", photo);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,requestInfo, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("msg").equals("Drink added")) {
                            Toast.makeText(getApplicationContext(), response.getString("msg"), Toast.LENGTH_LONG).show();
                            photoDrinkImageView.setImageResource(R.drawable.ic_camera);
                            nameDrinkEditText.setText("");
                            priceDrinkEditText.setText("");
                            ingredientsDrinkEditText.setText("");
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
            });
            RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
            rQueue.add(request);

        } else {
            Toast.makeText(getApplicationContext(), "Information Incomplet", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_others, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit_o:
                finish();
                break;
            case R.id.clean:
                photoDrinkImageView.setImageResource(R.drawable.ic_camera);
                nameDrinkEditText.setText("");
                priceDrinkEditText.setText("");
                ingredientsDrinkEditText.setText("");
                imageUri = null;
                break;
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean check() {
        return (((nameDrinkEditText.getText()).toString().equals("")) ||
                ((priceDrinkEditText.getText()).toString().equals("")) ||
                ((ingredientsDrinkEditText.getText()).toString().equals("")) ||
                (photoBitmap == null));
    }

    public void onImageGalleryClicked(View view) {
        ImageCodeClass.photoGallery(view,this,this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK) {

            Bundle b = data.getExtras();

            Bitmap selectedImage = b.getParcelable("data");
            photoDrinkImageView.setImageBitmap(selectedImage);
            photoBitmap = selectedImage;


        }
    }


    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        /*savedInstanceState.putString(StatusContract.Column_User.MAIL,mail);
        savedInstanceState.putString(StatusContract.Column_User.NAME,name);*/

    }



}
