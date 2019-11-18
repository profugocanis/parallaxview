package com.ijk.paralaxemample

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ijk.parallax.parallax_margin.ParallaxViewBottom
import com.ijk.parallax.parallax_margin.ParallaxViewMargin
import com.ijk.parallax.parallax_margin.ParallaxViewTop
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


//        scrollView.setOnTouchListener(OnTuckListener(this, main))


//        ParallaxViewTop(scrollView, this)
//        ParallaxViewBottom(scrollView, this)
        ParallaxViewMargin(scrollView, this)


//        ParallaxView.Builder(this)
//            .setContentView(R.layout.activity_main)
////            .setRecyclerView(recyclerView)
//            .setToolBarView(toolbar)
////            .setBottomView(linearLayoutBottom)
//            .build()
//        initRecycler()
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
//
//        val r = Random()
//
//        txtAddToRecyclerView.setOnClickListener {
//            animalNames.add(r.nextInt().toString())
//            adapter.notifyDataSetChanged()
//            recyclerView.scrollToPosition(animalNames.size - 1)
//        }

//        recyclerView.scr
    }

    private class OnTuckListener(val context: Context, val mainLayout: ViewGroup) :
        View.OnTouchListener {

        private var xDelta: Int = 0
        private var yDelta: Int = 0

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {

//            val x = event?.rawX?.toInt() ?: 0
            val y = event?.rawY?.toInt() ?: 0

            val action = event?.action ?: 0

            when (action and MotionEvent.ACTION_MASK) {

                MotionEvent.ACTION_DOWN -> {
                    val lParams = v?.layoutParams as LinearLayout.LayoutParams

//                    xDelta = x - lParams.leftMargin
                    yDelta = y - lParams.topMargin
                }

                MotionEvent.ACTION_UP -> Toast.makeText(
                    context,
                    "thanks for new location!", Toast.LENGTH_SHORT
                )
                    .show()

                MotionEvent.ACTION_MOVE -> {
                    val layoutParams = v
                        ?.layoutParams as LinearLayout.LayoutParams
//                    layoutParams.leftMargin = x - xDelta
                    layoutParams.topMargin = y - yDelta
                    layoutParams.rightMargin = 0
                    layoutParams.bottomMargin = 0
                    v.layoutParams = layoutParams
                }
            }
            mainLayout.invalidate()
            return true
        }
    }
}

