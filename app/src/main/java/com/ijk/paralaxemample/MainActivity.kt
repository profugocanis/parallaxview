package com.ijk.paralaxemample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ijk.parallax.ParallaxView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_main)

        ParallaxView.Builder(this)
            .setContentView(R.layout.activity_main)
            .setRecyclerView(recyclerView)
            .setToolBarView(toolbar)
            .setBottomView(linearLayoutBottom)
            .build()
        initRecycler()

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
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val r = Random()

        txtAddToRecyclerView.setOnClickListener {
            animalNames.add(r.nextInt().toString())
            adapter.notifyDataSetChanged()
            recyclerView.scrollToPosition(animalNames.size - 1)
        }

//        recyclerView.scr
    }

}