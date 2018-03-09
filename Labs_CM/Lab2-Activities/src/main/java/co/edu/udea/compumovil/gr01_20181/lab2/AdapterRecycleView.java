package co.edu.udea.compumovil.gr01_20181.lab2;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.edu.udea.compumovil.gr01_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.DishStructure;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.StatusContract;

public class AdapterRecycleView extends RecyclerView.Adapter<AdapterRecycleView.DishViewHolder>{

    public List<DishStructure> dishes;

    public AdapterRecycleView(List<DishStructure> dishes) {
        this.dishes = dishes;
    }

    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_card, parent, false);
        DishViewHolder pvh = new DishViewHolder(view, dishes);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DishViewHolder holder, int pos) {

        holder.dishName.setText(dishes.get(pos).getName());
        holder.dishPrice.setText(dishes.get(pos).getPrice());
        holder.dishPhoto.setImageBitmap(ImageCodeClass.decodeBase64(dishes.get(pos).getPicture()));
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }


    public static class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView;
        public TextView dishName;
        public TextView dishPrice;
        public ImageView dishPhoto;
        public List<DishStructure> dishes;


        DishViewHolder(View itemView, List<DishStructure> dishes) {
            super(itemView);
            itemView.setOnClickListener(this);
            cardView =  itemView.findViewById(R.id.card_view);
            dishName =  itemView.findViewById(R.id.dish_name);
            dishPhoto =  itemView.findViewById(R.id.dish_photo);
            dishPrice =  itemView.findViewById(R.id.dish_price);
            this.dishes = dishes;
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            Log.d("AdapterRecyclerView", "onClick: " + pos + "  Name: "+ dishName.getText() );
            Toast.makeText(itemView.getContext(), "Hello: "+ dishName.getText()+ "onClick: " + pos , Toast.LENGTH_SHORT).show();
            showPickerDialog(view);
        }

        public void showPickerDialog(View view) {
            Context c = view.getContext();
            int pos = getAdapterPosition();
            TextView nameLoad,priceLoad,scheduleLoad,ingredientsLoad,durationLoad;
            ImageView  imageLoad;
            LayoutInflater l = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View v = l.inflate(R.layout.detail_dish_dialog, null);
            final FrameLayout layout = new FrameLayout(c);
            layout.addView(v, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER));

            priceLoad = v.findViewById(R.id.priceLoad);
            ingredientsLoad = v.findViewById(R.id.ingredientsLoad);
            imageLoad = v.findViewById(R.id.imageLoad);
            durationLoad = v.findViewById(R.id.durationLoad);
            scheduleLoad = v.findViewById(R.id.scheduleLoad);
            priceLoad.setText(String.valueOf(dishes.get(pos).getPrice()));
            ingredientsLoad.setText(dishes.get(pos).getIngredients());
            imageLoad.setImageBitmap(ImageCodeClass.decodeBase64(dishes.get(pos).getPicture()));
            scheduleLoad.setText(dishes.get(pos).getType());
            durationLoad.setText(dishes.get(pos).getDuration());
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
            alertDialogBuilder.setTitle(dishes.get(pos).getName());
            alertDialogBuilder.setView(layout);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        }


    }

}