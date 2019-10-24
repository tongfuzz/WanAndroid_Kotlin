package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.databinding.FragmentHomePageBinding
import com.kk.tongfu.wanandroid_kotlin.service.LoadState
import com.kk.tongfu.wanandroid_kotlin.service.RefreshState
import com.kk.tongfu.wanandroid_kotlin.util.toast
import com.kk.tongfu.wanandroid_kotlin.util.viewModelProvider
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomePageFragment : DaggerFragment() {

    val TAG: String = "HomePageFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private lateinit var model: HomePageViewModel
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var refreshListener: OnRefreshLoadMoreListener
    private var adapter = HomePageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model = viewModelProvider(viewModelFactory)
        refreshListener = object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                model.loadMoreData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                model.refreshData()
            }
        }

        binding = FragmentHomePageBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = model
            refreshLis = refreshListener
            recyclerView.adapter = adapter
            swipeRefreshLayout.setRefreshHeader(ClassicsHeader(context))
            tvLoading.setOnClickListener {
                if(model.loadState!=LoadState.LOADING){
                    model.loadState.value=LoadState.LOADING
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.articleList?.observe(this, Observer {
            it ?: return@Observer
            adapter.submitList(it.toMutableList())
        })
        model.refreshState.observe(this, Observer {
            when(it){
                RefreshState.LOADING_ERROR->toast(R.string.no_more_data)
                RefreshState.REFRESHING_ERROR->toast(R.string.failed_to_refresh)
            }
        })

        model.loadState.observe(this, Observer {
            when(it){
                LoadState.LOADING->{model.loadData()}
            }
        })

        model.loadData()

    }


}
