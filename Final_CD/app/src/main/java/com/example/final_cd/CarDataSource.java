package com.example.final_cd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chiara
 */

public class CarDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    /*
     * This method creates a new database helper that is a new MySQLHelper object with the parameter context.
     *
     * @param context This parameter is a handle to the system. Helps obtain access to databases,
     * preferences, and helps resolve resources.
     */
    public CarDataSource(Context context) {
        dbHelper = MySQLiteHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public Car createCar( String typeOfCar, String caliberOfCar, String roundCount) {           //Added Strings as a parameter
        ContentValues values = new ContentValues();                                                              // Create a new ContentValue Object
        values.put(MySQLiteHelper.COLUMN_TYPE_OF_CAR, typeOfCar);                                                // Insert a species into the COLUMN_SPECIES field using MYSQLiteHelper
        values.put(MySQLiteHelper.COLUMN_YEAR, year);                                              // Insert roundCount into the COLUMN_ROUNDCOUNT field using MYSQLiteHelper
        values.put(MySQLiteHelper.COLUMN_MODEL, model);

        long insertId = database.insert(MySQLiteHelper.TABLE_CAR, null, values);         //  Instert the Firearm into the database using the parameters above
        Car newCar = new Car(insertId, typeOfCar, year, model);
        return newCar;
    }
    public Car createCar( String typeOfCar, String year, String model) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TYPE_OF_CAR, typeOfCar);
        values.put(MySQLiteHelper.COLUMN_YEAR, year);


        long insertId = database.insert(MySQLiteHelper.TABLE_CAR, null, values);
        Car newCar = new Car(insertId, typeOfCar, year, model);
        return newCar;
    }

    public void deleteCar(Car car) {
        long id = car.getId();
        System.out.println("Car deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_CAR, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Car> getAllCar() {
        List<Car> carList = new ArrayList<Car>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CAR,
                null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Car car = cursorToCar(cursor);
            carList.add(car);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        //cursor.close();
        return carList;
    }
    /**
     *  Converts the current row in the database cursor into a Car object
     * @param cursor points to the current row in the database cursor
     * @return a Car object created from that row
     */

    private Car cursorToCar(Cursor cursor) {
        Car car = new Car();
        car.setId(cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
        car.setTypeOfCar(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_TYPE_OF_CAR)));
        car.setYear(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_YEAR)));
        car.setBrand(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_BRAND)));
        car.setModel(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_MODEL)));

        return car;
    }


}
