package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.ItemArticleBinding
import com.example.newsapp.model.ArticlesItem
import org.w3c.dom.Text

class ArticlesAdapter(var items : List<ArticlesItem?>? = null) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    lateinit var itemArticleBinding: ItemArticleBinding

    class ViewHolder (var dataBinding : ItemArticleBinding) : RecyclerView.ViewHolder(dataBinding.root){
//        val imageView : ImageView = itemView.findViewById(R.id.item_article_image)
//        val titleTextView : TextView = itemView.findViewById(R.id.item_article_title)
//        val authorTextView : TextView = itemView.findViewById(R.id.item_article_author)
//        val dateTextView : TextView = itemView.findViewById(R.id.item_article_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var dataBinding = DataBindingUtil.inflate<ItemArticleBinding>( LayoutInflater.from(parent.context),(R.layout.item_article), parent,false)
        return ViewHolder(dataBinding)
    //        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_article,parent,false)
//        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var article = items!![position] ?: return
        holder.dataBinding.articlesItem = article
//        holder.titleTextView.text = article.title
//        holder.authorTextView.text = article.author
//        holder.dateTextView.text = article.publishedAt
        Glide.with(holder.itemView)
            .load(article.urlToImage)
            .into(holder.dataBinding.itemArticleImage)

    }

    override fun getItemCount(): Int {
        return if(items == null) 0 else items!!.size

    }
}