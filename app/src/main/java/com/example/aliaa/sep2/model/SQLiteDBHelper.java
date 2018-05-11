package com.example.aliaa.sep2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;

import android.util.Log;

import com.example.aliaa.sep2.viewController.MainActivity;

import java.util.ArrayList;

/**
 * Created by shereen on 4/19/2018.
 */

public class SQLiteDBHelper extends SQLiteOpenHelper  {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Library";

    //Book Table
    public static final String BOOK_TABLE = "book";
    public static final String BOOK_ISBN = "book_isbn";
    public static final String BOOK_TITLE = "book_title";
    public static final String BOOK_CATEGORY = "book_category";
    public static final String BOOK_COVER = "book_cover";
    public static final String BOOK_AUTHOR = "book_author";
    public static final String BOOK_URL = "book_url";
    public static final String BOOK_ZONE = "book_zone";
    public static final String BOOK_DATE = "book_date";
    public  static final String BOOK_ID = "book_id";

    //User Table
    public static final String USER_TABLE = "user";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_CREDITCARD = "user_creditcard";
    public static final String USER_AUTHORIZATION = "user_authorization";


    //Create USER Table Query
    private static final String SQL_CREATE_USER =
            "CREATE TABLE USER (" + USER_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT , "
                    + USER_NAME + " TEXT, " + USER_EMAIL + "  TEXT, "
                    + USER_PASSWORD  +  "  TEXT, "  + USER_AUTHORIZATION  +  "  INTEGER, " + USER_CREDITCARD +  "  TEXT);";


    private static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + USER_TABLE;


    //Create BOOK Table Query
    private static final String SQL_CREATE_BOOK =
            "CREATE TABLE BOOK (" + BOOK_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT , "
                   +BOOK_ISBN+ " TEXT, " + BOOK_TITLE + " TEXT, " + BOOK_COVER + "  INTEGER, "
                    + BOOK_AUTHOR  +  "  TEXT, " + BOOK_CATEGORY +  "  TEXT, "
                    + BOOK_URL +  "  TEXT, "
                    + BOOK_ZONE +  "  TEXT, " + BOOK_DATE + "  INTEGER);";


    private static final String SQL_DELETE_BOOK =
            "DROP TABLE IF EXISTS " + BOOK_TABLE;


    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BOOK);
        db.execSQL(SQL_CREATE_USER);
        // static till we change it

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Adds a new USER
    public boolean addUser(User user) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("user email" , user.getEmail());
        //Create a map having movie details to be inserted
        ContentValues user_details = new ContentValues();
        user_details.put(USER_NAME, user.getName());
        user_details.put(USER_EMAIL, user.getEmail());
        user_details.put(USER_PASSWORD, user.getPassword());
        user_details.put(USER_CREDITCARD, user.getCreditCard());
        user_details.put(USER_AUTHORIZATION,user.getAuthorization());
        long newRowId = db.insert(USER_TABLE, null, user_details);

        db.close();
        return newRowId != -1;

    }

    //Returns details of a particular user
    public User getUser(String email) {

        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("user email" , email);
        String query = "SELECT * FROM "+ USER_TABLE  +" WHERE " +USER_EMAIL+" = ?";
        Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(email) });

        User user = null;
        Log.d("before",email);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Log.d("user email" ,cursor.getString(cursor.getColumnIndex(USER_EMAIL)));

            user= new User(
                    cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getInt(5));
        }
        Log.d("after" , email);


        return user;

    }

    public ArrayList<User> getAllUsers() {

        ArrayList <User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + USER_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                User user = new User(cursor.getString(cursor.getColumnIndex(USER_NAME)),
                        cursor.getString(cursor.getColumnIndex(USER_EMAIL)), cursor.getString(cursor.getColumnIndex(USER_PASSWORD)),cursor.getString(cursor.getColumnIndex(USER_CREDITCARD)),cursor.getInt(cursor.getColumnIndex(USER_AUTHORIZATION)) );

                userList.add(user);
                Log.d("user email" ,cursor.getString(cursor.getColumnIndex(USER_EMAIL)));

            } while (cursor.moveToNext());
        }

        // return story list
        return userList;

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Adds a new book
    public boolean addBook(Book book) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Book Name" , book.getTitle());
        //Create a map having movie details to be inserted
        ContentValues book_details = new ContentValues();
        book_details.put(BOOK_ISBN, book.getISBN());
        book_details.put(BOOK_TITLE, book.getTitle());
        book_details.put(BOOK_COVER, MainActivity.appContext. getResources().getIdentifier("b"+String.valueOf(book.getThumbnail()),"drawable", MainActivity.appContext.getPackageName()));
        book_details.put(BOOK_AUTHOR, book.getAuthor());
        book_details.put(BOOK_CATEGORY, book.getCategory());
        book_details.put(BOOK_URL, book.getUrl());
        book_details.put(BOOK_ZONE, book.getZone());
        book_details.put(BOOK_DATE, book.getPuplicationDate());


        long newRowId = db.insert(BOOK_TABLE, null, book_details);

        db.close();
        return newRowId != -1;

    }

    //Retrieves details all stories
    public ArrayList<Book> getAllBooks() {

        ArrayList <Book> bookList = new ArrayList<Book>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + BOOK_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {

                Book book  = new Book(Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_ID))),cursor.getString(cursor.getColumnIndex(BOOK_ISBN)),cursor.getString(cursor.getColumnIndex(BOOK_TITLE)),cursor.getString(cursor.getColumnIndex(BOOK_CATEGORY)),cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)), cursor.getString(cursor.getColumnIndex(BOOK_URL)),Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_DATE))),cursor.getInt(cursor.getColumnIndex(BOOK_COVER)),cursor.getString(cursor.getColumnIndex(BOOK_ZONE)));
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        return bookList;
    }

    //Returns details of a particular book
    public Book getBookByISBN(String bookISBN) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(BOOK_TABLE, new String[] { BOOK_ID ,BOOK_ISBN,
                        BOOK_TITLE, BOOK_CATEGORY,BOOK_AUTHOR,BOOK_URL,BOOK_DATE, BOOK_COVER , BOOK_ZONE }, BOOK_ISBN + "=?",
                new String[] { bookISBN }, null, null, null, null);
        Book book = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            book  = new Book(Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_ID))),cursor.getString(cursor.getColumnIndex(BOOK_ISBN)),cursor.getString(cursor.getColumnIndex(BOOK_TITLE)),cursor.getString(cursor.getColumnIndex(BOOK_CATEGORY)),cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)), cursor.getString(cursor.getColumnIndex(BOOK_URL)),Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_DATE))),cursor.getInt(cursor.getColumnIndex(BOOK_COVER)),cursor.getString(cursor.getColumnIndex(BOOK_ZONE)));

        }

        return book;

    }

    //Retrieves details all stories
    public ArrayList<Book> getBooksByTitle(String bookTitle) {

        ArrayList <Book> bookList = new ArrayList<Book>();


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(BOOK_TABLE, new String[] {BOOK_ID , BOOK_ISBN,
                        BOOK_TITLE, BOOK_COVER, BOOK_AUTHOR, BOOK_CATEGORY ,BOOK_URL ,BOOK_DATE ,BOOK_ZONE }, BOOK_TITLE + "=?",
                new String[] { bookTitle }, null, null, null ,null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                Book book  = new Book(Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_ID))),cursor.getString(cursor.getColumnIndex(BOOK_ISBN)),cursor.getString(cursor.getColumnIndex(BOOK_TITLE)),cursor.getString(cursor.getColumnIndex(BOOK_CATEGORY)),cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)), cursor.getString(cursor.getColumnIndex(BOOK_URL)),Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_DATE))),cursor.getInt(cursor.getColumnIndex(BOOK_COVER)),cursor.getString(cursor.getColumnIndex(BOOK_ZONE)));
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        return bookList;
    }


    //Retrieves details all stories
    public ArrayList<Book> getBooksByAuthor(String bookAuthor) {

        ArrayList <Book> bookList = new ArrayList<Book>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(BOOK_TABLE, new String[] {BOOK_ID ,BOOK_ISBN,
                        BOOK_TITLE, BOOK_COVER, BOOK_AUTHOR, BOOK_CATEGORY ,BOOK_URL , BOOK_DATE, BOOK_ZONE}, BOOK_AUTHOR+ "=?",
                new String[] { bookAuthor }, null, null ,null );
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                Book book  = new Book(Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_ID))),cursor.getString(cursor.getColumnIndex(BOOK_ISBN)),cursor.getString(cursor.getColumnIndex(BOOK_TITLE)),cursor.getString(cursor.getColumnIndex(BOOK_CATEGORY)),cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)), cursor.getString(cursor.getColumnIndex(BOOK_URL)),Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_DATE))),cursor.getInt(cursor.getColumnIndex(BOOK_COVER)),cursor.getString(cursor.getColumnIndex(BOOK_ZONE)));
                bookList.add(book);
            } while (cursor.moveToNext());


        }

        return bookList;
    }

    //Retrieves details all stories
    public ArrayList<Book> getBooksByCategory(String bookCategory) {

        ArrayList <Book> bookList = new ArrayList<Book>();


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(BOOK_TABLE, new String[] { BOOK_ID ,BOOK_ISBN,
                        BOOK_TITLE, BOOK_COVER, BOOK_AUTHOR, BOOK_CATEGORY ,BOOK_URL , BOOK_DATE , BOOK_ZONE}, BOOK_CATEGORY+ "=?",
                new String[] { bookCategory }, null ,null ,null, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                Book book  = new Book(Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_ID))),cursor.getString(cursor.getColumnIndex(BOOK_ISBN)),cursor.getString(cursor.getColumnIndex(BOOK_TITLE)),cursor.getString(cursor.getColumnIndex(BOOK_CATEGORY)),cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)), cursor.getString(cursor.getColumnIndex(BOOK_URL)),Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_DATE))),cursor.getInt(cursor.getColumnIndex(BOOK_COVER)),cursor.getString(cursor.getColumnIndex(BOOK_ZONE)));
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        return bookList;
    }

    //Retrieves details all stories
    public ArrayList<Book> getBooksByDate(int bookDate) {

        ArrayList <Book> bookList = new ArrayList<Book>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(BOOK_TABLE, new String[] { BOOK_ID ,BOOK_ISBN,
                        BOOK_TITLE, BOOK_COVER, BOOK_AUTHOR, BOOK_CATEGORY ,BOOK_URL ,BOOK_DATE,BOOK_ZONE}, BOOK_DATE+ "=?",
                new String[] {String.valueOf(bookDate)}, null, null, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                Book book  = new Book(Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_ID))),cursor.getString(cursor.getColumnIndex(BOOK_ISBN)),cursor.getString(cursor.getColumnIndex(BOOK_TITLE)),cursor.getString(cursor.getColumnIndex(BOOK_CATEGORY)),cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)), cursor.getString(cursor.getColumnIndex(BOOK_URL)),Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_DATE))),cursor.getInt(cursor.getColumnIndex(BOOK_COVER)),cursor.getString(cursor.getColumnIndex(BOOK_ZONE)));
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        return bookList;
    }


    //Retrieves details all stories
    public ArrayList<Book> getBooksByZone(String bookZone) {

        ArrayList <Book> bookList = new ArrayList<Book>();


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(BOOK_TABLE, new String[] { BOOK_ID , BOOK_ISBN,
                        BOOK_TITLE, BOOK_COVER, BOOK_AUTHOR, BOOK_CATEGORY ,BOOK_URL , BOOK_DATE, BOOK_ZONE }, BOOK_ZONE + "=?",
                new String[] {bookZone},null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                Book book  = new Book(Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_ID))),cursor.getString(cursor.getColumnIndex(BOOK_ISBN)),cursor.getString(cursor.getColumnIndex(BOOK_TITLE)),cursor.getString(cursor.getColumnIndex(BOOK_CATEGORY)),cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)), cursor.getString(cursor.getColumnIndex(BOOK_URL)),Integer.parseInt(cursor.getString(cursor.getColumnIndex(BOOK_DATE))),cursor.getInt(cursor.getColumnIndex(BOOK_COVER)),cursor.getString(cursor.getColumnIndex(BOOK_ZONE)));
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        Log.d("database","books"+bookList.size());
        return bookList;
    }



    //Deletes the specified book
    public void deleteBook(String bookISBN) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(BOOK_TABLE, BOOK_ISBN + " = ?",
                new String[] { bookISBN });
        db.close();
    }




}



