package com.example.frontend_lnfs.model.api


import android.util.Log
import com.example.frontend_lnfs.config.APIConfig
import com.example.frontend_lnfs.model.Equipo
import com.example.frontend_lnfs.model.EquipoDataSource
import com.example.frontend_lnfs.model.EquipoMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object EquipoRepository : EquipoDataSource{
    private val api: EquipoApi

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(APIConfig.API_URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(
            EquipoApi::class.java)
    }


    override fun getEquipos(callback: EquipoDataSource.EquipoListRepositoryCallback) {
        callback.onEquipoLoading()
        val call = api.getAllEquipos()
        call.enqueue(object : Callback<List<Equipo>> {
            override fun onFailure(call: Call<List<Equipo>>, t: Throwable) {
                callback.onEquipoError(t.message)
            }

            override fun onResponse(call: Call<List<Equipo>>, response: Response<List<Equipo>>) {
                val equiposResponse = response.body().orEmpty()
                callback.onEquipoResponse(equiposResponse)
            }

        })
    }

    override fun getEquipo(id: Int, callback: EquipoDataSource.EquipoRepositoryCallback){
        val call = api.getEquipo(id)
        call.enqueue(object:Callback<Equipo>{
            override fun onFailure(call: Call<Equipo>, t: Throwable) {
                callback.onEquipoError(t.message)
            }

            override fun onResponse(call: Call<Equipo>, response: Response<Equipo>) {
                if (response.isSuccessful) {
                    val equipoResponse = response.body() ?: Equipo(1, "", "", mutableListOf())
                    callback.onEquipoResponse(equipoResponse)
                } else {
                    callback.onEquipoError(response.message())
                }
            }

        })
    }


    override fun addEquipo(equipo: Equipo, callback: EquipoDataSource.EquipoRepositoryCallback) {
        val call = api.addEquipo(EquipoMapper.transformObjectBoToDto(equipo))
        call.enqueue(object : Callback<Equipo> {
            override fun onFailure(call: Call<Equipo>, t: Throwable) {
                callback.onEquipoError(t.message)
            }

            override fun onResponse(call: Call<Equipo>, response: Response<Equipo>) {
                if (response.isSuccessful) {
                    val equipoResponse = response.body() ?: Equipo(1, "", "", mutableListOf())
                    callback.onEquipoResponse(equipoResponse)
                } else {
                    callback.onEquipoError(response.message())
                }

            }
        })
    }

    override fun deleteEquipo(equipo: Equipo, callback: EquipoDataSource.EquipoRepositoryCallback) {
        val call = api.deleteEquipo(equipo.id)
        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback.onEquipoError(t.message)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback.onEquipoResponse(equipo)
            }
        })
    }
    override fun updateEquipo(id: Int, equipo: Equipo?, callback: EquipoDataSource.EquipoRepositoryCallback) {
        val call: Call<Equipo?>? = api.updateEquipo(id,equipo)
        if (call != null) {
            call.enqueue(object : Callback<Equipo?> {
                override fun onResponse(
                    call: Call<Equipo?>,
                    response: Response<Equipo?>
                ) {
                    if (response.isSuccessful) {
                        val equipoResponse = response.body() ?: Equipo(1, "", "", mutableListOf())

                        callback.onEquipoResponse(equipoResponse)
                    }
                }

                override fun onFailure(call: Call<Equipo?>, t: Throwable) {
                    Log.e("ERROR: ", t.message.orEmpty())
                }
            })
        }
    }

}




