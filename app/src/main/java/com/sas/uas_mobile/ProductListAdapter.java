package com.sas.uas_mobile;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private ArrayList<Book> books;

    public ProductListAdapter(ArrayList<Book> books) {
        this.books = books;
    }


    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_home_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {
//        holder.thumbnail.setImageURI(Uri.parse(books.get(position).getImg()));
//        holder.thumbnail.setMinimumHeight(100);
//        holder.thumbnail.setMinimumWidth(100);
        Picasso.get().load(Uri.parse(books.get(position).getImg())).into(holder.thumbnail);
        holder.priceView.setText("$ "+books.get(position).getPrice());
        holder.titleView.setText(books.get(position).getName());
        holder.authorView.setText("By "+books.get(position).getAuthor());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ProductDetailActivity.class);
                intent.putExtra("book_id",books.get(position).getId());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView titleView,authorView,priceView;
        public LinearLayout container;
        public ViewHolder(@NonNull View productView) {
            super(productView);
            container = productView.findViewById(R.id.product_display_container);
            thumbnail = productView.findViewById(R.id.product_thumbnail);
            titleView = productView.findViewById(R.id.product_title);
            authorView = productView.findViewById(R.id.product_author);
            priceView = productView.findViewById(R.id.product_price);
        }
    }
}
