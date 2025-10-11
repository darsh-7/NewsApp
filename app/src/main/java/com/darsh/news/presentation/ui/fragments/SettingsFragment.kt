package com.darsh.news.presentation.ui.fragments

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
import com.darsh.news.databinding.FragmentSettingsBinding
import com.darsh.news.presentation.ui.adapters.CountryAdapter

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CountryAdapter
    private val countryViewModel: CountryViewModel by viewModels()
    private val authRepository = AuthRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countriesRv.layoutManager = LinearLayoutManager(requireContext())
        countryViewModel.countries.observe(viewLifecycleOwner) { countries ->
            adapter = CountryAdapter(countries)
            binding.countriesRv.adapter = adapter
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    findNavController().navigate(R.id.action_settingsFragment_to_favouritFragment)
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
