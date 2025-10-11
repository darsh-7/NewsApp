package com.darsh.news.presentation.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.darsh.news.data.remote.data_model.Country

class CountryViewModel : ViewModel() {
    private val _countries = MutableLiveData<List<String>>()
    val countries: LiveData<List<String>> = _countries

    init {
        loadCountries()
    }

    private fun loadCountries() {
        _countries.value = Country.values().map { it.name }
    }
}
