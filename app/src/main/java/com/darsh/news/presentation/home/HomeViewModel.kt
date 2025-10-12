package com.darsh.news.presentation.ui.fragments
//
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.darsh.news.data.remote.data_model.Category



class HomeViewModel : ViewModel() {

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    init {
        loadCategories()
    }

    private fun loadCategories() {
        val categoryList = Category.values().map {
            it.name.lowercase().replaceFirstChar { c -> c.uppercase() }
        }
        _categories.value = categoryList
    }
}
