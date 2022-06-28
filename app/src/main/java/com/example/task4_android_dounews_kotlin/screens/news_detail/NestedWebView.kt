package com.example.task4_android_dounews_kotlin.screens.news_detail

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView
import androidx.core.view.MotionEventCompat
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper

class NestedWebView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    WebView(context), NestedScrollingChild {

    private var mLastY: Int = 0
    private final val mScrollOffset: IntArray? = IntArray(2)
    private final val mScrollConsumed: IntArray? = IntArray(2)
    private var mNestedOffsetY = 0
    private var mChildHelper: NestedScrollingChildHelper? = null

    init {
        mChildHelper = NestedScrollingChildHelper(this)
        isNestedScrollingEnabled = true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var returnValue = false

        val myEvent = MotionEvent.obtain(event)
        val action = MotionEventCompat.getActionMasked(myEvent)
        if (action == MotionEvent.ACTION_DOWN) mNestedOffsetY = 0
        val eventY = myEvent.y.toInt()
        myEvent.offsetLocation(0F, mNestedOffsetY.toFloat())

        when (action) {
            MotionEvent.ACTION_MOVE -> {
                var deltaY = mLastY - eventY

                //NestedPreScroll
                if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                    deltaY -= mScrollConsumed!![1]
                    mLastY = eventY - mScrollOffset!![1]
                    myEvent.offsetLocation(0F, (-mScrollOffset[1]).toFloat())
                    mNestedOffsetY += mScrollOffset[1]
                }
                returnValue = super.onTouchEvent(event)

                // NestedScroll
                if(dispatchNestedScroll(0, mScrollOffset!![1], 0, deltaY, mScrollOffset)) {
                    myEvent.offsetLocation(0F, mScrollOffset[1].toFloat())
                    mNestedOffsetY += mScrollOffset[1]
                    mLastY -= mScrollOffset[1]
                }
            }
            MotionEvent.ACTION_DOWN -> {
                this.parent.requestDisallowInterceptTouchEvent(true)
                returnValue = super.onTouchEvent(event)
            }
        }
        return returnValue
    }

    // Nested Scroll implements
    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mChildHelper!!.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return mChildHelper!!.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return mChildHelper!!.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        mChildHelper!!.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return mChildHelper!!.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        return mChildHelper!!.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        return mChildHelper!!.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return mChildHelper!!.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return mChildHelper!!.dispatchNestedPreFling(velocityX, velocityY)
    }

}