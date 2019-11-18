package com.ijk.parallax.parallax_margin

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import com.ijk.parallax.Utils.POW_COEFICIENT
import com.ijk.parallax.Utils.getDY
import com.ijk.parallax.Utils.getTime
import com.ijk.parallax.parallax_margin.ParallaxViewMargin.Companion.isScrolling
import kotlin.math.pow

class ParallaxViewTop(private val scrollView: ScrollView, private val context: Activity) {

    private val scrollTopMargin: Int =
        (scrollView.layoutParams as LinearLayout.LayoutParams).topMargin

    private var dyFromAnimTop = 0

    var ifBottom = true

    private var rawY = 0F
    private var isAnimate = true

    /////
    private var firstY = 0F
    private var isFirstTouch = true
    var dy: Int = 0
    /////////

    fun initTopScroll(event: MotionEvent, v: View) {
        rawY = event.rawY
        ////////////////////////////////
        if (event.action == MotionEvent.ACTION_UP) {
            isFirstTouch = true
            isScrolling = false
            isAnimate = false
        }



        if (isFirstTouch && event.action == MotionEvent.ACTION_DOWN) {
            firstY = rawY + scrollView.scrollY
            isFirstTouch = false
            if (dyFromAnimTop - scrollTopMargin > 3) {
                firstY =
                    rawY + scrollView.scrollY - (dyFromAnimTop - scrollTopMargin).toDouble().pow(1 / POW_COEFICIENT).toInt()
            }
        }
////////////////////////////
        if (event.action == 6) {
            firstY = rawY - dyFromAnimTop.toDouble().pow(1 / POW_COEFICIENT).toFloat()
        }

        if (event.action == 5) {
            firstY = rawY - dyFromAnimTop.toDouble().pow(1 / POW_COEFICIENT).toFloat()
        }
//////////////////////////////
        dy = (rawY - firstY).toDouble().pow(POW_COEFICIENT).toInt()
        isScrolling = dy > 0

//        if (isScrolling && ifBottom) {
//            scrollView.smoothScrollTo(0, 0)
//        }

        if (event.action == MotionEvent.ACTION_MOVE && ifBottom) {
            val layoutParams = v.layoutParams as LinearLayout.LayoutParams
            layoutParams.topMargin = dy + scrollTopMargin
            dyFromAnimTop = dy
            v.layoutParams = layoutParams
        } else {
            isScrolling = false
            isAnimate = false
        }

        if (event.action == MotionEvent.ACTION_UP) {
            isAnimate = true
            animToTop(v, dyFromAnimTop)
        }
    }

    private fun animToTop(v: View?, from: Int) {
        Thread {
            var f = from.toFloat()
            while (f >= scrollTopMargin) {
                f -= getDY(f)
                context.runOnUiThread {
                    if (isAnimate) {
                        dyFromAnimTop = f.toInt()
                        val layoutParams = v?.layoutParams as LinearLayout.LayoutParams
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