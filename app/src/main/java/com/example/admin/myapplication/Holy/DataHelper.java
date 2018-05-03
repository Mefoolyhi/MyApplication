package com.example.admin.myapplication.Holy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.admin.myapplication.Utils.Event;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataHelper extends SQLiteOpenHelper {
    String DB_PATH = null;
    private static String DB_NAME = "fav";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private static int DATABASE_VERSION = 1;

    public DataHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";

    }


    public void delete(String id){
        try {
            this.openDataBase();
            myDataBase.delete("favourites", "id=" + id, null);

            myDataBase.close();
        }
        catch (Exception e){
            Log.e("delete",e.getMessage());
        }
    }
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }
    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    public void insert(ContentValues cv){
        this.openDataBase();
        try {
            myDataBase.insert("favourites", null, cv);
            myDataBase.close();
        }
        catch (Exception e){
            Log.e("Helper",e.getMessage());
        }
        myDataBase.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    public Event getEvent(int id) {
        Cursor c = query("favourites", null, null, null, null, null, null);
        c.moveToPosition(id);
        Event e = new Event(c.getDouble(11),c.getDouble(12),c.getDouble(10),c.getString(0),c.getString(1),c.getString(3),c.getString(2),
                c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(13));
       myDataBase.close();
        return e;


    }
    public ArrayList<Event> getFavourites(){
        Cursor c;
        ArrayList<Event> data = new ArrayList<>();
        try {
            createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        c = query("favourites", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Event e = new Event(c.getDouble(11),c.getDouble(12),c.getDouble(10),c.getString(0),c.getString(1),c.getString(3),c.getString(2),
                        c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(13));
                data.add(e);
            } while (c.moveToNext());
        }


        Log.e("DT", String.valueOf(data.size()));
        myDataBase.close();
        return data;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Cursor query(String table, String columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        this.openDataBase();
        return myDataBase.query(table, null, null, null, null, null, null);
    }
}