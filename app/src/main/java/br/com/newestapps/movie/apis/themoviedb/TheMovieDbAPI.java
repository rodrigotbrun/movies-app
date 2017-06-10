package br.com.newestapps.movie.apis.themoviedb;

import br.com.newestapps.movie.entities.Movie;
import br.com.newestapps.movie.entities.PagedResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDbAPI {

    /**
     * Filmes que estão em cartaz
     */
    @GET("/3/movie/now_playing")
    Call<PagedResult<Movie>> getNowPlaying();

    /**
     * Filmes mais assistidos e avaliados
     */
    @GET("/3/movie/top_rated")
    Call<PagedResult<Movie>> getTopRated();

    /**
     * Detalhes de um filme
     */
    @GET("/3/movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") int movieId);

    /**
     * Trailers de um filme
     */
    @GET("/3/movie/{movie_id}/videos")
    Call<Movie> getMovieTrailers(@Path("movie_id") int movieId);

}
