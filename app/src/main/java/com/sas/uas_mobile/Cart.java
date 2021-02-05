package com.sas.uas_mobile;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Cart implements Parcelable {
    private int bookId;
    private String nim;
    private int qty;

    public Cart(int bookId, String nim, int qty) {
        this.bookId = bookId;
        this.nim = nim;
        this.qty = qty;
    }

    public Cart(Parcel in)
    {
        this.bookId = in.readInt();
        this.nim = in.readString();
        this.qty = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.bookId);
        parcel.writeString(this.nim);
        parcel.writeInt(this.qty);
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

    public int getBookId() {
        return bookId;
    }

    public void setBook(int bookId) {
        this.bookId = bookId;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
