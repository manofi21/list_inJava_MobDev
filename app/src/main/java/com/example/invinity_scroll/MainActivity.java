package com.example.invinity_scroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.invinity_scroll.adapter.MovieAdapter;
import com.example.invinity_scroll.databinding.ActivityMainBinding;
import com.example.invinity_scroll.model.MovieResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private int page = 1;
    private int lastPage =0;

    private boolean isScroll = true;
    private MovieAdapter movieAdapter = new MovieAdapter();
    private ArrayList<String> dataContacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fetchData(page);
//        RecyclerView rvContacs = findViewById(R.id.rvContact);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//
        binding.rvMovie.setLayoutManager(layoutManager);
        binding.rvMovie.setHasFixedSize(true);
        binding.rvMovie.setAdapter(movieAdapter);

        binding.rvMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final int countItems = layoutManager.getItemCount();
                int currentItem = layoutManager.getChildCount();
                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                int totalScrollItem = currentItem + firstVisiblePosition;

                if(isScroll && totalScrollItem >= countItems && page <= lastPage){
                    isScroll = false;
                    page += 1;
                    fetchData(page);
                }
            }
        });
    }

    private void fetchData(int countItem) {
        loadloading();
        requestQueue = Volley.newRequestQueue(this);

        String mainUrl = BuildConfig.BASE_URL_MOVIE_DB + BuildConfig.API_VERSION;
        String movieUrl = mainUrl + "discover/movie?api_key=" + BuildConfig.TOKEN_MOVIE_DB + "&page=" + page;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, movieUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideLoading();
                if(response != null){
                    MovieResponse movieResponse = new Gson().fromJson(response, MovieResponse.class);
                    if(movieAdapter.getDataMovies() > 0){
                        movieAdapter.removeDataLoading();
                        isScroll = true;
                    }
                    lastPage = movieResponse.getTotalPages();
                    movieAdapter.addDataMovies(movieResponse.getMovies());
                    Log.d(TAG,"api response: " + response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideLoading();
                Log.d(TAG, Objects.requireNonNull(error.getMessage()));
            }
        });

        stringRequest.setTag(TAG);
        requestQueue.add(stringRequest);
    }

    private void hideLoading() {
        binding.swipeMain.setRefreshing(false);
    }

    private void loadloading() {
        if(movieAdapter.getDataMovies() > 0){
            movieAdapter.addLoading();
        }
        binding.swipeMain.setRefreshing(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(requestQueue != null){
            requestQueue.cancelAll(TAG);
        }
    }
}