package co.edu.udea.compumovil.gr01_20181.lab2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import co.edu.udea.compumovil.gr01_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.DishStructure;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.DrinkStructure;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;

public class DrinkActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView photoDrinkImageView, imageLoad;
    private EditText nameDrinkEditText;
    private Toolbar myToolbar;
    private EditText priceDrinkEditText;
    private EditText ingredientsDrinkEditText;
    private Button save;
    private Uri imageUri;
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
        // invoke the image gallery using an implict intent.
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        // where do we want to find the data?
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        // finally, get a URI representation
        Uri data = Uri.parse(pictureDirectoryPath);

        // set the data and type.  Get all image types.
        photoPickerIntent.setDataAndType(data, "image/*");

        // we will invoke this activity, and get something back from it.
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // if we are here, everything processed successfully.
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                // if we are here, we are hearing back from the image gallery.

                // the address of the image on the SD Card.
                imageUri = data.getData();

                // declare a stream to read the image data from the SD Card.
                InputStream inputStream;

                // we are getting an input stream, based on the URI of the image.
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    // get a photoBitmap from the stream.
                    Bitmap image = BitmapFactory.decodeStream(inputStream);


                    // show the image to the user
                    photoDrinkImageView.setImageBitmap(image);

                    photoBitmap = image;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }

            }
        }
    }


}
