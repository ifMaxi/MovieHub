package com.maxidev.moviehub.feature.search.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maxidev.moviehub.common.data.remote.ApiService
import com.maxidev.moviehub.common.utils.Constants.PAGE
import com.maxidev.moviehub.feature.search.domain.mapper.asExternal
import com.maxidev.moviehub.feature.search.domain.model.Search
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(
    private val apiService: ApiService,
    private val query: String
):PagingSource<Int, Search>() {

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        return try {
            val nextPage = params.key ?: PAGE
            val response = apiService.getSearch(query = query, page = nextPage)
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