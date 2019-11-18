package com.ijk.parallax.parallax_margin

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.view.doOnNextLayout
import com.ijk.parallax.Utils.POW_COEFICIENT
import com.ijk.parallax.Utils.getDY
import com.ijk.parallax.Utils.getTime
import com.ijk.parallax.Utils.loget
import com.ijk.parallax.parallax_margin.ParallaxViewMargin.Companion.isScrolling
import kotlin.math.pow

class ParallaxViewBottom(private val scrollView: ScrollView, private val context: Activity) {

    private val scrollBottomMargin: Int =
        (scrollView.layoutParams as LinearLayout.LayoutParams).bottomMargin

//    init {
//        initScroll()
//    }

    var dyFromAnimBottom = 0
    var isAnimate = true

    var rawY = 0F
    var firstY = 0F
    var isFirstTouch = true
    //        var isFirstGetMaxScroll = false
    var dy: Int = 0
    //    private var isScrolling = false
    var scrollViewHeight = 0
    var maxScrollViewPosition = 0
    var maxScrollViewHeight = 0
    var diff = 0

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

//            if (diff == 0) {
//                isFirstGetMaxScroll = true
//            }


        if (diff != 0) {
            diff += 30
        }

        if (isFirstTouch && event.action == MotionEvent.ACTION_DOWN) {
            firstY = rawY - diff + maxScrollViewHeight
            isFirstTouch = false

            if (dyFromAnimBottom - scrollBottomMargin > 3) {
                firstY =
                    rawY - diff + (dyFromAnimBottom - scrollBottomMargin).toDouble().pow(1 / POW_COEFICIENT).toInt()
            }
        }

        ////////////////////////////
        if (event.action == 6) {
            firstY = rawY + dyFromAnimBottom.toDouble().pow(1 / POW_COEFICIENT).toFloat()
        }

        if (event.action == 5) {
            firstY = rawY + dyFromAnimBottom.toDouble().pow(1 / POW_COEFICIENT).toFloat()
        }
//////////////////////////////

        dy = (firstY - rawY).toDouble().pow(POW_COEFICIENT).toInt()
        isScrolling = dy > 0

//        if (isScrolling) {
//            scrollView.doOnPreDraw {
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
//            }
//        }

        if (isScrolling) {
            scrollView.doOnNextLayout {
                scrollView.smoothScrollTo(0, maxScrollViewPosition)
            }
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
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
            }
        }.start()
    }
}