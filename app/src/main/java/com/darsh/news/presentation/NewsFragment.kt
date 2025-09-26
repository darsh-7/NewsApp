package com.darsh.news.presentation

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.darsh.news.R
import com.darsh.news.data.remote.api.NewsCallable
import com.darsh.news.data.remote.data_model.Article
import com.darsh.news.data.remote.data_model.News
import com.darsh.news.databinding.FragmentNewsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private val viewModel: NewsViewModel by viewModels()

    private lateinit var binding: FragmentNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getNews()
    }

    private fun getNews() {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsCall = retrofit.create(NewsCallable::class.java)
        newsCall.getNews().enqueue(object :
            Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                val articles = news?.articles!!
                Log.d("FetchNewsListUseCase", news.toString())
                binding.progressCircular.isVisible=false
                showNews(articles = articles )
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.e("FetchNewsListUseCase", t.toString())
                binding.progressCircular.isVisible=false

            }
        })
    }

    private fun showNews(articles: Array<Article>) {
       // val adapter = NewsAdapter(context,articles)
        //binding.newsList.adapter=adapter

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }
}