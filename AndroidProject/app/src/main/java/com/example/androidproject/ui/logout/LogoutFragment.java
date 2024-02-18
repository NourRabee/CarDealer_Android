package com.example.androidproject.ui.logout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidproject.AdminActivity;
import com.example.androidproject.CustomerActivity;
import com.example.androidproject.LoggedInUser;
import com.example.androidproject.LoginActivity;
import com.example.androidproject.R;
import com.example.androidproject.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {


    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Drawable drawable = ContextCompat.getDrawable(root.getContext(), R.drawable.car_dialog);

        Dialog dialog = new Dialog(root.getContext());
        dialog.setContentView(R.layout.logout_assurance);
        dialog.getWindow().setBackgroundDrawable(drawable);

        dialog.setCancelable(false);
        dialog.show();

        Button cancelBtn = (Button) dialog.findViewById(R.id.cancelBtn);
        Button logoutBtn = (Button) dialog.findViewById(R.id.deleteBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if (LoggedInUser.getInstance().user.getIsAdmin() == 1) {

                    intent = new Intent(getActivity(), AdminActivity.class);

                }else{
                    intent = new Intent(getActivity(), CustomerActivity.class);
                }

                startActivity(intent);

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}