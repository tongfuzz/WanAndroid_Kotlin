package com.kk.tongfu.wanandroid_kotlin.ui.system


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.databinding.FragmentSystemBinding
import com.kk.tongfu.wanandroid_kotlin.interfaces.ScrollTop
import com.kk.tongfu.wanandroid_kotlin.service.RefreshState
import com.kk.tongfu.wanandroid_kotlin.ui.homepage.ItemClickListener
import com.kk.tongfu.wanandroid_kotlin.util.toast
import com.kk.tongfu.wanandroid_kotlin.util.viewModelProvider
import com.kk.tongfu.wanandroid_kotlin.viewmodel.ChapterViewModel
import com.kk.tongfu.wanandroid_kotlin.viewmodel.SystemViewModel
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SystemFragment : DaggerFragment(),ScrollTop {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var systemViewModel: SystemViewModel
    private lateinit var binding: FragmentSystemBinding
    private var systemView: View? = null
    private var listener=object:ItemClickListener<ChapterViewModel>{

        override fun onItemClick(view: View, item: ChapterViewModel) {

        }
    }

    private var refreshListener= OnRefreshListener { systemViewModel.refreshSystemListData() }
    private var adapter=SystemAdapter(listener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (systemView == null) {
            binding = FragmentSystemBinding.inflate(inflater, container, false).apply {
                systemView = root
            }
        }
        return systemView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        systemViewModel = viewModelProvider(viewModelFactory)
        binding.apply {
            lifecycleOwner=viewLifecycleOwner
            viewModel=systemViewModel
            recyclerView.adapter=adapter
            refreshLayout.setRefreshHeader(ClassicsHeader(context))
            refreshLayout.setOnRefreshListener(refreshListener)
        }
        systemViewModel.systemList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        systemViewModel.refreshState.observe(this, Observer {
            when (it) {
                RefreshState.LOADING_ERROR, RefreshState.LOADING_NO_MORE_DATA -> toast(R.string.no_more_data)
                RefreshState.REFRESHING_ERROR -> toast(R.string.failed_to_refresh)
                RefreshState.LOADING_NO_NETWORK,RefreshState.REFRESHING_NO_NETWORK->toast(R.string.no_network_and_check)
                else -> {
                }
            }
        })

        systemViewModel.toastStr.observe(this, Observer {
            it?.apply {
                toast(this)
            }
        })

    }

    override fun scrollTop() {
        binding?.recyclerView?.smoothScrollToPosition(0)
    }


    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("SystemFragment","onCreate")

       *//* main1()
        println(coroutines())
        main2()*//*

        main3()

    }

    private fun main4() {
        runBlocking {
            launch {
                delay(200L)
                println("Task from runBlocking")
            }

            coroutineScope {
                // 创建一个协程作用域,创建一个协程作用域并且在所有已启动子协程执行完毕之前不会结束
                launch {
                    delay(500L)
                    println("Task from nested launch")
                }

                delay(100L)
                println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
            }

            println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
        }
    }

    private fun main1() {
        GlobalScope.launch {
            //delay 是一个特殊的挂起函数，它不会造成线程阻塞，但是会挂起协程，并且只能在协程中使用
            delay(1000L)
            println("world")
            println(Thread.currentThread().name)
        }

        //阻塞线程
        println("hello ")
        Thread.sleep(2000L)

        //显示使用runBlocking协程构建器来阻塞
        runBlocking {
            delay(2000L)
            println("tongfu ")
        }
    }

    fun coroutines():String= runBlocking<String> {

        GlobalScope.launch {
            delay(1000L)
            println("world")
        }
        println("hello ")
        delay(2000L)
        "haha"
    }

    fun main2()= runBlocking {
        val job = GlobalScope.launch {
            delay(5000L)
            println("world!")
        }
        println("hello ,")
        job.join()
    }

    fun main3()=GlobalScope.launch {
        val time = measureTimeMillis {
            val sum = withContext(Dispatchers.IO) {
                println("sum method invoked")
                val one = async { one() }
                val two = async { two() }
                println("sum method invoked finish")
                println("await() finished")
                one.await() + two.await()
            }
            println("这两个方法反追之的和为：${sum}")
        }

        println("执行耗时：${time}")

    }

    suspend fun one():Int{
        println("method one invoked")
        delay(3000)
        return 1
    }

    suspend fun two():Int{
        println("method two invoked")
        delay(3000)
        return 2
    }*/


}
