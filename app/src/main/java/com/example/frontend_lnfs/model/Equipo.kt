package com.example.frontend_lnfs.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Equipo(
    val id:Int,
    val nombre:String,
    val escudo:String,
    val idJugadores:MutableList<Int>
):Parcelable