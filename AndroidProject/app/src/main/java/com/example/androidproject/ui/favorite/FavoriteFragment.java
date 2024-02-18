package com.example.androidproject.ui.favorite;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidproject.Car;
import com.example.androidproject.DataBaseHelper;
import com.example.androidproject.LoggedInUser;
import com.example.androidproject.R;
import com.example.androidproject.SelectedDealer;
import com.example.androidproject.databinding.FragmentFavoriteBinding;
import com.example.androidproject.ui.list.CarsAdapter;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(root.getContext(), "db", null, 1);

        ArrayList<Car> favorites;

        if (SelectedDealer.getInstance().dealer == null || SelectedDealer.getInstance().dealer.equals("All")) {
            favorites = dataBaseHelper.getFavoriteCarsDealerBased(LoggedInUser.getInstance().user.getEmail(), "");
        } else
            favorites = dataBaseHelper.getFavoriteCarsDealerBased(LoggedInUser.getInstance().user.getEmail(), SelectedDealer.getInstance().dealer);

        for (Car car : favorites)
            car.setFavorite(true);

        RecyclerView favoriteRecyclerView = root.findViewById(R.id.favoriteRV);

        CarsAdapter carsAdapter = new CarsAdapter(requireContext(), favorites, "favorites");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        favoriteRecyclerView.setLayoutManager(linearLayoutManager);
        favoriteRecyclerView.setAdapter(carsAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}