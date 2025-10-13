package com.darsh.news.domain.model


import androidx.annotation.Keep

@Keep // Important for release builds
data class FavNews (
    val title: String ="" ,
    val url: String = ""
)