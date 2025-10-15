package com.darsh.news.presentation.ui.fragments
//
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.darsh.news.data.remote.data_model.Country
import com.darsh.news.data.remote.data_model.countryList

//
class CountryViewModel : ViewModel() {
    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries

    init {
        loadCountries()
    }

    private fun loadCountries() {
        _countries.value = countryList.map { it }
    }
}
