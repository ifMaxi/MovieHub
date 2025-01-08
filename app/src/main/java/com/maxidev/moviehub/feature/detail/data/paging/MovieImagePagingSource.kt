package com.maxidev.moviehub.feature.detail.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maxidev.moviehub.common.data.remote.ApiService
import com.maxidev.moviehub.common.utils.Constants.PAGE
import com.maxidev.moviehub.feature.detail.domain.mapper.asExternal
import com.maxidev.moviehub.feature.detail.domain.model.MovieImage
import retrofit2.HttpException
import java.io.IOException

class MovieImagePagingSource(
    private val apiService: ApiService,
    private val movieId: Int
): PagingSource<Int, MovieImage>() {

    override fun getRefreshKey(state: PagingState<Int, MovieImage>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieImage> {
        return try {
            val nextPage = params.key ?: PAGE
            val response = apiService.getImages(movieId)
            val prevKey = if (nextPage == 1) null else nextPage - 1
            val nextKey = if (response.backdrops.isNullOrEmpty()) null else nextPage + 1

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