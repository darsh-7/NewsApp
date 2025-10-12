package com.darsh.news.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darsh.news.databinding.CountriesListItemBinding

class CountryAdapter(private val countries: List<String>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(val binding: CountriesListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CountriesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.binding.countryName.text = country
    }

    override fun getItemCount(): Int = countries.size
}
