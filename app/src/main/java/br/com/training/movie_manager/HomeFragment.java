package br.com.training.movie_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.bumptech.glide.Glide;

import io.reactivex.disposables.CompositeDisposable;

import timber.log.Timber;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Home fragment.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public class HomeFragment extends Fragment {

    /**
     * Bind Layout elements
     */
    @BindView(R.id.home_swipe_refresh)
    SwipeRefreshLayout mDataSwipeRefresh;

    @BindView(R.id.viewFeaturedMovie)
    ImageView mImgViewFeaturedMovie;

    @BindView(R.id.listPopular)
    RecyclerView mListPopular;

    @BindView(R.id.listTopRated)
    RecyclerView mListTopRated;

    @BindView(R.id.listInTheatres)
    RecyclerView mListInTheatres;

    @BindView(R.id.listUpcoming)
    RecyclerView mListUpcoming;

    private MovieManagerNetRepository mMovieManagerNetRepository;
    private CompositeDisposable mCompositeDisposable;
    private Unbinder mUnbinder;

    // We need this variable to lock and unlock loading more.
    // We should not charge more when a request has already been made.
    // The load will be activated when the requisition is completed.
    private boolean itShouldLoadMore = true;

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

        mMovieManagerNetRepository = MovieManagerNetRepository.getInstance(getActivity().getApplicationContext());
        mCompositeDisposable = new CompositeDisposable();

        /**
         * Initialize LayoutManager and Adapter for "Popular Movies" RecyclerView
         */
        // Set LayoutManager
        popularLayoutManager = new LinearLayoutManager(
                getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        popularAdapter = new PopularAdapter(this.getContext());

        /**
         * For "Top Rated Movies" RecyclerView
         */
        topRatedLayoutManager = new LinearLayoutManager(
                getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        topRatedAdapter = new TopRatedAdapter(this.getContext());

        /**
         * For "In Theatres Movies" RecyclerView
         */
        inTheatresLayoutManager = new LinearLayoutManager(
                getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        inTheatresAdapter = new InTheatresAdapter(this.getContext());

        /**
         * For "Upcoming Movies" RecyclerView
         */
        upcomingLayoutManager = new LinearLayoutManager(
                getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        upcomingAdapter = new UpcomingAdapter(this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        mUnbinder = ButterKnife.bind(this, layout);

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureRecyclerViews();
        initComponents();

    }

    private void configureRecyclerViews() {
        // "Popular Movies"
        mListPopular.setLayoutManager(popularLayoutManager);
        mListPopular.setAdapter(popularAdapter);

        // "Top Rated Movies"
        mListTopRated.setLayoutManager(topRatedLayoutManager);
        mListTopRated.setAdapter(topRatedAdapter);

        // "In Theatres Movies"
        mListInTheatres.setLayoutManager(inTheatresLayoutManager);
        mListInTheatres.setAdapter(inTheatresAdapter);

        // "Upcoming Movies"
        mListUpcoming.setLayoutManager(upcomingLayoutManager);
        mListUpcoming.setAdapter(upcomingAdapter);
    }
    /**
     * Initialize components
     */
    private void initComponents() {
        initDataSwipeRefresh();
        loadMovieData();
    }

    /**
     * Initialize SwipeRefresh
     */
    private void initDataSwipeRefresh() {
        mDataSwipeRefresh.setOnRefreshListener(() -> {
            if (itShouldLoadMore) loadMovieData();
        });
    }

    /**
     * Load movie data from the TMDb server.
     */
    private void loadMovieData() {
        /**
         * Consuming "popular movies" API
         */
        mCompositeDisposable.add(mMovieManagerNetRepository
                .getPopularMovies(Default.API_KEY, Default.MOVIE_RESULTS_LANGUAGE, 1, null)
                .doOnSubscribe(disposable -> loading(true))
                .doAfterTerminate(() -> loading(false))
                .subscribe(movies -> {
                    List<Movie> moviesResults = movies.getResults();

                    // Featured movie logic
                    String imgFeaturedMovieUri = "https://image.tmdb.org/t/p/original"
                            + moviesResults.get(0).getBackdropPath();

                    Glide.with(this)
                            .load(imgFeaturedMovieUri)
                            .into(mImgViewFeaturedMovie);

                    // Popular movies logic
                    List<String> urlsPopular = new ArrayList<>();
                    for (int i = 1; i < 20; i++) {
                        urlsPopular.add(Default.BASE_URL_IMAGE + moviesResults.get(i).getPosterPath());
                    }

                    popularAdapter.setmDataSet(urlsPopular);
                    popularAdapter.notifyDataSetChanged();
                }, error -> Timber.e(error))
        );

        /**
         * Consuming "top rated movies" API
         */
        mCompositeDisposable.add(mMovieManagerNetRepository
                .getTopRatedMovies(Default.API_KEY, Default.MOVIE_RESULTS_LANGUAGE, 1, null)
                .doOnSubscribe(disposable -> loading(true))
                .doAfterTerminate(() -> loading(false))
                .subscribe(movies -> {
                    List<Movie> moviesResults = movies.getResults();

                    // Top rated movies logic
                    List<String> urlsTopRated = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        urlsTopRated.add(Default.BASE_URL_IMAGE + moviesResults.get(i).getPosterPath());
                    }

                    topRatedAdapter.setmDataSet(urlsTopRated);
                    topRatedAdapter.notifyDataSetChanged();
                }, error -> Timber.e(error))
        );

        /**
         * Consuming "in theatres movies" API
         */
        mCompositeDisposable.add(mMovieManagerNetRepository
                .getInTheatresMovies(Default.API_KEY, Default.MOVIE_RESULTS_LANGUAGE, 1, null)
                .doOnSubscribe(disposable -> loading(true))
                .doAfterTerminate(() -> loading(false))
                .subscribe(movies -> {
                    List<Movie> moviesResults = movies.getResults();

                    // In theatres movies logic
                    List<String> urlsInTheatres = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        urlsInTheatres.add(Default.BASE_URL_IMAGE + moviesResults.get(i).getPosterPath());
                    }

                    inTheatresAdapter.setmDataSet(urlsInTheatres);
                    inTheatresAdapter.notifyDataSetChanged();
                }, error -> Timber.e(error))
        );

        /**
         * Consuming "upcoming movies" API
         */
        mCompositeDisposable.add(mMovieManagerNetRepository
                .getUpcomingMovies(Default.API_KEY, Default.MOVIE_RESULTS_LANGUAGE, 1, null)
                .doOnSubscribe(disposable -> loading(true))
                .doAfterTerminate(() -> loading(false))
                .subscribe(movies -> {
                    List<Movie> moviesResults = movies.getResults();

                    // Upcoming movies logic
                    List<String> urlsUpcoming = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        urlsUpcoming.add(Default.BASE_URL_IMAGE + moviesResults.get(i).getPosterPath());
                    }

                    upcomingAdapter.setmDataSet(urlsUpcoming);
                    upcomingAdapter.notifyDataSetChanged();
                }, error -> Timber.e(error))
        );
    }

    /**
     * Enable/Disable display loading data.
     *
     * @param enabled boolean
     */
    private void loading(final boolean enabled) {
        mDataSwipeRefresh.setRefreshing(false);
        if (!enabled) {
            itShouldLoadMore = true;
            return;
        }
        itShouldLoadMore = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCompositeDisposable.dispose();
        mUnbinder.unbind();
    }
}
