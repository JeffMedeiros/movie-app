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
import com.bumptech.glide.util.ViewPreloadSizeProvider;

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

    @BindView(R.id.listPopularMovies)
    RecyclerView listPopularMovies;

    @BindView(R.id.listBestRatedMovies)
    RecyclerView viewBestRatedMovies;

    @BindView(R.id.listAtTheMovies)
    RecyclerView viewAtTheMoviesMovies;

    @BindView(R.id.listUpcoming)
    RecyclerView viewUpcomingMovies;

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
    private RecyclerAdapter popularAdapter;
    private ViewPreloadSizeProvider<String> preloadSizeProvider;

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

        listPopularMovies.setLayoutManager(popularLayoutManager);

        // Set Adapter
        popularAdapter = new RecyclerAdapter();
        listPopularMovies.setAdapter(popularAdapter);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /**
         * Consuming "popular movies" API
         */
        compositeDisposable.add(movieManagerNetRepository
                .getPopularMovies(MainActivity.API_KEY)
                .subscribe(movies -> {
                    List<Movie> moviesResults = movies.getResults();

                    // Featured movie logic
                    String imgFeaturedMovieUri = "https://image.tmdb.org/t/p/original"
                            + moviesResults.get(0).getBackdrop_path();

                    Glide.with(this)
                            .load(imgFeaturedMovieUri)
                            .into(imgViewFeaturedMovie);

                    // Popular movies logic
                    List<String> urlsPopularMovies = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        urlsPopularMovies.add("https://image.tmdb.org/t/p/w780"
                                + moviesResults.get(i).getPoster_path());
                    }

                    popularAdapter.setmDataSet(urlsPopularMovies);
                    popularAdapter.notifyDataSetChanged();

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
