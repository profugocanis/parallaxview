package com.ijk.paralaxemample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ijk.parallax.ParallaxView
import com.ijk.parallax.ScrollViewParallax
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

        ParallaxView.Builder()
            .setContentView(R.layout.activity_main, this)
//            .setRecyclerView(recyclerView)
            .setToolBarView(toolbar)
            .setBottomView(linearLayoutBottom)
//
//        initRecycler()

        val fTrans = supportFragmentManager.beginTransaction()

        frgmCont

        val f = BlankFragment()

        fTrans.add(R.id.frgmCont, f)
//        fTrans.show(f)
        fTrans.commit()


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

fun loget(data: Any) {
    Log.d("IJKAPP", "ijk: $data")
}