package com.example.aliaa.sep2.model;

import android.util.Log;

import java.io.Serializable;

import com.example.aliaa.sep2.R;
import com.example.aliaa.sep2.viewController.MainActivity;

public class Book implements Serializable {

    private Integer id ;
    private String isbn ;
    private String title ;
    private String category;
    private String author;
    private String url;
    private Integer puplicationDate;
    private Integer thumbnail;
    private String zone;

    public Book(String title, Integer thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Book(Integer id ,String isbn, String title, String category, String author, String url, Integer puplicationDate, Integer thumbnail, String zone) {
        this.id = id;
        this.isbn = isbn;
        this.title = title.toLowerCase();
        this.category = category.toLowerCase();
        this.author = author.toLowerCase();
        this.url = url;
        this.puplicationDate = puplicationDate;
        this.thumbnail = thumbnail;
        Log.d("pic",thumbnail+" pic");
        this.zone = zone;
    }

    public String getISBN() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Integer thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPuplicationDate() {
        return puplicationDate;
    }

    public void setPuplicationDate(Integer puplicationDate) {
        this.puplicationDate = puplicationDate;
    }
}
