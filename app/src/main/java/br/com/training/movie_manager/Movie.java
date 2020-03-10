package br.com.training.movie_manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Represents the object of movie.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public class Movie {
    @SerializedName("id")
    @Expose()
    private long id;

    @SerializedName("poster_path")
    @Expose()
    private String poster_path;

    @SerializedName("adult")
    @Expose()
    private boolean adult;

    @SerializedName("overview")
    @Expose()
    private String overview;

    @SerializedName("release_date")
    @Expose()
    private String release_date;

    @SerializedName("genre_ids")
    @Expose()
    private List<Integer> genre_ids;

    @SerializedName("original_title")
    @Expose()
    private String original_title;

    @SerializedName("original_language")
    @Expose()
    private String original_language;

    @SerializedName("title")
    @Expose()
    private String title;

    @SerializedName("backdrop_path")
    @Expose()
    private String backdrop_path;

    @SerializedName("popularity")
    @Expose()
    private double popularity;

    @SerializedName("vote_count")
    @Expose()
    private int vote_count;

    @SerializedName("video")
    @Expose()
    private boolean video;

    @SerializedName("vote_average")
    @Expose()
    private double vote_average;

    /**
     * GETTERS AND SETTERS
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
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
     * @return Movie
     */
    public static Movie jsonDeserialize(String json) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Type typeMovie = new TypeToken<Movie>() { }.getType();

        return gson.fromJson(json, typeMovie);
    }
}
