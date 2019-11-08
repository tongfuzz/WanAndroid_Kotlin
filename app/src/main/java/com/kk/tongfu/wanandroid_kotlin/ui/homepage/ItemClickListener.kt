package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import android.view.View
import com.kk.tongfu.wanandroid_kotlin.viewmodel.HomePageAdapterViewModel

/**
 * Created by tongfu
 * on 2019-10-31
 * Desc:
 */

interface ItemClickListener<T>  {

   fun  onItemClick(view:View, item:T)

}