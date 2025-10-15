package com.darsh.news.presentation.firestore

import com.darsh.news.data.remote.data_model.Article
import com.darsh.news.presentation.authentication.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = AuthRepository()

    private fun getUserFavoritesRef() = firestore
        .collection("users")
        .document(auth.getCurrentUser()!!.uid)
        .collection("favorites")

    suspend fun addToFavorites(article: Article) {
        getUserFavoritesRef()
            .document(article.url.hashCode().toString())
            .set(article)
            .await()
    }

    suspend fun removeFromFavorites(article: Article) {
        getUserFavoritesRef()
            .document(article.url.hashCode().toString())
            .delete()
            .await()
    }

    suspend fun isFavourite(articleUrl: String?): Boolean {
        val doc = getUserFavoritesRef()
            .document(articleUrl.hashCode().toString())
            .get()
            .await()

        return doc.exists()
    }

    suspend fun getAllFavourites(): List<Article> {
        val snapshot = getUserFavoritesRef().get().await()
        return snapshot.toObjects(Article::class.java)
    }
}