package com.ijk.parallax

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

class ParallaxViewFragment(private val context: FragmentActivity?) {

    private val scrollViewParallax = ScrollViewParallax()
    private var viewChildRes = -1
    private var viewOld: View? = null
    private var viewNew: View? = null
    private var isRecyclerViewExist = false
    private var toolbarView: View? = null

    companion object {
        fun Builder(context: FragmentActivity?): ParallaxViewFragment {
            return ParallaxViewFragment(context)
        }
    }

    fun setContentView(viewChildRes: Int): ParallaxViewFragment {
        this.viewChildRes = viewChildRes
        this.viewOld = context?.layoutInflater?.inflate(viewChildRes, null)
        return this
    }

    fun setContentView(viewChild: View): ParallaxViewFragmentOptions {
        this.viewOld = viewChild
        buildFragment()
        return ParallaxViewFragmentOptions(this)
    }

    private fun buildFragment() {
        this.scrollViewParallax.viewOld = viewOld
        this.viewNew = (context as Activity).layoutInflater.inflate(R.layout.scroll_parallax, null)
        this.scrollViewParallax.view = viewNew
        this.viewNew?.background = viewOld?.background
        val scrollView = viewNew?.findViewById<ScrollView>(R.id.scrollViewParallax)
        this.scrollViewParallax.scrollView = scrollView
        val linearLayoutScroll = viewNew?.findViewById<LinearLayout>(R.id.linearLayoutScroll)
        linearLayoutScroll?.addView(viewOld)
        linearLayoutScroll?.addView(getBigView(context))
        viewOld?.setBackgroundResource(R.color.trans)
        this.scrollViewParallax.isRecyclerViewExist = isRecyclerViewExist
        this.scrollViewParallax.setContentViewForParallax(context)

    }

    inner class ParallaxViewFragmentOptions(private val parallaxViewFragment: ParallaxViewFragment) {

        fun setRecyclerView(recyclerView: RecyclerView): ParallaxViewFragmentOptions {
            isRecyclerViewExist = true
            scrollViewParallax.setRecyclerViewFromParallax(recyclerView)
            return this
        }

        fun setToolBarView(toolbarView: View): ParallaxViewFragmentOptions {
            scrollViewParallax.setToolBar(toolbarView)
            parallaxViewFragment.toolbarView = toolbarView
            return this
        }

        fun setBottomView(bottomView: View): ParallaxViewFragmentOptions {
            scrollViewParallax.setBottomView(bottomView)
//            scrollViewParallax.bottomView = bottomView
            return this
        }

        fun build(): View {
            return viewNew ?: View(context)
        }
    }
}