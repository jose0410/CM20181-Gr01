package co.edu.udea.compumovil.gr0120181.lab3;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private static String URL = "http://192.168.0.11:8080/api/user/";
    private String urlFinal;

    private Button loginButton, logupButton;
    private EditText userEditText, passwordEditText;
    private String user, pass;
    private CheckBox sesionCheckButton;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = view.findViewById(R.id.login);
        logupButton = view.findViewById(R.id.logup);
        userEditText = view.findViewById(R.id.user);
        passwordEditText = view.findViewById(R.id.password);
        sesionCheckButton = view.findViewById(R.id.session);
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

                try {
                    urlFinal = URL + java.net.URLEncoder.encode(userEditText.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                StringRequest request = new StringRequest(Request.Method.GET, urlFinal, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        if (!s.equals("{'msg':'User/Password is incorrect'}")) {
                            JSONObject user;
                            try {
                                user = new JSONObject(s);
                                if (user.getString("password").equals(passwordEditText.getText().toString())) {
                                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    intent.putExtra("User", user.toString());
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getContext(), "User/Password is incorrect", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), "User/Password is incorrect", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(), "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("user", userEditText.getText().toString());
                        parameters.put("password", passwordEditText.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(getContext());
                rQueue.add(request);
                break;
        }


    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("user", user);
        savedInstanceState.putString("pass", pass);

    }

}
