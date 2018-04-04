package co.edu.udea.compumovil.gr01_20181.lab2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import co.edu.udea.compumovil.gr01_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.DishStructure;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;

public class DishActivity extends AppCompatActivity implements View.OnClickListener {


    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int EXTRAS_CODE = 1;
    private ImageView photoImageView;
    private EditText nameDishEditText;
    private EditText priceDishEditText;
    private Toolbar myToolbar;
    private EditText ingredientsDishEditText;
    private TextView durationTextView;
    private CheckBox morningCheckBox;
    private CheckBox afternoonCheckBox;
    private CheckBox eveningCheckBox;
    private String mail, name;
    private Button save;
    private Bitmap photoBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        photoBitmap = null;
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
            DishStructure dishDb = new DishStructure(name, type, price, duration, photo, ingredients);
            Snackbar.make(view, R.string.logupS, Snackbar.LENGTH_SHORT).show();
            db.insert(StatusContract.TABLE_DISH, null, dishDb.toContentValues());

            clear();


        } else {
            Toast.makeText(getApplicationContext(), "Información Incompleta", Toast.LENGTH_SHORT).show();

        }

    }

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
                clear();
                break;
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void clear() {

        photoImageView.setImageResource(R.drawable.ic_camera);
        nameDishEditText.setText("");
        priceDishEditText.setText("");
        ingredientsDishEditText.setText("");
        durationTextView.setText("");
        morningCheckBox.setChecked(false);
        afternoonCheckBox.setChecked(false);
        eveningCheckBox.setChecked(false);
        photoBitmap = null;

    }

    public boolean check() {
        return (((nameDishEditText.getText()).toString().equals("")) ||
                ((priceDishEditText.getText()).equals("")) ||
                ((ingredientsDishEditText.getText()).toString().equals("")) ||
                ((durationTextView.getText()).toString().equals("")) ||
                (!morningCheckBox.isChecked() && !eveningCheckBox.isChecked() &&
                        !afternoonCheckBox.isChecked()) || (photoBitmap == null));
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
        ImageCodeClass.photoGallery(view,this,this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK) {

            Bundle b = data.getExtras();

            Bitmap selectedImage = b.getParcelable("data");
            photoImageView.setImageBitmap(selectedImage);
            photoBitmap = selectedImage;


        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(StatusContract.Column_User.MAIL,mail);
        savedInstanceState.putString(StatusContract.Column_User.NAME,name);

    }


}
