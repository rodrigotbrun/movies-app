package br.com.newestapps.movie.presenter.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.squareup.otto.Subscribe;

import br.com.newestapps.movie.App;
import br.com.newestapps.movie.R;
import br.com.newestapps.movie.events.ChangeFragment;
import br.com.newestapps.movie.presenter.fragments.MovieBestRatedGridFragment;
import br.com.newestapps.movie.presenter.fragments.MoviePlayingGridFragment;
import br.com.newestapps.movie.presenter.fragments.MoviePopularsGridFragment;
import br.com.newestapps.movie.support.activities.NetworkActivity;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindLayout;
import io.github.sporklibrary.android.annotations.BindView;

@BindLayout(R.layout.activity_main)
public class MainActivity extends NetworkActivity {

    ///////////////////////////////////////////////////////////////////////////
    // Views
    ///////////////////////////////////////////////////////////////////////////

    @BindView(R.id.noInternetConnection)
    LinearLayout noInternetConnection;

    @BindView(R.id.contentContainer)
    LinearLayout contentContainer;

    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;

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

        setupBottomNavigationBar();
    }

    private void setupBottomNavigationBar() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.moviesPopular: // Mais Populares
                        fragment = MoviePopularsGridFragment.newInstance();
                        break;
                    case R.id.moviesBestRated: // Melhores Avaliados
                        fragment = MovieBestRatedGridFragment.newInstance();
                        break;
                    case R.id.moviesPlaying: // Em Cartaz
                    default:
                        fragment = MoviePlayingGridFragment.newInstance();
                }

                renderReusableFragment(R.id.content, fragment);
                return true;
            }
        });

        bottomNavigation.setSelectedItemId(R.id.moviesPlaying);
    }

    @Subscribe
    public void onRequestFragmentChange(ChangeFragment event) {
        renderReusableFragment(R.id.content, event.getFragment(), true);
    }

    @Override
    protected void onCreateIsConnected() {
//        loadPlayingNow();
    }

    @Override
    protected void onConnectionLost() {
        noInternetConnection.setVisibility(View.VISIBLE);
        contentContainer.setVisibility(View.GONE);
    }

    @Override
    protected void onConnectionRestart() {
//        loadPlayingNow();
        noInternetConnection.setVisibility(View.GONE);
        contentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onConnectionTypeChanged(int connectionType) {

    }

}
