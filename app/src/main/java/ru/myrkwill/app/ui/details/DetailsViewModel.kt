package ru.myrkwill.app.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.myrkwill.app.data.api.NewRepository
import ru.myrkwill.app.models.Article
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: NewRepository): ViewModel() {

    fun addToFavorite(article: Article) =
        viewModelScope.launch(Dispatchers.IO) { repository.addToFavorite(article) }

}