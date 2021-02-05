package com.sas.uas_mobile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface  {

    @GET("book")
    Call<ApiResponse> getBooks(
            @Query("nim")String nim,
            @Query("name")String name
    );

    @GET("book/{id}")
    Call<ApiResponse> getBook(
            @Path("id") int bookId,
            @Query("nim")String nim,
            @Query("name")String name
            );

}
