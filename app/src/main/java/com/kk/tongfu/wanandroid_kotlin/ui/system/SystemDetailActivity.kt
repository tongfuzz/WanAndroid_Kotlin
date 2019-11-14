package com.kk.tongfu.wanandroid_kotlin.ui.system

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavArgument
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.SystemDetailFragmentArgs
import com.kk.tongfu.wanandroid_kotlin.service.model.Chapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.acvitity_system_detail.*

/**
 * Created by tongfu
 * on 2019-11-13
 * Desc:
 */

class SystemDetailActivity : DaggerAppCompatActivity() {


    companion object {
        const val CHAPTER_ITEM="chapter"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acvitity_system_detail)
        //todo 此处用法需要文档记录一下
        findNavController(R.id.system_detail_host_fragment).setGraph(R.navigation.nav_system_detail,intent.extras)
        //toolBar.setupWithNavController(findNavController(R.id.system_detail_host_fragment))
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        intent?.extras?.takeIf { intent?.extras?.containsKey(CHAPTER_ITEM)?:false }.apply {
            supportActionBar?.title=this?.getParcelable<Chapter>(CHAPTER_ITEM)?.name
        }

    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.takeIf { item.itemId==android.R.id.home }.apply {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}