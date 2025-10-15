package com.darsh.news.presentation.newsList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.darsh.news.R
import com.darsh.news.databinding.FragmentFavouritBinding
import com.darsh.news.domain.model.FavNews
import com.darsh.news.presentation.ui.adapters.FavoriteAdapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.io.IOException

class FavouritFragment : Fragment() {

    private var _binding: FragmentFavouritBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    private lateinit var adapter: FavoriteAdapter
    private val favoriteList = arrayListOf<FavNews>()
    private val TAG = "FavouritFragment" // Tag for logging

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
        // Start loading UI
        binding.progressCircular.visibility = View.VISIBLE
        binding.emptyListMessage.visibility = View.GONE
        binding.newsList.visibility = View.GONE
        binding.swiperefresh.isRefreshing = true

        db.collection("fav")
            .get()
            .addOnSuccessListener { result ->
                if (_binding == null) return@addOnSuccessListener // Ensure fragment is still alive

                favoriteList.clear() // Clear the list before adding new items
                for (document in result) {
                    // Create a FavNews object from the document data
                    val favNews = document.toObject(FavNews::class.java)
                    favoriteList.add(favNews)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }

                adapter.notifyDataSetChanged()
                updateUiAfterFetch()
            }
            .addOnFailureListener { exception ->
                if (_binding == null) return@addOnFailureListener // Ensure fragment is still alive
                Log.w(TAG, "Error getting documents.", exception)
                showError("Failed to load favorites: ${exception.message}")
            }
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
