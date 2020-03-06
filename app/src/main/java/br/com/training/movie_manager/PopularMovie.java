package br.com.training.movie_manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Represents the object of popular movies.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public class PopularMovie {

    @SerializedName("page")
    @Expose()
    private int page;

    @SerializedName("total_results")
    @Expose()
    private int total_results;

    @SerializedName("total_pages")
    @Expose()
    private int total_pages;

    @SerializedName("results")
    @Expose()
    private List<Movie> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    /**
     * Convert Object to json format.
     *
     * @return String
     */
    public String toJson() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return gson.toJson(this);
    }

    /**
     * Convert json to Object.
     *
     * @param json String
     * @return PopularMovie
     */
    public static PopularMovie jsonDeserialize(String json) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Type typePopularMovie = new TypeToken<PopularMovie>() { }.getType();

        return gson.fromJson(json, typePopularMovie);
    }
}
