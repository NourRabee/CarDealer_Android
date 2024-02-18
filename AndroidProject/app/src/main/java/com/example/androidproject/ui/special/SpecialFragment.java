package com.example.androidproject.ui.special;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import com.example.androidproject.databinding.FragmentSpecialBinding;
import com.example.androidproject.ui.reservations.ReservationAdapter;

import java.util.ArrayList;

public class SpecialFragment extends Fragment {

    private FragmentSpecialBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSpecialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView specialRecyclerView = root.findViewById(R.id.specialRV);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(root.getContext(), "db", null, 1);

        ArrayList<Car> specialOffersList;

        if (SelectedDealer.getInstance().dealer == null || SelectedDealer.getInstance().dealer.equals("All")) {
            specialOffersList = new ArrayList<>(dataBaseHelper.getCarsWithDiscount(""));
        } else
            specialOffersList = new ArrayList<>(dataBaseHelper.getCarsWithDiscount(SelectedDealer.getInstance().dealer));

        ArrayList<Car> favorites = dataBaseHelper.getFavoriteCars(LoggedInUser.getInstance().user.getEmail());
        setFavorites(favorites, specialOffersList);

        SpecialAdapter specialAdapter = new SpecialAdapter(requireContext(), specialOffersList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        specialRecyclerView.setLayoutManager(linearLayoutManager);
        specialRecyclerView.setAdapter(specialAdapter);

        return root;
    }

    private void setFavorites(ArrayList<Car> favorites, ArrayList<Car> specialOffer) {

        for (Car car : specialOffer) {
            for (Car favorite : favorites) {
                if (favorite.getId() == car.getId()) {
                    car.setFavorite(true);
                    break;
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}