package com.maxidev.moviehub.common.data.remote

import com.maxidev.moviehub.common.data.remote.dto.CastingDto
import com.maxidev.moviehub.common.data.remote.dto.GenresDto
import com.maxidev.moviehub.common.data.remote.dto.MovieDetailDto
import com.maxidev.moviehub.common.data.remote.dto.MovieImageDto
import com.maxidev.moviehub.common.data.remote.dto.MoviesDto
import com.maxidev.moviehub.common.data.remote.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

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

    @GET(DETAIL)
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): MovieDetailDto

    @GET(SEARCH)
    suspend fun getSearch(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean = false
    ): SearchDto

    @GET(GENRES)
    suspend fun getGenres(): GenresDto

    @GET(IMAGES)
    suspend fun getImages(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en"
    ): MovieImageDto

    @GET(CREDITS)
    suspend fun getCredits(@Path("movie_id") movieId: Int): CastingDto

    @GET(COLLECTION_ID)
    suspend fun getCollectionId(@Path("collection_id") collectionId: Int)
}

private const val MOVIES = "movie/"
private const val NOW_PLAYING = "now_playing"
private const val POPULAR = "popular"
private const val TOP_RATED = "top_rated"
private const val UPCOMING = "upcoming"
private const val TRENDING = "trending/"
private const val SEARCH = "search/movie"
private const val DETAIL = "movie/{movie_id}"
private const val DAY = "day"
private const val GENRES = "genre/movie/list"
private const val IMAGES = "movie/{movie_id}/images"
private const val CREDITS = "movie/{movie_id}/credits"
private const val COLLECTION_ID = "collection/{collection_id}"