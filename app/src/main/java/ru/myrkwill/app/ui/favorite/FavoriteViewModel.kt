package ru.myrkwill.app.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.myrkwill.app.data.api.NewRepository
import ru.myrkwill.app.models.Article
import ru.myrkwill.app.utils.Resource
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: NewRepository): ViewModel() {

    val newsLiveData: MutableLiveData<Resource<List<Article>>> = MutableLiveData()

    init {
        getFavoriteArticles()
    }

    private fun getFavoriteArticles() = viewModelScope.launch(Dispatchers.IO) {
        newsLiveData.postValue(Resource.Loading())
        val response = repository.getFavoriteArticles()
        newsLiveData.postValue(Resource.Success(response))
    }

}