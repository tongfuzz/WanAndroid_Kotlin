package com.kk.tongfu.wanandroid_kotlin.ui.system

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kk.tongfu.wanandroid_kotlin.databinding.FragmentArticleListBinding
import com.kk.tongfu.wanandroid_kotlin.service.LoadState
import com.kk.tongfu.wanandroid_kotlin.service.model.Chapter
import com.kk.tongfu.wanandroid_kotlin.ui.homepage.ArticleDetailActivity
import com.kk.tongfu.wanandroid_kotlin.ui.homepage.ArticleListAdapter
import com.kk.tongfu.wanandroid_kotlin.ui.homepage.ItemClickListener
import com.kk.tongfu.wanandroid_kotlin.util.toast
import com.kk.tongfu.wanandroid_kotlin.util.viewModelProvider
import com.kk.tongfu.wanandroid_kotlin.viewmodel.ArticleAdapterViewModel
import com.kk.tongfu.wanandroid_kotlin.viewmodel.ArticleListViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_article_list.*
import javax.inject.Inject

/**
 * Created by tongfu
 * on 2019-11-13
 * Desc:
 */

const val ARG_ITEM = "item"

class ArticleListFragment : DaggerFragment() {

    private var chapter: Chapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding: FragmentArticleListBinding
    lateinit var articleListViewModel: ArticleListViewModel
    var isFirstVisiable = true

    var refreshListener = object : OnRefreshLoadMoreListener {
        override fun onRefresh(refreshLayout: RefreshLayout) {
            articleListViewModel.refreshData()
        }

        override fun onLoadMore(refreshLayout: RefreshLayout) {
            articleListViewModel.getMoreData()
        }
    }

    var itemClickListener = object : ItemClickListener<ArticleAdapterViewModel> {
        override fun onItemClick(view: View, item: ArticleAdapterViewModel) {
            startActivity(Intent(context,ArticleDetailActivity::class.java).putExtra(ArticleDetailActivity.URL_PATH,item.url))
        }
    }

    var articleListAdapter = ArticleListAdapter(itemClickListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleListBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(ARG_ITEM) }?.apply {
            chapter = getParcelable(ARG_ITEM)
            articleListViewModel = viewModelProvider(viewModelFactory)
            chapter?.id?.apply {
                articleListViewModel.setId(this)
            }
            binding.apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = articleListViewModel
                recyclerView.adapter = articleListAdapter
                refreshLayout.setOnRefreshLoadMoreListener(refreshListener)
                refreshLayout.setRefreshHeader(ClassicsHeader(context))
            }

            articleListViewModel.articleList.observe(this@ArticleListFragment, Observer {
                articleListAdapter.submitList(it.toMutableList<Any>())
            })

            articleListViewModel.toastStr.observe(this@ArticleListFragment, Observer {
                it?.apply {
                    toast(this)
                }
            })

            articleListViewModel.netWorkInfo.observe(this@ArticleListFragment, Observer {
                it?.apply {
                    if(!it.isConnected){
                        articleListViewModel.stateNoNetwork()
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFirstVisiable) {
            articleListViewModel.getArticleList()
            isFirstVisiable = false
        }
    }


}