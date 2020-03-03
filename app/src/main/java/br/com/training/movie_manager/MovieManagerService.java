package br.com.training.movie_manager;

import io.reactivex.rxjava3.core.Single;

import retrofit2.http.GET;

import java.util.List;

/**
 * Interface for MovieManager API.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public interface MovieManagerService {
    String BASE_URL = "https://api.themoviedb.org/3/movie/"; // BASE URL OF THE TMDb API

    // movie.popular
    @GET("popular")
    Single<List<Object>> getPopularMovies();

}
