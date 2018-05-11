package com.example.aliaa.sep2.viewController;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aliaa.sep2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shereen on 4/26/2018.
 */

public class FetchBook extends AsyncTask<String,Void,String> {

    // Class name for Log tag
    private static final String LOG_TAG = FetchBook.class.getSimpleName();
    private BookShelf bookShelf;

    // Constructor providing a reference to the views in MainActivity
    public FetchBook(BookShelf bookShelf) {
        this.bookShelf=bookShelf;
    }


    @Override
    protected String doInBackground(String... params) {
        // Get the search string
        String queryString = params[0];


        // Set up variables for the try block that need to be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        // Attempt to query the Books API.
        try {

            URL requestURL = new URL("https://www.googleapis.com/books/v1/volumes/?q=ISBN%3C" + queryString + "%3E");

            // Open the network connection.
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();

            // Read the response string into a StringBuilder.
            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // but it does make debugging a *lot* easier if you print out the completed buffer for debugging.
                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                // Stream was empty.  No point in parsing.
                // return null;
                return null;
            }
            bookJSONString = builder.toString();

            // Catch errors.
        } catch (IOException e) {
            e.printStackTrace();

            // Close the connections.
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        // Return the raw response.
        return bookJSONString;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            // Convert the response into a JSON object.
            Log.d("s****************" ,s);
            JSONObject jsonObject = new JSONObject(s);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize iterator and results fields.
            int i = 0;
            String title = null;
            String authors = null;
            String url = null;
            String id = null;
            String reviewUrl = null;


            // Look for results in the items array, exiting when both the title and author
            // are found or when all items have been checked.
            JSONObject book = itemsArray.getJSONObject(0);
            JSONObject volumeInfo = book.getJSONObject("volumeInfo");
            HashMap<String,String> map = new HashMap<String, String>();
            try {
                title = volumeInfo.getString("title");
                authors = volumeInfo.getString("authors");
                url = volumeInfo.getString("previewLink");
                id = book.getString("id");
                reviewUrl ="https://books.google.com.eg/books?id=" + id + "&sitesec=reviews";

            } catch (Exception e){
                e.printStackTrace();
            }


            // If both are found, display the result.
            if (title != null && authors != null){
                map.put("Title",title);
                map.put("Author",authors);
                map.put("Url",url);
                map.put("Id",id);
                map.put("ReviewUrl",reviewUrl);
                bookShelf.update(map);
            } else {
                // If none are found, update the UI to show failed results.
                Log.d("error" , "****************");
            }

        } catch (Exception e){
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            e.printStackTrace();
        }
    }





}
