package com.ijk.parallax

import android.annotation.SuppressLint
import android.app.Activity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.view.doOnPreDraw
import com.ijk.parallax.Utils.getDY
import com.ijk.parallax.Utils.getTime
import kotlin.math.pow

class ParallaxViewBottom(private val scrollView: ScrollView) {

    private val scrollBottomMargin: Int =
        (scrollView.layoutParams as LinearLayout.LayoutParams).bottomMargin

    init {

        initScroll()
    }

    var dyFromAnimBottom = 0
    var isAnimate = true

    @SuppressLint("ClickableViewAccessibility")
    private fun initScroll() {
        var dyFromAnim2 = 0
        var rawY = 0F
        var firstY = 0F
        var isFirstTouch = true
        var isFirstGetMaxScroll = false
        var dy: Int
        var isScrolling: Boolean
        var scrollViewHeight = 0
        var maxScrollViewPos = 0
        var maxScrollViewHeight = 0

        val viewGroup = scrollView.parent as ViewGroup

        this.scrollView.viewTreeObserver?.addOnGlobalLayoutListener {
            scrollViewHeight = this.scrollView.height
            maxScrollViewPos = scrollView.getChildAt(0).height


            if (scrollViewHeight == maxScrollViewPos) {
                maxScrollViewHeight = maxScrollViewPos
                maxScrollViewHeight = viewGroup.height - maxScrollViewPos
            }
        }

        scrollView.setOnTouchListener { v, event ->

            rawY = event.rawY
            if (event.action == MotionEvent.ACTION_UP) {
                isFirstTouch = true
                isScrolling = false
                isAnimate = false
            }

            val view = scrollView.getChildAt(scrollView.childCount - 1) as View
            val diff = view.bottom - (scrollView.height + scrollView.scrollY)

            if (diff == 0) {
                isFirstGetMaxScroll = true
            }

            if (isFirstTouch && event.action == MotionEvent.ACTION_DOWN) {
                firstY = rawY - diff + maxScrollViewHeight
                isFirstTouch = false
                dyFromAnim2 = dyFromAnimBottom
                isFirstGetMaxScroll = false
            }

            dy = (firstY - rawY).toDouble().pow(0.9).toInt() + dyFromAnim2

            if (dy > 0 && event.action == MotionEvent.ACTION_MOVE && isFirstGetMaxScroll) {
                isScrolling = true

                scrollView.doOnPreDraw {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                }

                val layoutParams = v
                    ?.layoutParams as LinearLayout.LayoutParams
                layoutParams.bottomMargin = dy
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
            isScrolling
        }
    }

    private fun animToBottom(v: View?, from: Int) {
        Thread {
            var f = from.toFloat()
            while (f >= scrollBottomMargin) {
                f -= getDY(f)
                dyFromAnimBottom = f.toInt()
                Thread.sleep(getTime(f.toInt()), 50)
                (v?.context as Activity).runOnUiThread {
                    if (isAnimate) {
                        val layoutParams = v.layoutParams as LinearLayout.LayoutParams
                        layoutParams.bottomMargin = f.toInt()
                        scrollView.layoutParams = layoutParams
                    } else {
                        f = -1f
                    }
                }
            }
        }.start()
    }
}