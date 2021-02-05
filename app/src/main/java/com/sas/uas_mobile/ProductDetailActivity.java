package com.sas.uas_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    Book book;
    int book_id;
    ProductService service;
    ImageView productImg,cartIcon;
    TextView uname_text,product_title,product_author,product_type,product_category,product_price,product_desc;
    SharedPreferences sp;
    String nim,name;
    Context ctx;
    Button buy_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        productImg = findViewById(R.id.product_detail_img);
        ctx =this;
        sp = ctx.getSharedPreferences("details",MODE_PRIVATE);
        nim = sp.getString("nim",null);
        name = sp.getString("name",null);
        uname_text = findViewById(R.id.username_text);
        product_title = findViewById(R.id.product_detail_title);
        product_author =findViewById(R.id.product_detail_author);
        product_type = findViewById(R.id.product_detail_type);
        product_category =findViewById(R.id.product_detail_genre);
        product_price =findViewById(R.id.product_detail_price);
        product_desc = findViewById(R.id.product_detail_description);
        buy_btn = findViewById(R.id.buy_btn);
        uname_text.setText(name);
        service = ProductService.getInstance();
        Intent intent = getIntent();
        book_id = intent.getExtras().getInt("book_id");
        book = service.getBook(book_id);
        if(book == null)
        {
            Log.d("Book","NULL");
        }
        product_title.setText(book.getName());
        product_author.setText(book.getAuthor());
        product_type.setText(book.getType());
        product_category.setText(book.getCategory());
        product_price.setText("$ "+book.getPrice());
        product_desc.setText(book.getDescription());
        Picasso.get().load(Uri.parse(book.getImg())).into(productImg);

        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,CartActivity.class);
                intent.putExtra("book_id",book_id);
                startActivity(intent);

            }
        });
        cartIcon = findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,CartActivity.class);
                startActivity(intent);
            }
        });
    }
}