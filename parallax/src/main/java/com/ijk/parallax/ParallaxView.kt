package com.ijk.parallax

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
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

    fun setContentView(viewChildRes: Int, context: Activity): ParallaxView {
        this.viewChildRes = viewChildRes
        this.viewOld = context.layoutInflater.inflate(viewChildRes, null)
        return this
    }

    fun setContentView(viewChildRes: Int, context: FragmentActivity?): ParallaxView {
        this.viewChildRes = viewChildRes
        this.viewOld = context?.layoutInflater?.inflate(viewChildRes, null)
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

    fun buildActivity(context: Activity): ParallaxView {
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

    fun buildFragment(context: FragmentActivity?): View {
        this.scrollViewParallax.viewOld = viewOld
        val viewNew = (context as Activity).layoutInflater.inflate(R.layout.scroll_parallax, null)
        this.scrollViewParallax.view = viewNew
        viewNew.background = viewOld?.background
        val scrollView = viewNew.findViewById<ScrollView>(R.id.scrollViewParallax)
        this.scrollViewParallax.scrollView = scrollView
        val linearLayoutScroll = viewNew.findViewById<LinearLayout>(R.id.linearLayoutScroll)
        linearLayoutScroll.addView(viewOld)
        linearLayoutScroll.addView(getBigView(context))
//        context.setContentView(viewNew)
        this.scrollViewParallax.isRecyclerViewExist = isRecyclerViewExist
        this.scrollViewParallax.setContentViewForParallax(context)
        return viewNew
    }

    private fun getBigView(context: Activity): View {
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