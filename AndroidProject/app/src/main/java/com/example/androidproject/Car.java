package com.example.androidproject;

import java.util.ArrayList;
import java.util.List;

public class Car {

    public static List<Car> cars = new ArrayList<>();

    private int id;
    private String make;
    private String type;
    private int price;
    private String year;
    private String color;
    private int distance;
    private int discount;
    private int horsePower;
    private int seatingCapacity;
    private int image;

    private int reserved;
    private boolean favorite = false;

    private String fuelType;
    private double rating;
    private String dealer;

    public Car() {
    }

    public Car(int id, String make, String type, int price, String year, String color, int distance, int discount, int horsePower, int seatingCapacity, int image, int reserved, String fuelType, double rating, String dealer) {
        this.id = id;
        this.make = make;
        this.type = type;
        this.price = price;
        this.year = year;
        this.color = color;
        this.distance = distance;
        this.discount = discount;
        this.horsePower = horsePower;
        this.seatingCapacity = seatingCapacity;
        this.image = image;
        this.reserved = reserved;
        this.fuelType = fuelType;
        this.rating = rating;
        this.dealer = dealer;
    }

    public static List<Car> getCars() {
        return cars;
    }

    public static void setCars(List<Car> cars) {
        Car.cars = cars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public static void fillCarsWithData() {
        setCarData(cars.get(0), "Jeep", 100, "2024", "Black", 0, 10, 250, 6, R.drawable.car1, 0, "Diesel", 0, "nour@gmail.com");
        setCarData(cars.get(1), "Jeep", 200, "2023", "Gray", 0, 0, 250, 4, R.drawable.car2, 0, "Diesel", 0, "nour@gmail.com");
        setCarData(cars.get(2), "Dodge", 300, "2017", "White", 0, 0, 250, 4, R.drawable.car3, 0, "Gasoline", 0, "najwa@gmail.com");
        setCarData(cars.get(3), "Tesla", 50, "2019", "Red", 0, 0, 250, 4, R.drawable.car4, 0, "Electricity", 0, "najwa@gmail.com");
        setCarData(cars.get(4), "Lamborghini", 421, "2020", "Orange", 0, 0, 250, 2, R.drawable.car5, 0, "Gasoline", 0, "najwa@gmail.com");
        setCarData(cars.get(5), "Tesla", 988, "2020", "White", 0, 0, 250, 6, R.drawable.car6, 0, "Hybrid", 0, "nour@gmail.com");
        setCarData(cars.get(6), "Ford", 123, "2021", "Gray", 0, 0, 250, 4, R.drawable.car7, 0, "Diesel", 0, "najwa@gmail.com");
        setCarData(cars.get(7), "Ford", 765, "2019", "Silver", 0, 0, 250, 4, R.drawable.car8, 0, "Diesel", 0, "nour@gmail.com");
        setCarData(cars.get(8), "Alpine", 444, "2021", "Yellow", 0, 30, 250, 4, R.drawable.car9, 0, "Gasoline", 0, "nour@gmail.com");
        setCarData(cars.get(9), "Tesla", 600, "2026", "Gray", 0, 0, 250, 2, R.drawable.car10, 0, "Electricity", 0, "nour@gmail.com");
        setCarData(cars.get(10), "Honda", 123, "2017", "Silver", 0, 0, 250, 6, R.drawable.car11, 0, "Diesel", 0, "najwa@gmail.com");
        setCarData(cars.get(11), "Tesla", 532, "2021", "Black", 0, 0, 250, 4, R.drawable.car12, 0, "Hybrid", 0, "najwa@gmail.com");
        setCarData(cars.get(12), "Toyota", 20, "2022", "Dark Blue", 0, 0, 250, 4, R.drawable.car13, 0, "Diesel", 0, "dealer@gmail.com");
        setCarData(cars.get(13), "Jeep", 321, "2017", "White", 0, 0, 250, 4, R.drawable.car14, 0, "Diesel", 0, "dealer@gmail.com");
        setCarData(cars.get(14), "Lamborghini", 642, "2003", "Yellow", 0, 0, 250, 2, R.drawable.car15, 0, "Diesel", 0, "dealer@gmail.com");
        setCarData(cars.get(15), "Lamborghini", 666, "2022", "Blue", 0, 99, 250, 6, R.drawable.car16, 0, "Gasoline", 0, "nour@gmail.com");
        setCarData(cars.get(16), "Jeep", 753, "2017", "White", 0, 0, 250, 4, R.drawable.car17, 0, "Diesel", 0, "dealer@gmail.com");
        setCarData(cars.get(17), "Ford", 545, "2018", "Silver", 0, 0, 250, 4, R.drawable.car18, 0, "Diesel", 0, "dealer@gmail.com");
        setCarData(cars.get(18), "Tesla", 800, "2020", "Silver", 0, 0, 250, 4, R.drawable.car19, 0, "Electricity", 0, "nour@gmail.com");
        setCarData(cars.get(19), "Honda", 920, "2019", "White", 0, 12, 250, 4, R.drawable.car20, 0, "Gasoline", 0, "dealer@gmail.com");
        setCarData(cars.get(20), "Honda", 102, "2011", "Gray", 0, 0, 250, 2, R.drawable.car21, 0, "Hybrid", 0, "najwa@gmail.com");
        setCarData(cars.get(21), "Koenigsegg", 630, "2014", "White", 0, 0, 250, 6, R.drawable.car22, 0, "Diesel", 0, "najwa@gmail.com");
        cars.get(21).setType("One");

        setCarData(cars.get(22), "Chevrolet", 750, "2018", "Red", 0, 0, 250, 4, R.drawable.car23, 0, "Gasoline", 0, "najwa@gmail.com");

        setCarData(cars.get(23), "Volkswagen", 500, "2022", "Blue", 0, 0, 250, 4, R.drawable.car24, 0, "Gasoline", 0, "nour@gmail.com");
        cars.get(23).setType("Arteon");

        System.out.println();
    }

    private static void setCarData(Car car, String make, int price, String year, String color, int distance, int discount, int horsePower, int seatingCapacity, int image, int reserved, String fuelType, double rating, String dealer) {
        car.setId(car.getId() - 1);
        car.setMake(make);
        car.setPrice(price);
        car.setYear(year);
        car.setColor(color);
        car.setDistance(distance);
        car.setDiscount(discount);
        car.setHorsePower(horsePower);
        car.setSeatingCapacity(seatingCapacity);
        car.setImage(image);
        car.setReserved(reserved);
        car.setFuelType(fuelType);
        car.setRating(rating);
        car.setDealer(dealer);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", type='" + type + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}