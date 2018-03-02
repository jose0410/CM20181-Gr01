package co.edu.udea.compumovil.gr01_20181.labscm20181.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import co.edu.udea.compumovil.gr01_20181.labscm20181.R;

public class Drink extends AppCompatActivity implements View.OnClickListener{

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView photoDrinkImageView, imageLoad;
    private EditText nameDrinkEditText;
    private EditText priceDrinkEditText;
    private EditText ingredientsDrinkEditText;
    private String uploadNameDrink = "",uploadIngredientsDrink, uploadImageDrink;
    private int uploadPriceDrink = 0;
    private TextView nameLoad, priceLoad, ingredientsLoad;
    private Button save;
    private Uri imageUri;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        Toolbar myToolbar = findViewById(R.id.toolbar_drink);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        photoDrinkImageView = findViewById(R.id.photoDrink);
        nameDrinkEditText = findViewById(R.id.nameDrink);
        priceDrinkEditText = findViewById(R.id.priceDrink);
        ingredientsDrinkEditText = findViewById(R.id.ingredientsDrink);

        nameLoad = findViewById(R.id.nameLoad);
        priceLoad = findViewById(R.id.priceLoad);
        ingredientsLoad = findViewById(R.id.ingredientsLoad);
        imageLoad = findViewById(R.id.imageLoad);

        save = findViewById(R.id.savebtn);

        save.setOnClickListener(this);

        loadData();
        nameLoad.setText(uploadNameDrink);
        priceLoad.setText(String.valueOf(uploadPriceDrink));
        ingredientsLoad.setText(uploadIngredientsDrink);
        imageLoad.setImageBitmap(decodeBase64(uploadImageDrink));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.savebtn) {
            uploadNameDrink = nameDrinkEditText.getText().toString();
            uploadPriceDrink = Integer.parseInt(priceDrinkEditText.getText().toString());
            uploadIngredientsDrink = ingredientsDrinkEditText.getText().toString();
            uploadImageDrink = encodeToBase64(bitmap);

            nameLoad.setText(uploadNameDrink);
            priceLoad.setText(String.valueOf(uploadPriceDrink));
            ingredientsLoad.setText(uploadIngredientsDrink);
            imageLoad.setImageBitmap(decodeBase64(uploadImageDrink));

            photoDrinkImageView.setImageResource(R.drawable.ic_camera);
            nameDrinkEditText.setText("");
            priceDrinkEditText.setText("");
            ingredientsDrinkEditText.setText("");

            saveData();


        }
    }

    public void saveData(){
        SharedPreferences preferDrink = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editorDrink = preferDrink.edit();
        editorDrink.putString("NameDrink", uploadNameDrink);
        editorDrink.putInt("PriceDrink", uploadPriceDrink);
        editorDrink.putString("IngredientsDrink", uploadIngredientsDrink);
        editorDrink.putString("PhotoDrink", uploadImageDrink);

        editorDrink.apply();
    }

    public void loadData(){
        SharedPreferences prefer = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefer.edit();
        uploadNameDrink = prefer.getString("NameDrink", "");
        uploadPriceDrink = prefer.getInt("PriceDrink", 0);
        uploadIngredientsDrink = prefer.getString("IngredientsDrink", "");
        uploadImageDrink = prefer.getString("PhotoDrink", "");

        editor.apply();
    }

    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_others, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (imageUri !=null) {
            savedInstanceState.putParcelable("photo", imageUri);
        }


        savedInstanceState.putString("nameDish", nameDrinkEditText.getText().toString());
        savedInstanceState.putString("priceDish", priceDrinkEditText.getText().toString());
        savedInstanceState.putString("ingredientsDish", ingredientsDrinkEditText.getText().toString());


    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        nameDrinkEditText.setText(savedInstanceState.getString("nameDish"));
        priceDrinkEditText.setText(savedInstanceState.getString("priceDish"));
        ingredientsDrinkEditText.setText(savedInstanceState.getString("ingredientsDish"));
        imageUri = (Uri) savedInstanceState.getParcelable("photo");
        if (imageUri !=null) {
            photoDrinkImageView.setImageURI(imageUri);
        }
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

                    // get a bitmap from the stream.
                    Bitmap image = BitmapFactory.decodeStream(inputStream);


                    // show the image to the user
                    photoDrinkImageView.setImageBitmap(image);

                    bitmap = image;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }

            }
        }
    }
}
