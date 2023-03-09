package ru.myrkwill.app.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.myrkwill.app.models.NewsResponse
import ru.myrkwill.app.utils.Constants

interface NewsService {

    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>

    @GET("/v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String = "ru",
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>
}