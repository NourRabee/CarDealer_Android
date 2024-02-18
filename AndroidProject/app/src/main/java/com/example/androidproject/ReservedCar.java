package com.example.androidproject;

public class ReservedCar {
    private int carId;
    private String make;
    private String model;
    private int price;
    private String year;
    private String color;
    private int distance;
    private int offer;
    private int horsepower;
    private int seatingCapacity;
    private int image;
    private int reserved;
    private String date;
    private String firstName;
    private String lastName;
    private String email;
    private String reservedColor;
    private String dealer;
    private double rating;

    public ReservedCar(int carId, String make, String model, int price, String year,
                       String color, int distance, int offer, int horsepower,
                       int seatingCapacity, int image, int reserved, String date,
                       String firstName, String lastName, String email, String reservedColor, double rating, String dealer) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.price = price;
        this.year = year;
        this.color = color;
        this.distance = distance;
        this.offer = offer;
        this.horsepower = horsepower;
        this.seatingCapacity = seatingCapacity;
        this.image = image;
        this.reserved = reserved;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.reservedColor = reservedColor;
        this.rating = rating;
        this.dealer = dealer;
    }

    public int getCarId() {
        return carId;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public String getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public int getDistance() {
        return distance;
    }

    public int getOffer() {
        return offer;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public int getImage() {
        return image;
    }

    public int getReserved() {
        return reserved;
    }

    public String getDate() {
        return date;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getReservedColor() {
        return reservedColor;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

