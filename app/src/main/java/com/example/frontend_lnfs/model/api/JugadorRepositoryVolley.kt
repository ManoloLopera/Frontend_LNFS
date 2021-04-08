package com.example.frontend_lnfs.model.api

import com.example.frontend_lnfs.model.*


import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.frontend_lnfs.R
import com.example.frontend_lnfs.config.APIConfig
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Method
import java.lang.reflect.Type


object JugadorRepositoryVolley : JugadoresDataSource {

    override fun getJugadores(callback: JugadoresDataSource.JugadorListRepositoryCallback) {
        VolleySingleton.getInstance().requestQueue

        val stringRequest = StringRequest(Request.Method.GET, APIConfig.API_URL_JUGADORES,
            Response.Listener {
                val collectionType: Type = object : TypeToken<Collection<Jugador>>() {}.type
                val resp= Gson().fromJson<List<Jugador>>(it,collectionType)
                callback.onJugadorResponse(resp)
            },
            Response.ErrorListener {
                callback.onJugadorError(it.message)
            })
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }


    override fun getJugador(id: Int, callback: JugadoresDataSource.JugadorRepositoryCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getJugadoresPorEquipo(
        idEquipo: Int,
        callback: JugadoresDataSource.JugadorListRepositoryCallback
    ) {
        VolleySingleton.getInstance().requestQueue

        val stringRequest = StringRequest(Request.Method.GET, "${APIConfig.API_URL_JUGADORES}?equipo=${idEquipo}",
            Response.Listener {
                val collectionType: Type = object : TypeToken<Collection<Jugador>>() {}.type
                val resp= Gson().fromJson<List<Jugador>>(it,collectionType)
                callback.onJugadorResponse(resp)
            },
            Response.ErrorListener {
                callback.onJugadorError(it.message)
            })
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }

    override fun addJugador(jugador: Jugador, callback: JugadoresDataSource.JugadorRepositoryCallback) {
        VolleySingleton.getInstance().requestQueue

        val jsonRequest = JsonObjectRequest(Request.Method.POST,APIConfig.API_URL_JUGADORES,
            JSONObject(Gson().toJson(JugadorMapper.transformObjectBoToDto(jugador))),
            Response.Listener {
                callback.onJugadorResponse(jugador)
            },
            Response.ErrorListener {
                callback.onJugadorError(it.message)
            })

        VolleySingleton.getInstance().addToRequestQueue(jsonRequest)
    }

    override fun deleteJugador(jugador: Jugador, callback: JugadoresDataSource.JugadorRepositoryCallback) {
        VolleySingleton.getInstance().requestQueue

        val jsonRequest= JsonObjectRequest(Request.Method.DELETE,"${APIConfig.API_URL_JUGADORES}/${jugador.id}",
            JSONObject(Gson().toJson(JugadorMapper.transformObjectBoToDto(jugador))),
            Response.Listener {
                callback.onJugadorResponse(jugador)
            },
            Response.ErrorListener {
                callback.onJugadorError(it.message)
            })

        VolleySingleton.getInstance().addToRequestQueue(jsonRequest)
    }

    override fun updateJugador(id: Int, jugador: Jugador?, callback: JugadoresDataSource.JugadorRepositoryCallback) {
        VolleySingleton.getInstance().requestQueue

        val jsonRequest = JsonObjectRequest(Request.Method.PUT,"${APIConfig.API_URL_JUGADORES}/${id}",
            JSONObject(Gson().toJson(jugador)),
            Response.Listener {
                callback.onJugadorResponse(jugador!!)
            },
            Response.ErrorListener {
                callback.onJugadorError(it.message)
            })

        VolleySingleton.getInstance().addToRequestQueue(jsonRequest)
    }

}