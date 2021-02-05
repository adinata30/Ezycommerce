package com.sas.uas_mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    String nim,name;
    SharedPreferences sp;
    Context ctx;
    ArrayList<Cart> carts,myCarts;
    ProductService service;
    TextView cart_total,cart_taxes,cart_subtotal;
    TextView uname;
    RecyclerView rv;
    CartAdapter adapter;
    Button clearBtn,checkoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        service = ProductService.getInstance();
        carts = new ArrayList<>();
        ctx = this;
        sp = ctx.getSharedPreferences("details",MODE_PRIVATE);
        nim = sp.getString("nim",null);
        name = sp.getString("name",null);
        uname = findViewById(R.id.username_text);
        uname.setText(name);

        initializeCart();
        cart_total = findViewById(R.id.cart_total);
        cart_subtotal = findViewById(R.id.cart_subtotal);
        cart_taxes = findViewById(R.id.cart_taxes);


        Intent intent = getIntent();
        if(intent.getExtras() != null)
        {
            boolean flag = false;
            int book_id = intent.getExtras().getInt("book_id");
            for (Cart cart:carts)
            {
                if(cart.getBookId() == book_id && cart.getNim().equals(nim))
                {
                    cart.setQty(cart.getQty()+1);
                    flag = true;
                }
            }
            if(flag == false)
            {
                carts.add(new Cart(book_id,nim,1));
            }
            saveCart();
        }

        rv = findViewById(R.id.cart_recycler_view);
        myCarts = getMyCart();
        adapter = new CartAdapter(myCarts,this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cart_subtotal.setText("$ "+String.format("%.2f",getCartSubtotal()));
        cart_taxes.setText("$ "+String.format("%.2f",getTaxes()));
        cart_total.setText("$ "+String.format("%.2f",getTotal()));

        clearBtn = findViewById(R.id.clear_cart_btn);
        checkoutBtn = findViewById(R.id.checkout_btn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getTotal() == 0f){
                    new AlertDialog.Builder(ctx).setTitle("Empty Cart").setMessage("Your cart is empty ").
                            setPositiveButton("Ok",null).show();

                    return;
                };
                new AlertDialog.Builder(ctx).setTitle("Clear Cart").setMessage("Are You sure want to clear your cart?").
                        setPositiveButton("No",null).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clearCart();
                        refreshData();
                        saveCart();
                    }
                }).show();
            };
        });
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getTotal() == 0f){
                    new AlertDialog.Builder(ctx).setTitle("Empty Cart").setMessage("Your cart is empty ").
                            setPositiveButton("Ok",null).show();
                    return;
                };
                new AlertDialog.Builder(ctx).setTitle("Checkout").setMessage("You have checkout your cart for $ "+String.format("%.2f",getTotal())).
                        setPositiveButton("Ok",null).show();

                clearCart();
                saveCart();
                refreshData();

            }
        });
    }

    private float getCartSubtotal(){
        float ret = 0f;
        for(Cart cart:carts)
        {

            if(cart.getNim().equals(nim) )
            {
                Book book = service.getBook(cart.getBookId());
                ret+= book.getPrice()*cart.getQty();
            }
        }

        return ret;
    }

    private float getTaxes(){
        return getCartSubtotal()*0.1f;
    }
    private float getTotal(){
        return getCartSubtotal()+getTaxes();
    }
    private void initializeCart(){
        String json = sp.getString("carts",null);
        if(json != null)
        {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Cart>>(){}.getType();
            carts = gson.fromJson(json,type);
        }
        else saveCart();
    }

    public void saveCart(){
        Gson gson = new Gson();
        String json = gson.toJson(carts);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("carts",json);
        editor.apply();
    }

    public void removeFromCart(int bookId){

        for (int i =0;i<carts.size();i++){
            Cart cart = carts.get(i);

            if(cart.getBookId() == bookId && cart.getNim().equals(nim) )
            {

                myCarts.remove(cart);
                carts.remove(i);
                break;
            }
        }
//        displayCart();
    }
    public void displayCart(){
        for(Cart cart:carts)
        {
            Log.d("Cart book",service.getBook(cart.getBookId()).getName());
            Log.d("Cart Nim",""+cart.getNim());
            Log.d("Cart qty",""+cart.getQty());
        }
    }
    public void refreshData(){
//        initializeCart();
//        adapter = new CartAdapter(carts,this);
//        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cart_subtotal.setText("$ "+String.format("%.2f",getCartSubtotal()));
        cart_taxes.setText("$ "+String.format("%.2f",getTaxes()));
        cart_total.setText("$ "+String.format("%.2f",getTotal()));
    }
    public void clearCart(){
        ArrayList<Integer> ids = new ArrayList<>();
        for (Cart cart: carts)
        {
            ids.add(cart.getBookId());
        }
        for(Integer id : ids)
        {
            removeFromCart(id);
        }
    }
    private ArrayList<Cart> getMyCart(){
        ArrayList<Cart>temp = new ArrayList<>();
        for(Cart cart:carts)
        {
            if(cart.getNim().equals(nim))temp.add(cart);
        }
        return temp;
    }
}