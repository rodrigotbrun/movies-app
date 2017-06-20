package br.com.newestapps.movie.presenter.activities;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import br.com.newestapps.movie.App;
import br.com.newestapps.movie.Config;
import br.com.newestapps.movie.R;
import br.com.newestapps.movie.data.BaseRepository;
import br.com.newestapps.movie.data.repositories.MovieRepository;
import br.com.newestapps.movie.entities.Genre;
import br.com.newestapps.movie.entities.Movie;
import br.com.newestapps.movie.entities.ProductionCompany;
import br.com.newestapps.movie.events.ChangeFragment;
import br.com.newestapps.movie.support.activities.NetworkActivity;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindLayout;
import io.github.sporklibrary.android.annotations.BindView;
import retrofit2.Call;
import retrofit2.Response;
import rx.Subscriber;

@BindLayout(R.layout.activity_movie_details)
public class MovieDetailsActivity extends NetworkActivity {

    private static final String KEY_MOVIE = "movieObject";

    ///////////////////////////////////////////////////////////////////////////
    // Views
    ///////////////////////////////////////////////////////////////////////////

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.moviePoster)
    ImageView moviePoster;

    @BindView(R.id.movieBackdrop)
    ImageView movieBackdrop;

    @BindView(R.id.movieTitle)
    AppCompatTextView movieTitle;

    @BindView(R.id.movieRate)
    TextView movieRate;

    @BindView(R.id.movieGenders)
    AppCompatTextView movieGenders;

    @BindView(R.id.movieRuntime)
    AppCompatTextView movieRuntime;

    @BindView(R.id.movieOverview)
    TextView movieOverview;

    @BindView(R.id.movieProducers)
    TextView movieProducers;

    @BindView(R.id.movieProducersTitle)
    TextView movieProducersTitle;

    @BindView(R.id.circularProgress)
    CircularProgressView circularProgress;

    @BindView(R.id.movieDetailsContainer)
    LinearLayout movieDetailsContainer;

    ///////////////////////////////////////////////////////////////////////////
    // Fields
    ///////////////////////////////////////////////////////////////////////////

    private Movie movie;
    private MovieRepository movieRepository;
    private Config config;

    private int CONTENT_ANIMATION_DURATION = 1200;

    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spork.bind(this);
        App.bus().register(this);
        config = new Config(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        if (b.containsKey(KEY_MOVIE)) {
            final String movieObject = b.getString(KEY_MOVIE, null);
            movie = new Gson().fromJson(movieObject, Movie.class);

            movieRepository = (MovieRepository)
                    BaseRepository.getProperRepository(this, MovieRepository.class);

            loadMovieDetails();
        } else {
            finish();
        }
    }

    private void loadMovieDetails() {
        final String backdropImgUrl = config.posterImgBaseUrl + movie.getBackdropPath();

        Picasso.with(this)
                .load(backdropImgUrl)
                .into(new PalettePicassoHandler(movieBackdrop, true, new Runnable() {
                    @Override
                    public void run() {
                        loadingState(true);
                        movieRepository.getMovie(movie.getId()).subscribe(movieGetDetailsSubscriber);
                    }
                }));
    }

    private void loadingState(boolean loadingContent /* ? */) {
        if (loadingContent) {
            circularProgress.animate()
                    .alpha(100)
                    .setDuration(500)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            movieDetailsContainer.animate()
                                    .alpha(0)
                                    .setDuration(CONTENT_ANIMATION_DURATION);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });

        } else {
            circularProgress.animate()
                    .alpha(0)
                    .setDuration(500)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            movieDetailsContainer.animate()
                                    .alpha(100)
                                    .setDuration(CONTENT_ANIMATION_DURATION);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
        }
    }

    private Subscriber<? super Movie> movieGetDetailsSubscriber = new Subscriber<Movie>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();

            new MaterialDialog.Builder(MovieDetailsActivity.this)
                    .content(e.getMessage())
                    .positiveText("OK")
                    .neutralText("Tentar Novamente")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();
                        }
                    })
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            loadMovieDetails();
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

        @Override
        public void onNext(Movie m) {
            loadingState(false);

            movie = m;
            renderView();
        }
    };


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void stylizeForLollipop(int activityColor) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(activityColor);
    }

    private void changeToolbarColors(int color, int colorDark, int title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stylizeForLollipop(alterColor(color, (float) 0.70));
        }

        toolbar.setBackgroundColor(color);
        toolbar.setTitleTextColor(title);
    }

    private void renderView() {
        final String posterImgUrl = config.posterImgBaseUrl + movie.getPosterPath();

        toolbar.setTitle(movie.getTitle());
        movieTitle.setText(movie.getTitle());
        movieRate.setText(String.format("%.1f", movie.getVoteAverage()));
        movieOverview.setText(movie.getOverview());

        if (movie.getRuntime() > 0) {
            movieRuntime.setText(movie.getRuntime() + " min");
            movieRuntime.setVisibility(View.VISIBLE);
        } else {
            movieRuntime.setVisibility(View.GONE);
        }

        if (movie.getGenres() != null && movie.getGenres().size() > 0) {
            String[] gendersNames = new String[movie.getGenres().size()];

            int i = 0;
            for (Genre g : movie.getGenres()) {
                gendersNames[i] = g.getName();
                i++;
            }

            String genders = implode(", ", gendersNames);

            movieGenders.setVisibility(View.VISIBLE);
            movieGenders.setText(genders);
        } else {
            movieGenders.setVisibility(View.GONE);
        }

        if (movie.getProductionCompanies() != null && movie.getProductionCompanies().size() > 0) {
            String[] producersNames = new String[movie.getProductionCompanies().size()];

            int i2 = 0;
            for (ProductionCompany g : movie.getProductionCompanies()) {
                producersNames[i2] = g.getName();
                i2++;
            }

            String producers = implode(", ", producersNames);

            movieProducers.setVisibility(View.VISIBLE);
            movieProducersTitle.setVisibility(View.VISIBLE);
            movieProducers.setText(producers);
        } else {
            movieProducers.setVisibility(View.GONE);
            movieProducersTitle.setVisibility(View.GONE);
        }

        Picasso.with(this)
                .load(posterImgUrl)
                .into(new PalettePicassoHandler(moviePoster, false, null));
    }

    @Subscribe
    public void onRequestFragmentChange(ChangeFragment event) {
        renderReusableFragment(R.id.content, event.getFragment(), true);
    }

    @Override
    protected void onCreateIsConnected() {
    }

    @Override
    protected void onConnectionLost() {
    }

    @Override
    protected void onConnectionRestart() {
    }

    @Override
    protected void onConnectionTypeChanged(int connectionType) {

    }

    ///////////////////////////////////////////////////////////////////////////
    // Statics
    ///////////////////////////////////////////////////////////////////////////

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);

        String movieObject = new Gson().toJson(movie);
        intent.putExtra(MovieDetailsActivity.KEY_MOVIE, movieObject);

        return intent;
    }

    private class PalettePicassoHandler implements Target {

        private ImageView where;
        private boolean callAdaptUIColors = false;
        private Runnable runAfter;

        public PalettePicassoHandler(ImageView where, boolean callAdaptUIColors, Runnable runAfter) {
            this.where = where;
            this.callAdaptUIColors = callAdaptUIColors;
            this.runAfter = runAfter;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            where.setImageBitmap(bitmap);

            Palette.from(bitmap)
                    .generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {

                            if (runAfter != null) {
                                runAfter.run();
                            }

                            if (callAdaptUIColors) {
                                changeToolbarColors(
                                        palette.getVibrantColor(Color.parseColor("#D32F2F")),
                                        palette.getVibrantColor(Color.parseColor("#D32F2F")),
                                        Color.parseColor("#FFFFFF"));
                            }
                        }
                    });

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }

    }

    public static int alterColor(int color, float factor) {
        int a = (color & (0xFF << 24)) >> 24;
        int r = (int) (((color & (0xFF << 16)) >> 16) * factor);
        int g = (int) (((color & (0xFF << 8)) >> 8) * factor);
        int b = (int) ((color & 0xFF) * factor);
        return Color.argb(a, r, g, b);
    }

    public static String implode(String separator, String... data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length - 1; i++) {
            //data.length - 1 => to not add separator at the end
            if (!data[i].matches(" *")) {//empty string are ""; " "; "  "; and so on
                sb.append(data[i]);
                sb.append(separator);
            }
        }
        sb.append(data[data.length - 1].trim());
        return sb.toString();
    }
}
