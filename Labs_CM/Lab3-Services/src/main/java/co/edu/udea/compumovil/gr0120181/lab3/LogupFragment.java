package co.edu.udea.compumovil.gr0120181.lab3;


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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogupFragment extends Fragment implements View.OnClickListener {

    private static String URL = "http://192.168.137.1:8080/api/user";
    private JSONObject jsonObject;

    private Button logupButton;
    private ImageView userPhoto;
    private Bitmap photoBitmap = null;
    private Map<String, String> parameters = new HashMap<>();


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
        final String name, user, mail, password, photo;

        editTextName = getView().findViewById(R.id.userNameLogup);
        editTextUser = getView().findViewById(R.id.userLogup);
        editTextMail = getView().findViewById(R.id.emailLogup);
        editTextPassword = getView().findViewById(R.id.passwordLogup);

        name = editTextName.getText().toString();
        user = editTextUser.getText().toString();
        mail = editTextMail.getText().toString();
        password = editTextPassword.getText().toString();


        if (name.equals("") || user.equals("") || mail.equals("") || password.equals("") || photoBitmap == null) {
            Toast.makeText(getContext(), "Falta Información", Toast.LENGTH_SHORT).show();
        } else {
            photo = ImageCodeClass.encodeToBase64(photoBitmap);

            parameters.put("name", name);
            parameters.put("user", user);
            parameters.put("email", mail);
            parameters.put("password", password);
            parameters.put("picture", photo);
            parameters.put("state", "ACTIVO");
            parameters.put("session", "INACTIVO");


            jsonObject = new JSONObject(parameters);

            JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    Log.d("RESPONSE:", response.toString());
                    if (!response.equals("")) {
                        Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Can't Register", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getContext(), "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                    volleyError.printStackTrace();
                    Log.d("ERROR: ", jsonObject.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getParams() {
                    return parameters;
                }
            };

            request.setShouldCache(false);
            RequestQueue rQueue = Volley.newRequestQueue(getContext());
            rQueue.add(request);
        }

    }

    public void setPhotoBitmap(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
        userPhoto.setImageBitmap(photoBitmap);
    }


}
