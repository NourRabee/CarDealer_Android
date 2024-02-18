package com.example.androidproject.ui.list_reserves;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.DataBaseHelper;
import com.example.androidproject.LoggedInUser;
import com.example.androidproject.R;
import com.example.androidproject.ReservedCar;
import com.example.androidproject.SelectedDealer;
import com.example.androidproject.databinding.FragmentListReservesBinding;
import com.example.androidproject.ui.reservations.ReservationAdapter;

import java.util.ArrayList;

public class ListReservesFragment extends Fragment {

    private FragmentListReservesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListReservesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView carRecyclerView = root.findViewById(R.id.reserveListAdminRV);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(root.getContext(), "db", null, 1);


        ArrayList<ReservedCar> carsList;

        if (LoggedInUser.getInstance().user.getIsAdmin() == 1 && LoggedInUser.getInstance().user.getSuperAdmin() == 1)
            carsList = new ArrayList<>(dataBaseHelper.getReservedCarDetails("", LoggedInUser.getInstance().user.getEmail()));
        else
            carsList = new ArrayList<>(dataBaseHelper.getReservedCarDetails("", LoggedInUser.getInstance().user.getManager()));


        ReservationAdapter reservationAdapter = new ReservationAdapter(requireContext(), carsList, "admin reservations");
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