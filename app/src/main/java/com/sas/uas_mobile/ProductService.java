package com.sas.uas_mobile;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductService extends Service {

    Retrofit retrofit;
    ApiInterface api;
    private static ProductService instance = new ProductService();
    String nim,name;
    ApiResponse resp;
    SharedPreferences sp;
    private ProductService() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/staging/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        responses = new ArrayList<>();
        api = retrofit.create(ApiInterface.class);


    }
    public static ProductService getInstance()
    {
        return instance;
    }

    public void setSharedPreferences(Context context)
    {
        sp = context.getSharedPreferences("details",MODE_PRIVATE);
        nim = sp.getString("nim",null);
        name = sp.getString("name",null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    public ArrayList<Book> getBooks()  {
    ArrayList<Book> books = new ArrayList<>();
        Call<ApiResponse> call = api.getBooks(nim,name);
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//
//                if(response.isSuccessful())
//                {
//                    resp = response.body();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
////                Toast.makeText(ctx,"ERROR = "+t.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    resp = call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resp.getBooks();
    }

    public Book getBook(int id)  {
        Call<ApiResponse> call = api.getBook(id,nim,name);
        Book book = null;
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                Log.d("Response",""+(response.isSuccessful()?"Successful ":"Fail ")+response.raw().request().url());
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//
//            }
//        });
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        resp = call.execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            thread.join();

            book = resp.getBooks().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;

    }

}
