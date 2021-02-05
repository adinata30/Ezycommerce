package com.sas.uas_mobile;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ProductListFragmentAdapter extends FragmentPagerAdapter {

    ProductService service;
    ArrayList<Book> books;
    ArrayList<ArrayList<Book>> booksCtg;
    ArrayList<String> categories;
    public ProductListFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
        service = ProductService.getInstance();
        books = service.getBooks();
        categories = new ArrayList<>();
        booksCtg = new ArrayList<>();
        for( Book book : books)
        {
            int ctgIdx = categories.indexOf(book.getCategory());
           if(ctgIdx == -1)
           {
               categories.add(book.getCategory());
               ArrayList<Book>temp = new ArrayList<>();
               temp.add(book);
               booksCtg.add(temp);
           }
           else{
               booksCtg.get(ctgIdx).add(book);
           }
        }
        booksCtg.add(0,booksCtg.get(booksCtg.size()-1));

    }
    public void refreshBook(){

    }
    public void reverseBook(){

    }
    @NonNull
    @Override
    public Fragment getItem(int position) {


        ProductListFragment fragment = new ProductListFragment();
//        position-=1;
        if(position>= categories.size())position%=4;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("books",booksCtg.get(position));
        fragment.setArguments(bundle);
//        bundle.put
        return fragment;
    }

    @Override
    public int getCount() {
        return categories.size()+2;
    }
}
