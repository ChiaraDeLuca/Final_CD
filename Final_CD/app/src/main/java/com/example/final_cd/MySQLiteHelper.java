package com.example.final_cd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class serves as a helper class to open and create a database. Constant variables are created for table columns,
 * a constructor is defined to create a table, an execute database SQL is executed in onCreate, and code is provided
 * to assist with upgrading the database.
 *
 */

class MySQLiteHelper  extends SQLiteOpenHelper {
    public static final String TABLE_CAR = "car";                 // a constant for the TABLE_FIREARM table name
    /**
     * These variable serves as a constant for the column names.
     **/
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TYPE_OF_CAR = "typeOfCar";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_MODEL = "model";

    //public static final String COLUMN_WEIGHT = "weight";
   // public static final String COLUMN_BARREL_LENGTH = "barrelLength";
    //public static final String COLUMN_ACCURACY = "MOA";

    private static final String DATABASE_NAME = "car.db";     // constant for the database name
    //This variable serves as a constant for the DATABASE version.
    //Incremented the version of the database so Android deletes the old one and creates a new one with our new field
    private static final int DATABASE_VERSION = 102;

    /*
     * This statement defines a database creation sql statement.
     */
    //Added COLUMN_RATING to DATABASE_CREATE
    private static final String DATABASE_CREATE = "create table "
            + TABLE_CAR + "( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TYPE_OF_CAR + " TEXT not null,"
            + COLUMN_YEAR + " TEXT,"
            + COLUMN_BRAND + " TEXT,"
            + COLUMN_MODEL + " TEXT" + ");";

    private static MySQLiteHelper sInstance;
    public static synchronized MySQLiteHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new MySQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /*
     * This method makes a method call to the SQLiteOpenHelper (super). Passes in the context
     * (parameter), database name (set as a constant), null (no value needed), and database version
     * (also defined as a constant). This is used to help open and reference the database.
     *
     * @param context This parameter is a handle to the calling activity.
     */
    private MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * This method contains a method call that will execute an SQL Statement defined as a String
     * parameter. Upon creating the application, we run our SQL command.
     *
     * @param database This is the database we are referencing to get the SQL statements.
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    /*
     * This method provides code to assist with a database upgrade. A log file is written stating
     * what the old and newer version is, and drops the table defined in the constant TABLE_COMMENTS.
     * Then, the database is recreated with the updated version.
     *
     * @param SQLiteDatabase db This is the database being referenced that is to be upgraded
     * @param int oldVersion This is the old version of the database
     * @param int newVersion This is the new version of the database
     *
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("MySQLiteHelper", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);
        onCreate(db);
    }

}
