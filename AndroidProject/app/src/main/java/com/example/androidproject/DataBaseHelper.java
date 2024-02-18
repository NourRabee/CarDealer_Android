package com.example.androidproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // CAR
        sqLiteDatabase.execSQL("CREATE TABLE CAR("
                + "ID INTEGER PRIMARY KEY UNIQUE,"
                + "MAKE TEXT,"
                + "MODEL TEXT,"
                + "PRICE INTEGER,"
                + "YEAR TEXT,"
                + "COLOR TEXT,"
                + "DISTANCE INTEGER,"
                + "OFFER INTEGER,"
                + "HORSEPOWER INTEGER,"
                + "SEATING_CAPACITY INTEGER,"
                + "IMAGE INTEGER,"
                + "RESERVED INTEGER,"
                + "RATING REAL,"
                + "FUEL_TYPE TEXT,"
                + "DEALER TEXT)");

        // RESERVE
        sqLiteDatabase.execSQL("CREATE TABLE RESERVE"
                + "(ID INTEGER PRIMARY KEY UNIQUE, "
                + "EMAIL TEXT,"
                + "CARID INTEGER,"
                + "RESERVED_COLOR TEXT,"
                + "DATE TEXT,"
                + "RATING REAL,"
                + "FOREIGN KEY(CARID) REFERENCES CAR(ID),"
                + "FOREIGN KEY(EMAIL) REFERENCES USER(EMAIL))");

        // FAVORITE
        sqLiteDatabase.execSQL("CREATE TABLE FAVORITE("
                + "ID INTEGER PRIMARY KEY UNIQUE, "
                + "EMAIL TEXT, "
                + "CARID INTEGER, "
                + "FOREIGN KEY(CARID) REFERENCES CAR(ID),"
                + "FOREIGN KEY(EMAIL) REFERENCES USER(EMAIL))");

        // USER
        sqLiteDatabase.execSQL("CREATE TABLE USER("
                + "EMAIL TEXT PRIMARY KEY,"
                + "FIRSTNAME TEXT,"
                + "LASTNAME TEXT, "
                + "HASHED_PASSWORD TEXT,"
                + "GENDER TEXT,"
                + "PHONENUMBER TEXT,"
                + "COUNTRY TEXT,"
                + "CITY TEXT,"
                + "PROFILE_IMAGE TEXT,"
                + "MANAGER TEXT,"
                + "LAST_LOGIN TEXT,"
                + "isAdmin INTEGER,"
                + "isSuperAdmin INTEGER)");


        String hashedNour = BCrypt.withDefaults().hashToString(10, "nour".toCharArray());
        String hashedNajwa = BCrypt.withDefaults().hashToString(10, "najwa".toCharArray());
        String hashedDealer = BCrypt.withDefaults().hashToString(10, "dealer".toCharArray());
        String hashedTest = BCrypt.withDefaults().hashToString(10, "test".toCharArray());

        // isAdmin 0 isSuperAdmin 0 -> Customer
        // isAdmin 1 isSuperAdmin 0 -> Employee belongs to dealer
        // isAdmin 1 isSuperAdmin 1 -> Dealer

        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", "nour@gmail.com");
        contentValues.put("FIRSTNAME", "Nour");
        contentValues.put("LASTNAME", "Rabee");
        contentValues.put("GENDER", "Female");
        contentValues.put("HASHED_PASSWORD", hashedNour);
        contentValues.put("COUNTRY", "Palestine");
        contentValues.put("CITY", "Ramallah");
        contentValues.put("PHONENUMBER", "059");
        contentValues.put("isAdmin", 1);
        contentValues.put("isSuperAdmin", 1);
        sqLiteDatabase.insert("USER", null, contentValues);

        contentValues.put("EMAIL", "najwa@gmail.com");
        contentValues.put("FIRSTNAME", "Najwa");
        contentValues.put("LASTNAME", "Bsharat");
        contentValues.put("GENDER", "Female");
        contentValues.put("HASHED_PASSWORD", hashedNajwa);
        contentValues.put("COUNTRY", "Palestine");
        contentValues.put("CITY", "Ramallah");
        contentValues.put("PHONENUMBER", "059");
        contentValues.put("PROFILE_IMAGE", "");
        contentValues.put("isAdmin", 1);
        contentValues.put("isSuperAdmin", 1);
        sqLiteDatabase.insert("USER", null, contentValues);

        contentValues.put("EMAIL", "dealer@gmail.com");
        contentValues.put("FIRSTNAME", "Dealer");
        contentValues.put("LASTNAME", "Dealer");
        contentValues.put("GENDER", "Female");
        contentValues.put("HASHED_PASSWORD", hashedDealer);
        contentValues.put("COUNTRY", "Palestine");
        contentValues.put("CITY", "Ramallah");
        contentValues.put("PHONENUMBER", "059");
        contentValues.put("PROFILE_IMAGE", "");
        contentValues.put("isAdmin", 1);
        contentValues.put("isSuperAdmin", 1);
        sqLiteDatabase.insert("USER", null, contentValues);

        contentValues.put("EMAIL", "test@gmail.com");
        contentValues.put("FIRSTNAME", "Test");
        contentValues.put("LASTNAME", "Test");
        contentValues.put("GENDER", "Female");
        contentValues.put("HASHED_PASSWORD", hashedTest);
        contentValues.put("COUNTRY", "Palestine");
        contentValues.put("CITY", "Ramallah");
        contentValues.put("PHONENUMBER", "059");
        contentValues.put("PROFILE_IMAGE", "");
        contentValues.put("isAdmin", 0);
        contentValues.put("isSuperAdmin", 0);
        sqLiteDatabase.insert("USER", null, contentValues);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("FIRSTNAME", user.getFirstName());
        contentValues.put("LASTNAME", user.getLastName());
        contentValues.put("HASHED_PASSWORD", user.getPassword());
        contentValues.put("GENDER", user.getGender());
        contentValues.put("PHONENUMBER", user.getPhoneNumber());
        contentValues.put("CITY", user.getCity());
        contentValues.put("COUNTRY", user.getCountry());
        contentValues.put("PROFILE_IMAGE", user.getCountry());
        contentValues.put("MANAGER", user.getManager());
        contentValues.put("isAdmin", user.isAdmin());
        contentValues.put("isSuperAdmin", user.getSuperAdmin());
        sqLiteDatabase.insert("USER", null, contentValues);

        sqLiteDatabase.close();
    }

    public void removeUserByEmail(String userEmail) {
        SQLiteDatabase db = this.getWritableDatabase();

        String table = "USER";
        String whereClause = "EMAIL=?";
        String[] whereArgs = new String[]{userEmail};

        int deletedRows = db.delete(table, whereClause, whereArgs);

        db.close();
    }


    public List<String> getAllUserEmails() {
        List<String> userEmails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String table = "USER";
        String[] columns = {"EMAIL"};

        Cursor cursor = db.query(table, columns, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("EMAIL"));
                userEmails.add(email);
            }
            cursor.close();
        }

        db.close();

        return userEmails;
    }

    @SuppressLint("Range")
    public User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String[] projection = {
                "EMAIL",
                "FIRSTNAME",
                "LASTNAME",
                "HASHED_PASSWORD",
                "GENDER",
                "PHONENUMBER",
                "COUNTRY",
                "CITY",
                "PROFILE_IMAGE",
                "LAST_LOGIN",
                "isAdmin",
                "isSuperAdmin"
        };

        String selection = "EMAIL=?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query("USER", projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
            user.setFirstName(cursor.getString(cursor.getColumnIndex("FIRSTNAME")));
            user.setLastName(cursor.getString(cursor.getColumnIndex("LASTNAME")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("HASHED_PASSWORD")));
            user.setGender(cursor.getString(cursor.getColumnIndex("GENDER")));
            user.setPhoneNumber(cursor.getString(cursor.getColumnIndex("PHONENUMBER")));
            user.setCountry(cursor.getString(cursor.getColumnIndex("COUNTRY")));
            user.setCity(cursor.getString(cursor.getColumnIndex("CITY")));
            user.setProfileImage(cursor.getString(cursor.getColumnIndex("PROFILE_IMAGE")));
            user.setLastLogin(cursor.getString(cursor.getColumnIndex("LAST_LOGIN")));
            user.setAdmin(cursor.getInt(cursor.getColumnIndex("isAdmin")));
            user.setSuperAdmin(cursor.getInt(cursor.getColumnIndex("isSuperAdmin")));

            cursor.close();
        }

        db.close();

        return user;
    }


    public void seedWithCars() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        for (Car car : Car.cars) {
            values.put("ID", car.getId());
            values.put("MAKE", car.getMake());
            values.put("MODEL", car.getType());
            values.put("PRICE", car.getPrice());
            values.put("YEAR", car.getYear());
            values.put("COLOR", car.getColor());
            values.put("DISTANCE", car.getDistance());
            values.put("OFFER", car.getDiscount());
            values.put("HORSEPOWER", car.getHorsePower());
            values.put("SEATING_CAPACITY", car.getSeatingCapacity());
            values.put("IMAGE", car.getImage());
            values.put("RESERVED", car.getReserved());
            values.put("FUEL_TYPE", car.getFuelType());
            values.put("RATING", car.getRating());
            values.put("DEALER", car.getDealer());

            db.insert("CAR", null, values);
        }
        db.close();
    }

    public void insertReservation(String email, int carId, String date, String color) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("CARID", carId);
        values.put("DATE", date);
        values.put("RESERVED_COLOR", color);

        long newRowId = db.insert("RESERVE", null, values);

        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Car> getReservedCarsForUser(String userEmail) {
        ArrayList<Car> reservedCars = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT CAR.* " + "FROM CAR " + "JOIN RESERVE ON CAR.ID = RESERVE.CARID " + "WHERE RESERVE.EMAIL = ?";

        Cursor cursor = db.rawQuery(query, new String[]{userEmail});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int carId = cursor.getInt(cursor.getColumnIndex("ID"));
                    String make = cursor.getString(cursor.getColumnIndex("MAKE"));
                    String model = cursor.getString(cursor.getColumnIndex("MODEL"));
                    int price = cursor.getInt(cursor.getColumnIndex("PRICE"));
                    String year = cursor.getString(cursor.getColumnIndex("YEAR"));
                    String color = cursor.getString(cursor.getColumnIndex("COLOR"));
                    int distance = cursor.getInt(cursor.getColumnIndex("DISTANCE"));
                    int offer = cursor.getInt(cursor.getColumnIndex("OFFER"));
                    int horsepower = cursor.getInt(cursor.getColumnIndex("HORSEPOWER"));
                    int seatingCapacity = cursor.getInt(cursor.getColumnIndex("SEATING_CAPACITY"));
                    int image = cursor.getInt(cursor.getColumnIndex("IMAGE"));
                    int reserved = cursor.getInt(cursor.getColumnIndex("RESERVED"));
                    double rating = cursor.getDouble(cursor.getColumnIndex("RATING"));
                    String fuel = cursor.getString(cursor.getColumnIndex("FUEL_TYPE"));
                    String reservedColor = cursor.getString(cursor.getColumnIndex("RESERVED_COLOR"));
                    String dealer = cursor.getString(cursor.getColumnIndex("DEALER"));

                    Car car = new Car(carId, make, model, price, year, reservedColor, distance, offer, horsepower, seatingCapacity, image, reserved, fuel, rating, dealer);

                    reservedCars.add(car);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();

        return reservedCars;
    }

    public void addToFavorites(String userEmail, int carId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("EMAIL", userEmail);
        values.put("CARID", carId);

        db.insert("FAVORITE", null, values);

        db.close();
    }

    public void removeFromFavorites(String userEmail, int carId) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = "EMAIL = ? AND CARID = ?";

        String[] whereArgs = {userEmail, String.valueOf(carId)};

        db.delete("FAVORITE", whereClause, whereArgs);

        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Car> getFavoriteCars(String userEmail) {
        ArrayList<Car> favoriteCars = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT CAR.* " + "FROM CAR " + "JOIN FAVORITE ON CAR.ID = FAVORITE.CARID " + "WHERE FAVORITE.EMAIL = ?";

        Cursor cursor = db.rawQuery(query, new String[]{userEmail});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int carId = cursor.getInt(cursor.getColumnIndex("ID"));
                    String make = cursor.getString(cursor.getColumnIndex("MAKE"));
                    String model = cursor.getString(cursor.getColumnIndex("MODEL"));
                    int price = cursor.getInt(cursor.getColumnIndex("PRICE"));
                    String year = cursor.getString(cursor.getColumnIndex("YEAR"));
                    String color = cursor.getString(cursor.getColumnIndex("COLOR"));
                    int distance = cursor.getInt(cursor.getColumnIndex("DISTANCE"));
                    int offer = cursor.getInt(cursor.getColumnIndex("OFFER"));
                    int horsepower = cursor.getInt(cursor.getColumnIndex("HORSEPOWER"));
                    int seatingCapacity = cursor.getInt(cursor.getColumnIndex("SEATING_CAPACITY"));
                    int image = cursor.getInt(cursor.getColumnIndex("IMAGE"));
                    int reserved = cursor.getInt(cursor.getColumnIndex("RESERVED"));
                    String fuel = cursor.getString(cursor.getColumnIndex("FUEL_TYPE"));
                    double rating = cursor.getDouble(cursor.getColumnIndex("RATING"));
                    String dealer = cursor.getString(cursor.getColumnIndex("DEALER"));

                    Car car = new Car(carId, make, model, price, year, color, distance, offer, horsepower, seatingCapacity, image, reserved, fuel, rating, dealer);

                    favoriteCars.add(car);
                } while (cursor.moveToNext());
            }

            cursor.close();
        }

        db.close();

        return favoriteCars;
    }

    @SuppressLint("Range")
    public ArrayList<Car> getCarsWithDiscount(String dealerEmail) {
        ArrayList<Car> carsWithDiscount = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM CAR WHERE OFFER > 0";

        if (dealerEmail != null && !dealerEmail.isEmpty()) {
            query += " AND DEALER = ?";
        }

        Cursor cursor = db.rawQuery(query, (dealerEmail != null && !dealerEmail.isEmpty()) ? new String[]{dealerEmail} : null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int carId = cursor.getInt(cursor.getColumnIndex("ID"));
                    String make = cursor.getString(cursor.getColumnIndex("MAKE"));
                    String model = cursor.getString(cursor.getColumnIndex("MODEL"));
                    int price = cursor.getInt(cursor.getColumnIndex("PRICE"));
                    String year = cursor.getString(cursor.getColumnIndex("YEAR"));
                    String color = cursor.getString(cursor.getColumnIndex("COLOR"));
                    int distance = cursor.getInt(cursor.getColumnIndex("DISTANCE"));
                    int offer = cursor.getInt(cursor.getColumnIndex("OFFER"));
                    int horsepower = cursor.getInt(cursor.getColumnIndex("HORSEPOWER"));
                    int seatingCapacity = cursor.getInt(cursor.getColumnIndex("SEATING_CAPACITY"));
                    int image = cursor.getInt(cursor.getColumnIndex("IMAGE"));
                    int reserved = cursor.getInt(cursor.getColumnIndex("RESERVED"));
                    String fuel = cursor.getString(cursor.getColumnIndex("FUEL_TYPE"));
                    double rating = cursor.getDouble(cursor.getColumnIndex("RATING"));
                    String dealer = cursor.getString(cursor.getColumnIndex("DEALER"));

                    // Check if the dealer matches the provided dealerEmail
                    if (dealerEmail == null || dealerEmail.isEmpty() || dealer.equalsIgnoreCase(dealerEmail)) {
                        Car car = new Car(carId, make, model, price, year, color, distance, offer, horsepower, seatingCapacity, image, reserved, fuel, rating, dealer);

                        carsWithDiscount.add(car);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();

        return carsWithDiscount;
    }

    public void updateReservedStatus(int carID, int reservedStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("RESERVED", reservedStatus);

        db.update("CAR", values, "ID = ?", new String[]{String.valueOf(carID)});

        db.close();
    }

    @SuppressLint("Range")
    public List<Car> getAllCars(String dealerEmail) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Car> carList = new ArrayList<>();

        String query = "SELECT * FROM CAR";

        if (dealerEmail != null && !dealerEmail.isEmpty()) {
            query += " WHERE DEALER = ?";
        }

        Cursor cursor = db.rawQuery(query, (dealerEmail != null && !dealerEmail.isEmpty()) ? new String[]{dealerEmail} : null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String make = cursor.getString(cursor.getColumnIndex("MAKE"));
            String model = cursor.getString(cursor.getColumnIndex("MODEL"));
            int price = cursor.getInt(cursor.getColumnIndex("PRICE"));
            String year = cursor.getString(cursor.getColumnIndex("YEAR"));
            String color = cursor.getString(cursor.getColumnIndex("COLOR"));
            int distance = cursor.getInt(cursor.getColumnIndex("DISTANCE"));
            int offer = cursor.getInt(cursor.getColumnIndex("OFFER"));
            int horsepower = cursor.getInt(cursor.getColumnIndex("HORSEPOWER"));
            int seatingCapacity = cursor.getInt(cursor.getColumnIndex("SEATING_CAPACITY"));
            int image = cursor.getInt(cursor.getColumnIndex("IMAGE"));
            int reserved = cursor.getInt(cursor.getColumnIndex("RESERVED"));
            String fuel = cursor.getString(cursor.getColumnIndex("FUEL_TYPE"));
            double rating = cursor.getDouble(cursor.getColumnIndex("RATING"));
            String dealer = cursor.getString(cursor.getColumnIndex("DEALER"));

            if (dealerEmail == null || dealerEmail.isEmpty() || dealer.equalsIgnoreCase(dealerEmail)) {
                Car car = new Car(id, make, model, price, year, color, distance, offer, horsepower, seatingCapacity, image, reserved, fuel, rating, dealer);
                carList.add(car);
            }
        }

        cursor.close();

        return carList;
    }

    @SuppressLint("Range")
    public List<ReservedCar> getReservedCarDetails(String userEmail, String dealerEmail) {
        List<ReservedCar> reservedCarList = new ArrayList<>();

        String query = "SELECT CAR.*, RESERVE.RESERVED_COLOR, RESERVE.EMAIL, RESERVE.DATE, USER.FIRSTNAME, USER.LASTNAME " +
                "FROM CAR " +
                "JOIN RESERVE ON CAR.ID = RESERVE.CARID " +
                "JOIN USER ON RESERVE.EMAIL = USER.EMAIL";

        if (userEmail != null && !userEmail.isEmpty()) {
            query += " WHERE RESERVE.EMAIL = ?";
        }

        if (dealerEmail != null && !dealerEmail.isEmpty()) {
            if (userEmail != null && !userEmail.isEmpty()) {
                query += " AND CAR.DEALER = ?";
            } else {
                query += " WHERE CAR.DEALER = ?";
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();

        String[] selectionArgs = null;
        if (userEmail != null && !userEmail.isEmpty() && dealerEmail != null && !dealerEmail.isEmpty()) {
            selectionArgs = new String[]{userEmail, dealerEmail};
        } else if (userEmail != null && !userEmail.isEmpty()) {
            selectionArgs = new String[]{userEmail};
        } else if (dealerEmail != null && !dealerEmail.isEmpty()) {
            selectionArgs = new String[]{dealerEmail};
        }

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                int carId = cursor.getInt(cursor.getColumnIndex("ID"));
                String make = cursor.getString(cursor.getColumnIndex("MAKE"));
                String model = cursor.getString(cursor.getColumnIndex("MODEL"));
                int price = cursor.getInt(cursor.getColumnIndex("PRICE"));
                String year = cursor.getString(cursor.getColumnIndex("YEAR"));
                String color = cursor.getString(cursor.getColumnIndex("COLOR"));
                int distance = cursor.getInt(cursor.getColumnIndex("DISTANCE"));
                int offer = cursor.getInt(cursor.getColumnIndex("OFFER"));
                int horsepower = cursor.getInt(cursor.getColumnIndex("HORSEPOWER"));
                int seatingCapacity = cursor.getInt(cursor.getColumnIndex("SEATING_CAPACITY"));
                int image = cursor.getInt(cursor.getColumnIndex("IMAGE"));
                int reserved = cursor.getInt(cursor.getColumnIndex("RESERVED"));
                String date = cursor.getString(cursor.getColumnIndex("DATE"));
                String firstName = cursor.getString(cursor.getColumnIndex("FIRSTNAME"));
                String lastName = cursor.getString(cursor.getColumnIndex("LASTNAME"));
                String email = cursor.getString(cursor.getColumnIndex("EMAIL"));
                String reservedColor = cursor.getString(cursor.getColumnIndex("RESERVED_COLOR"));
                double rating = cursor.getDouble(cursor.getColumnIndex("RATING"));
                String dealer = cursor.getString(cursor.getColumnIndex("DEALER"));

                // Check if the dealer matches the provided dealerEmail
                if (dealerEmail == null || dealerEmail.isEmpty() || dealer.equalsIgnoreCase(dealerEmail)) {
                    ReservedCar reservedCar = new ReservedCar(carId, make, model, price,
                            year, color, distance, offer, horsepower, seatingCapacity, image,
                            reserved, date, firstName, lastName, email, reservedColor, rating, dealer);
                    reservedCarList.add(reservedCar);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return reservedCarList;
    }

    public boolean isCarReserved(int carId, String reservedColor) {
        SQLiteDatabase db = getReadableDatabase();

        String tableName = "RESERVE";

        String[] columns = {"CARID", "RESERVED_COLOR"};

        String selection = "CARID = ? AND RESERVED_COLOR = ?";
        String[] selectionArgs = {String.valueOf(carId), reservedColor};

        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null);

        boolean carExists = cursor.moveToFirst();

        cursor.close();

        return carExists;
    }

    public List<String> getReservedColorsForCarId(int carId) {
        SQLiteDatabase db = getReadableDatabase();

        String tableName = "RESERVE";

        String[] columns = {"RESERVED_COLOR"};

        String selection = "CARID = ?";
        String[] selectionArgs = {String.valueOf(carId)};

        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null);

        List<String> reservedColors = new ArrayList<>();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String reservedColor = cursor.getString(cursor.getColumnIndex("RESERVED_COLOR"));
            reservedColors.add(reservedColor);
        }

        cursor.close();

        return reservedColors;
    }

    public void changePassword(String email, String newPassword) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE EMAIL=?", new String[]{email});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("HASHED_PASSWORD", newPassword);

            db.update("USER", values, "EMAIL=?", new String[]{email});

        }
        cursor.close();
        db.close();
    }

    public void changeFirstName(String email, String firstName) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE EMAIL=?", new String[]{email});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("FIRSTNAME", firstName);

            db.update("USER", values, "EMAIL=?", new String[]{email});

        }
        cursor.close();
        db.close();
    }

    public void changeLastName(String email, String lastName) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE EMAIL=?", new String[]{email});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("LASTNAME", lastName);

            db.update("USER", values, "EMAIL=?", new String[]{email});

        }
        cursor.close();
        db.close();
    }

    public void changePhoneNumber(String email, String phoneNumber) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE EMAIL=?", new String[]{email});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("PHONENUMBER", phoneNumber);

            db.update("USER", values, "EMAIL=?", new String[]{email});

        }
        cursor.close();
        db.close();
    }

    public void deleteUser(String email) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("USER", "EMAIL=?", new String[]{email});

        db.close();
    }

    public void updateProfileImage(String email, String newProfileImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("PROFILE_IMAGE", newProfileImage);

        db.update("USER", values, "EMAIL=?", new String[]{email});

        db.close();
    }

    @SuppressLint("Range")
    public String getProfileImageByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String profileImage = null;

        String[] columns = {"PROFILE_IMAGE"};

        String selection = "EMAIL=?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query("USER", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            profileImage = cursor.getString(cursor.getColumnIndex("PROFILE_IMAGE"));
        }

        cursor.close();
        db.close();

        return profileImage;
    }

    public List<String> getAllUserEmailsExcept(String excludeEmail) {

        List<String> userEmails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String table = "USER";
        String[] columns = {"EMAIL"};

        String selection = "EMAIL != ?";
        String[] selectionArgs = {excludeEmail};

        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("EMAIL"));
                userEmails.add(email);
            }
            cursor.close();
        }

        db.close();

        return userEmails;
    }

    public List<String> getUserEmailsExcludingSuperAdmins(String excludeEmail) {
        List<String> userEmails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String table = "USER";
        String[] columns = {"EMAIL"};

        String selection = "EMAIL != ? AND isSuperAdmin != ?";
        String[] selectionArgs = {excludeEmail, "1"};

        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("EMAIL"));
                userEmails.add(email);
            }
            cursor.close();
        }

        db.close();

        return userEmails;
    }

    public void updateRating(String email, int carId, String reservedColor, float newRating) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("RATING", newRating);

        String whereClause = "EMAIL = ? AND CARID = ? AND RESERVED_COLOR = ?";
        String[] whereArgs = {email, String.valueOf(carId), reservedColor};

        db.update("RESERVE", values, whereClause, whereArgs);
    }

    @SuppressLint("Range")
    public float getCarRating(int carId, String reservedColor, String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {"RATING"};
        String selection = "CARID = ? AND RESERVED_COLOR = ? AND EMAIL = ?";
        String[] selectionArgs = {String.valueOf(carId), reservedColor, email};

        try (Cursor cursor = db.query("RESERVE", projection, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getFloat(cursor.getColumnIndex("RATING"));
            }
        }
        return -1;
    }

    @SuppressLint("Range")
    public double getAverageRating(String email, int carId) {
        SQLiteDatabase db = this.getReadableDatabase();

        double averageRating = 0.0;

        String query = "SELECT AVG(RATING) AS AVERAGE_RATING FROM RESERVE WHERE EMAIL = ? AND CARID = ?";
        String[] selectionArgs = {email, String.valueOf(carId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            averageRating = cursor.getDouble(cursor.getColumnIndex("AVERAGE_RATING"));
            cursor.close();
        }

        return averageRating;
    }

    public void updateCarRating(int carId, double newRating) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("RATING", newRating);

        db.update("CAR", values, "ID=?", new String[]{String.valueOf(carId)});
    }

    public int checkIfRecordsExist() {
        SQLiteDatabase db = this.getReadableDatabase();

        int count = 0;

        String tableName = "RESERVE";

        String countQuery = "SELECT COUNT(*) FROM " + tableName;

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }

        return count > 0 ? 1 : 0;
    }

    @SuppressLint("Range")
    public HashMap<String, String> getAdminSuperAdminUsers() {
        HashMap<String, String> userMap = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"EMAIL", "FIRSTNAME", "LASTNAME"};
        String selection = "isAdmin = ? AND isSuperAdmin = ?";
        String[] selectionArgs = {"1", "1"};

        Cursor cursor = db.query("USER", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String email = cursor.getString(cursor.getColumnIndex("EMAIL"));
                String firstName = cursor.getString(cursor.getColumnIndex("FIRSTNAME"));
                String lastName = cursor.getString(cursor.getColumnIndex("LASTNAME"));

                String fullName = firstName + " " + lastName;

                userMap.put(email, fullName);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return userMap;
    }

    @SuppressLint("Range")
    public ArrayList<Car> getFavoriteCarsDealerBased(String userEmail, String dealerEmail) {
        ArrayList<Car> favoriteCars = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT CAR.* " +
                "FROM CAR " +
                "JOIN FAVORITE ON CAR.ID = FAVORITE.CARID " +
                "WHERE FAVORITE.EMAIL = ?";

        if (dealerEmail != null && !dealerEmail.isEmpty()) {
            query += " AND CAR.DEALER = ?";
        }

        Cursor cursor = db.rawQuery(query, (dealerEmail != null && !dealerEmail.isEmpty()) ? new String[]{userEmail, dealerEmail} : new String[]{userEmail});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int carId = cursor.getInt(cursor.getColumnIndex("ID"));
                    String make = cursor.getString(cursor.getColumnIndex("MAKE"));
                    String model = cursor.getString(cursor.getColumnIndex("MODEL"));
                    int price = cursor.getInt(cursor.getColumnIndex("PRICE"));
                    String year = cursor.getString(cursor.getColumnIndex("YEAR"));
                    String color = cursor.getString(cursor.getColumnIndex("COLOR"));
                    int distance = cursor.getInt(cursor.getColumnIndex("DISTANCE"));
                    int offer = cursor.getInt(cursor.getColumnIndex("OFFER"));
                    int horsepower = cursor.getInt(cursor.getColumnIndex("HORSEPOWER"));
                    int seatingCapacity = cursor.getInt(cursor.getColumnIndex("SEATING_CAPACITY"));
                    int image = cursor.getInt(cursor.getColumnIndex("IMAGE"));
                    int reserved = cursor.getInt(cursor.getColumnIndex("RESERVED"));
                    String fuel = cursor.getString(cursor.getColumnIndex("FUEL_TYPE"));
                    double rating = cursor.getDouble(cursor.getColumnIndex("RATING"));
                    String dealer = cursor.getString(cursor.getColumnIndex("DEALER"));

                    if (dealerEmail == null || dealerEmail.isEmpty() || dealer.equalsIgnoreCase(dealerEmail)) {
                        Car car = new Car(carId, make, model, price, year, color, distance, offer, horsepower, seatingCapacity, image, reserved, fuel, rating, dealer);

                        favoriteCars.add(car);
                    }
                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        db.close();
        return favoriteCars;
    }

    @SuppressLint("Range")
    public Car getLastReservedCarByUser(String userEmail, String dealerEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Car lastReservedCar = null;

        String query = "SELECT CAR.* FROM CAR " +
                "JOIN RESERVE ON CAR.ID = RESERVE.CARID " +
                "WHERE RESERVE.EMAIL = ? ";

        // Add optional dealer condition
        if (dealerEmail != null && !dealerEmail.isEmpty()) {
            query += "AND CAR.DEALER = ? ";
        }

        query += "ORDER BY RESERVE.DATE DESC LIMIT 1";

        String[] selectionArgs;
        if (dealerEmail != null && !dealerEmail.isEmpty()) {
            selectionArgs = new String[]{userEmail, dealerEmail};
        } else {
            selectionArgs = new String[]{userEmail};
        }

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String make = cursor.getString(cursor.getColumnIndex("MAKE"));
            String model = cursor.getString(cursor.getColumnIndex("MODEL"));
            int price = cursor.getInt(cursor.getColumnIndex("PRICE"));
            String year = cursor.getString(cursor.getColumnIndex("YEAR"));
            String color = cursor.getString(cursor.getColumnIndex("COLOR"));
            int distance = cursor.getInt(cursor.getColumnIndex("DISTANCE"));
            int offer = cursor.getInt(cursor.getColumnIndex("OFFER"));
            int horsepower = cursor.getInt(cursor.getColumnIndex("HORSEPOWER"));
            int seatingCapacity = cursor.getInt(cursor.getColumnIndex("SEATING_CAPACITY"));
            int image = cursor.getInt(cursor.getColumnIndex("IMAGE"));
            int reserved = cursor.getInt(cursor.getColumnIndex("RESERVED"));
            float rating = cursor.getFloat(cursor.getColumnIndex("RATING"));
            String fuelType = cursor.getString(cursor.getColumnIndex("FUEL_TYPE"));
            String dealer = cursor.getString(cursor.getColumnIndex("DEALER"));

            lastReservedCar = new Car(id, make, model, price, year, color, distance, offer,
                    horsepower, seatingCapacity, image, reserved, fuelType, rating, dealer);

            cursor.close();
        }

        return lastReservedCar;
    }

    @SuppressLint("Range")
    public Car getLastFavoriteCarByUser(String userEmail, String dealerEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Car lastFavoriteCar = null;

        String query = "SELECT CAR.* FROM CAR " +
                "JOIN FAVORITE ON CAR.ID = FAVORITE.CARID " +
                "WHERE FAVORITE.EMAIL = ? ";

        if (dealerEmail != null && !dealerEmail.isEmpty()) {
            query += "AND CAR.DEALER = ? ";
        }

        query += "ORDER BY FAVORITE.ID DESC LIMIT 1";

        String[] selectionArgs;
        if (dealerEmail != null && !dealerEmail.isEmpty()) {
            selectionArgs = new String[]{userEmail, dealerEmail};
        } else {
            selectionArgs = new String[]{userEmail};
        }

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String make = cursor.getString(cursor.getColumnIndex("MAKE"));
            String model = cursor.getString(cursor.getColumnIndex("MODEL"));
            int price = cursor.getInt(cursor.getColumnIndex("PRICE"));
            String year = cursor.getString(cursor.getColumnIndex("YEAR"));
            String color = cursor.getString(cursor.getColumnIndex("COLOR"));
            int distance = cursor.getInt(cursor.getColumnIndex("DISTANCE"));
            int offer = cursor.getInt(cursor.getColumnIndex("OFFER"));
            int horsepower = cursor.getInt(cursor.getColumnIndex("HORSEPOWER"));
            int seatingCapacity = cursor.getInt(cursor.getColumnIndex("SEATING_CAPACITY"));
            int image = cursor.getInt(cursor.getColumnIndex("IMAGE"));
            int reserved = cursor.getInt(cursor.getColumnIndex("RESERVED"));
            float rating = cursor.getFloat(cursor.getColumnIndex("RATING"));
            String fuelType = cursor.getString(cursor.getColumnIndex("FUEL_TYPE"));
            String dealer = cursor.getString(cursor.getColumnIndex("DEALER"));

            lastFavoriteCar = new Car(id, make, model, price, year, color, distance, offer,
                    horsepower, seatingCapacity, image, reserved, fuelType, rating, dealer);

            cursor.close();
        }

        return lastFavoriteCar;
    }

    @SuppressLint("Range")
    public List<String> getAllUsersExceptSuperAdmin() {
        List<String> emailList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM USER WHERE isSuperAdmin != 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String email = cursor.getString(cursor.getColumnIndex("EMAIL"));

                emailList.add(email);
            }
            cursor.close();
        }

        db.close();
        return emailList;
    }

    public void updateLastLogin(String email, String newLastLogin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("LAST_LOGIN", newLastLogin);

        db.update("USER", values, "EMAIL = ?", new String[]{email});
        db.close();
    }

    @SuppressLint("Range")
    public User getUserDetailsByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String[] columns = {"COUNTRY", "CITY", "PHONENUMBER"};
        String selection = "EMAIL=?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query("USER", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setCountry(cursor.getString(cursor.getColumnIndex("COUNTRY")));
            user.setCity(cursor.getString(cursor.getColumnIndex("CITY")));
            user.setPhoneNumber(cursor.getString(cursor.getColumnIndex("PHONENUMBER")));
            cursor.close();
        }

        db.close();
        return user;
    }

}

