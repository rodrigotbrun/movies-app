package br.com.newestapps.movie.data.datasource;

import android.content.Context;

import br.com.newestapps.movie.App;
import br.com.newestapps.movie.data.BaseRepository;
import br.com.newestapps.movie.data.repositories.MovieRepository;
import br.com.newestapps.movie.entities.Movie;
import br.com.newestapps.movie.entities.PagedResult;
import br.com.newestapps.movie.entities.Trailer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

public class ApiMovieDatasource extends BaseRepository<Movie>
        implements MovieRepository {

    public ApiMovieDatasource(Context context) {
        super(context);
    }

    @Override
    public Observable<PagedResult<Movie>> getNowPlaying() {
        return Observable.create(new Observable.OnSubscribe<PagedResult<Movie>>() {
            @Override
            public void call(final Subscriber<? super PagedResult<Movie>> subscriber) {

                App.api().getNowPlaying().enqueue(new Callback<PagedResult<Movie>>() {
                    @Override
                    public void onResponse(Call<PagedResult<Movie>> call, Response<PagedResult<Movie>> response) {
                        subscriber.onNext(response.body());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onFailure(Call<PagedResult<Movie>> call, Throwable t) {
                        subscriber.onError(t);
                        subscriber.onCompleted();
                    }
                });

            }
        });
    }

    @Override
    public Observable<PagedResult<Movie>> getTopRated() {
        return Observable.create(new Observable.OnSubscribe<PagedResult<Movie>>() {
            @Override
            public void call(final Subscriber<? super PagedResult<Movie>> subscriber) {
                App.api().getTopRated().enqueue(new Callback<PagedResult<Movie>>() {
                    @Override
                    public void onResponse(Call<PagedResult<Movie>> call, Response<PagedResult<Movie>> response) {
                        subscriber.onNext(response.body());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onFailure(Call<PagedResult<Movie>> call, Throwable t) {
                        subscriber.onError(t);
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    @Override
    public Observable<PagedResult<Movie>> getPopulars() {
        return Observable.create(new Observable.OnSubscribe<PagedResult<Movie>>() {
            @Override
            public void call(final Subscriber<? super PagedResult<Movie>> subscriber) {
                App.api().getPopulars().enqueue(new Callback<PagedResult<Movie>>() {
                    @Override
                    public void onResponse(Call<PagedResult<Movie>> call, Response<PagedResult<Movie>> response) {
                        subscriber.onNext(response.body());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onFailure(Call<PagedResult<Movie>> call, Throwable t) {
                        subscriber.onError(t);
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    @Override
    public Observable<Movie> getMovie(final int movieId) {
        return Observable.create(new Observable.OnSubscribe<Movie>() {
            @Override
            public void call(final Subscriber<? super Movie> subscriber) {
                App.api().getMovie(movieId).enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        subscriber.onNext(response.body());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        subscriber.onError(t);
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    @Override
    public Observable<Trailer> getMovieTrailers(final int movieId) {
        return Observable.create(new Observable.OnSubscribe<Trailer>() {
            @Override
            public void call(final Subscriber<? super Trailer> subscriber) {
                App.api().getMovieTrailers(movieId).enqueue(new Callback<Trailer>() {
                    @Override
                    public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                        subscriber.onNext(response.body());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onFailure(Call<Trailer> call, Throwable t) {
                        subscriber.onError(t);
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

}
