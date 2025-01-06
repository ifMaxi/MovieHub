package com.maxidev.moviehub.common.data.remote

import com.maxidev.moviehub.common.data.remote.dto.MovieDetailDto
import com.maxidev.moviehub.common.data.remote.dto.MoviesDto
import com.maxidev.moviehub.common.data.remote.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Movies
    @GET(MOVIES + NOW_PLAYING)
    suspend fun getNowPlayingMovies(@Query("page") page: Int): MoviesDto

    @GET(MOVIES + POPULAR)
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesDto

    @GET(MOVIES + TOP_RATED)
    suspend fun getTopRatedMovies(@Query("page") page: Int): MoviesDto

    @GET(MOVIES + UPCOMING)
    suspend fun getUpcomingMovies(@Query("page") page: Int): MoviesDto

    @GET(TRENDING + MOVIES + DAY)
    suspend fun getTrendingMovies(@Query("page") page: Int): MoviesDto

    @GET(MOVIE_DETAIL)
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): MovieDetailDto

    // Search
    @GET(SEARCH)
    suspend fun getSearch(
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchDto
}

private const val MOVIES = "movie/"
private const val NOW_PLAYING = "now_playing"
private const val POPULAR = "popular"
private const val TOP_RATED = "top_rated"
private const val UPCOMING = "upcoming"
private const val TRENDING = "trending/"
private const val SEARCH = "search/movie"
private const val MOVIE_DETAIL = "movie/{movie_id}"
private const val DAY = "day"