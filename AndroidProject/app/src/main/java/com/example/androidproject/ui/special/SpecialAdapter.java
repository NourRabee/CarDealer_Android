package com.example.androidproject.ui.special;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Car;
import com.example.androidproject.CarColorSelector;
import com.example.androidproject.DataBaseHelper;
import com.example.androidproject.LoggedInUser;
import com.example.androidproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Car> cars;

    public SpecialAdapter(Context context, ArrayList<Car> cars) {
        this.context = context;
        this.cars = cars;
    }

    @NonNull
    @Override
    public SpecialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_card, parent, false);
        return new SpecialAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SpecialAdapter.ViewHolder holder, int position) {
        Car car = cars.get(position);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, "db", null, 1);

        List<String> selectedColors = CarColorSelector.getSelectedColorsHex(car.getColor());
        List<String> reservedColors = dataBaseHelper.getReservedColorsForCarId(car.getId());

        GradientDrawable[] unselected = new GradientDrawable[4];
        GradientDrawable[] selected = new GradientDrawable[4];

        final boolean[] isSelected = {false, false, false, false};
        String[] carColor = {"White"};

        for (int i = 0; i < 4; i++) {
            unselected[i] = new GradientDrawable();
            selected[i] = new GradientDrawable();

            unselected[i].setShape(GradientDrawable.OVAL);
            unselected[i].setColor(Color.parseColor(selectedColors.get(i)));
            unselected[i].setSize(16, 16);
            unselected[i].setStroke(1, Color.parseColor("#000000"));

            selected[i].setShape(GradientDrawable.OVAL);
            selected[i].setColor(Color.parseColor(selectedColors.get(i)));
            selected[i].setSize(18, 18);
            selected[i].setStroke(3, Color.parseColor("#D2D1D2"));
        }

        // 0 Black 1 White 2 Gray
        holder.colorOne.setBackground(unselected[1]);
        holder.colorTwo.setBackground(unselected[2]);
        holder.colorThree.setBackground(unselected[0]);
        holder.colorFour.setBackground(unselected[3]);

        holder.colorOne.setOnClickListener(new View.OnClickListener() {
            // (colorOne White 1)  (colorTwo Gray 2)  (colorThree Black 0)
            @Override
            public void onClick(View v) {
                holder.colorOne.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
                if (isSelected[0]) {
                    isSelected[0] = false;
                    holder.colorOne.setBackground(unselected[1]);
                } else {
                    carColor[0] = "White";
                    isSelected[0] = true;
                    isSelected[1] = false;
                    isSelected[2] = false;
                    isSelected[3] = false;
                    holder.colorOne.setBackground(selected[1]);
                    holder.colorTwo.setBackground(unselected[2]);
                    holder.colorThree.setBackground(unselected[0]);
                    holder.colorFour.setBackground(unselected[3]);
                }
            }
        });

        holder.colorTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.colorTwo.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
                if (isSelected[1]) {
                    isSelected[1] = false;
                    holder.colorTwo.setBackground(unselected[2]);
                } else {
                    carColor[0] = "Gray";
                    isSelected[0] = false;
                    isSelected[1] = true;
                    isSelected[2] = false;
                    isSelected[3] = false;
                    holder.colorOne.setBackground(unselected[1]);
                    holder.colorTwo.setBackground(selected[2]);
                    holder.colorThree.setBackground(unselected[0]);
                    holder.colorFour.setBackground(unselected[3]);
                }
            }
        });

        holder.colorThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.colorThree.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
                if (isSelected[2]) {
                    isSelected[2] = false;
                    holder.colorThree.setBackground(unselected[0]);
                } else {
                    carColor[0] = "Black";
                    isSelected[0] = false;
                    isSelected[1] = false;
                    isSelected[2] = true;
                    isSelected[3] = false;
                    holder.colorOne.setBackground(unselected[1]);
                    holder.colorTwo.setBackground(unselected[2]);
                    holder.colorThree.setBackground(selected[0]);
                    holder.colorFour.setBackground(unselected[3]);
                }
            }
        });

        holder.colorFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.colorFour.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
                if (isSelected[3]) {
                    isSelected[3] = false;
                    holder.colorFour.setBackground(unselected[3]);
                } else {
                    carColor[0] = CarColorSelector.getColorNameFromHex(selectedColors.get(3));
                    isSelected[0] = false;
                    isSelected[1] = false;
                    isSelected[2] = false;
                    isSelected[3] = true;
                    holder.colorOne.setBackground(unselected[1]);
                    holder.colorTwo.setBackground(unselected[2]);
                    holder.colorThree.setBackground(unselected[0]);
                    holder.colorFour.setBackground(selected[3]);
                }
            }
        });

        for (String color : reservedColors) {
            switch (color) {
                case "White":
                    holder.redLineOne.setVisibility(View.VISIBLE);
                    holder.colorOne.setClickable(false);
                    break;
                case "Gray":
                    holder.redLineTwo.setVisibility(View.VISIBLE);
                    holder.colorTwo.setClickable(false);
                    break;
                case "Black":
                    holder.redLineThree.setVisibility(View.VISIBLE);
                    holder.colorThree.setClickable(false);
                    break;
                default:
                    if (color.equals("Red"))
                        holder.redLineFour.setBackgroundResource(R.drawable.black_line);
                    holder.redLineFour.setVisibility(View.VISIBLE);
                    holder.colorFour.setClickable(false);
                    break;
            }
        }

        holder.name.setText(car.getMake() + " " + car.getType());
        holder.price.setText("$" + Integer.toString(car.getPrice()));
        holder.year.setText(car.getYear());
        holder.image.setImageResource(car.getImage());

        holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.priceAfterOffer.setVisibility(View.VISIBLE);
        double offerPrice = car.getPrice() - (car.getPrice() * (double) (car.getDiscount() / 100.0));
        holder.priceAfterOffer.setText("$" + Integer.toString((int) offerPrice));

        // Initialize the dialog
        Context context = holder.itemView.getContext();

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.car_dialog);

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.car_detail_dialog);
        dialog.getWindow().setBackgroundDrawable(drawable);

        Dialog dialogReserve = new Dialog(context);
        dialogReserve.setContentView(R.layout.before_rent_dialog);
        dialogReserve.getWindow().setBackgroundDrawable(drawable);

        TextView manufacturer = (TextView) dialog.findViewById(R.id.manufacturerView);
        TextView type = (TextView) dialog.findViewById(R.id.modelView);
        TextView year = (TextView) dialog.findViewById(R.id.yearView);
        TextView distance = (TextView) dialog.findViewById(R.id.milesView);
        TextView price = (TextView) dialog.findViewById(R.id.priceView);
        TextView offers = (TextView) dialog.findViewById(R.id.offersView);
        TextView power = (TextView) dialog.findViewById(R.id.powerView);
        TextView seating = (TextView) dialog.findViewById(R.id.seatingView);


        TextView manufacturerReserve = (TextView) dialogReserve.findViewById(R.id.manufacturerViewReserve);
        TextView typeReserve = (TextView) dialogReserve.findViewById(R.id.modelViewReserve);
        TextView yearReserve = (TextView) dialogReserve.findViewById(R.id.yearViewReserve);
        TextView distanceReserve = (TextView) dialogReserve.findViewById(R.id.milesViewReserve);
        TextView priceReserve = (TextView) dialogReserve.findViewById(R.id.priceViewReserve);
        TextView offersReserve = (TextView) dialogReserve.findViewById(R.id.offersViewReserve);
        TextView powerReserve = (TextView) dialogReserve.findViewById(R.id.powerViewReserve);
        TextView seatingReserve = (TextView) dialogReserve.findViewById(R.id.seatingViewReserve);
        Button cancelReserve = (Button) dialogReserve.findViewById(R.id.cancelReserve);
        Button confirmReserve = (Button) dialogReserve.findViewById(R.id.confirmReserve);

        manufacturer.setText(car.getMake());
        type.setText(car.getType());
        year.setText(car.getYear());
        distance.setText(Integer.toString(car.getDistance()));
        price.setText("$" + Integer.toString(car.getPrice()));
        offers.setText(Integer.toString(car.getDiscount()));
        power.setText(Integer.toString(car.getHorsePower()));
        seating.setText(Integer.toString(car.getSeatingCapacity()));

        manufacturerReserve.setText(car.getMake());
        typeReserve.setText(car.getType());
        yearReserve.setText(car.getYear());
        distanceReserve.setText(Integer.toString(car.getDistance()));
        priceReserve.setText("$" + Integer.toString(car.getPrice()));
        offersReserve.setText(Integer.toString(car.getDiscount()));
        powerReserve.setText(Integer.toString(car.getHorsePower()));
        seatingReserve.setText(Integer.toString(car.getSeatingCapacity()));

        if (car.isFavorite())
            holder.favorite.setImageResource(R.drawable.baseline_favorite);
        else
            holder.favorite.setImageResource(R.drawable.favorite_border);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        holder.carButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogReserve.show();
            }
        });

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(context, "db", null, 1);
                if (!car.isFavorite()) {
                    holder.favorite.setImageResource(R.drawable.baseline_favorite);
                    car.setFavorite(true);
                    Car.cars.get(car.getId() - 1).setFavorite(true);

                    dataBaseHelper.addToFavorites(LoggedInUser.getInstance().user.getEmail(), car.getId());

                } else {
                    holder.favorite.setImageResource(R.drawable.favorite_border);
                    car.setFavorite(false);
                    Car.cars.get(car.getId() - 1).setFavorite(false);

                    dataBaseHelper.removeFromFavorites(LoggedInUser.getInstance().user.getEmail(), car.getId());

                }
            }
        });

        cancelReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogReserve.dismiss();
            }
        });

        confirmReserve.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if (car.getReserved() == 0) {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(context, "db", null, 1);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String reservationDateTime = sdf.format(new Date());

                    dataBaseHelper.insertReservation(LoggedInUser.getInstance().user.getEmail(), car.getId(), reservationDateTime, carColor[0]);
                    dataBaseHelper.updateReservedStatus(car.getId(), 1);

                    car.setReserved(1);

                    Toast.makeText(context, "Car reserved successfully...", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    dialogReserve.dismiss();
                } else
                    Toast.makeText(context, "Car is already reserved...", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView year;
        private final TextView price;
        private final TextView priceAfterOffer;
        private final ImageView image;
        private final ImageView favorite;
        private final Button carButton;
        private final ImageButton colorOne;
        private final ImageButton colorTwo;
        private final ImageButton colorThree;
        private final ImageButton colorFour;

        private final ImageButton redLineOne;
        private final ImageButton redLineTwo;
        private final ImageButton redLineThree;
        private final ImageButton redLineFour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.carName);
            price = itemView.findViewById(R.id.carPrice);
            carButton = itemView.findViewById(R.id.reserveButton);
            image = itemView.findViewById(R.id.carImage);
            year = itemView.findViewById(R.id.yearCard);
            favorite = itemView.findViewById(R.id.favoriteClick);
            priceAfterOffer = itemView.findViewById(R.id.carPriceAfterOffer);
            colorOne = itemView.findViewById(R.id.color1);
            colorTwo = itemView.findViewById(R.id.color2);
            colorThree = itemView.findViewById(R.id.color3);
            colorFour = itemView.findViewById(R.id.color4);
            redLineOne = itemView.findViewById(R.id.redLine1);
            redLineTwo = itemView.findViewById(R.id.redLine2);
            redLineThree = itemView.findViewById(R.id.redLine3);
            redLineFour = itemView.findViewById(R.id.redLine4);
        }
    }

}
