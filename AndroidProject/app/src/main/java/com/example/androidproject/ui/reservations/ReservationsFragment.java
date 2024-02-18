package com.example.androidproject.ui.reservations;

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
import com.example.androidproject.ReservedCar;
import com.example.androidproject.SelectedDealer;
import com.example.androidproject.databinding.FragmentReservationsBinding;

import java.util.ArrayList;

public class ReservationsFragment extends Fragment {

    private FragmentReservationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReservationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView carRecyclerView = root.findViewById(R.id.reservationsList);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(root.getContext(), "db", null, 1);

        ArrayList<ReservedCar> carsList;

        if (SelectedDealer.getInstance().dealer == null || SelectedDealer.getInstance().dealer.equals("All")) {
            carsList = new ArrayList<>(dataBaseHelper.getReservedCarDetails(LoggedInUser.getInstance().user.getEmail(), ""));
        } else
            carsList = new ArrayList<>(dataBaseHelper.getReservedCarDetails(LoggedInUser.getInstance().user.getEmail(), SelectedDealer.getInstance().dealer));

        ReservationAdapter reservationAdapter = new ReservationAdapter(requireContext(), carsList, "reservations");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        carRecyclerView.setLayoutManager(linearLayoutManager);
        carRecyclerView.setAdapter(reservationAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}