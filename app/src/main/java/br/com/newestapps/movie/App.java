package br.com.newestapps.movie;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import br.com.newestapps.movie.apis.themoviedb.ApiServiceGenerator;
import br.com.newestapps.movie.apis.themoviedb.TheMovieDbAPI;

public class App extends Application {

    private static TheMovieDbAPI api;
    private static Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        ApiServiceGenerator.init(this);
        api = ApiServiceGenerator.createService(this, TheMovieDbAPI.class);
        bus = new Bus(ThreadEnforcer.ANY);
    }

    public static TheMovieDbAPI api() {
        return api;
    }

    public static Bus bus(){
        return bus;
    }

}
