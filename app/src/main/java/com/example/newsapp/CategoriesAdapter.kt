package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.model.Category
import com.google.android.material.card.MaterialCardView

class CategoriesAdapter(var items: List<Category>) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    val RIGHT_SIDED_CODE = 2
    val LEFT_SIDED_CODE = 1
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var materialCard: MaterialCardView = itemView.findViewById(R.id.parent)
        var cardView: ImageView = itemView.findViewById(R.id.category_image)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View? = null
        if (viewType == RIGHT_SIDED_CODE) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.right_sided_category, parent, false)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.left_sided_category, parent, false)
        }

        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) 2 else 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]
        holder.cardView.setImageResource(item.imageId)
        holder.itemView.setOnClickListener {
            onCategoryClick?.onCategoryClick(position , item)
        }

    }
    var onCategoryClick : OnItemClick? = null
    interface OnItemClick{
        fun onCategoryClick(position: Int , item : Category)
    }

    override fun getItemCount(): Int {
        return items.size
    }


}