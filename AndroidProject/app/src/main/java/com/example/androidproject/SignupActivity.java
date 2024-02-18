package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SignupActivity extends AppCompatActivity {

    LoggedInUser loggedInUser;

    String[] countryList = {"USA", "Canada", "United Kingdom", "Germany", "Australia", "Japan", "Brazil", "Palestine", "Saudi Arabia", "Egypt", "United Arab Emirates", "Lebanon"};

    private Map<String, List<String>> cityMap = new HashMap<>();
    private Map<String, String> areaCodeMap = new HashMap<>();

    private Button signUpButton;
    private TextInputEditText signUpEmail;
    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private AutoCompleteTextView gender;
    private AutoCompleteTextView country;
    private AutoCompleteTextView city;
    private TextInputEditText password;
    private TextInputEditText confirm_password;
    private TextInputEditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeCityData();
        initializeAreaCodeData();

        signUpButton = findViewById(R.id.signup_main);
        signUpEmail = findViewById(R.id.email);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        phoneNumber = findViewById(R.id.phoneNumber);

        String passPattern = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[a-z\\d@$!%*?&]{5,}$";

        String[] genderList = {"Male", "Female"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, genderList);
        gender = findViewById(R.id.gender);
        gender.setAdapter(genderAdapter);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, countryList);
        country = findViewById(R.id.country);
        country.setAdapter(countryAdapter);

        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        city = findViewById(R.id.city);
        city.setAdapter(cityAdapter);

        country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = (String) parent.getItemAtPosition(position);
                updateCityAutoComplete(selectedCountry, cityAdapter);

                city.setText("");
                setAreaCode(selectedCountry);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signUpEmail.getText().toString().isEmpty() || firstName.getText().toString().isEmpty() ||
                        lastName.getText().toString().isEmpty() || password.getText().toString().isEmpty() ||
                        confirm_password.getText().toString().isEmpty() || phoneNumber.getText().toString().isEmpty() ||
                        country.getText().toString().isEmpty() || city.getText().toString().isEmpty() || gender.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(SignupActivity.this, "Something's Missing... Check Input Fields", Toast.LENGTH_LONG);
                    toast.show();
                } else if (!signUpEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    Toast toast = Toast.makeText(SignupActivity.this, "Email is not Valid", Toast.LENGTH_LONG);
                    toast.show();
                } else if (firstName.getText().toString().length() < 3 || lastName.getText().toString().length() < 3) {
                    Toast toast = Toast.makeText(SignupActivity.this, "Name can't be less than 3 characters", Toast.LENGTH_LONG);
                    toast.show();
                } else if (!Pattern.compile(passPattern).matcher(password.getText().toString()).matches()) {
                    Toast toast = Toast.makeText(SignupActivity.this, "Password must be at least 5 characters and include at least 1 character, 1 number, and 1 special character", Toast.LENGTH_LONG);
                    toast.show();
                } else if (!password.getText().toString().equals(confirm_password.getText().toString())) {
                    Toast toast = Toast.makeText(SignupActivity.this, "passwords don't match", Toast.LENGTH_LONG);
                    toast.show();
                } else if (phoneNumber.getText().toString().length() < 9) {
                    Toast toast = Toast.makeText(SignupActivity.this, "Phone number is not valid", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    String hashedPassword = BCrypt.withDefaults().hashToString(10, password.getText().toString().toCharArray());


                    User user = new User(signUpEmail.getText().toString(), firstName.getText().toString(), lastName.getText().toString(),
                            hashedPassword, gender.getText().toString(), phoneNumber.getText().toString(), country.getText().toString(), city.getText().toString(), null, 0, 0);


                    DataBaseHelper databaseHelper = new DataBaseHelper(SignupActivity.this, "db", null, 1);
                    databaseHelper.insertUser(user);

                    Toast toast = Toast.makeText(SignupActivity.this, "Successfully Registered", Toast.LENGTH_LONG);
                    toast.show();

                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    SignupActivity.this.startActivity(intent);
                    finish();
                }
            }
        });


    }

    private void updateCityAutoComplete(String selectedCountry, ArrayAdapter<String> cityAdapter) {
        List<String> cities = cityMap.get(selectedCountry);
        cityAdapter.clear();
        if (cities != null) {
            cityAdapter.addAll(cities);
        }
    }

    private void setAreaCode(String selectedCountry) {
        String areaCode = areaCodeMap.get(selectedCountry);
        TextInputEditText phoneNumberEditText = findViewById(R.id.phoneNumber);

        if (areaCode != null) {
            phoneNumberEditText.setText("+" + areaCode + " ");
        }
    }

    private void initializeCityData() {

        cityMap.put("USA", new ArrayList<>(List.of("New York", "Los Angeles", "Chicago", "Houston", "Miami")));
        cityMap.put("Canada", new ArrayList<>(List.of("Toronto", "Vancouver", "Montreal", "Calgary", "Ottawa")));
        cityMap.put("United Kingdom", new ArrayList<>(List.of("London", "Manchester", "Birmingham", "Edinburgh", "Glasgow")));
        cityMap.put("Germany", new ArrayList<>(List.of("Berlin", "Munich", "Hamburg", "Frankfurt", "Cologne")));
        cityMap.put("Australia", new ArrayList<>(List.of("Sydney", "Melbourne", "Brisbane", "Perth", "Adelaide")));
        cityMap.put("Japan", new ArrayList<>(List.of("Tokyo", "Osaka", "Kyoto", "Yokohama", "Sapporo")));
        cityMap.put("Brazil", new ArrayList<>(List.of("Sao Paulo", "Rio de Janeiro", "Brasilia", "Salvador", "Fortaleza")));
        cityMap.put("Palestine", new ArrayList<>(List.of("Jerusalem", "Ramallah", "Bethlehem", "Hebron", "Gaza City")));
        cityMap.put("Saudi Arabia", new ArrayList<>(List.of("Riyadh", "Jeddah", "Mecca", "Medina", "Dammam")));
        cityMap.put("Egypt", new ArrayList<>(List.of("Cairo", "Alexandria", "Giza", "Luxor", "Aswan")));
        cityMap.put("United Arab Emirates", new ArrayList<>(List.of("Dubai", "Abu Dhabi", "Sharjah", "Ajman", "Ras Al Khaimah")));
        cityMap.put("Lebanon", new ArrayList<>(List.of("Beirut", "Tripoli", "Sidon", "Tyre", "Byblos")));

    }

    private void initializeAreaCodeData() {
        areaCodeMap.put("USA", "001");
        areaCodeMap.put("Canada", "001");
        areaCodeMap.put("United Kingdom", "0044");
        areaCodeMap.put("Germany", "0049");
        areaCodeMap.put("Australia", "0061");
        areaCodeMap.put("Japan", "0081");
        areaCodeMap.put("Brazil", "0055");
        areaCodeMap.put("Palestine", "00970");
        areaCodeMap.put("Saudi Arabia", "00966");
        areaCodeMap.put("Egypt", "0020");
        areaCodeMap.put("United Arab Emirates", "00971");
        areaCodeMap.put("Lebanon", "00961");
    }
}

