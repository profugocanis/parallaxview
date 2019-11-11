package com.ijk.parallax

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

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

//fun getBlurView(context: Activity?, viewNew: View?, res: Int): BlurView? {
//    val blurView = viewNew?.findViewById<BlurView>(res)
//    val decorView = context?.window?.decorView
//    val rootView = decorView?.findViewById<View>(android.R.id.content) as ViewGroup
//    val windowBackground = decorView.background
//    blurView?.setupWith(rootView)
//        ?.setFrameClearDrawable(windowBackground)
//        ?.setBlurAlgorithm(RenderScriptBlur(context))
//        ?.setBlurRadius(20F)
////        ?.setBlurAutoUpdate(true)
////        ?.setHasFixedTransformationMatrix(true)
//    return blurView
//}