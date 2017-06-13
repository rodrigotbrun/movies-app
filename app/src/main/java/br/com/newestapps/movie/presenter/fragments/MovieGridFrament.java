package br.com.newestapps.movie.presenter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import br.com.newestapps.movie.App;
import br.com.newestapps.movie.R;
import br.com.newestapps.movie.entities.Movie;
import br.com.newestapps.movie.events.ChangeFragment;
import br.com.newestapps.movie.presenter.activities.adapters.MovieGridViewAdapter;

public class MovieGridFrament extends Fragment {

    ///////////////////////////////////////////////////////////////////////////
    // Views
    ///////////////////////////////////////////////////////////////////////////

    GridView movieGrid;

    ///////////////////////////////////////////////////////////////////////////
    // Fields
    ///////////////////////////////////////////////////////////////////////////

    private List<Movie> movies;

    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////

    public MovieGridFrament() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_grid, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieGrid = (GridView) view.findViewById(R.id.movieGrid);

        if (movies != null && movies.size() > 0) {
            MovieGridViewAdapter adapter = new MovieGridViewAdapter(getContext(), movies);
            movieGrid.setAdapter(adapter);

            movieGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Movie movie = movies.get(position);
                    App.bus().post(new ChangeFragment(MovieDetailFrament.newInstance(movie)));
                }
            });

        }
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public static MovieGridFrament newInstance(List<Movie> movies) {
        MovieGridFrament frament = new MovieGridFrament();
        frament.setMovies(movies);

        return frament;
    }
}
