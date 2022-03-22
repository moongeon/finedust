package com.mungeun.finedust.data.model.TmCoordinates


import com.google.gson.annotations.SerializedName

data class Document(
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double
)