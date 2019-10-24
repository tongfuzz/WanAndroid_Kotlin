package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kk.tongfu.wanandroid_kotlin.databinding.FragmentHomePageBinding
import com.kk.tongfu.wanandroid_kotlin.util.viewModelProvider
import com.scwang.smartrefresh.layout.api.RefreshLayout
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
                model.onLoadMore()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                model.onSwipeRefresh()
            }

        }


        binding = FragmentHomePageBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = model
            refreshLis = refreshListener
            recyclerView.adapter = adapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model._articleList?.observe(this, Observer {
            it ?: return@Observer
            adapter.submitList(it.toMutableList())
        })
        model.getData()
    }


}
