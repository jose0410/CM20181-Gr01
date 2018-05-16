package co.edu.udea.compumovil.gr0120181.lab3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by user on 4/04/2018.
 */

public class AdapterRecycleViewDrink extends RecyclerView.Adapter<AdapterRecycleViewDrink.DrinkViewHolder> {

    public JSONArray drinks;

    public AdapterRecycleViewDrink(JSONArray drinks) {
        this.drinks = drinks;
    }


    @Override
    public DrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_card, parent, false);
        DrinkViewHolder pvh = new DrinkViewHolder(view, drinks);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DrinkViewHolder holder, int pos) {
        try {
            JSONObject dish = new JSONObject(drinks.getString(pos));
            holder.drinkName.setText(dish.getString("name"));
            holder.drinkPrice.setText(dish.getString("price"));
            holder.drinkPhoto.setImageBitmap(ImageCodeClass.decodeBase64(dish.getString("picture")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return drinks.length();
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView;
        public TextView drinkName;
        public TextView drinkPrice;
        public ImageView drinkPhoto;
        public JSONArray drinks;


        DrinkViewHolder(View itemView, JSONArray drinks) {
            super(itemView);
            itemView.setOnClickListener(this);
            cardView = itemView.findViewById(R.id.card_view);
            drinkName = itemView.findViewById(R.id.drink_name);
            drinkPhoto = itemView.findViewById(R.id.drink_photo);
            drinkPrice = itemView.findViewById(R.id.drink_price);
            this.drinks = drinks;
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            try {
                JSONObject drink = new JSONObject(drinks.getString(pos));
                showPickerDialog(view,drink);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void showPickerDialog(View view, JSONObject drink) throws JSONException {
            Context c = view.getContext();
            int pos = getAdapterPosition();
            TextView nameLoad, priceLoad, ingredientsLoad;
            ImageView imageLoad;
            LayoutInflater l = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View v = l.inflate(R.layout.detail_drink_dialog, null);
            final FrameLayout layout = new FrameLayout(c);
            layout.addView(v, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER));

            priceLoad = v.findViewById(R.id.priceLoad);
            ingredientsLoad = v.findViewById(R.id.ingredientsLoad);
            imageLoad = v.findViewById(R.id.imageLoad);
            priceLoad.setText(String.valueOf(drink.getString("price")));
            ingredientsLoad.setText(drink.getString("ingredients"));
            imageLoad.setImageBitmap(ImageCodeClass.decodeBase64(drink.getString("picture")));
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
            alertDialogBuilder.setTitle(drink.getString("name"));
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

