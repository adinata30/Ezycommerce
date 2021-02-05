package com.sas.uas_mobile;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class Book implements Parcelable {
    private int id;
    private String name,description,author,type,img,category;
    private float price;
    private boolean inCart;

    public Book(int id, String name, String description, float price, String author, String type, String img, boolean inCart, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.type = type;
        this.img = img;
        this.category = category;
        this.price = price;
        this.inCart = inCart;
    }

    public Book(Parcel in)
    {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.author = in.readString();
        this.type = in.readString();
        this.img = in.readString();
        this.price = in.readFloat();
        this.inCart = false;
        this.category = in.readString();
        Log.d("bukuku","= "+this.name);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.description);
        parcel.writeString(this.author);
        parcel.writeString(this.type);
        parcel.writeString(this.img);
        parcel.writeFloat(this.price);
//        parcel.writeBoolean(false);
        parcel.writeString(this.category);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Parcelable.Creator<Book> CREATOR
            = new Parcelable.Creator<Book>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
