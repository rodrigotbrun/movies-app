package br.com.newestapps.movie.data.datasource;

import android.content.Context;

import br.com.newestapps.movie.data.repositories.MovieRepository;

public class LocalMovieDatasource extends ApiMovieDatasource
        implements MovieRepository {

    public LocalMovieDatasource(Context context) {
        super(context);
    }

}
