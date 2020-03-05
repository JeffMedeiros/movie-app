package br.com.training.movie_manager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Class for Home fragment.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private MovieManagerNetRepository movieManagerNetRepository;
    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieManagerNetRepository = MovieManagerNetRepository.getInstance(getActivity().getApplicationContext());
        Log.w(TAG, movieManagerNetRepository.toString());
        compositeDisposable = new CompositeDisposable();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home, container, false);

//        ImageView imgViewFeaturedMovie = getActivity().findViewById(R.id.viewFeaturedMovie);

//        Object moviesReturned;

        compositeDisposable.add(movieManagerNetRepository
                .getPopularMovies(MainActivity.API_KEY)
                .subscribe(movies -> {
                    Log.w(TAG, movies.toString());
                }, error -> {
                    Log.w(TAG, error);
                }));

        return layout;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        compositeDisposable.dispose();
    }
}
