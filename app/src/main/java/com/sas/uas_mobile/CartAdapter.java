package com.sas.uas_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<Cart> carts;
    private CartActivity ctx;
    private ProductService service;
    SharedPreferences sp;
    String nim ="";
    public CartAdapter(ArrayList<Cart> carts, CartActivity ctx) {
        sp = ctx.getSharedPreferences("details",Context.MODE_PRIVATE);
        nim = sp.getString("nim",null);
        this.carts = carts;
        this.ctx = ctx;

    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);

        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Book book = service.getBook(carts.get(position).getBookId());
        Picasso.get().load(Uri.parse(book.getImg())).into(holder.thumbnail);
        holder.titleView.setText(book.getName());
        holder.desc.setText(book.getDescription());
        holder.priceView.setText("$ "+book.getPrice());
        holder.qty.setText(carts.get(position).getQty()+" pcs");
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carts.get(position).setQty(carts.get(position).getQty()+1);
                ctx.saveCart();
                ctx.refreshData();
//                ctx.displayCart();
            }
        });
        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carts.get(position).setQty(carts.get(position).getQty()-1);
                if(carts.get(position).getQty()<=0)
                {
                    Log.d("Removing Cart",service.getBook(carts.get(position).getBookId()).getName());
                    ctx.removeFromCart(carts.get(position).getBookId());
                }
                ctx.saveCart();
                ctx.refreshData();
//                ctx.displayCart();
            }
        });

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail,add,decrease;
        public TextView titleView,desc,priceView,qty;

        public ViewHolder(@NonNull View cartView) {
            super(cartView);
            service = ProductService.getInstance();
            thumbnail = cartView.findViewById(R.id.cart_thumbnail);
            titleView = cartView.findViewById(R.id.cart_title);
            desc = cartView.findViewById(R.id.cart_desc);
            priceView = cartView.findViewById(R.id.cart_price);
            qty = cartView.findViewById(R.id.cart_qty);
            add = cartView.findViewById(R.id.cart_increase_icon);
            decrease = cartView.findViewById(R.id.cart_decrease_icon);
        }
    }
}
