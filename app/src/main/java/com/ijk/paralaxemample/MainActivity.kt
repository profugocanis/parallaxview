package com.ijk.paralaxemample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ijk.parallax.ParallaxViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        scrollViewParallax.setContentViewForParallax(R.layout.activity_main, this)

        setContentView(R.layout.activity_main)
//        scrollViewParallax.setToolBar(toolbar)

//        scrollViewParallax.setScrollViewFromParallax(scrollView)

//        scrollViewParallax.setRecyclerViewFromParallax(recyclerView)
//        scrollViewParallax.setBottomView(linearLayoutBottom)


//        setViewFromParallax(linearLayout)

        ParallaxViewActivity.Builder(this)
            .setContentView(R.layout.activity_main)
//            .setRecyclerView(recyclerView)
            .setToolBarView(toolbar)
            .setBlur(true)
            .setBottomView(linearLayoutBottom)
//
//        initRecycler()


//        val decorView = window.decorView
//
//        val rootView = decorView.findViewById<View>(android.R.id.content) as ViewGroup
//
//        val windowBackground = decorView.background
//
//        blurView.setupWith(rootView)
//            .setBlurEnabled(true)
//            .setFrameClearDrawable(windowBackground)
//            .setBlurAlgorithm(RenderScriptBlur(this))
//            .setBlurRadius(20F)
//            .setHasFixedTransformationMatrix(true)
    }

    private fun initRecycler() {
        val animalNames = ArrayList<String>()
        animalNames.add("Horse")
        animalNames.add("Cow")
        animalNames.add("Camel")
        animalNames.add("Sheep")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Camel")
        animalNames.add("Camel")
        animalNames.add("Camel")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Camel")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Goat")
        animalNames.add("Camel")

        val adapter = RecyclerViewAdapter(this, animalNames)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter


//        recyclerView.scr
    }

}