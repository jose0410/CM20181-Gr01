package co.edu.udea.compumovil.gr01_20181.lab2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr01_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.DishStructure;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.DrinkStructure;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrinksFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<DrinkStructure> drinkList;
    private AdapterRecycleViewDrink adapter;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DrinksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drinks, container, false);
        initializeDataPersons(view);

        mRecyclerView = view.findViewById(R.id.rv_content);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AdapterRecycleViewDrink(drinkList);
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    private void initializeDataPersons(View view){
        drinkList = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + StatusContract.TABLE_DRINK , null);

        if(c.moveToFirst()) {
            do {
                drinkList.add(new DrinkStructure(c.getString(1), c.getString(2), c.getString(4), c.getString(3)));
            } while (c.moveToNext());
        }else{
            Toast.makeText(view.getContext(), "no hay datos registrados" , Toast.LENGTH_SHORT).show();
        }
        db.close();


    }

    public void restart(){

        DbHelper dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + StatusContract.TABLE_DRINK, null);
        drinkList.clear();
        if(c.moveToFirst()) {
            do {
                drinkList.add(new DrinkStructure(c.getString(1), c.getString(2), c.getString(4), c.getString(3)));
            } while (c.moveToNext());
        }
        db.close();

        adapter.notifyDataSetChanged();
    }


    public void filter(String filter){

        dbHelper = new DbHelper(getContext());
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

        adapter.notifyDataSetChanged();
    }

}
