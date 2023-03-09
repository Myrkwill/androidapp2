package ru.myrkwill.app.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.myrkwill.app.data.api.NewRepository
import ru.myrkwill.app.models.NewsResponse
import ru.myrkwill.app.utils.Resource
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NewRepository): ViewModel() {

    val newsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val newsPage = 1

    init {
        getNews("ru")
    }

    private fun getNews(countryCode: String) {
        viewModelScope.launch {
            newsLiveData.postValue(Resource.Loading())
            val response = repository.getNews(countryCode = countryCode, pageNumber = newsPage)
            if (response.isSuccessful) {
                response.body().let {
                    newsLiveData.postValue(Resource.Success(it))
                }
            } else {
                newsLiveData.postValue(Resource.Error(message = response.message()))
            }
        }
    }

}