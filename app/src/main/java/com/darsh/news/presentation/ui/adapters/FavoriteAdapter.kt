package com.darsh.news.presentation.ui.adapters

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.darsh.news.data.remote.data_model.Article
import com.darsh.news.databinding.FavoritListItemBinding
import com.darsh.news.domain.model.FavNews
import com.darsh.news.presentation.ui.adapters.FavoriteAdapter.*

class FavoriteAdapter(
    val activity: Activity,
    val favoriteNews: ArrayList<Article>,
    private val onRemoveClick: (Article) -> Unit,
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder(val binding: FavoritListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoriteViewHolder {
        val binding = FavoritListItemBinding.inflate(activity.layoutInflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val fav = favoriteNews[position]

        holder.binding.newsTitle.text = fav.title
        holder.binding.newsUrl.text = fav.url

        holder.binding.buttonShare.setOnClickListener() {
            ShareCompat.IntentBuilder(activity)
                .setType("text/plain")
                .setChooserTitle("Share article with")
                .setText(fav.url)
                .startChooser()
        }

        holder.binding.favContainer.setOnClickListener {
            val uri = fav.url.toUri()
            val i = Intent(Intent.ACTION_VIEW, uri)
            activity.startActivity(i)
        }

        holder.binding.buttonFav.setOnClickListener{
            onRemoveClick(fav)
        }
    }

    override fun getItemCount(): Int = favoriteNews.size
}
