package com.example.androidproject.ui.contact;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.DataBaseHelper;
import com.example.androidproject.R;
import com.example.androidproject.SelectedDealer;
import com.example.androidproject.User;
import com.example.androidproject.databinding.FragmentContactBinding;

import org.w3c.dom.Text;

public class ContactFragment extends Fragment {

    private FragmentContactBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Button emailBtn = (Button) root.findViewById(R.id.emailBtn);
        Button callUsBtn = (Button) root.findViewById(R.id.phoneBtn);
        Button googleMapsBtn = (Button) root.findViewById(R.id.mapsBtn);
        TextView contactEmail = (TextView) root.findViewById(R.id.contactEmail);
        TextView phoneContact = (TextView) root.findViewById(R.id.phoneContact);
        TextView locationContact = (TextView) root.findViewById(R.id.locationContact);


        DataBaseHelper dataBaseHelper = new DataBaseHelper(root.getContext(), "db", null, 1);
        User contactInfo = new User();

        if (SelectedDealer.getInstance().dealer != null && !SelectedDealer.getInstance().dealer.equals("All")) {
            contactInfo = dataBaseHelper.getUserDetailsByEmail(SelectedDealer.getInstance().dealer);
            contactEmail.setText(SelectedDealer.getInstance().dealer);
            phoneContact.setText(contactInfo.getPhoneNumber());
            locationContact.setText(contactInfo.getCountry() + "\n" + contactInfo.getCity());
        }

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));

                if (SelectedDealer.getInstance().dealer != null && !SelectedDealer.getInstance().dealer.equals("All"))
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{SelectedDealer.getInstance().dealer});
                else
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"CarDealer@Contact.com"});

                if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null) {

                    startActivity(emailIntent);
                } else {
                    Toast.makeText(getContext(), "No email app found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        User finalContactInfo = contactInfo;
        callUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent with ACTION_DIAL and a tel: URI
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                if (SelectedDealer.getInstance().dealer != null && !SelectedDealer.getInstance().dealer.equals("All"))
                    callIntent.setData(Uri.parse("tel:" + finalContactInfo.getPhoneNumber()));
                else
                    callIntent.setData(Uri.parse("tel:0599000000"));

                // Check if there's an app that can handle this Intent
                if (callIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Start the phone call activity
                    startActivity(callIntent);
                } else {
                    // Handle the case where there's no app available to make a phone call
                    Toast.makeText(getContext(), "No phone app found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        googleMapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("geo:0,0?q=25 Olive Street, Ramallah");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(getContext(), "Google Maps app not found", Toast.LENGTH_SHORT).show();
                }
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