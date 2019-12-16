package com.ijk.parallax.parallax_margin.recycler_view

import android.annotation.SuppressLint
import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.ijk.parallax.Utils.loget

class ParallaxViewMarginRecycler(private val recyclerView: RecyclerView, context: Activity) {

    private val parallaxViewTop = ParallaxViewTopRecycler(recyclerView, context)
    private val parallaxViewBottom = ParallaxViewBottomRecycler(recyclerView, context)

    companion object {
        var isScrolling = false
    }

    init {
        initScroll()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initScroll() {
        recyclerView.setOnTouchListener { v, event ->

//            if (parallaxViewTop.dy > 0) {
//                parallaxViewBottom.ifTop = false
//                parallaxViewTop.ifBottom = true
////                isScrolling = true
//            }


//            if (parallaxViewBottom.dy > 0) {
//                parallaxViewBottom.ifTop = true
//                parallaxViewTop.ifBottom = false
////                isScrolling = true
//            }

//            parallaxViewBottom.initBottomScroll(event, v)
            parallaxViewTop.initTopScroll(event, v)
            loget(isScrolling)
            isScrolling
//            true
        }
    }
}