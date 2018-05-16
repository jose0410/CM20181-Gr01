package co.edu.udea.compumovil.gr0120181.lab3;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AdapterRecycleView extends Adapter<AdapterRecycleView.DishViewHolder> {

    public JSONArray dishes;

    public AdapterRecycleView(JSONArray dishes) {
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
        try {
            JSONObject dish = new JSONObject(dishes.getString(pos));
            holder.dishName.setText(dish.getString("name"));
            holder.dishPrice.setText(dish.getString("price"));
            holder.dishPhoto.setImageBitmap(ImageCodeClass.decodeBase64(dish.getString("picture")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dishes.length();
    }


    public static class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView;
        public TextView dishName;
        public TextView dishPrice;
        public ImageView dishPhoto;
        public JSONArray dishes;


        DishViewHolder(View itemView, JSONArray dishes) {
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
            try {
                JSONObject dish = new JSONObject(dishes.getString(pos));
                showPickerDialog(view,dish);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void showPickerDialog(View view, JSONObject dish) throws JSONException {
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

            priceLoad.setText(String.valueOf(dish.getString("price")));
            ingredientsLoad.setText(dish.getString("ingredients"));
            imageLoad.setImageBitmap(ImageCodeClass.decodeBase64(dish.getString("picture")));
            scheduleLoad.setText(dish.getString("type"));
            durationLoad.setText(dish.getString("duration"));
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
            alertDialogBuilder.setTitle(dish.getString("name"));
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
