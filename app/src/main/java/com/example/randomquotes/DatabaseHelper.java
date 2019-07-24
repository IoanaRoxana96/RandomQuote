package com.example.randomquotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Quotes.db";
    public static final String TABLE_NAME = "quotes_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "QUOTE";

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +  COL_2  + " TEXT NOT NULL UNIQUE ON CONFLICT IGNORE);");
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertQuote(String quote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, quote);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    /*public Cursor checkQuote(String quote) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + COL_2 + " FROM " + TABLE_NAME + " WHERE QUOTE = " + COL_2, null);
        return res;
    }*/

    /*public boolean checkQuote (String quote) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_2 + " FROM " + TABLE_NAME + " WHERE " + COL_2 + " = " + quote, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }*/

    public boolean checkQuote (String quote) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT QUOTE FROM " + TABLE_NAME + " WHERE QUOTE =? ", new  String[] {quote});
        if(cursor.getCount() > 0)
            return false;
        return true;
    }

    public Cursor getAllQuotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public Cursor getRandomQuote() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY RANDOM() LIMIT 1", null);
        return res;
    }

    public Integer deleteQuote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

}
