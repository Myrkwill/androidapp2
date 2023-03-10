package ru.myrkwill.app.data.api

import ru.myrkwill.app.data.db.ArticleDao
import ru.myrkwill.app.models.Article
import javax.inject.Inject

class NewRepository @Inject constructor(private val service: NewsService,
                                        private val articleDao: ArticleDao) {

    suspend fun getNews(countryCode: String, pageNumber: Int) =
        service.getHeadlines(countryCode, pageNumber)

    suspend fun getSearchNews(query: String, pageNumber: Int) =
        service.getEverything(query, pageNumber)

    fun getFavoriteArticles() = articleDao.getAllArticles()

    suspend fun addToFavorite(article: Article) = articleDao.insert(article)

    suspend fun deleteFromFavorite(article: Article) = articleDao.delete(article)

}