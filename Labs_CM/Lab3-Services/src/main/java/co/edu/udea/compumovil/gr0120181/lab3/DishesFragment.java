package co.edu.udea.compumovil.gr0120181.lab3;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class DishesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    //private List<DishStructure> dishList;
    //private AdapterRecycleView adapter;
    //private DbHelper dbHelper;
    private SQLiteDatabase db;


    public DishesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dishes, container, false);
        initializeDataPersons(view);

        mRecyclerView = view.findViewById(R.id.rv_content);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //adapter = new AdapterRecycleView(dishList);
        //mRecyclerView.setAdapter(adapter);
        return view;
    }

    private void initializeDataPersons(View view){
        /*dishList = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + StatusContract.TABLE_DISH , null);

        if(c.moveToFirst()) {
            do {
                dishList.add(new DishStructure(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(6), c.getString(5)));
            } while (c.moveToNext());
        }else{
            Toast.makeText(view.getContext(), "no hay datos registrados" , Toast.LENGTH_SHORT).show();
        }
        db.close();*/


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
