package co.edu.udea.compumovil.gr0120181.lab3;


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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class DrinksFragment extends Fragment {

    private static String URL = ":8080/api/drinks";
    private static String URLFINAL = "";
    private RecyclerView mRecyclerView;
    private AdapterRecycleViewDrink adapter;
    //private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DrinksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drinks, container, false);

        mRecyclerView = view.findViewById(R.id.rv_content);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        JsonArrayRequest request = new JsonArrayRequest( getArguments().getString("IP")+ URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray s) {
                adapter = new AdapterRecycleViewDrink(s);
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

        Cursor c = db.rawQuery("SELECT * FROM " + StatusContract.TABLE_DRINK, null);
        drinkList.clear();
        if(c.moveToFirst()) {
            do {
                drinkList.add(new DrinkStructure(c.getString(1), c.getString(2), c.getString(4), c.getString(3)));
            } while (c.moveToNext());
        }
        db.close();

        adapter.notifyDataSetChanged();*/
    }


    public void filter(String filter){

        /*dbHelper = new DbHelper(getContext());
        db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + StatusContract.TABLE_DRINK + " WHERE " +
                StatusContract.Column_Drink.NAME + " LIKE '%" + filter + "%' OR "
                + StatusContract.Column_Drink.PRICE + " LIKE '" + filter + "%'" , null);
        drinkList.clear();
        if(c.moveToFirst()) {
            do {
                drinkList.add(new DrinkStructure(c.getString(1), c.getString(2), c.getString(4), c.getString(3)));
            } while (c.moveToNext());
        }else{
            Toast.makeText(getContext(), "No existen datos con ese nombre o precio" , Toast.LENGTH_SHORT).show();
        }
        db.close();

        adapter.notifyDataSetChanged();*/
    }

}
