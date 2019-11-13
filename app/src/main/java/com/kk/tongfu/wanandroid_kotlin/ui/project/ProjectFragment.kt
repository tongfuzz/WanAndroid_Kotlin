package com.kk.tongfu.wanandroid_kotlin.ui.project


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.ui.navigation.NavigationFragment

/**
 * A simple [Fragment] subclass.
 */
class ProjectFragment : Fragment() {

    companion object{
        private  var projectFragment: ProjectFragment?=null
        fun getInstance(): ProjectFragment {
            if(projectFragment==null){
                projectFragment= ProjectFragment()
            }
            return projectFragment!!
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project, container, false)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("ProjectFragment","onCreate")
    }


}
