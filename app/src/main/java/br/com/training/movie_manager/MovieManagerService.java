package br.com.training.movie_manager;

import io.reactivex.Single;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Class for MovieManager Service.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public interface MovieManagerService {
    // movie.popular
    @GET("popular")
    Single<MovieResult> getPopularMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                         @Query("page") int page, @Query("region") String region);

    // movie.top_rated
    @GET("top_rated")
    Single<MovieResult> getTopRatedMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                          @Query("page") int page, @Query("region") String region);

    // movie.now_playing
    @GET("now_playing")
    Single<MovieResult> getInTheatresMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                            @Query("page") int page, @Query("region") String region);

    // movie.upcoming
    @GET("upcoming")
    Single<MovieResult> getUpcomingMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                          @Query("page") int page, @Query("region") String region);
}
