package com.ijk.parallax

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import kotlin.math.pow

object Utils {

    val POW_COEFFICIENT = 0.9

    fun loget(data: Any) {
        Log.d("IJKAPP", "ijk: $data")
    }

    fun getBigView(context: Activity?, background: Drawable?): View {
        val bigView = View(context)
        bigView.background = background
        bigView.setBackgroundResource(R.color.trans)
        bigView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            spToPx(1_000F, context)
        )
        return bigView
    }

    fun spToPx(sp: Float, context: Context?): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context?.resources?.displayMetrics
        ).toInt()
    }

//    fun getTime(f: Int): Long {
//        var res = 1L
//        if (f < 20) {
//            res = 20L
//        }
//        return res
//    }

    fun getTime(f: Int): Long {
        if (f <= 0){
            return 1L
        }
        val res = 70/f.toDouble().pow(0.85).toLong()
        loget(res)
        return res
    }

    fun getDY(f: Float): Float {
//        var res = 3F
//        if (f < 20) {
//            res = 1F
//        }
//        return res
        return 1F
    }
}