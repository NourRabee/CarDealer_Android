package com.example.androidproject;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.androidproject.databinding.ActivityCustomerBinding;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityCustomerBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarCustomer.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_car, R.id.nav_contact, R.id.nav_favorite, R.id.nav_logout, R.id.nav_profile, R.id.nav_special, R.id.nav_reservations)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_customer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        TextView name = (TextView) headerView.findViewById(R.id.userName);
        TextView email = (TextView) headerView.findViewById(R.id.userEmail);
        CircleImageView userImage = (CircleImageView) headerView.findViewById(R.id.userImage);

        name.setText(LoggedInUser.getInstance().user.getFirstName() + " " + LoggedInUser.getInstance().user.getLastName());
        email.setText(LoggedInUser.getInstance().user.getEmail());

        if (LoggedInUser.getInstance().user.getProfileImage() != null) {
            userImage.setImageURI(Uri.parse(LoggedInUser.getInstance().user.getProfileImage()));
        }

        NotificationHelper.createNotificationChannel(this);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(CustomerActivity.this, "db", null, 1);
        if (dataBaseHelper.checkIfRecordsExist() == 1) {
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            Notification notification = NotificationHelper.createNotification(
                    this,
                    "Special Offers",
                    "Check special offers page"
            );

            notificationManager.notify(1, notification);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_customer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}