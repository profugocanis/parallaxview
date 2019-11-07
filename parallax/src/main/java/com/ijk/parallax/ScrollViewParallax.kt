package com.ijk.parallax

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ScrollViewParallax {

    var view: View? = null
    var scrollView: ScrollView? = null

    fun setToolBar(toolbar: View) {
        val linearLayout = view?.findViewById<LinearLayout>(R.id.toolBarLinearLayout)

        if (toolbar.parent != null) {
            (toolbar.parent as ViewGroup).removeView(toolbar)
        }
        linearLayout?.addView(toolbar)
    }

    fun setBottomView(bottomView: View) {
        val linearLayout = view?.findViewById<LinearLayout>(R.id.bottomViewLinearLayout)

        if (bottomView.parent != null) {
            (bottomView.parent as ViewGroup).removeView(bottomView)
        }

        linearLayout?.addView(bottomView)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setContentViewForParallax(viewChildRes: Int, context: AppCompatActivity) {
        val viewChild = context.layoutInflater.inflate(viewChildRes, null)

        val view = context.layoutInflater.inflate(R.layout.scroll_parallax, null)

        this.view = view

        view.background = viewChild.background
        val scrollView = view.findViewById<ScrollView>(R.id.scrollViewParallax)
        this.scrollView = scrollView
        val linearLayoutScroll = view.findViewById<LinearLayout>(R.id.linearLayoutScroll)

        linearLayoutScroll.addView(viewChild)

        val bigView = View(context)
        bigView.setBackgroundResource(R.color.trans)
        viewChild.setBackgroundResource(R.color.trans)

        bigView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            spToPx(1000F, context)
        )
        linearLayoutScroll.addView(bigView)
        context.setContentView(view)

        var viewChildHeight = 0
        var scrollViewHeight = 0

        viewChild.viewTreeObserver.addOnGlobalLayoutListener {
            viewChildHeight = viewChild.height
        }

        scrollView.viewTreeObserver.addOnGlobalLayoutListener {
            scrollViewHeight = scrollView.height
        }

        val d = spToPx(1000F, context)

        var isFirstTop = true
        var isFirstBotton = true

        scrollView.scrollBarSize = 3

        var isFirst = true

        scrollView.viewTreeObserver.addOnScrollChangedListener {

            if (isFirst) {
                scrollView.smoothScrollTo(0, d)
                isFirst = false
            }

            if (viewChildHeight > scrollViewHeight) {
                if (scrollView.scrollY < d && isFirstTop) {
                    scrollView.smoothScrollTo(0, d)
                    isFirstTop = false
                }

                if (scrollView.scrollY > d) {
                    isFirstTop = true
                }

                if (scrollView.scrollY > d + viewChildHeight - scrollViewHeight && isFirstBotton) {
                    scrollView.smoothScrollTo(0, d + viewChildHeight - scrollViewHeight)
                    isFirstBotton = false
                }

                if (scrollView.scrollY < d + viewChildHeight - scrollViewHeight) {
                    isFirstBotton = true
                }
            }
        }

        scrollView.setOnTouchListener { v, event ->
            scrollView.onTouchEvent(event)
            toEvent(
                event = event.action,
                viewChildHeight = viewChildHeight,
                scrollViewHeight = scrollViewHeight,
                d = d,
                scrollY = scrollView.scrollY,
                scrollView = scrollView
            )
            false
        }
    }

    private fun toEvent(
        event: Int,
        viewChildHeight: Int,
        scrollViewHeight: Int,
        d: Int,
        scrollY: Int,
        scrollView: ScrollView
    ) {
        if (event == 1) {
            if (viewChildHeight < scrollViewHeight) {
                scrollView.smoothScrollTo(0, d)
            } else {
                if (scrollY < d) {
                    scrollView.smoothScrollTo(0, d)
                }
                if (scrollY > d + viewChildHeight - scrollViewHeight) {
                    scrollView.smoothScrollTo(0, d + viewChildHeight - scrollViewHeight)
                }
            }
        }
    }

    fun loget(data: Any) {
        Log.d("IJKAPP", "ijk: $data")
    }

    private fun spToPx(sp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context.resources.displayMetrics
        ).toInt()
    }

    fun setRecyclerViewFromParallax(recyclerView: RecyclerView) {
        recyclerView.isNestedScrollingEnabled = false
    }


}


