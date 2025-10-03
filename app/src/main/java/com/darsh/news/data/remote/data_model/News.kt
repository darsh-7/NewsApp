package com.darsh.news.data.remote.data_model

data class News (
    val articles : Array<Article> ,
    val totalResults: Int,
    val status: String,
)