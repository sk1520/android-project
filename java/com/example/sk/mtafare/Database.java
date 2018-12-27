package com.example.sk.mtafare; // https://www.sqlite.org/datatype3.html

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static Database instance;

    private final static int DB_VERSION = 1;
    private final static String TABLE_CLIENTDATA = "DATA";  //private final static String DB_Name = "DATA";
    private final static String TABLE_LOGS = "LOGS";
    private final static String SQL_CREATE_TABLE_CLIENTDATA = "CREATE TABLE IF NOT EXISTS " + TABLE_CLIENTDATA + " (balance DOUBLE)";
    private final static String SQL_CREATE_TABLE_LOGS = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGS + " (time TEXT, faretype TEXT, balance REAL, rides INTEGER, isNewCard TEXT, cost REAL)"; // "CREATE TABLE IF NOT EXISTS " + TABLE_LOGS + " (log TEXT NOT NULL)";

    private Database(Context context) {
        super(context, TABLE_CLIENTDATA, null, DB_VERSION);
    }

    static synchronized Database getInstance(final Context context) {
        if (instance == null) {
            instance = new Database(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CLIENTDATA); // NOTE - currently useless
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_LOGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("SQL-TEST", "UPG CALLED");
    }

    /*
    ========================================================
    2018.03.30 at 20:47:30 EDT  		        Default Fare
    New Card = No				                   Rides = 5
    Balance = $2.00				               Cost = $45.00
    ========================================================
    */

    void insertMtaLog(String time, String faretype, double balance, int rides, String isNewCard, double cost) { // TODO - change to boolean and confirm outcome
        String[] columns = { "time", "faretype", "balance", "rides", "isNewCard", "cost" };
        String selection = "time = ? and faretype = ? and balance = ? and rides = ? and isNewCard = ? and cost = ?";
        String[] selectionArgs = { time, faretype, Double.toString(balance), Integer.toString(rides), isNewCard, Double.toString(cost) };
        getWritableDatabase().beginTransaction();
        try {
            Cursor c = getReadableDatabase().query(TABLE_LOGS, columns, selection, selectionArgs, null, null, null);
            ContentValues values = new ContentValues();
            values.put("time", time);
            values.put("faretype", faretype);
            values.put("balance", balance);
            values.put("rides", rides);
            values.put("isNewCard", isNewCard);
            values.put("cost", cost);
            getWritableDatabase().insert(TABLE_LOGS, null, values);
            c.close();
            getWritableDatabase().setTransactionSuccessful();
        } finally {
            getWritableDatabase().endTransaction();
        }
    }

    void deleteMtaLog() { // TODO - change to boolean and confirm outcome
        getWritableDatabase().delete(TABLE_LOGS, null, null);
    }

    void displayMtaLog() { // TODO - change to boolean and confirm outcome (AFTER EDIT WILL ONLY SHOW DATE)
       List<String> list = new ArrayList<>(); //List<String>[] list = (ArrayList<String>[])new ArrayList[10];
       Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " +  TABLE_LOGS, null);

       if (cursor.moveToFirst()) { // Tests if query returns empty & moves cursor to first result
           while (!cursor.isAfterLast()) { // Traverse till end of last cursor record
               list.add(cursor.getString(cursor.getColumnIndex("time")));
               cursor.moveToNext();
           }
       }
       cursor.close();

       Log.d("L", "LOGS:");
       if (list.size() == 0) {
           Log.d("L", "N/A - No logs.");
       }
       else {
           for (int i = 0; i < list.size(); i++) {
               Log.d("L", String.format("%-6s%s", i + ":", list.get(i)));
           }
       }
   }

   void insertLogIntoList(ArrayList<LogItem> log) {
       Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_LOGS, null);
       if (cursor.moveToFirst()) {
           while (!cursor.isAfterLast()) {
               log.add(new LogItem(cursor.getString(cursor.getColumnIndex("time")),
                                   cursor.getString(cursor.getColumnIndex("faretype")),
                                   cursor.getDouble(cursor.getColumnIndex("balance")),
                                   cursor.getInt(cursor.getColumnIndex("rides")),
                                   cursor.getString(cursor.getColumnIndex("isNewCard")),
                                   cursor.getDouble(cursor.getColumnIndex("cost"))
                      ));
               cursor.moveToNext();
           }
       }
       cursor.close();
   }

   long getLogCount(String table) {
       long count = 0;
       try {
           count = DatabaseUtils.queryNumEntries(this.getReadableDatabase(), table);
           Log.d("SQL-Counter", "ROWS FOUND = " + count);
       } catch (Exception e) {
           Log.e("SQL-ERROR", "Incorrect table name most likely!");
           e.printStackTrace();
       }
       return count;
   }
}