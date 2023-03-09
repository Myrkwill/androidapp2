package ru.myrkwill.app.data.api

import javax.inject.Inject

class NewRepository @Inject constructor(private val service: NewsService) {

    suspend fun getNews(countryCode: String, pageNumber: Int) =
        service.getHeadlines(countryCode, pageNumber)

    suspend fun getSearchNews(query: String, pageNumber: Int) =
        service.getEverything(query, pageNumber)

}