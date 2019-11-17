package com.ijk.parallax

import android.annotation.SuppressLint
import android.app.Activity
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import com.ijk.parallax.Utils.getDY
import com.ijk.parallax.Utils.getTime
import com.ijk.parallax.Utils.loget
import kotlin.math.pow

class ParallaxViewTop(private val scrollView: ScrollView) {

    private val scrollTopMargin: Int =
        (scrollView.layoutParams as LinearLayout.LayoutParams).topMargin

    init {
        initScroll()
    }

    private var dyFromAnim = 0


    var rawY = 0F
    var isAnimate = true
//    var rawDY = 0F

    @SuppressLint("ClickableViewAccessibility")
    private fun initScroll() {
        var firstY = 0F
        var isFirstTouch = true
        var isFirstTouch2 = true
        var dy: Int
        var isScrolling: Boolean = false
        var dyFromAnim2 = 0
        var rawDY = 0F
        var scrollAdd = 0

        var isDownScroll = false

        scrollView.setOnTouchListener { v, event ->
            rawY = event.rawY

            if (event.action == MotionEvent.ACTION_UP) {
                isFirstTouch = true
                isScrolling = false
                isAnimate = false
            }



            if (isFirstTouch && event.action == MotionEvent.ACTION_DOWN) {
                firstY = rawY + scrollView.scrollY
                isFirstTouch = false
                dyFromAnim2 = dyFromAnim
            }

            if (dyFromAnim2 > -1) {
                dyFromAnim2 = (rawY - firstY).toDouble().pow(0.9).toInt()
            } else {
                dyFromAnim2 = -1
            }

            if (event.action == 261) {
//                firstY = rawY
//                rawDY += rawY
            }

            if (event.action == 262) {
//                firstY = rawY
//                firstY -= rawY
            }

            if (event.action == 6) {
//                rawDY += firstY
//                firstY -= rawDY - firstY
            }

            dy = (rawY - firstY).toDouble().pow(0.85).toInt() + dyFromAnim2


//            dyFromAnim2 = (rawY - firstY).toDouble().pow(0.8).toInt()

            isScrolling = dy > 0

            if (event.action == MotionEvent.ACTION_MOVE) {
                val layoutParams = v?.layoutParams as LinearLayout.LayoutParams
                layoutParams.topMargin = dy
                dyFromAnim = dy
                v.layoutParams = layoutParams
            } else {
                isScrolling = false
                isAnimate = false
            }

//            if (isDownScroll && event.action == MotionEvent.ACTION_MOVE && scrollView.scrollY == 0) {
//                val layoutParams = v.layoutParams as LinearLayout.LayoutParams
//                layoutParams.topMargin = scrollTopMargin
//                scrollView.layoutParams = layoutParams
////                isDownScroll = false
//            }
//
//            if (event.action == MotionEvent.ACTION_DOWN && scrollView.scrollY > 0) {
//                isDownScroll = true
//            }
//
//            if (event.action == MotionEvent.ACTION_UP){
//                isDownScroll = false
//            }

//            if (scrollView.scrollY == 0) {
////                isFirstTouch = true
//                isScrolling = false
////                isAnimate = false
//            }

            if (event.action == MotionEvent.ACTION_UP) {
//                if (scrollView.scrollY == 0) {
                isAnimate = true
                animToTop(v, dyFromAnim)
//                }
            }
            isScrolling
        }
    }

    private fun animToTop(v: View?, from: Int) {
        Thread {
            var f = from.toFloat()
            while (f >= scrollTopMargin) {
                f -= getDY(f)
                (v?.context as Activity).runOnUiThread {
                    if (isAnimate) {
                        dyFromAnim = f.toInt()
                        val layoutParams = v.layoutParams as LinearLayout.LayoutParams
                        layoutParams.topMargin = f.toInt()
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