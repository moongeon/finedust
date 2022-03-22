package com.mungeun.finedust.DatabindingAdapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("fineDustUnit")
fun TextView.setFineDustUnit(fineDust : String?){
    text = fineDust?.let{fineDust + "㎍/㎥"}
}

@BindingAdapter("ppmUnit")
fun TextView.setPPMUnit(ppm : String?){
    text = ppm?.let { ppm + " ppm" }
}

@BindingAdapter("isGone")
fun bindIsGone(view : View, isGone : Boolean){
    view.visibility = if(!isGone) View.GONE else View.VISIBLE
}


