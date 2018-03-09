package co.edu.udea.compumovil.gr01_20181.lab2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import co.edu.udea.compumovil.gr01_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.DishStructure;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.UserStructure;

public class DishActivity extends AppCompatActivity implements View.OnClickListener {


    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView photoImageView, imageLoad;
    private EditText nameDishEditText;
    private EditText priceDishEditText;
    private Toolbar myToolbar;
    private EditText ingredientsDishEditText;
    private TextView durationTextView;
    private CheckBox morningCheckBox;
    private CheckBox afternoonCheckBox;
    private CheckBox eveningCheckBox;
    private Button save;
    private Uri imageUri;
    private Bitmap photoBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        imageUri = null;
        myToolbar = findViewById(R.id.toolbar_dish);
        nameDishEditText = findViewById(R.id.nameDish);
        priceDishEditText = findViewById(R.id.price);
        ingredientsDishEditText = findViewById(R.id.ingredients);
        durationTextView = findViewById(R.id.duration);
        morningCheckBox = findViewById(R.id.morningCheckBox);
        afternoonCheckBox = findViewById(R.id.afternoonCheckBox);
        eveningCheckBox = findViewById(R.id.eveningCheckBox);
        photoImageView = findViewById(R.id.photoBitmap);
        save = findViewById(R.id.savebtn);


        save.setOnClickListener(this);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.savebtn && !check()) {

            String name, price, ingredients, duration, photo, type;
            name = nameDishEditText.getText().toString();
            price = priceDishEditText.getText().toString();
            ingredients = ingredientsDishEditText.getText().toString();
            duration = durationTextView.getText().toString();
            photo = ImageCodeClass.encodeToBase64(photoBitmap);
            type = "";
            if(morningCheckBox.isChecked()){
                type += getString(R.string._morning);
            }
            if(eveningCheckBox.isChecked()){
                type += getString(R.string._evening);
            }
            if(afternoonCheckBox.isChecked()){
                type += getString(R.string._afternoon);
            }
            DbHelper dbHelper = new DbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            photoImageView.setImageResource(R.drawable.ic_camera);
            nameDishEditText.setText("");
            priceDishEditText.setText("");
            ingredientsDishEditText.setText("");
            durationTextView.setText("");
            morningCheckBox.setChecked(false);
            afternoonCheckBox.setChecked(false);
            eveningCheckBox.setChecked(false);

            DishStructure dishDb = new DishStructure(name, price, price, duration, photo, ingredients);
            Snackbar.make(view, R.string.logupS, Snackbar.LENGTH_SHORT).show();
            db.insert(StatusContract.TABLE_DISH, null, dishDb.toContentValues());


        } else {
            Toast.makeText(getApplicationContext(), "Información Incompleta", Toast.LENGTH_SHORT).show();

        }

    }

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
                photoImageView.setImageResource(R.drawable.ic_camera);
                nameDishEditText.setText("");
                priceDishEditText.setText("");
                ingredientsDishEditText.setText("");
                durationTextView.setText("");
                morningCheckBox.setChecked(false);
                afternoonCheckBox.setChecked(false);
                eveningCheckBox.setChecked(false);
                imageUri = null;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean check() {
        return (((nameDishEditText.getText()).toString().equals("")) ||
                ((priceDishEditText.getText()).equals("")) ||
                ((ingredientsDishEditText.getText()).toString().equals("")) ||
                ((durationTextView.getText()).toString().equals("")) ||
                (!morningCheckBox.isChecked() && !eveningCheckBox.isChecked() &&
                        !afternoonCheckBox.isChecked()) || (imageUri == null));
    }

    public void onCheckboxClicked(View view) {
    }

    public void showPickerDialog(View view) {
        LayoutInflater l = getLayoutInflater();
        final View v = l.inflate(R.layout.picker_layout, null);
        final FrameLayout layout = new FrameLayout(this);
        layout.addView(v, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));
        NumberPicker numberPicker = v.findViewById(R.id.minute_picker);
        numberPicker.setMaxValue(60);
        numberPicker.setMinValue(0);
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        numberPicker = v.findViewById(R.id.second_picker);
        numberPicker.setMaxValue(60);
        numberPicker.setMinValue(0);
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.duration);
        alertDialogBuilder.setView(layout);
        final NumberPicker minutePicker = v.findViewById(R.id.minute_picker);
        final NumberPicker secondPicker = numberPicker;
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                String minute = String.valueOf(minutePicker.getValue());
                                String second = String.valueOf(secondPicker.getValue());
                                if (minute.length() == 1) {
                                    minute = "0" + minute;
                                }
                                if (second.length() == 1) {
                                    second = "0" + second;
                                }
                                TextView tv = (TextView) findViewById(R.id.duration);
                                tv.setText(minute + ":" + second + " min");

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


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
                    photoImageView.setImageBitmap(image);

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
