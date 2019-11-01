package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kk.tongfu.wanandroid_kotlin.databinding.ItemArticleBinding
import com.kk.tongfu.wanandroid_kotlin.databinding.LayoutBannerBinding
import com.kk.tongfu.wanandroid_kotlin.service.model.Article
import com.kk.tongfu.wanandroid_kotlin.service.model.BannerList

/**
 * Created by tongfu
 * on 2019-10-23
 * Desc:
 */

class HomePageAdapter(val listener:HomePageItemClickListener) :
    ListAdapter<Any, RecyclerView.ViewHolder>(ArticleDiff) {

    companion object {
        const val VIEW_TYPE_ARTICLE = 1
        const val VIEW_TYPE_BANNER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ARTICLE -> ArticleViewHolder(
                ItemArticleBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            VIEW_TYPE_BANNER -> BannerViewHolder(
                LayoutBannerBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> {
                throw Exception("the tyep of item must be either VIEW_TYPE_BANNER or VIEW_TYPE_ARTICLE")
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder) {
            holder.bind(getItem(position) as Article,listener)
        }

        if (holder is BannerViewHolder) {
            holder.bind(getItem(position) as BannerList)
        }
    }

    override fun getItemViewType(position: Int): Int {

        val item = getItem(position)
        if (item is Article) {
            return VIEW_TYPE_ARTICLE
        }

        if (item is BannerList) {
            return VIEW_TYPE_BANNER
        }
        return super.getItemViewType(position)
    }


}

class ArticleViewHolder(private val binding: ItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Article,listener:HomePageItemClickListener) {
        binding.viewModel = HomePageAdapterViewModel(item)
        binding.onClickListener=listener
    }
}

class BannerViewHolder(private val binding: LayoutBannerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(items: BannerList) {
        binding.bannerList = items
    }
}

object ArticleDiff : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is Article && newItem is Article) {
            return oldItem.id == newItem.id
        }
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is Article && newItem is Article) {
            return oldItem.publishTime == newItem.publishTime
        }
        return true

    }

}