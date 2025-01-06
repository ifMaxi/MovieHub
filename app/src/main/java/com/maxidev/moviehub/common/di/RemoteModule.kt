package com.maxidev.moviehub.common.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.maxidev.moviehub.BuildConfig
import com.maxidev.moviehub.common.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val CONTENT_TYPE = "application/json"
    private const val TIMER_OUT = 15L
    private const val MAX_AGE = 2
    private const val CACHE_SIZE = 50L * 1024L * 1024L
    private const val CACHE_NAME = "http_cache"
    private const val CACHE_HEADER = "Cache-Control"
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val API_KEY = BuildConfig.apiKey

    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providesJsonConfig(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesOkHttp(
        interceptor: Interceptor,
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .readTimeout(TIMER_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIMER_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIMER_OUT, TimeUnit.SECONDS)
            .callTimeout(TIMER_OUT, TimeUnit.SECONDS)

        return httpBuilder.build()
    }

    @Provides
    @Singleton
    fun providesCache(
        @ApplicationContext context: Context
    ): Cache {
        val directory = File(context.cacheDir, CACHE_NAME)

        return Cache(directory = directory, maxSize = CACHE_SIZE)
    }

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor {
        return Interceptor { chain ->
            val cacheControl = CacheControl.Builder()
                .maxAge(MAX_AGE, TimeUnit.HOURS)
                .build()
            val request = chain.request().newBuilder()
                .addHeader(AUTHORIZATION_HEADER, "Bearer $API_KEY")
                .header(CACHE_HEADER, cacheControl.toString())

            chain.proceed(request.build())
        }
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}