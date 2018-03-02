package co.edu.udea.compumovil.gr01_20181.labscm20181.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.NumberPicker;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import co.edu.udea.compumovil.gr01_20181.labscm20181.R;

public class Dish extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView photoImageView, imageLoad;
    private Toolbar myToolbar;
    private EditText nameDishEditText;
    private EditText priceDishEditText;
    private EditText ingredientsDishEditText;
    private TextView durationTextView;
    private TextView nameLoad,priceLoad,scheduleLoad,ingredientsLoad,durationLoad;
    private CheckBox morningCheckBox;
    private CheckBox afternoonCheckBox;
    private CheckBox eveningCheckBox;
    private Button save;
    View.OnClickListener listener;
    private Uri imageUri;
    private Bitmap bitmap;
    private String uploadName = "",uploadIngredients, uploadSchedule, uploadDuration, uploadImage;
    private int uploadPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        myToolbar = findViewById(R.id.toolbar_dish);
        nameDishEditText = findViewById(R.id.nameDish);
        priceDishEditText = findViewById(R.id.price);
        ingredientsDishEditText = findViewById(R.id.ingredients);
        durationTextView = findViewById(R.id.duration);
        morningCheckBox = findViewById(R.id.morningCheckBox);
        afternoonCheckBox = findViewById(R.id.afternoonCheckBox);
        eveningCheckBox = findViewById(R.id.eveningCheckBox);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        photoImageView = findViewById(R.id.photo);

        save = findViewById(R.id.savebtn);

        nameLoad = findViewById(R.id.nameLoad);
        priceLoad = findViewById(R.id.priceLoad);

        loadData();
        nameLoad.setText(uploadName);
        priceLoad.setText(String.valueOf(uploadPrice));

    }

    public void buttonClick(){
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.savebtn) {
                    uploadName = nameDishEditText.getText().toString();
                    uploadPrice = Integer.parseInt(priceDishEditText.getText().toString());
                    saveData();
                }
            }
        };
    }


    public void saveData(){
        SharedPreferences prefer = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefer.edit();
        editor.putString("Name",uploadName);
        editor.putInt("Price",uploadPrice);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences prefer = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefer.edit();
        uploadName = prefer.getString("Name","");
        uploadPrice = prefer.getInt("Price",0);
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

    public void onCheckboxClicked(View view) {
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

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (imageUri !=null) {
            savedInstanceState.putParcelable("photo", imageUri);
        }


        savedInstanceState.putString("nameDish", nameDishEditText.getText().toString());
        savedInstanceState.putString("priceDish", priceDishEditText.getText().toString());
        savedInstanceState.putString("ingredientsDish", ingredientsDishEditText.getText().toString());
        savedInstanceState.putString("duration", durationTextView.getText().toString());
        savedInstanceState.putBoolean("morning", morningCheckBox.isChecked());
        savedInstanceState.putBoolean("afternoon", afternoonCheckBox.isChecked());
        savedInstanceState.putBoolean("evening", eveningCheckBox.isChecked());

    }
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        nameDishEditText.setText(savedInstanceState.getString("nameDish"));
        priceDishEditText.setText(savedInstanceState.getString("priceDish"));
        ingredientsDishEditText.setText(savedInstanceState.getString("ingredientsDish"));
        durationTextView.setText(savedInstanceState.getString("duration"));
        morningCheckBox.setChecked(savedInstanceState.getBoolean("morning"));
        afternoonCheckBox.setChecked(savedInstanceState.getBoolean("afternoon"));
        eveningCheckBox.setChecked(savedInstanceState.getBoolean("evening"));
        imageUri = (Uri) savedInstanceState.getParcelable("photo");
        if (imageUri !=null) {
            photoImageView.setImageURI(imageUri);
        }
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

                    // get a bitmap from the stream.
                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    // show the image to the user
                    photoImageView.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }

            }
        }
    }
}
