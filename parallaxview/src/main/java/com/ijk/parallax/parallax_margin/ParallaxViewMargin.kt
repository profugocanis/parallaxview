package com.ijk.parallax.parallax_margin

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView

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

            if (parallaxViewTop.dy > 0) {
                parallaxViewBottom.ifTop = false
                parallaxViewTop.ifBottom = true
            }

            if (parallaxViewBottom.dy > 0) {
                parallaxViewBottom.ifTop = true
                parallaxViewTop.ifBottom = false
            }

            parallaxViewBottom.initBottomScroll(event, v)
            parallaxViewTop.initTopScroll(event, v)
            isScrolling
        }
    }
}