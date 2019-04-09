package com.luisitura.dlymansura.rssgrants.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.luisitura.dlymansura.rssgrants.model.RssItem;
import com.luisitura.dlymansura.rssgrants.model.SqlItem;

import java.util.ArrayList;

/**
 * Created by Admin on 05.04.2017.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "FavourDB";
    // Books table name
    private static final String TABLE_FAVOUR = "favour";

    // Books Table Columns names
    private static final String KEY_TITLE = "title";
    private static final String KEY_LINK = "link";
    private static final String KEY_DATE = "pubDate";
    private static final String KEY_RESOURCE = "resource";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_FAVOUR_TABLE = "CREATE TABLE favour ( " +
                "title TEXT, "+
                "link TEXT, "+
                "pubDate TEXT, "+
                "resource TEXT )";

        // create books table
        db.execSQL(CREATE_FAVOUR_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS favour");

        // create fresh books table
        this.onCreate(db);
    }

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    private static final String[] COLUMNS = {KEY_TITLE, KEY_LINK, KEY_DATE, KEY_RESOURCE};

    public void addItem(SqlItem item){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, item.getTitle()); // get title
        values.put(KEY_LINK, item.getLink()); // get link
        values.put(KEY_DATE, item.getPubDate()); // get date
        values.put(KEY_RESOURCE, item.getResource()); // get resource

        // 3. insert
        db.insert(TABLE_FAVOUR, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public SqlItem getItem(String link){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_FAVOUR, // a. table
                        COLUMNS, // b. column names
                        " link = ?", // c. selections
                        new String[] { link }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        SqlItem item = new SqlItem(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // 5. return book
        return item;
    }

    // Get All Books
    public ArrayList<RssItem> getAllItems() {
        ArrayList<RssItem> items = new ArrayList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_FAVOUR;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        SqlItem item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new SqlItem(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

                // Add book to books
                items.add(item);
            } while (cursor.moveToNext());
        }

        // return books
        return items;
    }

    // Updating single book
    public int updateItem(SqlItem item) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, item.getTitle()); // get title
        values.put(KEY_LINK, item.getLink()); // get link
        values.put(KEY_DATE, item.getPubDate()); // get date
        values.put(KEY_RESOURCE, item.getResource()); // get resource

        // 3. updating row
        int i = db.update(TABLE_FAVOUR, //table
                values, // column/value
                KEY_LINK+" = ?", // selections
                new String[] { item.getLink() }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single book
    public void deleteItem(SqlItem item) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_FAVOUR,
                KEY_LINK+" = ?",
                new String[] { item.getLink() });

        // 3. close
        db.close();

    }

    public boolean ifItemExist(String link){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        String Query = "Select * from " + TABLE_FAVOUR + " where " + KEY_LINK + " = '" + link + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();

        return true;
    }
}
