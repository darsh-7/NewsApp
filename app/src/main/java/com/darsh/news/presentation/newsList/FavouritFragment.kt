package com.darsh.news.presentation.newsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.darsh.news.R
import com.darsh.news.databinding.FragmentFavouritBinding
import com.darsh.news.domain.model.FavNews
import com.darsh.news.presentation.ui.adapters.FavoriteAdapter
import java.io.IOException

class FavouritFragment : Fragment() {

    private var _binding: FragmentFavouritBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteAdapter
    private val favoriteList = arrayListOf<FavNews>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteAdapter(requireActivity(), favoriteList)
        binding.newsList.adapter = adapter

        loadFavorites()

        binding.swiperefresh.setOnRefreshListener {
            loadFavorites()
        }
        binding.backBtn.setOnClickListener {
    it.findNavController().navigate(R.id.action_FavouritFragment_to_homeFragment)

        }

    }

    private fun loadFavorites() {
        try {
            binding.progressCircular.visibility = View.VISIBLE
            binding.emptyListMessage.visibility = View.GONE
            binding.newsList.visibility = View.GONE

            simulateFetchingData()
        } catch (e: IOException) {
            showError("Check your internet connection")
        }
    }

    private fun simulateFetchingData() {
        binding.newsList.postDelayed({
            if (_binding == null) return@postDelayed
            binding.progressCircular.visibility = View.GONE
            binding.swiperefresh.isRefreshing = false

            favoriteList.clear()
            // favoriteList.add(FavNews(1, "Sample News", "https://example.com"))

            adapter.notifyDataSetChanged()

            if (favoriteList.isEmpty()) {
                binding.emptyListMessage.text = "No favorite news added"
                binding.emptyListMessage.visibility = View.VISIBLE
            } else {
                binding.newsList.visibility = View.VISIBLE
            }
        }, 1500)
    }

    private fun showError(message: String) {
        binding.progressCircular.visibility = View.GONE
        binding.newsList.visibility = View.GONE
        binding.swiperefresh.isRefreshing = false
        binding.emptyListMessage.visibility = View.VISIBLE
        binding.emptyListMessage.text = message
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
