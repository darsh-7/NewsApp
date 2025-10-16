package com.darsh.news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darsh.news.data.remote.data_model.Article
import com.darsh.news.presentation.authentication.AuthRepository
import com.darsh.news.presentation.firestore.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = FirestoreRepository()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val _favorites = MutableStateFlow<List<Article>>(emptyList())
    val favorites: StateFlow<List<Article>> get() = _favorites

    init {
        firebaseAuth.addAuthStateListener { auth ->
            val user = auth.currentUser
            if (user == null) _favorites.value = emptyList()
            else viewModelScope.launch { _favorites.value = repository.getAllFavourites() }
        }
    }


    fun toggleFavorite(article: Article) {
        viewModelScope.launch {
            val isFavorite = repository.isFavourite(article.url)

            val updatedList = _favorites.value.toMutableList()
            if (isFavorite) {
                updatedList.removeAll { it.url == article.url }
                repository.removeFromFavorites(article)
            } else {
                updatedList.add(article)
                repository.addToFavorites(article)
            }
            _favorites.value = updatedList
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getAllFavourites()
        }
    }
}