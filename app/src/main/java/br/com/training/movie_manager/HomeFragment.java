package br.com.training.movie_manager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;

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

    private static final String TAG = "HomeFragment";

    private MovieManagerNetRepository movieManagerNetRepository;
    private CompositeDisposable compositeDisposable;
    private Unbinder unbinder;

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
                    Movie featuredMovie = movies.getResults().get(0);

                    String imgFeaturedMovieUri = "https://image.tmdb.org/t/p/original"
                            + featuredMovie.getBackdrop_path();


                    Glide.with(this)
                            .load(imgFeaturedMovieUri)
                            .into(imgViewFeaturedMovie);
                    Log.w(TAG, featuredMovie.toJson());
                }, error -> {
                    Log.w(TAG, error);
                }));
    }
}
