package com.ijk.parallax

import android.annotation.SuppressLint
import android.app.Activity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import com.ijk.parallax.Utils.POW_COEFFICIENT
import com.ijk.parallax.Utils.spToPx
import kotlin.math.abs
import kotlin.math.pow

class ScrollViewParallax {

    var viewNew: View? = null
    var scrollView: ScrollView? = null
    var viewOld: View? = null

    private var isFirstTop = false
    private var isFirstBottom = false
    private var isEvent1 = false
    private var toolBarViewHeight = 0
    private var bottomViewHeight = 0

    fun setToolBar(toolbar: View?) {
        val linearLayout = viewNew?.findViewById<LinearLayout>(R.id.toolBarLinearLayout)
        if (toolbar?.parent != null) {
            (toolbar.parent as ViewGroup).removeView(toolbar)
        }
        linearLayout?.addView(toolbar)
        toolbar?.viewTreeObserver?.addOnGlobalLayoutListener {
            toolBarViewHeight = toolbar.height
        }
    }

    fun setBottomView(bottomView: View?) {
        val linearLayout = viewNew?.findViewById<LinearLayout>(R.id.bottomBlurView)
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

        this.viewOld?.viewTreeObserver?.addOnGlobalLayoutListener {
            viewChildHeight = this.viewOld?.height ?: 0
        }

        this.scrollView?.viewTreeObserver?.addOnGlobalLayoutListener {
            scrollViewHeight = this.scrollView?.height ?: 0
        }

        val d = spToPx(1_000F, context)

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

            scrollTop(event, d)
            if (viewChildHeight >= scrollViewHeight) {
                scrollBottom(event, d, viewChildHeight, scrollViewHeight)
            } else {
                scrollLittleTopSpeed(event, d)
            }

            false
        }

        this.scrollView?.post {
            this.scrollView?.scrollTo(0, d - toolBarViewHeight)
        }
    }

    private var yFirstTouchBottom = 0F
    private var isYFirstTouchBottom = true
    private var isYFirstTouchBottom2 = true

    private var yFirstTouchLittleTopBottom = 0F
    private var isYFirstTouchLittleTopBottom = true

    private var yFirstTouchTop = 0F
    private var isYFirstTouchTop = true
    private var isYFirstTouchTop2 = true

    private var isOnPresetTop = false
    private var isOnPresetBottom = false


    private fun scrollLittleTopSpeed(event: MotionEvent, d: Int) {
        if (event.action == MotionEvent.ACTION_MOVE && isYFirstTouchLittleTopBottom) {
            yFirstTouchLittleTopBottom = event.y
            isYFirstTouchLittleTopBottom = false
        }
        if (event.action == MotionEvent.ACTION_UP) {
            isYFirstTouchLittleTopBottom = true
        }
        val dy = abs(event.y - yFirstTouchLittleTopBottom).toDouble()
        if (event.action == MotionEvent.ACTION_MOVE && dy != 0.toDouble()) {
            if (scrollView?.scrollY!! > d - toolBarViewHeight) {
                this.scrollView?.scrollTo(
                    0,
                    d - toolBarViewHeight + dy.pow(POW_COEFFICIENT).toInt()
                )
            }
        }
    }


    private fun scrollTop(event: MotionEvent, d: Int) {
        if (event.action == MotionEvent.ACTION_MOVE && isYFirstTouchTop) {
            yFirstTouchTop = event.y
            isYFirstTouchTop = false
        }
        if (event.action == MotionEvent.ACTION_UP) {
            isYFirstTouchTop = true
            yFirstTouchTop = 0F
        }
        var dy = abs(event.y - yFirstTouchTop).toDouble().pow(POW_COEFFICIENT).toInt()

        if (scrollView?.scrollY!! > d - toolBarViewHeight) {
            isOnPresetTop = true
            isYFirstTouchTop = true
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            if (scrollView?.scrollY!! < d - toolBarViewHeight) {
                //////////
                if (isOnPresetTop) {
                    if (isYFirstTouchTop2) {
                        yFirstTouchTop = event.y
                        isYFirstTouchTop2 = false
                    }
                    if (event.action == MotionEvent.ACTION_UP) {
                        isYFirstTouchTop2 = true
                        yFirstTouchTop = 0F
                    }
                    dy = abs(event.y - yFirstTouchTop).toDouble().pow(POW_COEFFICIENT).toInt()
                }
                /////////

                this.scrollView?.scrollTo(
                    0,
                    d - toolBarViewHeight - dy
                )
            }
        }
    }

    private fun scrollBottom(
        event: MotionEvent,
        d: Int,
        viewChildHeight: Int,
        scrollViewHeight: Int
    ) {
        if (event.action == MotionEvent.ACTION_MOVE && isYFirstTouchBottom) {
            yFirstTouchBottom = event.y
            isYFirstTouchBottom = false
        }
        if (event.action == MotionEvent.ACTION_UP) {
            isYFirstTouchBottom = true
        }

        if (scrollView?.scrollY!! < d + viewChildHeight + bottomViewHeight - scrollViewHeight) {
            isOnPresetBottom = true
            isYFirstTouchBottom = true
        }

        var dy = abs(event.y - yFirstTouchBottom).toDouble().pow(
            POW_COEFFICIENT
        ).toInt()
        if (event.action == MotionEvent.ACTION_MOVE) {
            if (scrollView?.scrollY!! > d + viewChildHeight + bottomViewHeight - scrollViewHeight) {
                if (isOnPresetBottom) {
                    if (isYFirstTouchBottom2) {
                        yFirstTouchBottom = event.y
                        isYFirstTouchBottom2 = false
                    }
                    if (event.action == MotionEvent.ACTION_UP) {
                        isYFirstTouchBottom2 = true
                        yFirstTouchBottom = 0F
                    }
                    dy = abs(event.y - yFirstTouchBottom).toDouble().pow(POW_COEFFICIENT).toInt()
                }
                this.scrollView?.scrollTo(
                    0,
                    d + viewChildHeight + bottomViewHeight - scrollViewHeight + dy
                )
            }
        }
    }

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
        if (event == MotionEvent.ACTION_MOVE) {
            isEvent1 = false
        } else if (event == MotionEvent.ACTION_UP) {
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