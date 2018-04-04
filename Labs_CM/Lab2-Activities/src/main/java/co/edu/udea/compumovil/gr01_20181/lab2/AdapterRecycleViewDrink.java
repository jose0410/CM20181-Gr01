package co.edu.udea.compumovil.gr01_20181.lab2;

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

import java.util.List;
import co.edu.udea.compumovil.gr01_20181.lab2.DB.DrinkStructure;

/**
 * Created by user on 4/04/2018.
 */

public class AdapterRecycleViewDrink extends RecyclerView.Adapter<AdapterRecycleViewDrink.DrinkViewHolder> {

    public List<DrinkStructure> drinks;

    public AdapterRecycleViewDrink(List<DrinkStructure> drinks) {
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
        holder.drinkName.setText(drinks.get(pos).getName());
        holder.drinkPrice.setText(drinks.get(pos).getPrice());
        holder.drinkPhoto.setImageBitmap(ImageCodeClass.decodeBase64(drinks.get(pos).getPicture()));
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView;
        public TextView drinkName;
        public TextView drinkPrice;
        public ImageView drinkPhoto;
        public List<DrinkStructure> drinks;


        DrinkViewHolder(View itemView, List<DrinkStructure> drinks) {
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
            showPickerDialog(view);
        }

        public void showPickerDialog(View view) {
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
            priceLoad.setText(String.valueOf(drinks.get(pos).getPrice()));
            ingredientsLoad.setText(drinks.get(pos).getIngredients());
            imageLoad.setImageBitmap(ImageCodeClass.decodeBase64(drinks.get(pos).getPicture()));
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
            alertDialogBuilder.setTitle(drinks.get(pos).getName());
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
