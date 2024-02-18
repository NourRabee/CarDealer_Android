package com.example.androidproject.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.androidproject.DataBaseHelper;
import com.example.androidproject.LoggedInUser;
import com.example.androidproject.R;
import com.example.androidproject.databinding.FragmentProfileBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.regex.Pattern;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class ProfileFragment extends Fragment {

    private static final Pattern FirstNameLastName_Pattern = Pattern.compile("^[a-zA-Z]{3,}$");
    private static final Pattern PhoneNumber_Pattern = Pattern.compile("^\\d{9}$");

    private static final Pattern Password_Pattern = Pattern.compile("^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[a-z\\d@$!%*?&]{5,}$");

    private FragmentProfileBinding binding;

    FloatingActionButton cameraActionButton;
    ShapeableImageView profilePic;
    DataBaseHelper dataBaseHelper;

    int SELECT_PICTURE = 200;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dataBaseHelper = new DataBaseHelper(root.getContext(), "db", null, 1);

        TextView userName = (TextView) root.findViewById(R.id.userName);
        TextView userEmail = (TextView) root.findViewById(R.id.userMail);
        TextView userPassword = (TextView) root.findViewById(R.id.userPass);
        EditText userFirstName = (EditText) root.findViewById(R.id.userFirstName);
        EditText userLastName = (EditText) root.findViewById(R.id.userLastName);
        EditText userPhoneNumber = (EditText) root.findViewById(R.id.userPhoneNum);
        Button saveBtn = (Button) root.findViewById(R.id.saveBtn);

        cameraActionButton = (FloatingActionButton) root.findViewById(R.id.cameraActionButton);
        profilePic = (ShapeableImageView) root.findViewById(R.id.profilePic);

        String imageURI = dataBaseHelper.getProfileImageByEmail(LoggedInUser.getInstance().user.getEmail());

        if (imageURI != null) {
            profilePic.setImageURI(Uri.parse(imageURI));
        }

        cameraActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        userName.setText(LoggedInUser.getInstance().user.getFirstName() + " " + LoggedInUser.getInstance().user.getLastName());
        userEmail.setText(LoggedInUser.getInstance().user.getEmail());
        userFirstName.setHint(LoggedInUser.getInstance().user.getFirstName());
        userLastName.setHint(LoggedInUser.getInstance().user.getLastName());
        userPhoneNumber.setHint(LoggedInUser.getInstance().user.getPhoneNumber());

        userFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String firstNameInput = s.toString();

                if (!firstNameInput.isEmpty() && !FirstNameLastName_Pattern.matcher(firstNameInput).matches()) {
                    userFirstName.setError("Please enter a valid Name!");
                    userFirstName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else if (!firstNameInput.isEmpty() && FirstNameLastName_Pattern.matcher(firstNameInput).matches()) {
                    userFirstName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else {
                    userFirstName.setError(null); // Clear any previous error
                    userFirstName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
//
            }
        });

        userLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String lastNameInput = s.toString();

                if (!lastNameInput.isEmpty() && !FirstNameLastName_Pattern.matcher(lastNameInput).matches()) {
                    userLastName.setError("Please enter a valid Name!");
                    userLastName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else if (!lastNameInput.isEmpty() && FirstNameLastName_Pattern.matcher(lastNameInput).matches()) {
                    userLastName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else {
                    userLastName.setError(null); // Clear any previous error
                    userLastName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }
        });

        userPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String phoneNumInput = s.toString();

                if (!phoneNumInput.isEmpty() && !PhoneNumber_Pattern.matcher(phoneNumInput).matches()) {
                    userPhoneNumber.setError("Please enter a valid Phone number!");
                    userPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else if (!phoneNumInput.isEmpty() && PhoneNumber_Pattern.matcher(phoneNumInput).matches()) {
                    userPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else {
                    userPhoneNumber.setError(null); // Clear any previous error
                    userPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = userFirstName.getText().toString();
                String lastName = userLastName.getText().toString();
                String phoneNum = userPhoneNumber.getText().toString();

                if (firstName.equals(""))
                    firstName = userFirstName.getHint().toString();
                if (lastName.equals(""))
                    lastName = userLastName.getHint().toString();
                if (phoneNum.equals(""))
                    phoneNum = userPhoneNumber.getHint().toString();

                boolean isFirstNameValid, isLastNameValid, isPhoneNumValid;

                if (!firstName.isEmpty() && FirstNameLastName_Pattern.matcher(firstName).matches()) {

                    isFirstNameValid = true;

                } else if (firstName.isEmpty()) {

                    isFirstNameValid = true;
                } else {

                    isFirstNameValid = false;
                }


                if (!lastName.isEmpty() && FirstNameLastName_Pattern.matcher(lastName).matches()) {

                    isLastNameValid = true;

                } else if (lastName.isEmpty()) {

                    isLastNameValid = true;
                } else {

                    isLastNameValid = false;
                }

                if (!phoneNum.isEmpty() && PhoneNumber_Pattern.matcher(phoneNum).matches()) {

                    isPhoneNumValid = true;

                } else if (phoneNum.isEmpty()) {

                    isPhoneNumValid = true;
                } else {

                    isPhoneNumValid = false;
                }


                if (!isFirstNameValid) {
                    userFirstName.setError("Please enter a valid Name!");
                } else {
                    userFirstName.setError(null);
                }

                if (!isLastNameValid) {
                    userLastName.setError("Please enter a valid Name!");
                } else {
                    userLastName.setError(null);
                }

                if (!isPhoneNumValid) {
                    userPhoneNumber.setError("Please enter a valid Phone number!");
                } else {
                    userPhoneNumber.setError(null);
                }


                if (isFirstNameValid && isLastNameValid && isPhoneNumValid) {

                    dataBaseHelper.changeFirstName(LoggedInUser.getInstance().user.getEmail(), firstName);
                    dataBaseHelper.changeLastName(LoggedInUser.getInstance().user.getEmail(), lastName);
                    dataBaseHelper.changePhoneNumber(LoggedInUser.getInstance().user.getEmail(), phoneNum);

                    LoggedInUser.getInstance().user.setFirstName(!firstName.isEmpty() ? firstName : LoggedInUser.getInstance().user.getFirstName());
                    LoggedInUser.getInstance().user.setLastName(!lastName.isEmpty() ? lastName : LoggedInUser.getInstance().user.getLastName());
                    LoggedInUser.getInstance().user.setPhoneNumber(!phoneNum.isEmpty() ? phoneNum : LoggedInUser.getInstance().user.getPhoneNumber());


                    Toast.makeText(root.getContext(), "Your info has been changed successfully!", Toast.LENGTH_LONG).show();

                    userFirstName.setText("");
                    userLastName.setText("");
                    userPhoneNumber.setText("");

                    userFirstName.setHint(firstName.isEmpty() ? LoggedInUser.getInstance().user.getFirstName() : firstName);
                    userLastName.setHint(lastName.isEmpty() ? LoggedInUser.getInstance().user.getLastName() : lastName);
                    userPhoneNumber.setHint(phoneNum.isEmpty() ? LoggedInUser.getInstance().user.getPhoneNumber() : phoneNum);

                }


            }
        });

        userPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Drawable drawable = ContextCompat.getDrawable(root.getContext(), R.drawable.car_dialog);

                Dialog dialog = new Dialog(root.getContext());
                dialog.setContentView(R.layout.password_assurance);
                dialog.getWindow().setBackgroundDrawable(drawable);

                dialog.setCancelable(false);
                dialog.show();

                TextView closeBtn = (TextView) dialog.findViewById(R.id.closeBtn);
                EditText oldPass = (EditText) dialog.findViewById(R.id.oldPass);
                EditText newPass = (EditText) dialog.findViewById(R.id.newPass);
                EditText confirmPass = (EditText) dialog.findViewById(R.id.confirmPass);
                Button submit = (Button) dialog.findViewById(R.id.submitBtn);


                newPass.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String newPassInput = s.toString();

                        if (!newPassInput.isEmpty() && !Password_Pattern.matcher(newPassInput).matches()) {
                            newPass.setError("Password must be at least 5 characters and include at least 1 character, 1 number, and 1 special character!");
                            newPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                        } else if (!newPassInput.isEmpty() && Password_Pattern.matcher(newPassInput).matches()) {
                            newPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                        } else {
                            newPass.setError(null);
                            newPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        }

                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String oldHashedPassword = LoggedInUser.getInstance().user.getPassword();

                        if (!checkPassword(oldPass.getText().toString(), oldHashedPassword)) {
                            Toast.makeText(dialog.getContext(), "This is not your old password.", Toast.LENGTH_LONG).show();
                        }

                        if (!newPass.getText().toString().equals(confirmPass.getText().toString())) {
                            Toast.makeText(dialog.getContext(), "The new password does not match the confirm one!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(dialog.getContext(), "Password changed successfully!", Toast.LENGTH_LONG).show();

                            String hashedPassword = BCrypt.withDefaults().hashToString(10, confirmPass.getText().toString().toCharArray());
                            LoggedInUser.getInstance().user.setPassword(hashedPassword);

                            DataBaseHelper dataBaseHelper = new DataBaseHelper(dialog.getContext(), "db", null, 1);
                            dataBaseHelper.changePassword(LoggedInUser.getInstance().user.getEmail(), hashedPassword);
                            dialog.dismiss();
                        }

                    }
                });


                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

            }
        });


        return root;
    }

    void imageChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    String uriString = selectedImageUri.toString();

                    dataBaseHelper.updateProfileImage(LoggedInUser.getInstance().user.getEmail(), uriString);

                    profilePic.setImageURI(selectedImageUri);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }
}