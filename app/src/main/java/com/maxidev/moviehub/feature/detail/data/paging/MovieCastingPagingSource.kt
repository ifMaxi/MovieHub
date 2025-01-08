package com.maxidev.moviehub.feature.detail.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maxidev.moviehub.common.data.remote.ApiService
import com.maxidev.moviehub.common.utils.Constants.PAGE
import com.maxidev.moviehub.feature.detail.domain.mapper.asExternal
import com.maxidev.moviehub.feature.detail.domain.model.Casting
import retrofit2.HttpException
import java.io.IOException

class MovieCastingPagingSource(
    private val apiService: ApiService,
    private val movieId: Int
): PagingSource<Int, Casting>() {

    override fun getRefreshKey(state: PagingState<Int, Casting>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Casting> {
        return try {
            val nextPage = params.key ?: PAGE
            val response = apiService.getCredits(movieId)
            val prevKey = if (nextPage == 1) null else nextPage - 1
            val nextKey = if (response.cast.isNullOrEmpty()) null else nextPage + 1

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