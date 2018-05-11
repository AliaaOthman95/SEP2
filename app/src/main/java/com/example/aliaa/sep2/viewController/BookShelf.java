package com.example.aliaa.sep2.viewController;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aliaa.sep2.controller.Engine;
import com.example.aliaa.sep2.model.BookshelfAdapter;
import com.example.aliaa.sep2.R;
import com.example.aliaa.sep2.model.Book;
import java.util.ArrayList;
import java.util.HashMap;

public class BookShelf extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookshelfAdapter adapter;
    private ArrayList<Book> bookList;
    private String filter = "name" ;
    private HashMap<String,String> map;
    private Engine engine = Engine.getInstance(MainActivity.appContext);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        bookList = new ArrayList<>();
        adapter = new BookshelfAdapter(this, bookList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
       /* Book a = new Book(1,"", "a", "history", "aliaa", "bla bla" , 2014, 1, "a");
        Book b = new Book(2, "","b", "art",  "aliaa", "bla bla" , 2012, 2, "b");
        Book c = new Book(3, "","c", "islamic",  "aliaa", "bla bla" , 2010, 3, "a");
        Book d = new Book(4, "9780142424179" ,"The Fault in Our Stars", "romance", "John Green", "http://www.pu.if.ua/depart/Inmov/resource/file/samostijna_robota/Green_John_The_Fault_in_Our_Stars.pdf" , 1995, 4, "b");
        engine.addBook(a);
        engine.addBook(b);
        engine.addBook(c);
        engine.addBook(d);*/
        engine.setBookShelf(this);
        prepareBooks(engine.getAllBooks());

        //searchBook("9780142424179");


        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void searchBook(String ISBN){
        // String queryString ="9780262140874";
        FetchBook fetchBook = new FetchBook(this);
        fetchBook.execute(ISBN);
    }
    public  void  update(HashMap<String,String> map){
        this.map = map;
        Log.d("len", map.get("ReviewUrl"));
    }
    public String getUrl() {
        return map.get("ReviewUrl");
    }
    public String getBookUrl() {
        return map.get("Url");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        SearchView searchView =(SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // Toast.makeText(getApplication(), query+" "+filter , Toast.LENGTH_SHORT).show();
                prepareBooks(engine.search(query.toLowerCase(),filter));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // load list of book
               // Toast.makeText(getApplication(), newText+" "+filter , Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        MenuInflater inflater1 = getMenuInflater();
        inflater1.inflate(R.menu.filter_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_filter_category:
                Toast.makeText(getApplication(), "category", Toast.LENGTH_SHORT).show();
                filter = "category";
                break;
            case R.id.action_filter_name:
                Toast.makeText(getApplication(), "name", Toast.LENGTH_SHORT).show();
                filter = "name";
                break;
            case R.id.action_filter_author:
                Toast.makeText(getApplication(), "author", Toast.LENGTH_SHORT).show();
                filter = "author";
                break;
            case R.id.action_filter_date:
                Toast.makeText(getApplication(), "date", Toast.LENGTH_SHORT).show();
                filter = "date";
                break;
            case R.id.action_filter_zone:
                Toast.makeText(getApplication(), "zone", Toast.LENGTH_SHORT).show();
                filter = "zone";
                break;
            default:
                Toast.makeText(getApplication(), "default", Toast.LENGTH_SHORT).show();
                filter = "default";
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    public void prepareBooks(ArrayList<Book> list) {

        bookList.clear();
        for(Book book : list) {
            bookList.add(book);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
