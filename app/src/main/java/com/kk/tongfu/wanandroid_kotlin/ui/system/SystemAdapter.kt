package com.kk.tongfu.wanandroid_kotlin.ui.system

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kk.tongfu.wanandroid_kotlin.databinding.ItemChapterBinding
import com.kk.tongfu.wanandroid_kotlin.service.model.Chapter
import com.kk.tongfu.wanandroid_kotlin.ui.homepage.ItemClickListener
import com.kk.tongfu.wanandroid_kotlin.viewmodel.ChapterViewModel


/**
 * Created by tongfu
 * on 2019-11-07
 * Desc:
 */

class SystemAdapter(val listener: ItemClickListener<ChapterViewModel>) :
    ListAdapter<Chapter, ChapterViewHolder>(ChapterDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        return ChapterViewHolder(
            ItemChapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

}

class ChapterViewHolder(val binding: ItemChapterBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        chapter: Chapter,
        listener: ItemClickListener<ChapterViewModel>
    ) {
        binding.viewModel = ChapterViewModel(chapter)
        binding.listener=listener
    }

}

object ChapterDiffer : DiffUtil.ItemCallback<Chapter>() {
    override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
        return oldItem == newItem
    }

}