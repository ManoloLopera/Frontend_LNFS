package com.example.frontend_lnfs.model.api


import com.example.frontend_lnfs.model.Jugador
import retrofit2.Call
import retrofit2.http.*

interface JugadorApi {

    @GET("jugadores")
    fun getAllJugadores() : Call<List<Jugador>>

    @GET("/jugador/{id}")
    fun getJugador(@Path("id") id: Int) : Call<Jugador>

    @GET("jugadores")
    fun getJugadoresDeEquipo(@Query("equipo") idEquipo: Int) : Call<List<Jugador>>

    @POST("jugadores")
    fun addJugador(@Body jugador: JugadorDto): Call<Jugador>

    @DELETE("jugadores/{id}")
    fun deleteJugador(@Path("id") id: Int): Call<Void>

    @PUT("jugadores/{id}")
    fun updateJugador(@Path("id") id: Int, @Body jugador: Jugador?): Call<Jugador?>?
}