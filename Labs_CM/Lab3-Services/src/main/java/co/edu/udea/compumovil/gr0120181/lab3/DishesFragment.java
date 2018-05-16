package co.edu.udea.compumovil.gr0120181.lab3;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class DishesFragment extends Fragment {

    private static String URL = "http://192.168.0.11:8080/api/dishes";
    private RecyclerView mRecyclerView;
    private AdapterRecycleView adapter;
    //private DbHelper dbHelper;
    //private SQLiteDatabase db;


    public DishesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dishes, container, false);


        mRecyclerView = view.findViewById(R.id.rv_content);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        JsonArrayRequest request = new JsonArrayRequest( URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray s) {
                adapter = new AdapterRecycleView(s);
                mRecyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);

        return view;
    }

    public void restart(){

        /*DbHelper dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + StatusContract.TABLE_DISH, null);
        dishList.clear();
        if(c.moveToFirst()) {
            do {
                dishList.add(new DishStructure(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(6), c.getString(5)));
            } while (c.moveToNext());
        }
        db.close();

        adapter.notifyDataSetChanged();*/
    }


    public void filter(String filter){

        /*dbHelper = new DbHelper(getContext());
        db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + StatusContract.TABLE_DISH + " WHERE " +
                StatusContract.Column_Dish.NAME + " LIKE '%" + filter + "%' OR "
                + StatusContract.Column_Dish.PRICE + " LIKE '" + filter + "%'" , null);
        dishList.clear();
        if(c.moveToFirst()) {
            do {
                dishList.add(new DishStructure(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(6), c.getString(5)));
            } while (c.moveToNext());
        }else{
            Toast.makeText(getContext(), "No existen datos con ese nombre o precio" , Toast.LENGTH_SHORT).show();
        }
        db.close();

        adapter.notifyDataSetChanged();*/
    }

}
