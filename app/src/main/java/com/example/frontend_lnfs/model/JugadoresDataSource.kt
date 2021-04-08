package com.example.frontend_lnfs.model

interface JugadoresDataSource {
    fun getJugadores(callback: JugadorListRepositoryCallback)
    fun getJugador(id: Int, callback: JugadorRepositoryCallback)
    fun getJugadoresPorEquipo(idEquipo:Int,callback: JugadorListRepositoryCallback)
    fun addJugador(jugador: Jugador, callback: JugadorRepositoryCallback)
    fun deleteJugador(jugador: Jugador, callback: JugadorRepositoryCallback)
    fun updateJugador(id: Int, jugador: Jugador?, callback: JugadorRepositoryCallback)

    interface JugadorListRepositoryCallback {
        fun onJugadorResponse(jugadores: List<Jugador>)
        fun onJugadorError(msg: String?)
        fun onJugadorLoading()
    }
    interface JugadorRepositoryCallback {
        fun onJugadorResponse(jugador: Jugador)
        fun onJugadorError(msg: String?)
    }
}