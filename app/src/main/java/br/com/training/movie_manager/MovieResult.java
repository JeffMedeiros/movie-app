package br.com.training.movie_manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Represents the object returned by the API's movie.popular, movie.top_rated, movie.now_playing
 * and movie.upcoming routes.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public class MovieResult {

    @SerializedName("page")
    @Expose()
    private int page;

    @SerializedName("total_results")
    @Expose()
    private int totalResults;

    @SerializedName("total_pages")
    @Expose()
    private int totalPages;

    @SerializedName("results")
    @Expose()
    private List<Movie> results;

    @SerializedName("dates")
    @Expose()
    private DateRange dateRange;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
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
     * @return MovieResult
     */
    public static MovieResult jsonDeserialize(String json) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Type typePopularMovie = new TypeToken<MovieResult>() {
        }.getType();

        return gson.fromJson(json, typePopularMovie);
    }
}
