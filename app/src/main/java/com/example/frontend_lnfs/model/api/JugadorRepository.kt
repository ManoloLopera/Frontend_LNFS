package com.example.frontend_lnfs.model.api



import android.util.Log
import com.example.frontend_lnfs.config.APIConfig
import com.example.frontend_lnfs.model.Jugador
import com.example.frontend_lnfs.model.JugadorMapper
import com.example.frontend_lnfs.model.JugadoresDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object JugadorRepository : JugadoresDataSource {
    private val api: JugadorApi

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(APIConfig.API_URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(
            JugadorApi::class.java)
    }


    override fun getJugadores(callback: JugadoresDataSource.JugadorListRepositoryCallback) {
        callback.onJugadorLoading()
        val call = api.getAllJugadores()
        call.enqueue(object : Callback<List<Jugador>> {
            override fun onFailure(call: Call<List<Jugador>>, t: Throwable) {
                callback.onJugadorError(t.message)
            }

            override fun onResponse(call: Call<List<Jugador>>, response: Response<List<Jugador>>) {
                val jugadoresResponse = response.body().orEmpty()
                callback.onJugadorResponse(jugadoresResponse)
            }

        })
    }

    override fun getJugador(id: Int, callback: JugadoresDataSource.JugadorRepositoryCallback){
        val call=api.getJugador(id)

        call.enqueue(object: Callback<Jugador>{
            override fun onFailure(call: Call<Jugador>, t: Throwable) {
                callback.onJugadorError(t.message)
            }

            override fun onResponse(call: Call<Jugador>, response: Response<Jugador>) {
                val playerResponse = response.body() ?: Jugador(1,1,"","","")
                callback.onJugadorResponse(playerResponse)
            }

        })
    }

    override fun getJugadoresPorEquipo(idEquipo:Int,callback: JugadoresDataSource.JugadorListRepositoryCallback){
        val call = api.getJugadoresDeEquipo(idEquipo)

        call.enqueue(object : Callback<List<Jugador>> {
            override fun onFailure(call: Call<List<Jugador>>, t: Throwable) {
                callback.onJugadorError(t.message)
            }

            override fun onResponse(call: Call<List<Jugador>>, response: Response<List<Jugador>>) {
                val jugadoresResponse = response.body().orEmpty()
                callback.onJugadorResponse(jugadoresResponse)
            }

        })
    }


    override fun addJugador(jugador: Jugador, callback: JugadoresDataSource.JugadorRepositoryCallback) {
        val call = api.addJugador(JugadorMapper.transformObjectBoToDto(jugador))
        call.enqueue(object : Callback<Jugador> {
            override fun onFailure(call: Call<Jugador>, t: Throwable) {
                callback.onJugadorError(t.message)
            }

            override fun onResponse(call: Call<Jugador>, response: Response<Jugador>) {
                if (response.isSuccessful) {
                    val jugadorResponse = response.body() ?: Jugador(1, 1, "", "","")
                    callback.onJugadorResponse(jugadorResponse)

                } else {
                    callback.onJugadorError(response.message())
                }

            }
        })
    }

    override fun deleteJugador(jugador: Jugador, callback: JugadoresDataSource.JugadorRepositoryCallback) {
        val call = api.deleteJugador(jugador.id)
        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback.onJugadorError(t.message)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback.onJugadorResponse(jugador)
            }
        })
    }
    override fun updateJugador(id: Int, jugador: Jugador?, callback: JugadoresDataSource.JugadorRepositoryCallback) {
        val call: Call<Jugador?>? = api.updateJugador(id,jugador)
        call?.enqueue(object : Callback<Jugador?> {
            override fun onResponse(
                call: Call<Jugador?>,
                response: Response<Jugador?>
            ) {
                if (response.isSuccessful) {
                    val jugadorResponse = response.body() ?: Jugador(1, 1, "", "","")

                    callback.onJugadorResponse(jugadorResponse)
                }
            }

            override fun onFailure(call: Call<Jugador?>, t: Throwable) {
                Log.e("ERROR: ", t.message.orEmpty())
            }
        })
    }

}




