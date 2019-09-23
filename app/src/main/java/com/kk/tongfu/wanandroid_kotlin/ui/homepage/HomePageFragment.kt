package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.databinding.FragmentHomePageBinding
import dagger.android.support.DaggerFragment

class HomePageFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homePageBinding = FragmentHomePageBinding.inflate(inflater, container, false)
        return homePageBinding.root
    }

}
