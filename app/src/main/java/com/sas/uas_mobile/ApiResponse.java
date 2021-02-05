package com.sas.uas_mobile;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse {

    @SerializedName("statuscode")
    private int code;

    private String nim,name,credits;
    private int productId;
    @SerializedName("products")
    private ArrayList<Book> books;

    public int getCode() {
        return code;
    }

    public String getNim() {
        return nim;
    }

    public String getName() {
        return name;
    }

    public String getCredits() {
        return credits;
    }

    public int getProductId() {
        return productId;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
