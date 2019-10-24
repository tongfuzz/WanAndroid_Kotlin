package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import androidx.databinding.BindingAdapter
import com.kk.tongfu.wanandroid_kotlin.service.RefreshState
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * Created by tongfu
 * on 2019-10-23
 * Desc：进行适配器的绑定
 */

@BindingAdapter("app:refreshState")
fun setRefreshState(view: SmartRefreshLayout, state: RefreshState) {
    when (state) {
        RefreshState.LOADING_SUCCESS, RefreshState.LOADING_ERROR -> {
            view.finishLoadMore()
        }

        RefreshState.REFRESHING_ERROR, RefreshState.REFRESHING_SUCCESS -> {
            view.finishRefresh()
        }

    }
}

@BindingAdapter("app:onRefreshLoadMoreListener")
fun setRefreshListener(view: SmartRefreshLayout, listener: OnRefreshLoadMoreListener) {
    view.setOnRefreshLoadMoreListener(listener)
}

@BindingAdapter("app:enableLoadMore")
fun setEnableLoadMore(view:SmartRefreshLayout,enableLoadMore: Boolean){
    view.setEnableLoadMore(enableLoadMore)
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


