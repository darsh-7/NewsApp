package com.darsh.news.presentation.home


import AuthRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.darsh.news.R
import com.darsh.news.databinding.FragmentHomeBinding
import com.darsh.news.presentation.ui.adapters.HomeAdapter
import com.darsh.news.presentation.ui.fragments.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: HomeAdapter
    private val authRepository = AuthRepository()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickListener { category ->
            val bundle = Bundle().apply {
                putString("category", category)
            }
            findNavController().navigate(R.id.action_homeFragment_to_newsFragment, bundle)
        }

        viewModel.categories.observe(viewLifecycleOwner) { categoryList ->
            adapter.updateList(categoryList)
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settings -> {
                    openSettingsFragment()
                    true
                }
                R.id.favorite -> {
                    findNavController().navigate(R.id.action_homeFragment_to_favouritFragment)
                    true
                }
                R.id.logout -> {
                    logoutUser()
                    true
                }
                else -> false
            }
        }
    }

    private fun openSettingsFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
    }

    private fun logoutUser() {
        authRepository.logout()
        findNavController().navigate(
            R.id.signInFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.app_nav, true)
                .build()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
