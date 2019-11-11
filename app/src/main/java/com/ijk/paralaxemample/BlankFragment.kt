package com.ijk.paralaxemample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ijk.parallax.ParallaxView
import kotlinx.android.synthetic.main.fragment_blank.view.*

class BlankFragment : BaseFragment() {
    override fun obtainLayoutResId() = R.layout.fragment_blank

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val view = inflater.inflate(R.layout.fragment_blank, container, false)

//        return ParallaxView.Builder(activity)
//            .setContentView(view)
//            .setToolBarView(view.toolBar)
//            .setBottomView(view.bottomView)
//            .onBlur()
//            .build()

        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
