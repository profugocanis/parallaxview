package com.ijk.paralaxemample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ijk.parallax.ParallaxViewFragment
import kotlinx.android.synthetic.main.fragment_blank.view.*

class BlankFragment : BaseFragment() {
    override fun obtainLayoutResId() = R.layout.fragment_blank

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val view1 = inflater.inflate(R.layout.fragment_blank, container, false)

        return ParallaxViewFragment.Builder(activity)
            .setContentView(view1)
            .setBottomView(view1.bottomView!!)
            .build()

//        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
