package com.example.frontend_lnfs.model.api

import com.example.frontend_lnfs.model.Equipo
import retrofit2.Call
import retrofit2.http.*

interface EquipoApi {

    @GET("equipos")
    fun getAllEquipos() : Call<List<Equipo>>

    @GET("/equipos/{id}")
    fun getEquipo(@Path("id") id: Int) : Call<Equipo>

    @POST("equipos")
    fun addEquipo(@Body equipo: EquipoDto): Call<Equipo>

    @DELETE("equipos/{id}")
    fun deleteEquipo(@Path("id") id: Int): Call<Void>

    @PUT("equipos/{id}")
    fun updateEquipo(@Path("id") id: Int, @Body equipo: Equipo?): Call<Equipo?>?
}