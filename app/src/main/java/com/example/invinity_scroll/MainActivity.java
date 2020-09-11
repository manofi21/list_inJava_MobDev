package com.example.invinity_scroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.invinity_scroll.adapter.ContactAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean isScroll = true;
    private ContactAdapter contactAdapter = new ContactAdapter();
    private ArrayList<String> dataContacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchData(0);
        RecyclerView rvContacs = findViewById(R.id.rvContact);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rvContacs.setLayoutManager(layoutManager);
        rvContacs.setHasFixedSize(true);
        rvContacs.setAdapter(contactAdapter);

        rvContacs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final int countItems = layoutManager.getItemCount();
                int currentItem = layoutManager.getChildCount();
                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                int totalScrollItem = currentItem + firstVisiblePosition;

                if(isScroll && totalScrollItem >= countItems){
                    isScroll = false;
                    contactAdapter.addLoading();

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            contactAdapter.removeDataLoading();
                            fetchData(countItems);
                            isScroll = true;
                        }
                    },2000);
                }
            }
        });
    }

    private void fetchData(int countItem) {
        if(dataContacts.size() > 0){
            dataContacts.clear();
        }
        for (int i = countItem; i < countItem + 15; i++){
            dataContacts.add(i + 1 + ".Johns");
        }
        contactAdapter.addDataContact(dataContacts);
    }
}