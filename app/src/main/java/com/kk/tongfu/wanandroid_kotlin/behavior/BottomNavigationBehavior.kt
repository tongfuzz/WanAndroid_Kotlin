package com.kk.tongfu.wanandroid_kotlin.behavior

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

/**
 * Created by tongfu
 * on 2019-10-24
 * Desc: 底部导航栏动画
 */

class BottomNavigationBehavior constructor(context: Context, attributeSet: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attributeSet) {

    private var inAnimator: ObjectAnimator? = null
    private var outAnimator: ObjectAnimator? = null


    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (dy > 0 && child.translationY.toInt() == 0) {
            //说明子view向上进行了滚动，但是底部导航栏并没有滚动
            if (outAnimator == null) {
                outAnimator = ObjectAnimator.ofFloat(
                    child,
                    "translationY",
                    0.toFloat(),
                    child.height.toFloat()
                ).setDuration(300)
            }
            outAnimator?.apply {
                if(!isRunning) start()
            }
        } else if (dy < 0 && child.translationY.toInt() == child.height) {
            if (inAnimator == null) {
                inAnimator = ObjectAnimator.ofFloat(
                    child,
                    "translationY",
                    child.height.toFloat(),
                    0.toFloat()
                ).setDuration(300)
            }

            inAnimator?.apply {
                if(!isRunning) start()
            }
        }

    }
}