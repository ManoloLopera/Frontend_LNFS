package com.example.frontend_lnfs.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Jugador(
    val id:Int,
    val equipo: Int,
    val nombre:String,
    val sobrenombre:String,
    val posicion:String
):Parcelable