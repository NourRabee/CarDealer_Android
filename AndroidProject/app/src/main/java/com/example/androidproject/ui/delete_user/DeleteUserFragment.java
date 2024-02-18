package com.example.androidproject.ui.delete_user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.DataBaseHelper;
import com.example.androidproject.LoggedInUser;
import com.example.androidproject.R;
import com.example.androidproject.databinding.FragmentDeleteUserBinding;

import java.util.ArrayList;
import java.util.List;


public class DeleteUserFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<String> usersEmail;

    private FragmentDeleteUserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDeleteUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set windowSoftInputMode to adjustPan
        if (getActivity() != null) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext(), "db", null, 1);

        if (LoggedInUser.getInstance().user.getSuperAdmin() == 1) {

            usersEmail = dataBaseHelper.getAllUsersExceptSuperAdmin();


        } else if (LoggedInUser.getInstance().user.getSuperAdmin() == 0) {

            usersEmail = dataBaseHelper.getUserEmailsExcludingSuperAdmins(LoggedInUser.getInstance().user.getEmail());

        }


        searchView = root.findViewById(R.id.searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filterText(newText);
                return true;
            }
        });

        recyclerView = root.findViewById(R.id.rvDeleteAdmin);

        UserEmailAdapter userEmailAdapter = new UserEmailAdapter(requireContext(), usersEmail);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userEmailAdapter);

        return root;
    }

    private void filterText(String text) {

        List<String> filterdList = new ArrayList<>();
        for (String email : usersEmail) {

            if (email.toLowerCase().contains(text.toLowerCase())) {

                filterdList.add(email);
            }
        }
        if (filterdList.isEmpty()) {

            Toast.makeText(getContext(), "No data found!", Toast.LENGTH_SHORT).show();
        } else {
            UserEmailAdapter userEmailAdapter = new UserEmailAdapter(requireContext(), filterdList);
            userEmailAdapter.setFilteredList(filterdList);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(userEmailAdapter);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}