package com.darsh.news.presentation.ui.adapters
//
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darsh.news.databinding.CategoriesItemBinding

class HomeAdapter(private var categories: List<String>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoriesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.categoryTitle.text = category

        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(category)
        }
    }

    fun updateList(newList: List<String>) {
        categories = newList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: CategoriesItemBinding) : RecyclerView.ViewHolder(binding.root)
}
