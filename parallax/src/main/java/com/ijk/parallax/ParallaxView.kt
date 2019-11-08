package com.ijk.parallax

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ParallaxView {

    private val scrollViewParallax = ScrollViewParallax()
    private var viewChildRes = -1
    private var viewOld: View? = null
    private var isRecyclerViewExist = false
    private var toolbarView: View? = null
    private var bottomView: View? = null

    companion object {
        fun Builder(): ParallaxView {
            return ParallaxView()
        }
    }

    fun setContentView(viewChildRes: Int, context: AppCompatActivity): ParallaxView {
        this.viewChildRes = viewChildRes
        this.viewOld = context.layoutInflater.inflate(viewChildRes, null)
        return this
    }

    fun setContentView(viewChild: View): ParallaxView {
        this.viewOld = viewChild
        return this
    }

    fun setRecyclerView(recyclerView: RecyclerView): ParallaxView {
        this.isRecyclerViewExist = true
        scrollViewParallax.setRecyclerViewFromParallax(recyclerView)
        return this
    }

    fun setToolBarView(toolbarView: View): ParallaxView {
        scrollViewParallax.setToolBar(toolbarView)
        this.toolbarView = toolbarView
        return this
    }

    fun setBottomView(bottomView: View): ParallaxView {
        scrollViewParallax.setBottomView(bottomView)
        this.bottomView = bottomView
        return this
    }

    fun build(context: AppCompatActivity): ParallaxView {
        this.scrollViewParallax.viewOld = viewOld
        val viewNew = context.layoutInflater.inflate(R.layout.scroll_parallax, null)
        this.scrollViewParallax.view = viewNew
        viewNew.background = viewOld?.background
        val scrollView = viewNew.findViewById<ScrollView>(R.id.scrollViewParallax)
        this.scrollViewParallax.scrollView = scrollView
        val linearLayoutScroll = viewNew.findViewById<LinearLayout>(R.id.linearLayoutScroll)
        linearLayoutScroll.addView(viewOld)
        linearLayoutScroll.addView(getBigView(context))
        context.setContentView(viewNew)
        this.scrollViewParallax.isRecyclerViewExist = isRecyclerViewExist
        this.scrollViewParallax.setContentViewForParallax(context)
        return this
    }

    private fun getBigView(context: AppCompatActivity): View {
        val bigView = View(context)
        bigView.setBackgroundResource(R.color.trans)
        viewOld?.setBackgroundResource(R.color.trans)

        bigView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            spToPx(1000F, context)
        )
        return bigView
    }

    private fun spToPx(sp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context.resources.displayMetrics
        ).toInt()
    }
}