package com.ijk.parallax

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class ParallaxView(
    private val context: AppCompatActivity?,
    private val isFragment: Boolean = false
) {

    constructor(
        context: FragmentActivity?,
        isFragment: Boolean
    ) : this(context as AppCompatActivity?, isFragment)

    private val scrollViewParallax = ScrollViewParallax()
    private var toolBarView1: View? = null
    private var bottomView1: View? = null
    private var isBlur = false
    private var viewNew: View? = null

    companion object {
        @JvmStatic
        fun Builder(context: AppCompatActivity?): ParallaxView {
            return ParallaxView(context)
        }

        @JvmStatic
        fun Builder(context: FragmentActivity?): ParallaxView {
            return ParallaxView(context, true)
        }
    }

    fun setContentView(viewChildRes: Int): Options {
        val viewOld = context?.layoutInflater?.inflate(viewChildRes, null)
        initContentView(viewOld)
        return Options()
    }

    fun setContentView(view: View): Options {
        if (view.parent != null) {
            (view.parent as ViewGroup).removeView(view)
        }
        initContentView(view)
        return Options()
    }

    private fun initContentView(viewOld: View?) {
        this.scrollViewParallax.viewOld = viewOld
        viewNew = context?.layoutInflater?.inflate(R.layout.scroll_parallax, null)
        viewNew?.background = viewOld?.background
        viewOld?.background = null
        val scrollView = viewNew?.findViewById<ScrollView>(R.id.scrollViewParallax)
        this.scrollViewParallax.scrollView = scrollView
        val linearLayoutScroll = viewNew?.findViewById<LinearLayout>(R.id.linearLayoutScroll)
        linearLayoutScroll?.addView(viewOld)
        linearLayoutScroll?.addView(getBigView(context, viewNew?.background))

        if (!isFragment)
            context?.setContentView(viewNew)

        this.scrollViewParallax.viewNew = viewNew
        this.scrollViewParallax.initContentViewForParallax(context)
    }

    inner class Options {
//        fun onBlur(): Options {
//            isBlur = true
//            return this
//        }

        fun setRecyclerView(recyclerView: RecyclerView): Options {
            recyclerView.isNestedScrollingEnabled = false
            return this
        }

        fun setToolBarView(toolBarView: View): Options {
            toolBarView1 = toolBarView
            return this
        }

        fun setBottomView(bottomView: View): Options {
            bottomView1 = bottomView
            return this
        }

        fun build(): View {
            if (toolBarView1 != null)
                if (isBlur) {
//                    scrollViewParallax.setToolBarFromBlur(toolBarView1, context)
                } else {
                    scrollViewParallax.setToolBar(toolBarView1)
                }

            if (bottomView1 != null)
                if (isBlur) {
//                    scrollViewParallax.setBottomViewFromBlur(bottomView1, context)
                } else {
                    scrollViewParallax.setBottomView(bottomView1)
                }
            return viewNew ?: View(context)
        }
    }
}