package com.kk.tongfu.wanandroid_kotlin.ui.system


import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kk.tongfu.wanandroid_kotlin.R
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * A simple [Fragment] subclass.
 */
class SystemFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system, container, false)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("SystemFragment","onCreate")

       /* main1()
        println(coroutines())
        main2()*/

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
    }


}
