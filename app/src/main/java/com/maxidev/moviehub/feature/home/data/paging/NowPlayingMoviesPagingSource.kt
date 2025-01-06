package com.maxidev.moviehub.feature.home.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maxidev.moviehub.common.data.remote.ApiService
import com.maxidev.moviehub.common.utils.Constants.PAGE
import com.maxidev.moviehub.feature.home.domain.mappers.asExternal
import com.maxidev.moviehub.feature.home.domain.model.Movies
import retrofit2.HttpException
import java.io.IOException

class NowPlayingMoviesPagingSource(
    private val apiService: ApiService
): PagingSource<Int, Movies>() {

    override fun getRefreshKey(state: PagingState<Int, Movies>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies> {
        return try {
            val nextPage = params.key ?: PAGE
            val response = apiService.getNowPlayingMovies(page = nextPage)
            val prevKey = if (nextPage == 1) null else nextPage - 1
            val nextKey = if (response.results.isNullOrEmpty()) null else nextPage + 1

            LoadResult.Page(
                data = response.asExternal() ?: emptyList(),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (ioException: IOException) {
            LoadResult.Error(ioException)
        } catch (httpException: HttpException) {
            LoadResult.Error(httpException)
        }
    }
}