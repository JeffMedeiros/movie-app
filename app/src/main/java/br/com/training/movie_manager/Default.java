package br.com.training.movie_manager;

/**
 * Defines constants to be used in the application.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public class Default {
    public static final String API_KEY = "2a946dd20bb20e6eed31ee5e8e8687c9";

    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/"; // BASE URL OF THE TMDb API

    public static final String MOVIE_RESULTS_LANGUAGE = "en-US";

    public static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w780";

    public static final String BASE_URL_VIDEO = "https://www.youtube.com/watch?v=";

    public static final String TRAILER_TYPE = "Trailer";

    private Default() {
        throw new IllegalStateException("Utility class");
    }
}
