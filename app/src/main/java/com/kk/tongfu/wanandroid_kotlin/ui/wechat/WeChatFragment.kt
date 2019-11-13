package com.kk.tongfu.wanandroid_kotlin.ui.wechat


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.ui.project.ProjectFragment

/**
 * A simple [Fragment] subclass.
 */
class WeChatFragment : Fragment() {

    companion object{
        private  var wechatFragment: WeChatFragment?=null
        fun getInstance(): WeChatFragment {
            if(wechatFragment==null){
                wechatFragment= WeChatFragment()
            }
            return wechatFragment!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_we_chat, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("WeChatFragment","onCreate")
    }


}
