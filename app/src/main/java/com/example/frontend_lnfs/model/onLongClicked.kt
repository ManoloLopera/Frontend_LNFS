package com.example.frontend_lnfs.model

interface onLongClicked{
    interface TeamLongClicked {
        fun teamLongClicked(equipo: Equipo):Boolean
    }

    interface PlayerLongClicked{
        fun playerLongClicked(jugador: Jugador):Boolean
    }
}
