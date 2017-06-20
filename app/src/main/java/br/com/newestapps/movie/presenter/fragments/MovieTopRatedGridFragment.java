package br.com.newestapps.movie.presenter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import br.com.newestapps.movie.R;
import br.com.newestapps.movie.data.BaseRepository;
import br.com.newestapps.movie.data.repositories.MovieRepository;
import br.com.newestapps.movie.entities.Movie;
import br.com.newestapps.movie.entities.PagedResult;
import br.com.newestapps.movie.presenter.activities.MovieDetailsActivity;
import br.com.newestapps.movie.presenter.activities.adapters.MovieGridViewAdapter;
import rx.Subscriber;

public class MovieTopRatedGridFragment extends Fragment {

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

    public MovieTopRatedGridFragment() {

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

        MovieRepository movieRepository = (MovieRepository) BaseRepository.getProperRepository(
                getContext(), MovieRepository.class);

        movieRepository.getTopRated().subscribe(fetchSubscriber);
    }

    private Subscriber<PagedResult<Movie>> fetchSubscriber = new Subscriber<PagedResult<Movie>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            new MaterialDialog.Builder(getContext())
                    .content(e.getMessage())
                    .show();
        }

        @Override
        public void onNext(PagedResult<Movie> moviePagedResult) {
            movies = moviePagedResult.getResults();

            if (movies != null && movies.size() > 0) {
                MovieGridViewAdapter adapter = new MovieGridViewAdapter(getContext(), movies);
                movieGrid.setAdapter(adapter);

                movieGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Movie movie = movies.get(position);
                        startActivity(MovieDetailsActivity.newIntent(getContext(), movie));
                    }
                });
            }
        }

    };

    public static MovieTopRatedGridFragment newInstance() {
        MovieTopRatedGridFragment frament = new MovieTopRatedGridFragment();
        return frament;
    }
}
