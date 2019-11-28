package com.kk.tongfu.wanandroid_kotlin.ui.navigation


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.databinding.FragmentNavigationBinding
import com.kk.tongfu.wanandroid_kotlin.ui.system.SystemFragment
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class NavigationFragment : DaggerFragment() {


    private lateinit var fragmentNavigationBinding: FragmentNavigationBinding


    companion object{
        private  var navigationFragment: NavigationFragment?=null
        fun getInstance(): NavigationFragment {
            if(navigationFragment==null){
                navigationFragment= NavigationFragment()
            }
            return navigationFragment!!
        }
    }

    @Inject
    lateinit var viewModel: NavigationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentNavigationBinding = FragmentNavigationBinding.inflate(inflater, container, false)
        return fragmentNavigationBinding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("NavigationFragment","onCreate")
        viewModel.apply {

        }
    }

}
