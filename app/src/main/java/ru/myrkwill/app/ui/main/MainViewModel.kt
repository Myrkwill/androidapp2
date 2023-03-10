package ru.myrkwill.app.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.myrkwill.app.data.api.NewRepository
import ru.myrkwill.app.models.Article
import ru.myrkwill.app.models.NewsResponse
import ru.myrkwill.app.utils.Resource
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NewRepository): ViewModel() {

    val newsLiveData: MutableLiveData<Resource<List<Article>>> = MutableLiveData()
    val newsPage = 1

    init {
        getNews("uk")
    }

    private fun getNews(countryCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            newsLiveData.postValue(Resource.Loading())
            val response = repository.getNews(countryCode = countryCode, pageNumber = newsPage)
            if (response.isSuccessful) {
                response.body()?.let {
                    val articlesFavorite = repository.getFavoriteArticles()
                    it.articles.forEach { article ->
                        article.isFavorite = articlesFavorite.contains(article)
                    }
                    newsLiveData.postValue(Resource.Success(it.articles))
                }
            } else {
                newsLiveData.postValue(Resource.Error(message = response.message()))
            }
        }
    }

}