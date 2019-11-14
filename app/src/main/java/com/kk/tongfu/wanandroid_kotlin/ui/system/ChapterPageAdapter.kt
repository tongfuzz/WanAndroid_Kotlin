package com.kk.tongfu.wanandroid_kotlin.ui.system

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kk.tongfu.wanandroid_kotlin.SystemDetailFragment
import com.kk.tongfu.wanandroid_kotlin.service.model.Chapter

/**
 * Created by tongfu
 * on 2019-11-13
 * Desc:
 */

class ChapterPageAdapter(val chapter: Chapter, fragment: SystemDetailFragment):FragmentStateAdapter(fragment) {



    override fun getItemCount(): Int {
        return chapter.children?.size?:0
    }

    override fun createFragment(position: Int): Fragment {
        val chapterFragment = ArticleListFragment()
        chapterFragment.arguments= Bundle().apply {
            putParcelable(ARG_ITEM, chapter.children?.get(position))
        }
        return chapterFragment
    }
}