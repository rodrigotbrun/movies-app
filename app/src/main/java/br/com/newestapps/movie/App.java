package br.com.newestapps.movie;

import android.app.Application;

import br.com.newestapps.movie.apis.themoviedb.ApiServiceGenerator;
import br.com.newestapps.movie.apis.themoviedb.TheMovieDbAPI;

public class App extends Application {

    private static TheMovieDbAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        ApiServiceGenerator.init(this);
        api = ApiServiceGenerator.createService(this, TheMovieDbAPI.class);
    }

    public static TheMovieDbAPI api() {
        return api;
    }

}
