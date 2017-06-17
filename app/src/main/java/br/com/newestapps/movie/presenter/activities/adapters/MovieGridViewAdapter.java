package br.com.newestapps.movie.presenter.activities.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.newestapps.movie.Config;
import br.com.newestapps.movie.R;
import br.com.newestapps.movie.entities.Movie;

public class MovieGridViewAdapter extends ArrayAdapter<Movie> {

    private List<Movie> movies;
    private Config config;

    public MovieGridViewAdapter(@NonNull Context context, List<Movie> movies) {
        super(context, R.layout.movie_grid_item);
        this.movies = movies;
        this.config = new Config(context);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Nullable
    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.movie_grid_item, null, false);
        Movie movie = getItem(position);

        ImageView poster = (ImageView) view.findViewById(R.id.moviePoster);
        TextView rate = (TextView) view.findViewById(R.id.movieRate);

        final String posterImgUrl = config.posterImgBaseUrl + movie.getPosterPath();

        Log.d("MovieGridViewAdapter", posterImgUrl);

        Picasso.with(getContext())
                .load(posterImgUrl)
                .into(poster, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Log.d("MovieGridViewAdapter", "ERRROOuu!!!");
                    }
                });

        rate.setText(String.format("%.1f", movie.getVoteAverage()));

        return view;
    }
}
