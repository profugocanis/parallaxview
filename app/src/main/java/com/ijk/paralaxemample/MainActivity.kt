package com.ijk.paralaxemample

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ijk.parallaxijk.setContentViewForParallax

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewForParallax(R.layout.activity_main)
    }

}

fun loget(data: Any) {
    Log.d("IJKAPP", data.toString())
}