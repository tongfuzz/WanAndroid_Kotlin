package com.kk.tongfu.wanandroid_kotlin


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.kk.tongfu.wanandroid_kotlin.ui.system.ChapterPageAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_system_detail.*

/**
 * A simple [Fragment] subclass.
 */
class SystemDetailFragment : DaggerFragment() {


    private val args: SystemDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.chapter?.apply {
            val chapterPageAdapter = ChapterPageAdapter(this, this@SystemDetailFragment)
            viewPage.adapter = chapterPageAdapter
            if(!this.children.isNullOrEmpty()){
               viewPage.offscreenPageLimit= children!!.size
            }
            TabLayoutMediator(tabLayout!!, viewPage) { tab, position ->
                tab.text = this.children?.get(position)?.name
            }.attach()
        }
    }


}
