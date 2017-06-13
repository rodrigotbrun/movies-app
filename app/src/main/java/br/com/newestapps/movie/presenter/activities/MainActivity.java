package br.com.newestapps.movie.presenter.activities;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

import com.squareup.otto.Subscribe;

import br.com.newestapps.movie.App;
import br.com.newestapps.movie.R;
import br.com.newestapps.movie.entities.Movie;
import br.com.newestapps.movie.entities.PagedResult;
import br.com.newestapps.movie.events.ChangeFragment;
import br.com.newestapps.movie.presenter.fragments.MovieGridFrament;
import br.com.newestapps.movie.support.activities.NetworkActivity;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindLayout;
import io.github.sporklibrary.android.annotations.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@BindLayout(R.layout.activity_main)
public class MainActivity extends NetworkActivity {

    ///////////////////////////////////////////////////////////////////////////
    // Views
    ///////////////////////////////////////////////////////////////////////////

    @BindView(R.id.noInternetConnection)
    LinearLayout noInternetConnection;

    @BindView(R.id.contentContainer)
    LinearLayout contentContainer;

    ///////////////////////////////////////////////////////////////////////////
    // Fields
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spork.bind(this);
        App.bus().register(this);
    }

    private void loadPlayingNow() {
        App.api().getNowPlaying().enqueue(new Callback<PagedResult<Movie>>() {
            @Override
            public void onResponse(Call<PagedResult<Movie>> call, Response<PagedResult<Movie>> response) {
                if (response.body() != null) {
                    renderReusableFragment(R.id.content, MovieGridFrament.newInstance(response.body().getResults()));
                }
            }


            @Override
            public void onFailure(Call<PagedResult<Movie>> call, Throwable t) {

            }
        });
    }

    @Subscribe
    public void onRequestFragmentChange(ChangeFragment event) {
        renderReusableFragment(R.id.content, event.getFragment(), true);
    }

    @Override
    protected void onCreateIsConnected() {
        loadPlayingNow();
    }

    @Override
    protected void onConnectionLost() {
        noInternetConnection.setVisibility(View.VISIBLE);
        contentContainer.setVisibility(View.GONE);
    }

    @Override
    protected void onConnectionRestart() {
        loadPlayingNow();
        noInternetConnection.setVisibility(View.GONE);
        contentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onConnectionTypeChanged(int connectionType) {

    }

}
