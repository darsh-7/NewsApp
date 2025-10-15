package com.darsh.news.presentation

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.darsh.news.R
import com.darsh.news.data.remote.data_model.Article
import com.darsh.news.databinding.ArticleListItemBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class NewsAdapter(val a: Activity, val article: Array<Article>) :
    Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(val binding: ArticleListItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val b = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(b)
    }

    override fun getItemCount(): Int = article.size


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val binder = holder.binding
        val article = article[position]
        val db = Firebase.firestore

        binder.newsTitle.text = article.title
        binder.newsSubtitle.text = article.description
        Glide.with(binder.newsImage.context).load(article.urlToImage)
            .error(R.drawable.broken_image).transition(
                DrawableTransitionOptions.withCrossFade(1000)
            ).into(binder.newsImage)

        binder.newsContainer.setOnClickListener {
            val uri = article.url.toUri()
            val i = Intent(Intent.ACTION_VIEW, uri)
            a.startActivity(i)
        }

        binder.buttonShare.setOnClickListener() {
            ShareCompat.IntentBuilder(a).setType("text/plain")
                .setChooserTitle("Share article with")
                .setText(article.url)
                .startChooser()
        }
        binder.favorite.setOnClickListener {
            // Create a new user with a first and last name
            val post = hashMapOf(
                "title" to article.title,
                "url" to article.url,

                )

// Add a new document with a generated ID
            db.collection("fav")
                .add(post)
                .addOnSuccessListener { documentReference ->
                    Log.d("save post", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("save post", "Error adding document", e)
                }
        }

    }


}