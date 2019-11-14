package com.kk.tongfu.wanandroid_kotlin.ui

import android.os.Bundle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.kk.tongfu.wanandroid_kotlin.MainApplication
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.interfaces.ScrollTop
import com.kk.tongfu.wanandroid_kotlin.ui.homepage.HomePageFragment
import com.kk.tongfu.wanandroid_kotlin.ui.navigation.NavigationFragment
import com.kk.tongfu.wanandroid_kotlin.ui.project.ProjectFragment
import com.kk.tongfu.wanandroid_kotlin.ui.system.SystemFragment
import com.kk.tongfu.wanandroid_kotlin.ui.wechat.WeChatFragment
import com.kk.tongfu.wanandroid_kotlin.viewmodel.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : DaggerAppCompatActivity() {


    private val homePageFragment by lazy { HomePageFragment.getInstance() }
    private val systemFragment by lazy { SystemFragment.getInstance() }
    private val weChatFragment by lazy { WeChatFragment.getInstance() }
    private val navigationFragment by lazy { NavigationFragment.getInstance() }
    private val projectFragment by lazy { ProjectFragment.getInstance() }
    private val fragments = mapOf(
        R.id.homepage to homePageFragment,
        R.id.system to systemFragment,
        R.id.wechat to weChatFragment,
        R.id.navigation to navigationFragment,
        R.id.project to projectFragment
    )
    private val titles = mapOf(
        R.id.homepage to R.string.homepage,
        R.id.system to R.string.system,
        R.id.wechat to R.string.we_chat,
        R.id.navigation to R.string.navigation,
        R.id.project to R.string.project
    )
    private var currentId: Int = -1
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(
            this,
            SavedStateViewModelFactory(MainApplication.application!!, this)
        ).get(MainViewModel::class.java)
        initToolbar()
        initView()
        bottomNav.selectedItemId = R.id.homepage
        //todo 横竖屏切换需要处理
    }

    private fun initView() {
        nav_view.inflateHeaderView(R.layout.view_nav_header)
        initBottomNav()
        floating_button.setOnClickListener {
            fragments[currentId].apply {
                if (this is ScrollTop) {
                    this.scrollTop()
                }
            }
        }
    }


    private fun initBottomNav() {
        /*val navGraphIds = listOf(
            R.navigation.nav_homepage,
            R.navigation.nav_system,
            R.navigation.nav_wechat,
            R.navigation.nav_navigation,
            R.navigation.nav_project
        )
        val controller: LiveData<NavController>
        controller = bottomNav.setupWithNavController(
            navGraphIds,
            supportFragmentManager,
            R.id.fragment_content,
            intent
        )
        controller.observe(this, Observer {
            val appBarConfiguration = AppBarConfiguration(it.graph, drawerLayout)
            toolBar.setupWithNavController(it, appBarConfiguration)
        })
        currentNavController = controller*/

        bottomNav.setOnNavigationItemSelectedListener { menuItem ->

            val transaction = supportFragmentManager.beginTransaction()

            if (currentId != menuItem.itemId) {
                fragments[currentId]?.also {
                    transaction.hide(it)
                }

                fragments[menuItem.itemId]?.apply {
                    if (!this.isAdded) {
                        transaction.add(R.id.fragment_content, this)
                    }
                    transaction.show(this).commitAllowingStateLoss()
                    currentId = menuItem.itemId
                    titles[currentId]?.apply {
                        supportActionBar?.setTitle(this)
                    }
                }
            }

            true
        }

    }

    private fun initToolbar() {
        setSupportActionBar(toolBar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setShowHideAnimationEnabled(false)
        supportActionBar?.setTitle(R.string.homepage)

    }


}
