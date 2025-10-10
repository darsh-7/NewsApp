package com.darsh.news.data

object Constant {
    // news api
    const val key = "3ed7f6e141974453aadf3f5dd9c6c300"
    const val baseUrl ="https://newsapi.org"

    const val url =
        "/v2/top-headlines?category=business&country=us&apiKey=$key"

    // data store
    const val PREFERENCES_NAME = "country_preference"
    const val PREFERENCES_COUNTRY_NAME = "country_name"


}