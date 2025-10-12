package com.darsh.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.darsh.news.data.remote.api.news.NewsCallable
import com.darsh.news.data.remote.data_model.Article
import com.darsh.news.data.remote.data_model.News
import com.darsh.news.databinding.ActivityMainBinding
import com.darsh.news.firebaseLogic.AuthViewModel
import com.darsh.news.presentation.NewsAdapter
import com.darsh.news.presentation.ui.fragments.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.news_fragment_container, NewsFragment()) // Or .add() if you don't plan to replace
//                .commit()
//        }

        getNews()

        binding.swiperefresh.setOnRefreshListener {
            getNews()

        }
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
                binding.progressCircular.isVisible = false
                binding.swiperefresh.isRefreshing = false
                showNews(articles = articles)
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.e("FetchNewsListUseCase", t.toString())
                binding.progressCircular.isVisible = false

            }
        })
    }

    private fun showNews(articles: Array<Article>) {
        val adapter = NewsAdapter(this, articles)
        binding.newsList.adapter = adapter

    }
//    private fun showNews(articles: List<Article>) {
//        newsAdapter.updateData(articles) // Create an updateData method in your adapter
//    }
}