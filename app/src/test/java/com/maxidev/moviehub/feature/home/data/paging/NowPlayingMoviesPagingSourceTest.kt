package com.maxidev.moviehub.feature.home.data.paging

import androidx.paging.PagingSource
import com.maxidev.moviehub.common.data.remote.ApiService
import com.maxidev.moviehub.common.data.remote.dto.MoviesDto
import com.maxidev.moviehub.feature.home.domain.model.Movies
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NowPlayingMoviesPagingSourceTest {

    @MockK
    lateinit var api: ApiService

    private lateinit var nowPlayingMoviesPagingSource: NowPlayingMoviesPagingSource

    companion object {
        val responseOne = MoviesDto(
            page = null, results = listOf(
                MoviesDto.Result(
                    id = 1012,
                    posterPath = "image",
                    backdropPath = "backdrop",
                    title = "title"
                )
            )
        )
        val responseTwo = Movies(
            id = 2074, posterPath = "dicta", backdropPath = "gubergren", title = "tristique"
        )
    }

    @Before
    fun setUp() {
        val dispatcher = UnconfinedTestDispatcher()

        MockKAnnotations.init(this)
        nowPlayingMoviesPagingSource = NowPlayingMoviesPagingSource(api)

        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Fail case
    @Test
    fun `paging source load - failure - http error`() = runBlocking {
        val error = RuntimeException("404", Throwable())
            coEvery { api.getNowPlayingMovies(any()) } throws error

        val expectedResult = PagingSource.LoadResult.Error<Int, Movies>(error)

        assertEquals(
            expectedResult, nowPlayingMoviesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    // Success case
    @Test
    fun `paging source refresh - success`() = runBlocking {

        coEvery { api.getNowPlayingMovies(any()) } returns responseOne

        val expectedResult = PagingSource.LoadResult.Page(
            data = responseOne.results?.map {
                Movies(
                    it?.id ?: 0,
                    it?.posterPath ?: "",
                    it?.backdropPath ?: "",
                    it?.title ?: ""
                )
            }.orEmpty(),
            prevKey = -1,
            nextKey = 1
        )

        assertEquals(
            expectedResult, nowPlayingMoviesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `paging source append - success`() = runBlocking {

        coEvery { api.getNowPlayingMovies(any()) } returns responseOne

        val expectedResult = PagingSource.LoadResult.Page(
            data = responseOne.results?.map {
                Movies(
                    it?.id ?: 0,
                    it?.posterPath ?: "",
                    it?.backdropPath ?: "",
                    it?.title ?: ""
                )
            }.orEmpty(),
            prevKey = -1,
            nextKey = 1
        )

        assertEquals(
            expectedResult, nowPlayingMoviesPagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `paging source prepend - success`() = runBlocking {

        coEvery { api.getNowPlayingMovies(any()) } returns responseOne

        val expectedResult = PagingSource.LoadResult.Page(
            data = responseOne.results?.map {
                Movies(
                    it?.id ?: 0,
                    it?.posterPath ?: "",
                    it?.backdropPath ?: "",
                    it?.title ?: ""
                )
            }.orEmpty(),
            prevKey = -1,
            nextKey = 1
        )

        assertEquals(
            expectedResult, nowPlayingMoviesPagingSource.load(
                PagingSource.LoadParams.Prepend(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
}