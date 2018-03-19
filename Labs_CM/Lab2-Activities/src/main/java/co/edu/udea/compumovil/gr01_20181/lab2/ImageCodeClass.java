package co.edu.udea.compumovil.gr01_20181.lab2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;

/**
 * Created by luisernesto on 8/03/18.
 */

public class ImageCodeClass {

    public static final int IMAGE_GALLERY_REQUEST = 20;


    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static void photoGallery(View v, Context c, Activity a) {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (ContextCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(a, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            pickImageIntent.setType("image/*");
            pickImageIntent.putExtra("crop", "true");
            pickImageIntent.putExtra("outputX", 200);
            pickImageIntent.putExtra("outputY", 200);
            pickImageIntent.putExtra("aspectX", 1);
            pickImageIntent.putExtra("aspectY", 1);
            pickImageIntent.putExtra("scale", true);
            pickImageIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            a.startActivityForResult(pickImageIntent, IMAGE_GALLERY_REQUEST);
        }
    }

}
