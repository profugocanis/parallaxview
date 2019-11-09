package com.ijk.paralaxemample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation

abstract class BaseFragment : Fragment() {
    @LayoutRes
    internal abstract fun obtainLayoutResId(): Int

    private val navController: NavController? by lazy {
        view?.let { Navigation.findNavController(it) }
    }

    fun navGoTo(resId: Int, args: Bundle? = null) {
        navController?.navigate(resId, args)
    }

    fun navGoToNotReturn(resId: Int, resIdDonReturn: Int, args: Bundle? = null) {
        navController
            ?.navigate(
                resId, args, NavOptions.Builder().setPopUpTo(resIdDonReturn, true).build()
            )
    }

    fun navToBack() {
        navController?.popBackStack()
    }

//    fun startAnim(view: View) {
//        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_btn))
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(obtainLayoutResId(), container, false)
}