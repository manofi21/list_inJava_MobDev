package com.example.invinity_scroll.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.invinity_scroll.BuildConfig;
import com.example.invinity_scroll.R;
import com.example.invinity_scroll.databinding.ItemMovieBinding;
import com.example.invinity_scroll.model.Movie;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Movie> dataMovie = new ArrayList<>();
    private static final int TYPE_MOVIE = 1;
    private static final int TYPE_LOADING = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_MOVIE){
            return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent,false));
        }
        return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_MOVIE){
            ((MovieViewHolder) holder).bind(dataMovie.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataMovie.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (dataMovie.get(position) != null) ? TYPE_MOVIE : TYPE_LOADING;
    }

    public void addDataMovies(List<Movie> movies){
        if(movies != null){
            dataMovie.addAll(movies);
            notifyDataSetChanged();
        }
    }

    public void addLoading(){
        dataMovie.add(null);
        notifyDataSetChanged();
    }

    public void removeDataLoading(){
        dataMovie.remove((dataMovie.size() - 1));
        notifyDataSetChanged();
    }

    public int getDataMovies(){
        return dataMovie.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        private ItemMovieBinding binding;
        TextView tvName;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemMovieBinding.bind(itemView);
//            tvName = itemView.findViewById(R.id.tvName);
        }

        public void bind(Movie movie) {
            if(movie != null){
                String urlImage = BuildConfig.BASE_URL_IMAGE_MOVIE_DB + movie.getPosterPath();
                DecimalFormat decimalFormat = new DecimalFormat("#.#");

                Glide.with(itemView).load(urlImage).into(binding.ivMovie);
                binding.tvTitleMovie.setText(movie.getOriginalTitle());
                binding.tvReleaseDate.setText(movie.getReleaseDate());
                binding.tvRatingMovie.setText(decimalFormat.format(movie.movieRate()));
                binding.raintBarMovie.setRating(movie.movieRate());
            }
        }
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
