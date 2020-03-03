package br.com.training.movie_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class HomeFragment extends Fragment {

    private MovieManagerNetRepository movieManagerNetRepository;
    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieManagerNetRepository = MovieManagerNetRepository.getInstance(getActivity().getApplicationContext());
        compositeDisposable = new CompositeDisposable();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home, container, false);

//        compositeDisposable.add(movieManagerNetRepository
//                .getPopularMovies().subscribe());

        return layout;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        compositeDisposable.dispose();
    }
}
