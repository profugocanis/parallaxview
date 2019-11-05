package com.ijk.parallaxijk

import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
fun AppCompatActivity.setContentViewForParallax(viewChildRes: Int) {
    val viewChild = this.layoutInflater.inflate(viewChildRes, null)


    val view = this.layoutInflater.inflate(R.layout.scroll_parallax, null)

    view.background = viewChild.background
    val scrollView = view.findViewById<ScrollView>(R.id.scrollViewParallax)
    val linearLayoutScroll = view.findViewById<LinearLayout>(R.id.linearLayoutScroll)


    linearLayoutScroll.addView(viewChild)

    val bigView = View(this)
    bigView.setBackgroundResource(R.color.trans)
    viewChild.setBackgroundResource(R.color.trans)

    bigView.layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        spToPx(1000F, this)
    )
    linearLayoutScroll.addView(bigView)


    this.setContentView(view)

    scrollView.setOnTouchListener { v, event ->
        if (event.action == 1) {

            (v as ScrollView).onTouchEvent(event)
            val d = spToPx(1000F, this)

//                if (v.scrollY < d) {
//                    v.onTouchEvent(event)
            v.smoothScrollTo(0, d)
//                }
        }
        false
    }
}

fun spToPx(sp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        context.resources.displayMetrics
    ).toInt()
}