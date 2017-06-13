package br.com.newestapps.movie.presenter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import br.com.newestapps.movie.R;
import br.com.newestapps.movie.entities.Movie;
import br.com.newestapps.movie.presenter.activities.adapters.MovieGridViewAdapter;

public class MovieDetailFrament extends Fragment {

    ///////////////////////////////////////////////////////////////////////////
    // Views
    ///////////////////////////////////////////////////////////////////////////

    private TextView movieTitle;

    ///////////////////////////////////////////////////////////////////////////
    // Fields
    ///////////////////////////////////////////////////////////////////////////

    private Movie movie;

    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////

    public MovieDetailFrament() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieTitle = (TextView) view.findViewById(R.id.movieTitle);

        movieTitle.setText(movie.getTitle());
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public static MovieDetailFrament newInstance(Movie movie) {
        MovieDetailFrament frament = new MovieDetailFrament();
        frament.setMovie(movie);

        return frament;
    }
}
