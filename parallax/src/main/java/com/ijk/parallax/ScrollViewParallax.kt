package com.ijk.parallax

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

class ScrollViewParallax {

    var viewNew: View? = null
    var scrollView: ScrollView? = null
    var viewOld: View? = null

    private var toolBarViewHeight = 0

    fun setToolBarFromBlur(toolbar: View?, context: Activity?) {
        val blurView = viewNew?.findViewById<BlurView>(R.id.toolBarLinearLayout)
        if (toolbar?.parent != null) {
            (toolbar.parent as ViewGroup).removeView(toolbar)
        }
        blurView?.addView(toolbar)
        val decorView = context?.window?.decorView
        val rootView = decorView?.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBackground = decorView.background
        blurView?.setupWith(rootView)
            ?.setFrameClearDrawable(windowBackground)
            ?.setBlurAlgorithm(RenderScriptBlur(context))
            ?.setBlurRadius(20F)
            ?.setBlurAutoUpdate(true)
            ?.setHasFixedTransformationMatrix(true)

        toolbar?.viewTreeObserver?.addOnGlobalLayoutListener {
            toolBarViewHeight = toolbar.height
        }
    }

    fun setToolBar(toolbar: View?) {
        val linearLayout = viewNew?.findViewById<BlurView>(R.id.toolBarLinearLayout)
        if (toolbar?.parent != null) {
            (toolbar.parent as ViewGroup).removeView(toolbar)
        }
        linearLayout?.addView(toolbar)
        toolbar?.viewTreeObserver?.addOnGlobalLayoutListener {
            toolBarViewHeight = toolbar.height
        }
    }

    private var bottomViewHeight = 0

    fun setBottomViewFromBlur(bottomView: View?, context: Activity?) {
        val blurView = viewNew?.findViewById<BlurView>(R.id.bottomBlurView)
        val decorView = context?.window?.decorView
        val rootView = decorView?.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBackground = decorView.background
        blurView?.setupWith(rootView)
            ?.setFrameClearDrawable(windowBackground)
            ?.setBlurAlgorithm(RenderScriptBlur(context))
            ?.setBlurRadius(20F)
            ?.setBlurAutoUpdate(true)
            ?.setHasFixedTransformationMatrix(true)

        if (bottomView?.parent != null) {
            (bottomView.parent as ViewGroup).removeView(bottomView)
        }
        blurView?.addView(bottomView)
        bottomView?.viewTreeObserver?.addOnGlobalLayoutListener {
            bottomViewHeight = bottomView.height
        }
    }

    fun setBottomView(bottomView: View?) {
        val linearLayout = viewNew?.findViewById<BlurView>(R.id.bottomBlurView)
        if (bottomView?.parent != null) {
            (bottomView.parent as ViewGroup).removeView(bottomView)
        }
        linearLayout?.addView(bottomView)
        bottomView?.viewTreeObserver?.addOnGlobalLayoutListener {
            bottomViewHeight = bottomView.height
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initContentViewForParallax(context: Activity?) {
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
            this.scrollView?.scrollTo(0, d - toolBarViewHeight)
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
        if (viewChildHeight >= scrollViewHeight) {

            if (!isEvent1 && scrollView!!.scrollY < d - toolBarViewHeight) {
                isFirstTop = false
            }

            if (scrollView!!.scrollY < d - toolBarViewHeight && isFirstTop && isEvent1) {
                isFirstTop = false
                this.scrollView?.scrollTo(0, this.scrollView?.scrollY!!)
                this.scrollView?.post {
                    this.scrollView?.smoothScrollTo(0, d - toolBarViewHeight)
                }
            }

            if (scrollView!!.scrollY > d - toolBarViewHeight) {
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
                scrollView?.smoothScrollTo(0, d - toolBarViewHeight)
            } else {
                if (scrollY < d - toolBarViewHeight) {
                    scrollView?.smoothScrollTo(0, d - toolBarViewHeight)
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
}