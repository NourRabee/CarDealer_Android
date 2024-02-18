package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.databinding.ActivityLoginBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity {

    private TextView signUp;
    private Button loginButton;

    private TextInputEditText loginEmail;
    private TextInputEditText loginPassword;

    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences loggedOnUser = getSharedPreferences("LoggedUser", Context.MODE_PRIVATE);

        signUp = findViewById(R.id.signUp);
        loginButton = findViewById(R.id.logInButton);
        loginEmail = findViewById(R.id.email);
        loginPassword = findViewById(R.id.password);
        rememberMe = findViewById(R.id.rememberMeCheckBox);

        if (!loggedOnUser.getString("email", "").isEmpty()) {
            loginEmail.setText(loggedOnUser.getString("email", ""));
            rememberMe.setChecked(true);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Objects.requireNonNull(loginEmail.getText()).toString();
                String password = Objects.requireNonNull(loginPassword.getText()).toString();

                DataBaseHelper dataBaseHelper = new DataBaseHelper(LoginActivity.this, "db", null, 1);

                if (email.equals("") || password.equals(""))
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else {
                    User loggedUser = dataBaseHelper.getUser(email);

                    if (checkPassword(password, loggedUser.getPassword())) {

                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();

                        LoggedInUser loggedInUser = LoggedInUser.getInstance();
                        try {
                            loggedInUser.user = (User) loggedUser.clone();
                        } catch (CloneNotSupportedException e) {
                            throw new RuntimeException(e);
                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        String lastLogin = sdf.format(new Date());

                        dataBaseHelper.updateLastLogin(loggedInUser.user.getEmail(), lastLogin);

                        Intent intent;

                        if (rememberMe.isChecked()) {
                            SharedPreferences.Editor editor = loggedOnUser.edit();
                            editor.putString("email", email);
                            editor.apply();
                        } else {
                            SharedPreferences.Editor editor = loggedOnUser.edit();
                            editor.putString("email", null);
                            editor.apply();
                        }

                        if (loggedUser.isAdmin() == 1)
                            intent = new Intent(getApplicationContext(), AdminActivity.class);
                        else
                            intent = new Intent(getApplicationContext(), DealerActivity.class);

                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }
}