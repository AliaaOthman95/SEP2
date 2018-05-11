package com.example.aliaa.sep2.controller;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.aliaa.sep2.model.SQLiteDBHelper;
import com.example.aliaa.sep2.model.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.aliaa.sep2.model.Book;
import com.example.aliaa.sep2.viewController.BookShelf;

/**
 * Created by shereen on 4/19/2018.
 */

public class Engine {

    private static Engine instance = null ;
    private static SQLiteDBHelper db = null;
    private BookShelf bookShelf;


    public static Engine getInstance (Context context){

        if(instance == null){
            db = new SQLiteDBHelper(context);
            return new Engine();
        }

        return instance;
    }
    public void setBookShelf(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    public boolean addUser(User user){
        Pattern peamil = Pattern.compile("[a-z0-9]+@[a-z]+\\.[a-z]{2,3}");
        Matcher m1 = peamil.matcher(user.getEmail());
        Pattern pPass = Pattern.compile("[0-9]{8,10}");
        Matcher m2 = pPass.matcher(user.getPassword());
        Pattern pcredit = Pattern.compile("[0-9]{10,14}");
        Matcher m3 = pPass.matcher(user.getCreditCard());

        if (!m1.find()){
            Log.d("print","****************");
            return false;
        }else if (!m2.find()){
            Log.d("print","********11********");
            return false;
        }else if (!m3.find()){
            Log.d("print","***************22**************");
            return false;
        }
        return db.addUser(user);
    }

    public boolean findUser(String email , String password){
        User user =  db.getUser(email);

        if (user != null && user.getPassword().compareTo(password) == 0 && (user.getEmail().compareTo(email)==0)){
            return true;
        }
        return false;
    }

    public void getAllUser(){
        db.getAllUsers();
    }

    public ArrayList<Book> getAllBooks (){

        return db.getAllBooks();
    }
    public boolean addBook(Book book){
        Pattern digit = Pattern.compile("[0-9]+");
        Matcher m2 = digit.matcher(book.getPuplicationDate().toString());
        Matcher m3 = digit.matcher(book.getISBN().toString());
        Pattern characters = Pattern.compile("[a-zA-Z]+");
        Matcher m4 = characters.matcher(book.getAuthor());
        Matcher m5 = characters.matcher(book.getCategory());
        if(book.getTitle().isEmpty() || book.getAuthor().isEmpty() || book.getCategory().isEmpty()
                || book.getISBN().isEmpty() || book.getPuplicationDate() == null || book.getThumbnail() == null
                ||book.getUrl().isEmpty() || book.getZone().isEmpty() || book.getISBN().length() != 13
                || book.getPuplicationDate() > Calendar.getInstance().get(Calendar.YEAR))
        {
                return false ;
        }

        if(!m2.find() || !m3.find() || !m4.find() || !m5.find()) return false;
        if(db.getBookByISBN(book.getISBN()) == null) return false;
        db.addBook(book);
        bookShelf.prepareBooks(getAllBooks());
        return true;
    }
    public boolean deleteBook(String ISBN){
        Pattern digit = Pattern.compile("[0-9]{13}");
        Matcher m3 = digit.matcher(ISBN.toString());
        if(ISBN.isEmpty() || !m3.find() || ISBN.length() != 13) return false;
        if(db.getBookByISBN(ISBN) == null) return false;
            db.deleteBook(ISBN);
            return true ;
    }
    public ArrayList <Book> search(String query, String filter){

        ArrayList <Book> books = null;

        if(filter.compareTo("category") == 0){
            books = db.getBooksByCategory(query);
        }else if(filter.compareTo("name") == 0){
            books = db.getBooksByTitle(query);
        }else if(filter.compareTo("zone") == 0){
            books = db.getBooksByZone(query);
        }else if(filter.compareTo("author") == 0){
            books = db.getBooksByAuthor(query);
        }else if(filter.compareTo("date") == 0){
            books = db.getBooksByDate(Integer.parseInt(query));
        }else {
            books =  getAllBooks();
        }
        return books;
    }
}


