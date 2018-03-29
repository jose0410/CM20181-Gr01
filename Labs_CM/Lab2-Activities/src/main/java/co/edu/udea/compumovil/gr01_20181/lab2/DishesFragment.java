package co.edu.udea.compumovil.gr01_20181.lab2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;


/**
 * A simple {@link Fragment} subclass.
 */
public class DishesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<DishStructure> dishList;



    public DishesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dishes, container, false);
        initializeDataPersons(view);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_content);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        AdapterRecycleView adapter = new AdapterRecycleView(dishList);
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    private void initializeDataPersons(View view){
        dishList = new ArrayList<>();

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

        db.close();


    }

}
