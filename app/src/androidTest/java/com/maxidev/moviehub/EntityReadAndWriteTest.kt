package com.maxidev.moviehub

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.maxidev.moviehub.common.data.local.AppDataBase
import com.maxidev.moviehub.common.data.local.dao.MovieDao
import com.maxidev.moviehub.common.data.local.entity.BelongsToCollectionEntity
import com.maxidev.moviehub.common.data.local.entity.MovieEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class EntityReadAndWriteTest {

    private lateinit var dao: MovieDao
    private lateinit var db: AppDataBase

    @Before
    fun createDb() {
        val dispatcher = UnconfinedTestDispatcher()
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context, AppDataBase::class.java
        ).build()

        dao = db.movieDao()

        Dispatchers.setMain(dispatcher)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
        Dispatchers.resetMain()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadMovieById() = runBlocking {

        dao.saveMovie(movie1)

        val byId = dao.getMovieById(4036)

        assertEquals(byId.first().id, movie1.id)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndDeleteMovie() = runBlocking {

        dao.saveMovie(movie1)
        dao.saveMovie(movie2)

        val deleteMovieOne = dao.getMovieById(4036).first()

        dao.deleteMovie(deleteMovieOne)

        val allMovies = dao.getMovies().first()
        val deletedMovie = dao.getMovieById(4036).firstOrNull()

        assertEquals(allMovies.size, 1)
        assertNull(deletedMovie)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllMovies() = runBlocking {

        dao.saveMovie(movie1)
        dao.saveMovie(movie2)

        val allMovies = dao.getMovies().first()

        dao.clearAll()

        val allMoviesAfterDelete = dao.getMovies().first()

        assertEquals(allMovies.size, 2)
        assertEquals(allMoviesAfterDelete.size, 0)
        assertEquals(allMoviesAfterDelete, emptyList<MovieEntity>())
    }

    companion object {
        val movie1 = MovieEntity(
            id = 4036,
            title = "errem",
            posterPath = "no",
            backdropPath = "et",
            overview = "ludus",
            voteAverage = 2.3,
            tagline = "vim",
            releaseDate = "venenatis",
            status = "malorum",
            runtime = 1523,
            genres = listOf(),
            productionCompanies = listOf(),
            belongsToCollection = BelongsToCollectionEntity(
                collectionId = 3694,
                name = "Ira Stewart",
                collectionPosterPath = "neque"
            ),
            favorite = false,
            homePage = "molestiae"
        )

        val movie2 = MovieEntity(
            id = 4234,
            title = "eros",
            posterPath = "quidam",
            backdropPath = "prompta",
            overview = "doming",
            voteAverage = 6.7,
            tagline = "accumsan",
            releaseDate = "vidisse",
            status = "ridiculus",
            runtime = 8601,
            genres = listOf(),
            productionCompanies = listOf(),
            belongsToCollection = BelongsToCollectionEntity(
                collectionId = 5008,
                name = "Maynard Hendrix",
                collectionPosterPath = "iudicabit"
            ),
            favorite = false,
            homePage = "accusata"
        )
    }
}