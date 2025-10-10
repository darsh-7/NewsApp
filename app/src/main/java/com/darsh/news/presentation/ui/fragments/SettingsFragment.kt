package com.darsh.news.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.darsh.news.databinding.FragmentSettingsBinding
import com.darsh.news.presentation.ui.adapters.CountryAdapter

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countries = listOf(
            "Egypt",
            "Saudi Arabia",
            "United Arab Emirates",
            "Kuwait",
            "Jordan",
            "Qatar",
            "Lebanon",
            "Morocco"
        )

        adapter = CountryAdapter(countries)

        binding.countriesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.countriesRv.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}