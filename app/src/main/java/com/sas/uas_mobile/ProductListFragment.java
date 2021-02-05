package com.sas.uas_mobile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ProductListFragment extends Fragment {

    RecyclerView rv;
    ArrayList<Book> books;
    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            books = getArguments().getParcelableArrayList("books");
            Log.d("Books",""+books.size());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.product_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        ProductListAdapter adapter = new ProductListAdapter(books);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.d("ADapter ready","True, "+books.size());
    }
}