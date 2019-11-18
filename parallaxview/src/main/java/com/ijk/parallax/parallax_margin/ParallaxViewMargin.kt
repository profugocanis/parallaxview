package com.ijk.parallax.parallax_margin

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.ScrollView

class ParallaxViewMargin(private val scrollView: ScrollView, context: Activity) {

    private val parallaxViewTop = ParallaxViewTop(scrollView, context)
    private val parallaxViewBottom = ParallaxViewBottom(scrollView, context)

    companion object {
        var isScrolling = false
    }

    init {
        initScroll()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initScroll() {
        scrollView.setOnTouchListener { v, event ->

            if (parallaxViewTop.dy == 0) {
                parallaxViewBottom.initBottomScroll(event, v)
            }

            if (parallaxViewBottom.dy == 0) {
                parallaxViewTop.initTopScroll(event, v)
            }

//            parallaxViewBottom.initBottomScroll(event, v)
//            parallaxViewTop.initTopScroll(event, v)
            isScrolling
        }
    }
}