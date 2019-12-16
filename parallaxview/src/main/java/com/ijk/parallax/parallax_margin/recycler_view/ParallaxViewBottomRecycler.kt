package com.ijk.parallax.parallax_margin.recycler_view

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.RecyclerView
import com.ijk.parallax.Utils.POW_COEFICIENT
import com.ijk.parallax.Utils.getDY
import com.ijk.parallax.Utils.getTime
import com.ijk.parallax.parallax_margin.ParallaxViewMargin.Companion.isScrolling
import kotlin.math.pow

class ParallaxViewBottomRecycler(
    private val scrollView: RecyclerView,
    private val context: Activity
) {

    private val scrollBottomMargin: Int =
        (scrollView.layoutParams as LinearLayout.LayoutParams).bottomMargin

    var ifTop = true

    private var dyFromAnimBottom = 0
    private var isAnimate = true

    private var rawY = 0F
    private var firstY = 0F
    private var isFirstTouch = true
    //        var isFirstGetMaxScroll = false
    var dy: Int = 0
    //    private var isScrolling = false
    private var scrollViewHeight = 0
    private var maxScrollViewPosition = 0
    private var maxScrollViewHeight = 0

    init {
        val viewGroup = scrollView.parent as ViewGroup
        this.scrollView.viewTreeObserver?.addOnGlobalLayoutListener {
            scrollViewHeight = this.scrollView.height
            maxScrollViewPosition = scrollView.getChildAt(0).height

            if (scrollViewHeight == maxScrollViewPosition) {
                maxScrollViewHeight = viewGroup.height - maxScrollViewPosition
            }
        }
    }

    fun initBottomScroll(event: MotionEvent, v: View) {
        rawY = event.rawY
        if (event.action == MotionEvent.ACTION_UP) {
            isFirstTouch = true
            isScrolling = false
            isAnimate = false
        }

        val view = scrollView.getChildAt(scrollView.childCount - 1) as View
        var diff = view.bottom - (scrollView.height + scrollView.scrollY)

        if (diff != 0) {
            diff += 30
        }

        if (isFirstTouch && event.action == MotionEvent.ACTION_DOWN) {
            firstY = rawY - diff + maxScrollViewHeight
            isFirstTouch = false

            if (dyFromAnimBottom - scrollBottomMargin > 3) {
                firstY =
                    rawY - diff + maxScrollViewHeight + (dyFromAnimBottom - scrollBottomMargin).toDouble().pow(
                        1 / POW_COEFICIENT
                    ).toInt()
            }
        }

        ////////////////////////////
        if (event.action == 6) {
            firstY = rawY - diff + dyFromAnimBottom.toDouble().pow(1 / POW_COEFICIENT).toFloat()
        }

        if (event.action == 5) {
            firstY = rawY - diff + dyFromAnimBottom.toDouble().pow(1 / POW_COEFICIENT).toFloat()
        }
        /////////////////////////////

        dy = (firstY - rawY).toDouble().pow(POW_COEFICIENT).toInt()
        isScrolling = dy > 0

        if (isScrolling && ifTop) {
            scrollView.scrollToPosition(0)
            scrollView.doOnPreDraw {
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
//                scrollView.scrollToPosition(31)
//                scrollView.scr
            }
        }

        if (event.action == MotionEvent.ACTION_MOVE && ifTop) {
            val layoutParams = v.layoutParams as LinearLayout.LayoutParams
            layoutParams.bottomMargin = dy + scrollBottomMargin
            dyFromAnimBottom = dy
            v.layoutParams = layoutParams

        } else {
            isScrolling = false
            isAnimate = false
        }

        if (event.action == MotionEvent.ACTION_UP) {
            isAnimate = true
            animToBottom(v, dyFromAnimBottom)
        }
    }

    private fun animToBottom(v: View?, from: Int) {
        Thread {
            var f = from.toFloat()
            while (f >= scrollBottomMargin) {
                f -= getDY(f)
                context.runOnUiThread {
                    if (isAnimate) {
                        dyFromAnimBottom = f.toInt()
                        val layoutParams = v?.layoutParams as LinearLayout.LayoutParams
                        layoutParams.bottomMargin = f.toInt()
                        scrollView.layoutParams = layoutParams
                    } else {
                        f = -1f
                    }
                }
                Thread.sleep(getTime(f.toInt()), 50)
//                Thread.sleep(getTime(f.toInt()))
            }
        }.start()
    }
}
