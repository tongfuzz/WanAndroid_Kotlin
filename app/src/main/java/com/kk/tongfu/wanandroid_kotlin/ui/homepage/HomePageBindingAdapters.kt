package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.service.LoadState
import com.kk.tongfu.wanandroid_kotlin.service.RefreshState
import com.kk.tongfu.wanandroid_kotlin.service.model.BannerList
import com.kk.tongfu.wanandroid_kotlin.util.GlideImageLoader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.Banner
import com.youth.banner.BannerConfig

/**
 * Created by tongfu
 * on 2019-10-23
 * Desc：进行适配器的绑定
 */

@BindingAdapter("refreshState")
fun setRefreshState(view: SmartRefreshLayout, state: RefreshState) {
    when (state) {
        RefreshState.LOADING_SUCCESS, RefreshState.LOADING_ERROR,RefreshState.LOADING_NO_MORE_DATA,RefreshState.LOADING_NO_NETWORK -> {
            view.finishLoadMore()
        }

        RefreshState.REFRESHING_ERROR, RefreshState.REFRESHING_SUCCESS,RefreshState.REFRESHING_NO_NETWORK -> {
            view.finishRefresh()
        }

        else -> {}
    }
}

@BindingAdapter("onRefreshLoadMoreListener")
fun setRefreshListener(view: SmartRefreshLayout, listener: OnRefreshLoadMoreListener) {
    view.setOnRefreshLoadMoreListener(listener)
}

@BindingAdapter("enableLoadMore")
fun setEnableLoadMore(view: SmartRefreshLayout, enableLoadMore: Boolean) {
    view.setEnableLoadMore(enableLoadMore)
}

@BindingAdapter("textInfoAndVisiable" )
fun setTextViewText(view: TextView, loadState: LoadState) {
    when (loadState) {
        LoadState.LOADING -> {
            view.setText(R.string.loading)
            view.visibility = View.VISIBLE
        }
        LoadState.ERROR -> {
            view.setText(R.string.load_error)
            view.visibility = View.VISIBLE
        }
        LoadState.NO_NETWORK -> {
            view.setText(R.string.no_network_and_retry)
            view.visibility = View.VISIBLE
        }
        LoadState.NO_DATA -> {
            view.setText(R.string.no_data)
            view.visibility = View.VISIBLE
        }

        LoadState.SUCCESS -> {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("bannerItems")
fun setBannerList(banner:Banner,bannerList: BannerList){
    val images = bannerList.data?.map {
        it.imagePath
    }

    val titles = bannerList.data?.map {
        it.title
    }

    banner.setImageLoader(GlideImageLoader())
    banner.setImages(images)
    banner.setBannerTitles(titles)
    banner.setOnBannerListener {
        bannerList.data?.get(it)?.title.apply {
            banner.context.startActivity(Intent(banner.context,ArticleDetailActivity::class.java))
        }
    }
    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
    banner.start()
}

/*
@BindingAdapter("app:materialRefreshListener")
fun setRefreshListener(view: MaterialRefreshLayout, listener: MaterialRefreshListener) {
    view.setMaterialRefreshListener(listener)
}

@BindingAdapter("app:hasMore")
fun setHasMore(view: MaterialRefreshLayout, hasMore: Boolean) {
    view.setLoadMore(hasMore)
}
*/


/*@BindingAdapter("app:isLoadMore")
fun setIsLoadMore(view:MaterialRefreshLayout,isLoadMore: MutableLiveData<Boolean>){
}*/


