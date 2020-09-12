package com.example.invinity_scroll.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<Movie> movies;

	@SerializedName("total_results")
	private int totalResults;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<Movie> results){
		this.movies = results;
	}

	public List<Movie> getMovies(){
		return movies;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	@Override
 	public String toString(){
		return
			"Response{" +
			"page = '" + page + '\'' +
			",total_pages = '" + totalPages + '\'' +
			",results = '" + movies + '\'' +
			",total_results = '" + totalResults + '\'' +
			"}";
		}
}