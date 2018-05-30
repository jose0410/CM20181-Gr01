package co.edu.udea.compumovil.gr0120181.lab3;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ConfigFragment extends PreferenceFragment {

    private JSONObject userInfo;
    private static String URL = ":8080/api/updateuser/";
    private static String URLFINAL;
    private ImageView photo;

    public ConfigFragment(JSONObject userInfo) throws JSONException, UnsupportedEncodingException {
        this.userInfo = userInfo;
        URLFINAL = URL + java.net.URLEncoder.encode(userInfo.getString("user"), "UTF-8");
    }

    public ConfigFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.app_preferences);

        try {
            findPreference("user").setTitle(userInfo.getString("user"));
            findPreference("name").setTitle(userInfo.getString("name"));
            findPreference("mail").setTitle(userInfo.getString("email"));
            findPreference("photo").setLayoutResource(R.layout.profile_preference_photo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        updateSettings(findPreference("user"));
        updateSettings(findPreference("name"));
        updateSettings(findPreference("mail"));
        updateSettings(findPreference("password"));


    }

    private void updateSettings(Preference preference) {
        preference.setOnPreferenceChangeListener(listener);
        listener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(
                preference.getContext()).getString(preference.getKey(), ""));
    }

    private Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            boolean isChange = false;
            if (preference instanceof EditTextPreference) {
                try {
                    if (preference.getKey().equals("user") && newValue != "") {
                        userInfo.put("user", newValue.toString());
                        findPreference("user").setTitle(newValue.toString());
                        isChange = true;
                    }

                    if (preference.getKey().equals("name") && newValue != "") {
                        userInfo.put("name", newValue.toString());
                        findPreference("name").setTitle(newValue.toString());
                        isChange = true;
                    }

                    if (preference.getKey().equals("mail") && newValue != "") {
                        userInfo.put("email", newValue.toString());
                        findPreference("mail").setTitle(newValue.toString());
                        isChange = true;
                    }

                    if (preference.getKey().equals("password") && newValue != "") {
                        userInfo.put("password", newValue.toString());
                        isChange = true;
                    }

                    if (isChange) {
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, getArguments().getString("IP") + URLFINAL, userInfo, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (Objects.equals(response.getString("msg"), "Update successful")) {
                                        URLFINAL = URL + java.net.URLEncoder.encode(userInfo.getString("user"), "UTF-8");
                                        Toast.makeText(getContext(), response.getString("msg"), Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), "Can't Register", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(getContext(), "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                                volleyError.printStackTrace();
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
                        };

                        RequestQueue rQueue = Volley.newRequestQueue(getContext());
                        rQueue.add(request);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    };


}
