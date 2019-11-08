package com.ijk.parallax

import android.annotation.SuppressLint
import android.content.Context
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
    private var isFirst = true
    var viewOld: View? = null
    var isRecyclerViewExist = false
    private var bottomView: View? = null

    fun setToolBar(toolbar: View) {
        val linearLayout = view?.findViewById<LinearLayout>(R.id.toolBarLinearLayout)
        if (toolbar.parent != null) {
            (toolbar.parent as ViewGroup).removeView(toolbar)
        }
        linearLayout?.addView(toolbar)
    }

    private var bottomViewHeight = 0

    fun setBottomView(bottomView: View) {
        val linearLayout = view?.findViewById<LinearLayout>(R.id.bottomViewLinearLayout)
        this.bottomView = bottomView
        if (bottomView.parent != null) {
            (bottomView.parent as ViewGroup).removeView(bottomView)
        }
        linearLayout?.addView(bottomView)

        bottomView.viewTreeObserver?.addOnGlobalLayoutListener {
            bottomViewHeight = bottomView.height
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setContentViewForParallax(context: AppCompatActivity) {

        var viewChildHeight = 0
        var scrollViewHeight = 0

        viewOld?.viewTreeObserver?.addOnGlobalLayoutListener {
            viewChildHeight = viewOld?.height ?: 0
        }

        scrollView?.viewTreeObserver?.addOnGlobalLayoutListener {
            scrollViewHeight = scrollView?.height ?: 0
        }

        val d = spToPx(context)

        this.scrollView?.viewTreeObserver?.addOnScrollChangedListener {
            toScrollChangedListener(
                viewChildHeight = viewChildHeight,
                scrollViewHeight = scrollViewHeight,
                d = d
            )
        }

        this.scrollView?.setOnTouchListener { _, event ->
            this.scrollView?.onTouchEvent(event)
            loget(event.eventTime)
            toEvent(
                event = event.action,
                viewChildHeight = viewChildHeight,
                scrollViewHeight = scrollViewHeight,
                d = d,
                scrollY = this.scrollView?.scrollY ?: 0,
                scrollView = this.scrollView
            )
            false
        }
    }

    private var isFirstTop = true
    private var isFirstBottom = true

    private fun toScrollChangedListener(
        viewChildHeight: Int,
        scrollViewHeight: Int,
        d: Int
    ) {
        if (isFirst && isRecyclerViewExist) {
            scrollView?.smoothScrollTo(0, d)
            isFirst = false
        }

        if (viewChildHeight >= scrollViewHeight) {
            if (scrollView!!.scrollY < d && isFirstTop) {
                scrollView!!.smoothScrollTo(0, d)
                isFirstTop = false
            }

            if (scrollView!!.scrollY > d) {
                isFirstTop = true
            }

            if (scrollView!!.scrollY > d + viewChildHeight + bottomViewHeight - scrollViewHeight && isFirstBottom) {
                scrollView!!.smoothScrollTo(
                    0,
                    d + viewChildHeight + bottomViewHeight - scrollViewHeight
                )
                isFirstBottom = false
            }

            if (scrollView!!.scrollY < d + viewChildHeight - scrollViewHeight) {
                isFirstBottom = true
            }
        }
    }

    private fun toEvent(
        event: Int,
        viewChildHeight: Int,
        scrollViewHeight: Int,
        d: Int,
        scrollY: Int,
        scrollView: ScrollView?
    ) {
        if (event == 1) {
            if (viewChildHeight < scrollViewHeight) {
                scrollView?.smoothScrollTo(0, d)
            } else {
                if (scrollY < d) {
                    scrollView?.smoothScrollTo(0, d)
                }
                if (scrollY > d + viewChildHeight - scrollViewHeight) {
                    scrollView?.smoothScrollTo(
                        0,
                        d + viewChildHeight + bottomViewHeight - scrollViewHeight
                    )
                }
            }
        }
    }

    private fun spToPx(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            1000F,
            context.resources.displayMetrics
        ).toInt()
    }

    fun setRecyclerViewFromParallax(recyclerView: RecyclerView) {
        this.isFirst = true
        recyclerView.isNestedScrollingEnabled = false
    }
}