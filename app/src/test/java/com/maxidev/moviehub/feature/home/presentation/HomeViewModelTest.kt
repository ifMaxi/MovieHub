package com.maxidev.moviehub.feature.home.presentation

import com.maxidev.moviehub.feature.home.domain.model.Genres
import com.maxidev.moviehub.feature.home.domain.repository.HomeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val repository: HomeRepository = mockk(relaxed = true)

    companion object {
        val genres = listOf(
            Genres(id = 0, name = "Action"),
            Genres(id = 1, name = "Comedy"),
            Genres(id = 2, name = "Drama"),
            Genres(id = 3, name = "Terror")
        )
    }

    @Before
    fun setUp() {
        val dispatcher = UnconfinedTestDispatcher()
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(repository)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch genres`() = runBlocking {

        coEvery { repository.fetchGenres() } returns genres

        viewModel.getGenres()

        coVerify(exactly = 2) { repository.fetchGenres() }

        val result = viewModel.homeState.value.genres

        assertEquals(true, result.containsAll(genres))
    }
}