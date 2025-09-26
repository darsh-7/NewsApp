package com.darsh.news.data.remote.api

import com.darsh.news.data.remote.data_model.News
import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {

    @GET("/v2/everything?q=Apple&sortBy=popularity&apiKey=3ed7f6e141974453aadf3f5dd9c6c300")
    fun getNews(): Call<News>


}