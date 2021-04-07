package com.tommy.shen.module_public.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator


class BottomFabBehavior : CoordinatorLayout.Behavior<View> {

    private var isAnimatingOut = false //动画是否在进行

    constructor() : super()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0 //判断是否竖直滚动
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !isAnimatingOut && child.visibility == View.VISIBLE) {//往下滑
            scaleHide(child, viewPropertyAnimatorListener)
            onStateChangedListener?.invoke(false)
        } else if ((dyConsumed < 0 || dyUnconsumed < 0) && child.visibility != View.VISIBLE) {
            scaleShow(child)
            onStateChangedListener?.invoke(true)
        }
    }

    // 外部监听显示和隐藏。
    private var onStateChangedListener: ((isShow: Boolean) -> Unit)? = null

    fun setOnStateChangeListener(onStateChangedListener: (isShow: Boolean) -> Unit) {
        this.onStateChangedListener = onStateChangedListener
    }

    private val viewPropertyAnimatorListener: ViewPropertyAnimatorListener =
        object : ViewPropertyAnimatorListener {
            override fun onAnimationStart(view: View) {
                isAnimatingOut = true
            }

            override fun onAnimationEnd(view: View) {
                isAnimatingOut = false
                view.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(arg0: View) {
                isAnimatingOut = false
            }
        }

    // 显示view
    private fun scaleShow(
        view: View, viewPropertyAnimatorListener: ViewPropertyAnimatorListener? = null
    ) {
        view.visibility = View.VISIBLE
        ViewCompat.animate(view)
            .scaleX(1.0f)
            .scaleY(1.0f)
            .alpha(1.0f)
            .setDuration(400)
            .setListener(viewPropertyAnimatorListener)
            .setInterpolator(LinearOutSlowInInterpolator())
            .start()
    }

    // 隐藏view
    private fun scaleHide(
        view: View, viewPropertyAnimatorListener: ViewPropertyAnimatorListener
    ) {
        ViewCompat.animate(view)
            .scaleX(0.0f)
            .scaleY(0.0f)
            .alpha(0.0f)
            .setDuration(400)
            .setInterpolator(LinearOutSlowInInterpolator())
            .setListener(viewPropertyAnimatorListener)
            .start()
    }


}