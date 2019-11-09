package com.ijk.parallax

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup


fun loget(data: Any) {
    Log.d("IJKAPP", "ijk: $data")
}

fun getBigView(context: Activity): View {
    val bigView = View(context)
    bigView.setBackgroundResource(R.color.trans)

    bigView.layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        spToPx(1000F, context)
    )
    return bigView
}

fun spToPx(sp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        context.resources.displayMetrics
    ).toInt()
}
