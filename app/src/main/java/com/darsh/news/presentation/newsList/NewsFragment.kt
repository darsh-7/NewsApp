package com.darsh.news.presentation.newsList

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.darsh.news.data.local.DataStorePreference
import com.darsh.news.data.remote.api.news.NewsCallable
import com.darsh.news.data.remote.data_model.Article
import com.darsh.news.data.remote.data_model.News
import com.darsh.news.databinding.FragmentNewsBinding
import com.darsh.news.presentation.NewsAdapter
import com.darsh.news.presentation.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setupRecyclerView()
        val selectedCategory = arguments?.getString("category")

        getNews(selectedCategory)

        binding.swiperefresh.setOnRefreshListener {
            getNews(selectedCategory)
        }


    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(requireActivity(), emptyArray())
        binding.newsList.adapter = newsAdapter
    }

    private fun getNews(selectedCategory: String?) {
        binding.progressCircular.isVisible = true
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsCall = retrofit.create(NewsCallable::class.java)
        val country = DataStorePreference(requireContext()).readIsFirstTimeEnterApp()
        newsCall.getNews(country = country, category=selectedCategory).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                binding.progressCircular.isVisible = false
                binding.swiperefresh.isRefreshing = false
//

                if (response.isSuccessful) {
                    val news = response.body()
                    val articles = news?.articles
                    if (articles != null) {
                        showNews(articles)
                    }
                }

            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.e("NewsFragment", "Error fetching news", t)
                binding.progressCircular.isVisible = false
                binding.swiperefresh.isRefreshing = false
            }
        })
    }

    private fun showNews(articles: Array<Article>) {
        // Inside MainActivity.kt
        val activity : Activity? = getActivity()

        val adapter = NewsAdapter(activity!!, articles)
        binding.newsList.adapter = adapter

    }
}



