package com.darsh.news.presentation.newsList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.darsh.news.R
import com.darsh.news.data.remote.data_model.Article
import com.darsh.news.databinding.FragmentFavouritBinding
import com.darsh.news.presentation.NewsAdapter
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue


class favouritFragment : Fragment() {
    lateinit var binding: FragmentFavouritBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding= FragmentFavouritBinding.inflate(inflater,container,false)
        return binding.root
    }
    private lateinit var FavNewsAdapter: NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FavNewsAdapter = NewsAdapter(requireActivity(), emptyArray<Article>() )

        binding.newsList.adapter = FavNewsAdapter
        binding.backBtn.setOnClickListener {

        }

       // observeNewsData()

        //viewModel.fetchNews()
    }

}