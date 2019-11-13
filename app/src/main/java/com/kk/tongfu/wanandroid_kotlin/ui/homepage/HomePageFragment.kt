package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.databinding.FragmentHomePageBinding
import com.kk.tongfu.wanandroid_kotlin.di.Constant
import com.kk.tongfu.wanandroid_kotlin.interfaces.ScrollTop
import com.kk.tongfu.wanandroid_kotlin.service.RefreshState
import com.kk.tongfu.wanandroid_kotlin.util.toast
import com.kk.tongfu.wanandroid_kotlin.util.viewModelProvider
import com.kk.tongfu.wanandroid_kotlin.viewmodel.HomePageAdapterViewModel
import com.kk.tongfu.wanandroid_kotlin.viewmodel.HomePageViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home_page.*
import javax.inject.Inject

class HomePageFragment : DaggerFragment(), ScrollTop {


    companion object {
        private var homePageFragment: HomePageFragment? = null
        fun getInstance(): HomePageFragment {
            if (homePageFragment == null) {
                homePageFragment = HomePageFragment()
            }
            return homePageFragment!!
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var model: HomePageViewModel
    private lateinit var binding: FragmentHomePageBinding
    private val refreshListener = object : OnRefreshLoadMoreListener {
        override fun onLoadMore(refreshLayout: RefreshLayout) {
            model.loadData()
        }

        override fun onRefresh(refreshLayout: RefreshLayout) {
            model.refreshData()
        }

    }

    private val onClickListener = object : ItemClickListener<HomePageAdapterViewModel> {
        override fun onItemClick(view: View, viewModel: HomePageAdapterViewModel) {
            val bundle = Bundle().apply {
                putString(ArticleDetailActivity.URL_PATH, viewModel.url)
            }
            startActivity(Intent(context,ArticleDetailActivity::class.java).putExtras(bundle))
        }
    }

    private var adapter = HomePageAdapter(onClickListener)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //解决Navigation每次都会重新调用onCreateView(）等方法的问题
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        model = viewModelProvider(viewModelFactory)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = model
            refreshLis = refreshListener
            recyclerView.adapter = adapter
            swipeRefreshLayout.setRefreshHeader(ClassicsHeader(context))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.articleList.observe(this, Observer {
            it ?: return@Observer
            adapter.submitList(it.toMutableList())
        })

        model.refreshState.observe(this, Observer {
            when (it) {
                RefreshState.LOADING_ERROR, RefreshState.LOADING_NO_MORE_DATA -> toast(R.string.no_more_data)
                RefreshState.REFRESHING_ERROR -> toast(R.string.failed_to_refresh)
                RefreshState.LOADING_NO_NETWORK, RefreshState.REFRESHING_NO_NETWORK -> toast(R.string.no_network_and_check)
                else -> {
                }
            }
        })
        model.toastStr.observe(this, Observer {
            it?.apply {
                toast(this)
            }
        })

        model.netWorkInfo?.observe(this, Observer {
            it?.also {
                if (!it.isConnected) {
                    model.stateNoNetwork()
                }
            }
        })
    }


    override fun scrollTop() {
        //todo 滑动距离不准确的问题需要处理一下
        recyclerView?.smoothScrollToPosition(0)
    }


}
