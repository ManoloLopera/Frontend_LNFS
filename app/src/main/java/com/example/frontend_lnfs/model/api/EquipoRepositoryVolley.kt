package com.example.frontend_lnfs.model.api

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.frontend_lnfs.R
import com.example.frontend_lnfs.config.APIConfig
import com.example.frontend_lnfs.model.Equipo
import com.example.frontend_lnfs.model.EquipoDataSource
import com.example.frontend_lnfs.model.EquipoMapper
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Method
import java.lang.reflect.Type


object EquipoRepositoryVolley : EquipoDataSource {

    override fun getEquipos(callback: EquipoDataSource.EquipoListRepositoryCallback) {

        VolleySingleton.getInstance().requestQueue

        val stringRequest = StringRequest(Request.Method.GET, APIConfig.API_URL_EQUIPOS,
            Response.Listener {
                //Aquí creo el tipo Colección de Equipos, para evitar el JsonSyntaxException
                //Línea patrocinada por stackoverflow
                val collectionType: Type = object : TypeToken<Collection<Equipo>>() {}.type
                val resp= Gson().fromJson<List<Equipo>>(it,collectionType)
                    callback.onEquipoResponse(resp)
                },
            Response.ErrorListener {
                callback.onEquipoError(it.message)
            })
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }

    override fun getEquipo(id: Int, callback: EquipoDataSource.EquipoRepositoryCallback) {
        VolleySingleton.getInstance().requestQueue

        val stringRequest = StringRequest(Request.Method.GET, "${APIConfig.API_URL_EQUIPOS}/${id}",
            Response.Listener {
                //Aquí creo el tipo Colección de Equipos, para evitar el JsonSyntaxException
                //Línea patrocinada por stackoverflow
                val resp= Gson().fromJson<Equipo>(it,Equipo::class.java)
                callback.onEquipoResponse(resp)
            },
            Response.ErrorListener {
                callback.onEquipoError(it.message)
            })
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }

    override fun addEquipo(equipo: Equipo, callback: EquipoDataSource.EquipoRepositoryCallback) {
        VolleySingleton.getInstance().requestQueue

        val jsonRequest = JsonObjectRequest(Request.Method.POST,APIConfig.API_URL_EQUIPOS,
            JSONObject(Gson().toJson(EquipoMapper.transformObjectBoToDto(equipo))),
            Response.Listener {
                callback.onEquipoResponse(equipo)
            },
            Response.ErrorListener {
                callback.onEquipoError(it.message)
            })

        VolleySingleton.getInstance().addToRequestQueue(jsonRequest)
    }

    override fun deleteEquipo(equipo: Equipo, callback: EquipoDataSource.EquipoRepositoryCallback) {
        VolleySingleton.getInstance().requestQueue

        val jsonRequest= JsonObjectRequest(Request.Method.DELETE,"${APIConfig.API_URL_EQUIPOS}/${equipo.id}",
            JSONObject(Gson().toJson(EquipoMapper.transformObjectBoToDto(equipo))),
            Response.Listener {
                callback.onEquipoResponse(equipo)
            },
            Response.ErrorListener {
                callback.onEquipoError(it.message)
            })

        VolleySingleton.getInstance().addToRequestQueue(jsonRequest)
    }

    override fun updateEquipo(id: Int, equipo: Equipo?, callback: EquipoDataSource.EquipoRepositoryCallback) {
        VolleySingleton.getInstance().requestQueue

        val jsonRequest = JsonObjectRequest(Request.Method.PUT,"${APIConfig.API_URL_EQUIPOS}/${id}",
            JSONObject(Gson().toJson(equipo)),
                Response.Listener {
                    callback.onEquipoResponse(equipo!!)
                },
                Response.ErrorListener {
                    callback.onEquipoError(it.message)
                })

        VolleySingleton.getInstance().addToRequestQueue(jsonRequest)
    }

}