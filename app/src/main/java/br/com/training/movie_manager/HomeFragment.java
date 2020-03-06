package br.com.training.movie_manager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;

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

    @BindView(R.id.viewFeaturedMovie)
    ImageView imgViewFeaturedMovie;

    @BindView(R.id.viewPopularMovies)
    RecyclerView viewPopularMovies;

    @BindView(R.id.viewBestRatedMovies)
    RecyclerView viewBestRatedMovies;

    @BindView(R.id.viewAtTheMovies)
    RecyclerView viewAtTheMoviesMovies;

    @BindView(R.id.viewUpcoming)
    RecyclerView viewUpcomingMovies;

    private static final String TAG = "HomeFragment";

    private MovieManagerNetRepository movieManagerNetRepository;

    private CompositeDisposable compositeDisposable;

    private Unbinder unbinder;

    private RecyclerView.LayoutManager popularLayoutManager;
    private RecyclerView.LayoutManager bestRatedLayoutManager;
    private RecyclerView.LayoutManager atTheMoviesLayoutManager;
    private RecyclerView.LayoutManager upcomingLayoutManager;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieManagerNetRepository = MovieManagerNetRepository.getInstance(getActivity().getApplicationContext());
        Log.w(TAG, movieManagerNetRepository.toString());
        compositeDisposable = new CompositeDisposable();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, layout);

        // Set Layout Manager for RecyclerView objects
        popularLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        bestRatedLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        atTheMoviesLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        upcomingLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        viewPopularMovies.setLayoutManager(popularLayoutManager);
        viewBestRatedMovies.setLayoutManager(bestRatedLayoutManager);
        viewAtTheMoviesMovies.setLayoutManager(atTheMoviesLayoutManager);
        viewUpcomingMovies.setLayoutManager(upcomingLayoutManager);

        return layout;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        compositeDisposable.dispose();
        unbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
                    for (int i = 0; i < 20; i++) {
                        String imgMovieUri = "https://image.tmdb.org/t/p/original"
                                + moviesResults.get(i).getPoster_path();

//                        Glide.with(this)
//                                .load(imgMovieUri)
//                                .into(imgViewFeaturedMovie);
                    }

                    Log.w(TAG, movies.toJson());
                }, error -> {
                    Log.w(TAG, error);
                }));
    }
}
