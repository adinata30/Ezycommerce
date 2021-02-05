package com.sas.uas_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {


    String nim="",name="";
    SharedPreferences sp;
    List<Book> books;
    ArrayList<String>categories;
    Context ctx;
    static ArrayList<Button> buttons;
//    List<ApiResponse> responses;
    SharedPreferences.Editor editor;
    ApiResponse resp;
    TextView usernameTxt;
    LinearLayout categoryContainer;
    static HorizontalScrollView sv;
    ProductService service;
    ViewPager vp;
    ImageView cartIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ctx = this;
        service = ProductService.getInstance();
        service.setSharedPreferences(ctx);
        books = new ArrayList<>();
        categories = new ArrayList<>();
        sp = ctx.getSharedPreferences("details",MODE_PRIVATE);
        nim = sp.getString("nim",null);
        name = sp.getString("name",null);
        usernameTxt = findViewById(R.id.username_text);
        usernameTxt.setText(name);
        categoryContainer = findViewById(R.id.category_container);
        buttons = new ArrayList<>();
        cartIcon = findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,CartActivity.class);
                startActivity(intent);
            }
        });


        sv = findViewById(R.id.category_scroll);
        vp = findViewById(R.id.product_list_view_pager);
        ProductListFragmentAdapter adapter = new ProductListFragmentAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        vp.setCurrentItem(1);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position > categories.size()){
                    position =1;
                }
                else if(position == 0)
                {
                    position = categories.size();
                }
                vp.setCurrentItem(position,false);
                activateButton(position-1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getBooks();
    }
    public void getBooks()
    {
        books = service.getBooks();
        displayBooks();
    }

    public void displayBooks(){


        String temp = "";
        for (Book book : books)
        {
            if (!categories.contains(book.getCategory()))
            {
                categories.add(book.getCategory());
                temp+= book.getCategory()+" ";
            }
        }
        displayCategories();
    }

    public void displayCategories(){
        for (int i =0 ; i <categories.size();i++)
        {
            String category = categories.get(i);
            Button button = new Button(ctx);
            button.setText(category);
            button.setPadding(40,20,40,20);
            button.setTextColor(Color.BLACK);
            button.setWidth(500);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 20, 20, 0);
            button.setLayoutParams(params);
            int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vp.setCurrentItem(finalI+1);
                    activateButton(finalI);
                }
            });
            buttons.add(button);
            categoryContainer.addView(button);
        }
        activateButton(0);
    }

    public static void activateButton(int position){
        sv.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator=ObjectAnimator.ofInt(sv, "scrollX",position*500 );
//                animator.setStartDelay(100);
                animator.setDuration(100);
                animator.start();
//                sv.scrollTo(1000,0);
            }
        });
        for(int i =0;i<buttons.size();i++)
        {
            Button temp = buttons.get(i);
            if (i == position)
            {
                temp.setBackgroundResource(R.drawable.button);
//                temp.set
            }
            else{
                temp.setBackgroundResource(R.drawable.button_secondary);
            }
        }
    }
}