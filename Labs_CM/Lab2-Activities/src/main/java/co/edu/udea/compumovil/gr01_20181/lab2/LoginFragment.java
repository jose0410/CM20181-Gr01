package co.edu.udea.compumovil.gr01_20181.lab2;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.udea.compumovil.gr01_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private Button loginButton, logupButton;
    private EditText userEditText, passwordEditText;
    private String mail, name;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = (Button) view.findViewById(R.id.login);
        logupButton = (Button) view.findViewById(R.id.logup);
        userEditText = (EditText) view.findViewById(R.id.user);
        passwordEditText = (EditText) view.findViewById(R.id.password);
        loginButton.setOnClickListener(this);
        logupButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.logup:
                Fragment frag = new LogupFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag, "registrarUsuario").addToBackStack(null).commit();
                break;

            case R.id.login:
                String user = "", pass = "";
                if (userEditText.getText() != null && passwordEditText.getText() != null) {
                    user = userEditText.getText().toString();
                    pass = passwordEditText.getText().toString();
                }
                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(getContext(), "Información Incompleta", Toast.LENGTH_SHORT).show();
                } else {
                    DbHelper dbHelper = new DbHelper(getContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor c = db.rawQuery("SELECT " + StatusContract.Column_User.MAIL +
                            " FROM " + StatusContract.TABLE_USER +
                            " WHERE " + StatusContract.Column_User.USER + " = '" + user +
                            "' AND " + StatusContract.Column_User.PASSWORD + " = '" + pass + "'", null);

                    if (c.moveToFirst()) {
                        mail = c.getString(0);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(StatusContract.Column_User.STATE, "ACTIVO");
                        db.updateWithOnConflict(StatusContract.TABLE_USER, contentValues,
                                StatusContract.Column_User.USER + "='" + user + "'", null, SQLiteDatabase.CONFLICT_IGNORE);
                        Intent other = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        getActivity().finish();
                        startActivity(other);


                    } else {
                        Toast.makeText(getContext(), "Usuario o Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }
                break;
        }


    }
}
