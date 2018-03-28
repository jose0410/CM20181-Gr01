package co.edu.udea.compumovil.gr01_20181.lab2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import co.edu.udea.compumovil.gr01_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.DrinkStructure;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;

public class DrinkActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int EXTRAS_CODE = 1;
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
            mail = bundle.getString(StatusContract.Column_User.MAIL);
            name = bundle.getString(StatusContract.Column_User.NAME);
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

            DbHelper dbHelper = new DbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            DrinkStructure drinkDb = new DrinkStructure(name, price, photo, ingredients);
            Snackbar.make(view, R.string.logupS, Snackbar.LENGTH_SHORT).show();
            db.insert(StatusContract.TABLE_DRINK, null, drinkDb.toContentValues());

            photoDrinkImageView.setImageResource(R.drawable.ic_camera);
            nameDrinkEditText.setText("");
            priceDrinkEditText.setText("");
            ingredientsDrinkEditText.setText("");

        } else {
            Toast.makeText(getApplicationContext(), "Información Incompleta", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_others, menu);
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
                ((priceDrinkEditText.getText()).equals("")) ||
                ((ingredientsDrinkEditText.getText()).toString().equals("")) ||
                (imageUri == null));
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
        savedInstanceState.putString(StatusContract.Column_User.MAIL,mail);
        savedInstanceState.putString(StatusContract.Column_User.NAME,name);

    }



}
