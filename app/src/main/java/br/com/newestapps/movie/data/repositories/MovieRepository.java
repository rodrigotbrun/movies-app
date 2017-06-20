package br.com.newestapps.movie.data.repositories;

import br.com.newestapps.movie.entities.Movie;
import br.com.newestapps.movie.entities.PagedResult;
import br.com.newestapps.movie.entities.Trailer;
import rx.Observable;

public interface MovieRepository {

    Observable<PagedResult<Movie>> getNowPlaying();

    Observable<PagedResult<Movie>> getTopRated();

    Observable<PagedResult<Movie>> getPopulars();

    Observable<Movie> getMovie(int movieId);

    Observable<Trailer> getMovieTrailers(int movieId);

}
