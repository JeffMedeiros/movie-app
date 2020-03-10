package br.com.training.movie_manager;

import android.content.Context;
import android.util.Log;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interface for MovieManager API.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public class MovieManagerNetRepository extends BaseNetRepository {

    private MovieManagerService movieManagerService;

    private MovieManagerNetRepository(Context context) {
        super(context);
        this.mContext = context;

        super.addInterceptor(requestInterceptor());
        super.addInterceptor(responseInterceptor());

        movieManagerService = super.provideRetrofit(MovieManagerService.BASE_URL)
                .create(MovieManagerService.class);
    }

    public static MovieManagerNetRepository getInstance(Context context) {
        return new MovieManagerNetRepository(context);
    }

    /**
     * Provide intercept with header according to Movie Manager API Service.
     *
     * @return Interceptor
     */
    private Interceptor requestInterceptor() {
        return chain -> {
            Request request = chain.request();
            final Request.Builder requestBuilder = request.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-type", "application/json")
                    .method(request.method(), request.body());

            Log.w("InterceptorHANIOT", "| REQUEST: " + requestBuilder.build().method() + " "
                    + requestBuilder.build().url().toString());
            return chain.proceed(requestBuilder.build());
        };
    }

    /**
     * Provide intercept with to request response.
     *
     * @return Interceptor
     */
    private Interceptor responseInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());

            return response;
        };
    }

    // movies.popular
    public Single<MovieResult> getPopularMovies(String api_key, String language, int page, String region) {
        return movieManagerService.getPopularMovies(api_key, language, page, region)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // movies.top_rated
    public Single<MovieResult> getTopRatedMovies(String api_key, String language, int page, String region) {
        return movieManagerService.getTopRatedMovies(api_key, language, page, region)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // movies.now_playing
    public Single<MovieResult> getInTheatresMovies(String api_key, String language, int page, String region) {
        return movieManagerService.getInTheatresMovies(api_key, language, page, region)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // movies.upcoming
    public Single<MovieResult> getUpcomingMovies(String api_key, String language, int page, String region) {
        return movieManagerService.getUpcomingMovies(api_key, language, page, region)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
