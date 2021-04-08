package com.example.frontend_lnfs.model

interface onClicked {
    interface TeamClicked{
        fun onClicked(equipo:Equipo)
    }

    interface PlayerClicked{
        fun onClicked(jugador: Jugador)
    }
}