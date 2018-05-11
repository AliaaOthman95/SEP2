package com.example.aliaa.sep2.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aliaa.sep2.R;
import com.example.aliaa.sep2.viewController.BookShelf;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class BookshelfAdapter extends RecyclerView.Adapter<BookshelfAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> bookList;
    private  Book book;
    private String isbn ;
    private BookShelf bookShelf = new BookShelf();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }


    public BookshelfAdapter(Context mContext, List<Book> bookList) {
        this.mContext = mContext;
        this.bookList = bookList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        book = bookList.get(position);
        holder.title.setText(book.getTitle());

        // loading album cover using Glide library
        Glide.with(mContext).load(book.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //implement onClick
                Intent browserInternet = new Intent (Intent.ACTION_VIEW);
                browserInternet.setData(Uri.parse(bookList.get(holder.getAdapterPosition()).getUrl()));
                mContext.startActivity(browserInternet);

            }
        });
    }


    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu( MyViewHolder holder) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, holder.overflow);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_bookshelf, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
        isbn = bookList.get(holder.getAdapterPosition()).getISBN();
        Log.d("isbn", "isbn" + isbn);
        bookShelf.searchBook(isbn);


    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_view_reviews:
                    Toast.makeText(mContext, bookShelf.getUrl(), Toast.LENGTH_SHORT).show();
                    Intent browserInternet = new Intent (Intent.ACTION_VIEW);
                    browserInternet.setData(Uri.parse(bookShelf.getUrl()));
                    mContext.startActivity(browserInternet);
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}