package com.darsh.news.data.remote.data_model

data class Article(
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String = "",
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null,
    val isFavorite: Boolean = false,
)