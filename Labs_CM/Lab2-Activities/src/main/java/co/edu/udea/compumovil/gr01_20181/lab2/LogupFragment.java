package co.edu.udea.compumovil.gr01_20181.lab2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import co.edu.udea.compumovil.gr01_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.UserStructure;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogupFragment extends Fragment implements View.OnClickListener {


    private Button logupButton;
    private ImageView userPhoto;
    private Bitmap photoBitmap = null;



    public LogupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_up, container, false);

        userPhoto = view.findViewById(R.id.photoUser);
        logupButton = (Button) view.findViewById(R.id.logupButton);
        logupButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        EditText editTextName, editTextUser, editTextMail, editTextPassword;
        ImageView imageViewPhoto;
        String name, user, mail, password, photo;

        editTextName = (EditText) getView().findViewById(R.id.userNameLogup);
        editTextUser = (EditText) getView().findViewById(R.id.userLogup);
        editTextMail = (EditText) getView().findViewById(R.id.emailLogup);
        editTextPassword = (EditText) getView().findViewById(R.id.passwordLogup);

        name = editTextName.getText().toString();
        user = editTextUser.getText().toString();
        mail = editTextMail.getText().toString();
        password = editTextPassword.getText().toString();

        if (name.equals("") || user.equals("") || mail.equals("") ||password.equals("")|| photoBitmap ==null) {
            Toast.makeText(getContext(), "Falta Información", Toast.LENGTH_SHORT).show();
        } else {
            photo = ImageCodeClass.encodeToBase64(photoBitmap);
            DbHelper dbHelper = new DbHelper(getContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query1 = "SELECT " + StatusContract.Column_User.USER + " FROM " + StatusContract.TABLE_USER + " WHERE " + StatusContract.Column_User.USER + " = '" + user + "'";
            String query2 = "SELECT " + StatusContract.Column_User.MAIL + " FROM " + StatusContract.TABLE_USER + " WHERE " + StatusContract.Column_User.MAIL + " = '" + mail + "'";
            Cursor c1 = db.rawQuery(query1, null);
            Cursor c2 = db.rawQuery(query2,null);

            if (c1.getCount()!=0||c2.getCount()!=0) {
                if(c1.getCount()!=0&&c2.getCount()!=0){
                    Toast.makeText(getContext(),"El correo y el usuario están en uso",Toast.LENGTH_SHORT).show();
                }else{
                    if(c1.getCount()!=0){
                        Toast.makeText(getContext(),"El usuario ya está en uso",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(),"El correo ya está en uso",Toast.LENGTH_SHORT).show();
                    }
                }
            } else {

                UserStructure userDb = new UserStructure(name,user,password,mail, photo);
                Snackbar.make(view, R.string.logupS, Snackbar.LENGTH_SHORT).show();
                db.insert(StatusContract.TABLE_USER,null,userDb.toContentValues());
                Fragment frag = new LoginFragment();
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
            }
        }

    }

    public void setPhotoBitmap(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
        userPhoto.setImageBitmap(photoBitmap);
    }




}
