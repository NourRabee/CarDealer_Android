package com.example.androidproject.ui.home;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.androidproject.Car;
import com.example.androidproject.DataBaseHelper;
import com.example.androidproject.LoggedInUser;
import com.example.androidproject.R;
import com.example.androidproject.SelectedDealer;
import com.example.androidproject.databinding.FragmentHomeBinding;


import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(root.getContext(), "db", null, 1);

        Car lastFavorite;
        Car lastReserved;

        if (SelectedDealer.getInstance().dealer == null || SelectedDealer.getInstance().dealer.equals("All")) {
            lastFavorite = dataBaseHelper.getLastFavoriteCarByUser(LoggedInUser.getInstance().user.getEmail(), "");
            lastReserved = dataBaseHelper.getLastReservedCarByUser(LoggedInUser.getInstance().user.getEmail(), "");
        } else {
            lastFavorite = dataBaseHelper.getLastFavoriteCarByUser(LoggedInUser.getInstance().user.getEmail(), SelectedDealer.getInstance().dealer);
            lastReserved = dataBaseHelper.getLastReservedCarByUser(LoggedInUser.getInstance().user.getEmail(), SelectedDealer.getInstance().dealer);
        }

        // Last Login
        TextView lastLogin = root.findViewById(R.id.lastLogin);
        if (LoggedInUser.getInstance().user.getLastLogin() == null)
            lastLogin.setText("NaN");
        else
            lastLogin.setText(LoggedInUser.getInstance().user.getLastLogin());

        // Favorite
        CardView favoriteCarCard = root.findViewById(R.id.favoriteCarCard);
        TextView carName = root.findViewById(R.id.carName);
        TextView carPrice = root.findViewById(R.id.carPrice);
        TextView yearCard = root.findViewById(R.id.yearCard);
        TextView carRating = root.findViewById(R.id.carRating);
        ImageView carImage = root.findViewById(R.id.carImage);
        ImageView favoriteClick = root.findViewById(R.id.favoriteClick);

        // Reserved
        CardView reservedCarCard = root.findViewById(R.id.reservedCarCard);
        TextView carNameReserved = root.findViewById(R.id.carNameReserved);
        TextView cardYear = root.findViewById(R.id.cardYear);
        ImageView carImageReserved = root.findViewById(R.id.carImageReserved);

        if (lastFavorite == null) {
            favoriteCarCard.setVisibility(View.INVISIBLE);
        } else {
            carName.setText(lastFavorite.getMake() + " " + lastFavorite.getType());
            carPrice.setText(Integer.toString(lastFavorite.getPrice()));
            yearCard.setText(lastFavorite.getYear());
            carRating.setText(Double.toString(lastFavorite.getRating()));
            carImage.setImageResource(lastFavorite.getImage());
            favoriteClick.setImageResource(R.drawable.baseline_favorite);
        }

        if (lastReserved == null) {
            reservedCarCard.setVisibility(View.INVISIBLE);
        } else {
            carNameReserved.setText(lastReserved.getMake() + " " + lastReserved.getType());
            cardYear.setText(lastReserved.getYear());
            carImageReserved.setImageResource(lastReserved.getImage());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}