package com.example.androidproject.ui.list;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.androidproject.Car;
import com.example.androidproject.DataBaseHelper;
import com.example.androidproject.LoggedInUser;
import com.example.androidproject.R;
import com.example.androidproject.SelectedDealer;
import com.example.androidproject.databinding.FragmentListBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class ListFragment extends Fragment {
    private ChipGroup chipGroup1;
    private ChipGroup chipGroup2;
    private ChipGroup chipGroup3;
    private ChipGroup chipGroup4;
    private ChipGroup chipGroup5;

    private FragmentListBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Drawable drawable = ContextCompat.getDrawable(root.getContext(), R.drawable.car_dialog);

        Dialog dialog = new Dialog(root.getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.filter_dialog);

        EditText minPrice = dialog.findViewById(R.id.minPrice);
        EditText maxPrice = dialog.findViewById(R.id.maxPrice);
        Button cancel = dialog.findViewById(R.id.cancelFilter);
        Button search = dialog.findViewById(R.id.applyFilter);

        minPrice.setText("0");
        maxPrice.setText("1000");

        RecyclerView carRecyclerView = root.findViewById(R.id.carList);

        ImageView filter = (ImageView) root.findViewById(R.id.filterBtn);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(root.getContext(), "db", null, 1);

        ArrayList<Car> temp;
        ArrayList<Car> carsList;

        if (SelectedDealer.getInstance().dealer == null || SelectedDealer.getInstance().dealer.equals("All")) {
            temp = (ArrayList<Car>) dataBaseHelper.getAllCars("");
            carsList = (ArrayList<Car>) dataBaseHelper.getAllCars("");
        } else {
            temp = (ArrayList<Car>) dataBaseHelper.getAllCars(SelectedDealer.getInstance().dealer);
            carsList = (ArrayList<Car>) dataBaseHelper.getAllCars(SelectedDealer.getInstance().dealer);
        }

        ArrayList<Car> favorites = dataBaseHelper.getFavoriteCars(LoggedInUser.getInstance().user.getEmail());

        ArrayList<String> manufacturers = (ArrayList<String>) getManufacturers(carsList);
        ArrayList<String> models = (ArrayList<String>) getModels(carsList);
        ArrayList<String> years = (ArrayList<String>) getYears(carsList);
        ArrayList<String> fuels = (ArrayList<String>) getFuelTypes(carsList);
        ArrayList<String> seatingCapacity = (ArrayList<String>) getSeatingCapacity(carsList);


        chipGroup1 = dialog.findViewById(R.id.chipGroup);
        chipGroup2 = dialog.findViewById(R.id.chipGroup2);
        chipGroup3 = dialog.findViewById(R.id.chipGroup3);
        chipGroup4 = dialog.findViewById(R.id.chipGroup4);
        chipGroup5 = dialog.findViewById(R.id.chipGroup5);

        fillChips(manufacturers, models, years, fuels, seatingCapacity, dialog);

        setFavorites(favorites, carsList);


        CarsAdapter carsAdapter = new CarsAdapter(requireContext(), carsList, "list");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        carRecyclerView.setLayoutManager(linearLayoutManager);
        carRecyclerView.setAdapter(carsAdapter);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                carsList.clear();
                carsList.addAll(temp);
                setFavorites(favorites, carsList);

                ArrayList<String> selectedManufacturers = new ArrayList<>();
                ArrayList<String> selectedModels = new ArrayList<>();
                ArrayList<String> selectedYears = new ArrayList<>();
                ArrayList<String> selectedFuels = new ArrayList<>();
                ArrayList<String> selectedSeats = new ArrayList<>();

                getSelectedItems(chipGroup1, selectedManufacturers);
                getSelectedItems(chipGroup2, selectedModels);
                getSelectedItems(chipGroup3, selectedYears);
                getSelectedItems(chipGroup4, selectedFuels);
                getSelectedItems(chipGroup5, selectedSeats);

                if (selectedManufacturers.isEmpty())
                    selectedManufacturers.add("All");
                if (selectedYears.isEmpty())
                    selectedYears.add("All");
                if (selectedModels.isEmpty())
                    selectedModels.add("All");


                int priceMin = Integer.parseInt(minPrice.getText().toString());
                int priceMax = Integer.parseInt(maxPrice.getText().toString());

                List<Car> filteredCars = carsList.stream()
                        .filter(car -> (selectedManufacturers.contains("All") || selectedManufacturers.contains(car.getMake()))
                                && (selectedModels.contains("All") || selectedModels.contains(car.getType()))
                                && (selectedYears.isEmpty() || selectedYears.contains("All") || selectedYears.contains(car.getYear()))
                                && (selectedFuels.isEmpty() || selectedFuels.contains("All") || selectedFuels.contains(car.getFuelType()))
                                && (selectedSeats.isEmpty() || selectedSeats.contains("All") || selectedSeats.contains(Integer.toString(car.getSeatingCapacity())))
                                && (car.getPrice() >= priceMin && car.getPrice() <= priceMax))
                        .collect(Collectors.toList());


                carsList.clear();
                carsList.addAll(filteredCars);
                setFavorites(favorites, carsList);


                carsAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        return root;
    }

    private void fillChips(List<String> manufacturers, List<String> models, List<String> years, List<String> fuels, List<String> seatingCapacities, Dialog dialog) {
        Random random = new Random();

        manufacturers.add(0, "All");
        models.add(0, "All");
        years.add(0, "All");
        fuels.add(0, "All");
        seatingCapacities.add(0, "All");

        for (String manufacturer : manufacturers) {
            Chip chip = (Chip) LayoutInflater.from(dialog.getContext()).inflate(R.layout.chip_layout, null);
            chip.setText(manufacturer);
            chip.setId(random.nextInt());
            chipGroup1.addView(chip);

            if (manufacturer.equals("All")) chip.setChecked(true);

        }

        for (String model : models) {
            Chip chip = (Chip) LayoutInflater.from(dialog.getContext()).inflate(R.layout.chip_layout, null);
            chip.setText(model);
            chip.setId(random.nextInt());
            chipGroup2.addView(chip);

            if (model.equals("All")) chip.setChecked(true);

        }

        for (String year : years) {
            Chip chip = (Chip) LayoutInflater.from(dialog.getContext()).inflate(R.layout.chip_layout, null);
            chip.setText(year);
            chip.setId(random.nextInt());
            chipGroup3.addView(chip);

            if (year.equals("All")) chip.setChecked(true);
        }

        for (String fuel : fuels) {
            Chip chip = (Chip) LayoutInflater.from(dialog.getContext()).inflate(R.layout.chip_layout, null);
            chip.setText(fuel);
            chip.setId(random.nextInt());
            chipGroup4.addView(chip);

            if (fuel.equals("All")) chip.setChecked(true);
        }

        for (String seating : seatingCapacities) {
            Chip chip = (Chip) LayoutInflater.from(dialog.getContext()).inflate(R.layout.chip_layout, null);
            chip.setText(seating);
            chip.setId(random.nextInt());
            chipGroup5.addView(chip);

            if (seating.equals("All")) chip.setChecked(true);
        }
    }

    private void setFavorites(ArrayList<Car> favorites, ArrayList<Car> cars) {

        for (Car car : cars) {
            for (Car favorite : favorites) {
                if (favorite.getId() == car.getId()) {
                    car.setFavorite(true);
                    break;
                }
            }
        }
    }

    private void getSelectedItems(ChipGroup chipGroup, List<String> selectedItems) {
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            View childView = chipGroup.getChildAt(i);

            if (childView instanceof Chip) {
                Chip chip = (Chip) childView;

                if (chip.isChecked()) {
                    selectedItems.add(chip.getText().toString());
                }
            }
        }
    }

    private List<String> getManufacturers(List<Car> cars) {
        Set<String> uniqueNamesSet = new HashSet<>();
        for (Car car : cars) {
            uniqueNamesSet.add(car.getMake());
        }
        return new ArrayList<>(uniqueNamesSet);
    }

    private List<String> getModels(List<Car> cars) {
        Set<String> uniqueNamesSet = new HashSet<>();
        for (Car car : cars) {
            uniqueNamesSet.add(car.getType());
        }
        return new ArrayList<>(uniqueNamesSet);
    }

    private List<String> getYears(List<Car> cars) {
        Set<String> uniqueNamesSet = new HashSet<>();
        for (Car car : cars) {
            uniqueNamesSet.add(car.getYear());
        }
        return new ArrayList<>(uniqueNamesSet);
    }

    private List<String> getFuelTypes(List<Car> cars) {
        Set<String> uniqueNamesSet = new HashSet<>();
        for (Car car : cars) {
            uniqueNamesSet.add(car.getFuelType());
        }
        return new ArrayList<>(uniqueNamesSet);
    }

    private List<String> getSeatingCapacity(List<Car> cars) {
        Set<String> uniqueNamesSet = new HashSet<>();
        for (Car car : cars) {
            uniqueNamesSet.add(Integer.toString(car.getSeatingCapacity()));
        }
        return new ArrayList<>(uniqueNamesSet);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}