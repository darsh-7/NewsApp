package com.darsh.news.data.remote.api.news

import com.darsh.news.data.Constant
import com.darsh.news.data.remote.data_model.News
import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {

    @GET(Constant.url)
    fun getNews(): Call<News>


}