package br.com.training.movie_manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Represents the object returned by the API's movie.movie_id.videos route.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public class MovieVideoResult {

    @SerializedName("id")
    @Expose()
    private int id;

    @SerializedName("results")
    @Expose()
    private List<MovieVideo> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieVideo> getResults() {
        return results;
    }

    public void setResults(List<MovieVideo> results) {
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
     * @return MovieVideoResult
     */
    public static MovieVideoResult jsonDeserialize(String json) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Type typeMovieVideoResult = new TypeToken<MovieVideoResult>() {
        }.getType();

        return gson.fromJson(json, typeMovieVideoResult);
    }
}
