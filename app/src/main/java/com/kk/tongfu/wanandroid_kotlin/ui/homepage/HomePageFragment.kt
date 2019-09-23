package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.databinding.FragmentHomePageBinding
import com.kk.tongfu.wanandroid_kotlin.service.model.Banner
import com.kk.tongfu.wanandroid_kotlin.util.viewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomePageFragment : DaggerFragment() {

    val TAG:String = "HomePageFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model:HomePageViewModel
    private lateinit var binding:FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model=viewModelProvider(viewModelFactory)
        binding = FragmentHomePageBinding.inflate(inflater, container, false).apply {
            lifecycleOwner=viewLifecycleOwner
            viewModel=model
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model.getBannerListObservable().observe(this,
            Observer<List<Banner>> {
                Log.e(TAG,Thread.currentThread().name)
                Log.e(TAG,it.size.toString())
            })
    }


}
