package com.darsh.news.presentation.newsList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.darsh.news.R
import com.darsh.news.data.remote.data_model.Article
import com.darsh.news.databinding.FragmentFavouritBinding
import com.darsh.news.domain.model.FavNews
import com.darsh.news.presentation.NewsViewModel
import com.darsh.news.presentation.ui.adapters.FavoriteAdapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import java.io.IOException

class FavouritFragment : Fragment() {

    private var _binding: FragmentFavouritBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: FavoriteAdapter
    private val favoriteList = arrayListOf<Article>()
    private val TAG = "FavouritFragment" // Tag for logging

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavouritBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
        adapter = FavoriteAdapter(
            requireActivity(), favoriteList,
            onRemoveClick = { article ->
                viewModel.toggleFavorite(article)
            }
        )

        binding.newsList.adapter = adapter

        lifecycleScope.launch {
            viewModel.favorites.collect { favs ->
                adapter.favoriteNews.clear()
                adapter.favoriteNews.addAll(favs)
                adapter.notifyDataSetChanged()

                updateUiAfterFetch()
            }
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.loadFavorites()
        }

        binding.backBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_FavouritFragment_to_homeFragment)
        }

        binding.progressCircular.visibility = View.VISIBLE
        viewModel.loadFavorites()
    }

    private fun updateUiAfterFetch() {
        // This function is called after a successful fetch
        binding.progressCircular.visibility = View.GONE
        binding.swiperefresh.isRefreshing = false

        if (favoriteList.isEmpty()) {
            binding.emptyListMessage.text = "No favorite news added"
            binding.emptyListMessage.visibility = View.VISIBLE
            binding.newsList.visibility = View.GONE
        } else {
            binding.emptyListMessage.visibility = View.GONE
            binding.newsList.visibility = View.VISIBLE
        }
    }

    private fun showError(message: String) {
        // This function is called on failure
        if (_binding == null) return
        binding.progressCircular.visibility = View.GONE
        binding.swiperefresh.isRefreshing = false
        binding.newsList.visibility = View.GONE
        binding.emptyListMessage.visibility = View.VISIBLE
        binding.emptyListMessage.text = message
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
