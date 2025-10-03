package com.darsh.news.domain.model

data class User(
    val id: Int = 0, // recommended
    val username: String,
    val email: String,
    val password: String
)
