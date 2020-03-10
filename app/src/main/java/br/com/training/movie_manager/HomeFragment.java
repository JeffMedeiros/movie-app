package br.com.training.movie_manager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Class for Home fragment.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public class HomeFragment extends Fragment {

    /**
     * Bind Layout elements
     */
    @BindView(R.id.viewFeaturedMovie)
    ImageView imgViewFeaturedMovie;

    @BindView(R.id.listPopular)
    RecyclerView listPopular;

    @BindView(R.id.listTopRated)
    RecyclerView listTopRated;

    @BindView(R.id.listInTheatres)
    RecyclerView listInTheatres;

    @BindView(R.id.listUpcoming)
    RecyclerView listUpcoming;

    /**
     * CONSTANTS
     */
    private static final int PRELOAD_AHEAD_ITEMS = 15;
    private static final String TAG = "HomeFragment";


    private MovieManagerNetRepository movieManagerNetRepository;
    private CompositeDisposable compositeDisposable;
    private Unbinder unbinder;

    /**
     * RecyclerView objects
     */
    private RecyclerView.LayoutManager popularLayoutManager;
    private RecyclerView.LayoutManager topRatedLayoutManager;
    private RecyclerView.LayoutManager inTheatresLayoutManager;
    private RecyclerView.LayoutManager upcomingLayoutManager;

    private BaseAdapter popularAdapter;
    private BaseAdapter topRatedAdapter;
    private BaseAdapter inTheatresAdapter;
    private BaseAdapter upcomingAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieManagerNetRepository = MovieManagerNetRepository.getInstance(getActivity().getApplicationContext());
        Log.w(TAG, movieManagerNetRepository.toString());
        compositeDisposable = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, layout);

        /**
         * Settings for "popular movies" RecyclerView
         */
        // Set LayoutManager
        popularLayoutManager = new LinearLayoutManager(
                getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        listPopular.setLayoutManager(popularLayoutManager);

        // Set Adapter
        popularAdapter = new PopularAdapter(this.getContext());
        listPopular.setAdapter(popularAdapter);

        /**
         * Settings for "top rated movies" RecyclerView
         */
        topRatedLayoutManager = new LinearLayoutManager(
                getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        listTopRated.setLayoutManager(topRatedLayoutManager);

        topRatedAdapter = new TopRatedAdapter(this.getContext());
        listTopRated.setAdapter(topRatedAdapter);

        /**
         * Settings for "in theatres movies" RecyclerView
         */
        inTheatresLayoutManager = new LinearLayoutManager(
                getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        listInTheatres.setLayoutManager(inTheatresLayoutManager);

        inTheatresAdapter = new InTheatresAdapter(this.getContext());
        listInTheatres.setAdapter(inTheatresAdapter);

        /**
         * Settings for "upcoming movies" RecyclerView
         */
        upcomingLayoutManager = new LinearLayoutManager(
                getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        listUpcoming.setLayoutManager(upcomingLayoutManager);

        upcomingAdapter = new UpcomingAdapter(this.getContext());
        listUpcoming.setAdapter(upcomingAdapter);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /**
         * Consuming "popular movies" API
         */
        compositeDisposable.add(movieManagerNetRepository
                .getPopularMovies(MainActivity.API_KEY, "en-US", 1, null)
                .subscribe(movies -> {
                    List<Movie> moviesResults = movies.getResults();

                    // Featured movie logic
                    String imgFeaturedMovieUri = "https://image.tmdb.org/t/p/original"
                            + moviesResults.get(0).getBackdrop_path();

                    Glide.with(this)
                            .load(imgFeaturedMovieUri)
                            .into(imgViewFeaturedMovie);

                    // Popular movies logic
                    List<String> urlsPopular = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        urlsPopular.add("https://image.tmdb.org/t/p/w780"
                                + moviesResults.get(i).getPoster_path());
                    }

                    popularAdapter.setmDataSet(urlsPopular);
                    popularAdapter.notifyDataSetChanged();

                    Log.w(TAG, movies.toJson());
                }, error -> {
                    Log.w(TAG, error);
                }));

        /**
         * Consuming "top rated movies" API
         */
        compositeDisposable.add(movieManagerNetRepository
                .getTopRatedMovies(MainActivity.API_KEY, "en-US", 1, null)
                .subscribe(movies -> {
                    List<Movie> moviesResults = movies.getResults();

                    // Top rated movies logic
                    List<String> urlsTopRated = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        urlsTopRated.add("https://image.tmdb.org/t/p/w780"
                                + moviesResults.get(i).getPoster_path());
                    }

                    topRatedAdapter.setmDataSet(urlsTopRated);
                    topRatedAdapter.notifyDataSetChanged();

                    Log.w(TAG, movies.toJson());
                }, error -> {
                    Log.w(TAG, error);
                }));

        /**
         * Consuming "in theatres movies" API
         */
        compositeDisposable.add(movieManagerNetRepository
                .getInTheatresMovies(MainActivity.API_KEY, "en-US", 1, null)
                .subscribe(movies -> {
                    List<Movie> moviesResults = movies.getResults();

                    // In theatres movies logic
                    List<String> urlsInTheatres = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        urlsInTheatres.add("https://image.tmdb.org/t/p/w780"
                                + moviesResults.get(i).getPoster_path());
                    }

                    inTheatresAdapter.setmDataSet(urlsInTheatres);
                    inTheatresAdapter.notifyDataSetChanged();

                    Log.w(TAG, movies.toJson());
                }, error -> {
                    Log.w(TAG, error);
                }));

        /**
         * Consuming "upcoming movies" API
         */
        compositeDisposable.add(movieManagerNetRepository
                .getUpcomingMovies(MainActivity.API_KEY, "en-US", 1, null)
                .subscribe(movies -> {
                    List<Movie> moviesResults = movies.getResults();

                    // Upcoming movies logic
                    List<String> urlsUpcoming = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        urlsUpcoming.add("https://image.tmdb.org/t/p/w780"
                                + moviesResults.get(i).getPoster_path());
                    }

                    upcomingAdapter.setmDataSet(urlsUpcoming);
                    upcomingAdapter.notifyDataSetChanged();

                    Log.w(TAG, movies.toJson());
                }, error -> {
                    Log.w(TAG, error);
                }));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        compositeDisposable.dispose();
        unbinder.unbind();
    }
}
