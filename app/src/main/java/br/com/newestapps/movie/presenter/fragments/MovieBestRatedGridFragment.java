package br.com.newestapps.movie.presenter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import br.com.newestapps.movie.App;
import br.com.newestapps.movie.R;
import br.com.newestapps.movie.entities.Movie;
import br.com.newestapps.movie.entities.PagedResult;
import br.com.newestapps.movie.events.ChangeFragment;
import br.com.newestapps.movie.presenter.activities.MovieDetailsActivity;
import br.com.newestapps.movie.presenter.activities.adapters.MovieGridViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieBestRatedGridFragment extends Fragment {

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

    public MovieBestRatedGridFragment() {

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

        App.api().getTopRated().enqueue(new Callback<PagedResult<Movie>>() {
            @Override
            public void onResponse(Call<PagedResult<Movie>> call, Response<PagedResult<Movie>> response) {
                if (response.body() != null) {
                    movies = response.body().getResults();

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
            }

            @Override
            public void onFailure(Call<PagedResult<Movie>> call, Throwable t) {

            }
        });
    }

    public static MovieBestRatedGridFragment newInstance() {
        MovieBestRatedGridFragment frament = new MovieBestRatedGridFragment();
        return frament;
    }
}
