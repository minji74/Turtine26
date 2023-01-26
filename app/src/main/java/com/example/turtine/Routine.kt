package com.example.turtine

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// https://developer.android.com/kotlin/parcelize 참조
@Parcelize
data class Routine(
    val title: String,
    val minute: Int,
    val second: Int
) : Parcelable