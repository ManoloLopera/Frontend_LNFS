package com.example.frontend_lnfs.model.api

import com.example.frontend_lnfs.model.Jugador

data class EquipoDto(
    val nombre:String,
    val escudo:String,
    val idJugadores:MutableList<Int>
)