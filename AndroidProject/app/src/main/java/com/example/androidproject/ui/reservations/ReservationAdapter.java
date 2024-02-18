package com.example.androidproject.ui.reservations;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Car;
import com.example.androidproject.DataBaseHelper;
import com.example.androidproject.LoggedInUser;
import com.example.androidproject.R;
import com.example.androidproject.ReservedCar;

import java.util.ArrayList;


public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<ReservedCar> cars;
    private final String fragment;

    public ReservationAdapter(Context context, ArrayList<ReservedCar> cars, String fragment) {
        this.context = context;
        this.cars = cars;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservations_card, parent, false);
        return new ReservationAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.ViewHolder holder, int position) {
        if (fragment.equals("admin reservations")) {
            holder.rate.setVisibility(View.INVISIBLE);
            holder.rate.setClickable(false);
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, "db", null, 1);

        ReservedCar reservedCarDetails = cars.get(position);
        holder.name.setText(reservedCarDetails.getMake() + " " + reservedCarDetails.getModel());
        holder.year.setText(reservedCarDetails.getYear());
        holder.image.setImageResource(reservedCarDetails.getImage());

        float[] oldRating = {dataBaseHelper.getCarRating(reservedCarDetails.getCarId(), reservedCarDetails.getReservedColor(), LoggedInUser.getInstance().user.getEmail())};

        // Initialize the dialog
        Context context = holder.itemView.getContext();

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.car_dialog);

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.reservation_details_dialog);
        dialog.getWindow().setBackgroundDrawable(drawable);

        Dialog rateDialog = new Dialog(context);
        rateDialog.setContentView(R.layout.rating_dialog);
        rateDialog.getWindow().setBackgroundDrawable(drawable);

        TextView reservedBy = (TextView) dialog.findViewById(R.id.reservedByViewReserveDetails);
        TextView email = (TextView) dialog.findViewById(R.id.emailViewReserveDetails);
        TextView manufacturer = (TextView) dialog.findViewById(R.id.manufacturerViewReserveDetails);
        TextView type = (TextView) dialog.findViewById(R.id.modelViewReserveDetails);
        TextView year = (TextView) dialog.findViewById(R.id.yearViewReserveDetails);
        TextView price = (TextView) dialog.findViewById(R.id.priceViewReserveDetails);
        TextView date = (TextView) dialog.findViewById(R.id.reserveDateViewReserveDetails);
        TextView reservedColor = (TextView) dialog.findViewById(R.id.reservedColorReserveDetails);

        TextView rateView = (TextView) rateDialog.findViewById(R.id.ratingView);
        RatingBar ratingBar = (RatingBar) rateDialog.findViewById(R.id.ratingBar);
        Button submitRate = (Button) rateDialog.findViewById(R.id.submitRate);
        Button cancelRate = (Button) rateDialog.findViewById(R.id.cancelRate);

        reservedBy.setText(reservedCarDetails.getFirstName() + " " + reservedCarDetails.getLastName());
        email.setText(reservedCarDetails.getEmail());
        manufacturer.setText(reservedCarDetails.getMake());
        type.setText(reservedCarDetails.getModel());
        year.setText(reservedCarDetails.getYear());
        price.setText("$" + Integer.toString(reservedCarDetails.getPrice()));
        date.setText(reservedCarDetails.getDate());
        reservedColor.setText(reservedCarDetails.getReservedColor());

        holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateDialog.show();
                ratingBar.setRating(oldRating[0]);
                rateView.setText("Your Rating is: " + oldRating[0]);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateView.setText("Your Rating is: " + String.format("%.1f", ratingBar.getRating()));
            }
        });

        submitRate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                dataBaseHelper.updateRating(LoggedInUser.getInstance().user.getEmail(), reservedCarDetails.getCarId(), reservedCarDetails.getReservedColor(), Float.parseFloat(String.format("%.1f", ratingBar.getRating())));
                oldRating[0] = Float.parseFloat(String.format("%.1f", ratingBar.getRating()));
                Toast.makeText(context, "Your Rating has been submitted", Toast.LENGTH_SHORT).show();
                rateDialog.dismiss();
            }
        });

        cancelRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateDialog.dismiss();
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
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
        private final TextView rate;
        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.carName);
            year = itemView.findViewById(R.id.cardYear);
            image = itemView.findViewById(R.id.carImage);
            rate = itemView.findViewById(R.id.rateBtn);
        }
    }

}
