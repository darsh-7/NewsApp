package com.darsh.news.presentation.ui.adapters

import android.app.Activity
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.darsh.news.databinding.FavoritListItemBinding
import com.darsh.news.domain.model.FavNews
import com.darsh.news.presentation.ui.adapters.FavoriteAdapter.*

class FavoriteAdapter(val activity: Activity, val favoriteNews: ArrayList<FavNews>): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(val binding: FavoritListItemBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
       val binding= FavoritListItemBinding.inflate(activity.layoutInflater,parent,false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int
    ) {
      holder.binding.newsTitle.text=favoriteNews[position].title
        holder.binding.newsSubtitle.text=favoriteNews[position].url
        holder.binding.buttonShare.setOnClickListener() {
            ShareCompat.IntentBuilder(activity).setType("text/plain")
                .setChooserTitle("Share article with")
                .setText(favoriteNews[position].url)
                .startChooser()
        }

    }

    override fun getItemCount(): Int =favoriteNews.size




}




