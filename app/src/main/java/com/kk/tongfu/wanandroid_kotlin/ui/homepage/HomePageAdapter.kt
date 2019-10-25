package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kk.tongfu.wanandroid_kotlin.databinding.ItemArticleBinding
import com.kk.tongfu.wanandroid_kotlin.service.model.Article

/**
 * Created by tongfu
 * on 2019-10-23
 * Desc:
 */

class HomePageAdapter :
    ListAdapter<Article, HomePageViewHolder>(ArticleDiff) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {
        val itemArticleBinding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomePageViewHolder(itemArticleBinding)
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class HomePageViewHolder(private val binding: ItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Article) {
        binding.articleItem=item
    }
}

object ArticleDiff : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}