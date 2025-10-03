package com.darsh.news.domain

import android.util.Log
import com.darsh.news.data.remote.api.news.NewsCallable
import com.darsh.news.data.Constant
import com.darsh.news.data.remote.data_model.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FetchNewsListUseCase {
     operator fun invoke()/*: List<Article>*/ {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(Constant.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val newsCall = retrofit.create(NewsCallable::class.java).getNews().enqueue(object :
            Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                val articles = news?.articles
                Log.d("FetchNewsListUseCase", articles.toString())
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.e("FetchNewsListUseCase", t.toString())
            }
        })
        //return newsCall.
    }
}