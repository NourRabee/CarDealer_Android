package com.example.androidproject;

import java.util.ArrayList;

public class User implements Cloneable {
    public static ArrayList<User> allUsers = new ArrayList<>();

    private int isAdmin;
    private String email;
    private String gender;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String country;
    private String city;
    private String profileImage;
    private String lastLogin;
    private String manager;
    private int superAdmin;

    public User(String email, String firstName, String lastName, String password, String gender, String phoneNumber,
                String country, String city, String profileImage, int isAdmin, int superAdmin) {
        this.isAdmin = isAdmin;
        this.email = email;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.country = country;
        this.superAdmin = superAdmin;
        this.profileImage = profileImage;
    }

    public User() {
    }

    public int isAdmin() {
        return isAdmin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAdmin(int admin) {
        isAdmin = admin;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(int superAdmin) {
        this.superAdmin = superAdmin;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "isAdmin =" + isAdmin +
                ", email ='" + email + '\'' +
                ", gender ='" + gender + '\'' +
                ", firstName ='" + firstName + '\'' +
                ", lastName ='" + lastName + '\'' +
                ", password ='" + password + '\'' +
                ", phone ='" + phoneNumber + '\'' +
                ", city ='" + city + '\'' +
                ", country ='" + country + '\'' +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}