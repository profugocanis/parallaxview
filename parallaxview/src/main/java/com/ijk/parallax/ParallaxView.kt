package com.ijk.parallax

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.ijk.parallax.Utils.getBigView

class ParallaxView(
    private val context: AppCompatActivity?,
    private val isFragment: Boolean = false
) {

    constructor(
        context: FragmentActivity?,
        isFragment: Boolean
    ) : this(context as AppCompatActivity?, isFragment)

    private val scrollViewParallax = ScrollViewParallax(context)
    private var toolBarView1: View? = null
    private var bottomView1: View? = null
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
        val bottomBigView = getBigView(context, viewNew?.background)
        linearLayoutScroll?.addView(bottomBigView)
        this.scrollViewParallax.bottomBigView = bottomBigView
        this.scrollViewParallax.topBigView = viewNew?.findViewById(R.id.topBigView)
        if (!isFragment)
            context?.setContentView(viewNew)



        this.scrollViewParallax.viewNew = viewNew
        this.scrollViewParallax.initContentViewForParallax(context)
    }

    inner class Options {

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
                scrollViewParallax.setToolBar(toolBarView1)
            if (bottomView1 != null)
                scrollViewParallax.setBottomView(bottomView1)
            return viewNew ?: View(context)
        }
    }
}