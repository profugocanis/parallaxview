package com.ijk.parallax

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

class ScrollViewParallax {

    var view: View? = null
    var scrollView: ScrollView? = null
    var viewOld: View? = null
    var isRecyclerViewExist = false
    //    internal var bottomView: View? = null
    private var isFirst = true

    fun setToolBar(toolbar: View) {
        val linearLayout = view?.findViewById<LinearLayout>(R.id.toolBarLinearLayout)
        if (toolbar.parent != null) {
            (toolbar.parent as ViewGroup).removeView(toolbar)
        }
        linearLayout?.addView(toolbar)
    }

    private var bottomViewHeight = 0

    fun setBottomViewFromBlur(bottomView: View?, context: Activity?) {
        val blurView = view?.findViewById<BlurView>(R.id.blurView)
        val decorView = context?.window?.decorView
        val rootView = decorView?.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBackground = decorView.background
        blurView?.setupWith(rootView)
            ?.setFrameClearDrawable(windowBackground)
            ?.setBlurAlgorithm(RenderScriptBlur(context))
            ?.setBlurRadius(20F)
            ?.setBlurAutoUpdate(true)
            ?.setHasFixedTransformationMatrix(true)
//        blurView.colo

        if (bottomView?.parent != null) {
            (bottomView.parent as ViewGroup).removeView(bottomView)
        }

        bottomView?.viewTreeObserver?.addOnGlobalLayoutListener {
            bottomViewHeight = bottomView.height
        }
        blurView?.addView(bottomView)
    }

    fun setBottomView(bottomView: View?) {

        val linearLayout = view?.findViewById<LinearLayout>(R.id.bottomViewLinearLayout)
        if (bottomView?.parent != null) {
            (bottomView.parent as ViewGroup).removeView(bottomView)
        }
        linearLayout?.addView(bottomView)
        bottomView?.viewTreeObserver?.addOnGlobalLayoutListener {
            bottomViewHeight = bottomView.height
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setContentViewForParallax(context: Activity) {
        var viewChildHeight = 0
        var scrollViewHeight = 0

        viewOld?.viewTreeObserver?.addOnGlobalLayoutListener {
            viewChildHeight = viewOld?.height ?: 0
        }

        scrollView?.viewTreeObserver?.addOnGlobalLayoutListener {
            scrollViewHeight = scrollView?.height ?: 0
        }

        val d = spToPx(1000F, context)


        this.scrollView?.viewTreeObserver?.addOnScrollChangedListener {
            toScrollChangedListener(
                viewChildHeight = viewChildHeight,
                scrollViewHeight = scrollViewHeight,
                d = d
            )
        }

        this.scrollView?.setOnTouchListener { _, event ->
            this.scrollView?.onTouchEvent(event)
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

        this.scrollView?.post {
            this.scrollView?.scrollTo(0, d)
        }
    }

    private var isFirstTop = false
    private var isFirstBottom = false

    private var isEvent1 = false

    private fun toScrollChangedListener(
        viewChildHeight: Int,
        scrollViewHeight: Int,
        d: Int
    ) {

//        loget("-------")
//        loget(scrollView!!.scrollY)
//        loget(d)

        loget(isEvent1)


        if (viewChildHeight >= scrollViewHeight) {

            if (!isEvent1 && scrollView!!.scrollY < d) {
                isFirstTop = false
            }

            if (scrollView!!.scrollY < d && isFirstTop && isEvent1) {
                isFirstTop = false
                this.scrollView?.scrollTo(0, this.scrollView?.scrollY!!)
                this.scrollView?.post {
                    this.scrollView?.smoothScrollTo(0, d)
                }
            }

            if (scrollView!!.scrollY > d) {
                isFirstTop = true
            }
            //////

            if (!isEvent1 && scrollView!!.scrollY > d + viewChildHeight + bottomViewHeight - scrollViewHeight) {
                isFirstBottom = false
            }

            if (scrollView!!.scrollY > d + viewChildHeight + bottomViewHeight - scrollViewHeight && isFirstBottom && isEvent1) {
                this.scrollView?.scrollTo(0, this.scrollView?.scrollY!!)
                this.scrollView?.post {
                    scrollView!!.smoothScrollTo(
                        0,
                        d + viewChildHeight + bottomViewHeight - scrollViewHeight
                    )
                }
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
        if (event == 2) {
            isEvent1 = false
        } else if (event == 1) {
            isEvent1 = true
        }

        if (event == 1) {
            if (viewChildHeight < scrollViewHeight) {
                scrollView?.smoothScrollTo(0, d)
            } else {
                if (scrollY < d) {
                    scrollView?.smoothScrollTo(0, d)
                }
                if (scrollY >= d + viewChildHeight + bottomViewHeight - scrollViewHeight) {
                    scrollView?.smoothScrollTo(
                        0,
                        d + viewChildHeight + bottomViewHeight - scrollViewHeight
                    )
                }
            }
        }
    }

//    private fun spToPx(context: Context): Int {
//        return TypedValue.applyDimension(
//            TypedValue.COMPLEX_UNIT_SP,
//            1000F,
//            context.resources.displayMetrics
//        ).toInt()
//    }

    fun setRecyclerViewFromParallax(recyclerView: RecyclerView) {
        this.isFirst = true
        recyclerView.isNestedScrollingEnabled = false
    }
}