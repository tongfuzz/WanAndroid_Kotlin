package com.kk.tongfu.wanandroid_kotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kk.tongfu.wanandroid_kotlin.*
import com.kk.tongfu.wanandroid_kotlin.util.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {


    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState==null){
            initView()
        }
    }

    private fun initView() {
        nav_view.inflateHeaderView(R.layout.view_nav_header)
        initToolbar()
        initBottomNav()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        initView()
    }


    private fun initBottomNav() {

        val navGraphIds=listOf(R.navigation.nav_homepage,R.navigation.nav_system,R.navigation.nav_wechat,R.navigation.nav_navigation,R.navigation.nav_project)
        val controller = bottomNav.setupWithNavController(navGraphIds,supportFragmentManager,R.id.fragment_content,intent)
        controller.observe(this, Observer {
            val appBarConfiguration = AppBarConfiguration(it.graph, drawerLayout)
            toolBar.setupWithNavController(it,appBarConfiguration)
        })
        currentNavController=controller

    }

    private fun initToolbar() {
        setSupportActionBar(toolBar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setShowHideAnimationEnabled(false)
        supportActionBar?.setTitle(R.string.app_name)

    }


}
