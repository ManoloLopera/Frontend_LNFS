package com.example.frontend_lnfs.model



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frontend_lnfs.model.api.JugadorRepository
import com.example.frontend_lnfs.model.api.JugadorRepositoryVolley

class JugadoresViewModel : ViewModel() {
    val jugadorListLiveData = MutableLiveData<Resource<List<Jugador>>>()
    val jugadorAddedLiveData = MutableLiveData<Resource<Jugador>>()
    val jugadorDeletedLiveData = MutableLiveData<Resource<Jugador>>()
    val jugadorUpdatedLiveData = MutableLiveData<Resource<Jugador>>()
    val repositorio= JugadorRepository

    fun getJugadores() {
        repositorio.getJugadores(object :
            JugadoresDataSource.JugadorListRepositoryCallback {
            override fun onJugadorResponse(jugadores: List<Jugador>) {
                jugadorListLiveData.value = Resource.success(jugadores)
            }

            override fun onJugadorError(msg: String?) {
                jugadorListLiveData.value = Resource.error(msg.orEmpty(), emptyList())
            }

            override fun onJugadorLoading() {
                jugadorListLiveData.value = Resource.loading(emptyList())
            }
        })
    }

    fun getJugadoresPorEquipo(idEquipo: Int){
        repositorio.getJugadoresPorEquipo(idEquipo,object: JugadoresDataSource.JugadorListRepositoryCallback{
            override fun onJugadorResponse(jugadores: List<Jugador>) {
                jugadorListLiveData.value = Resource.success(jugadores)
            }

            override fun onJugadorError(msg: String?) {
                jugadorListLiveData.value = Resource.error(msg.orEmpty(), emptyList())
            }

            override fun onJugadorLoading() {
                jugadorListLiveData.value = Resource.loading(emptyList())
            }

        })
    }


    fun getJugador(id:Int){
        repositorio.getJugador(id,object: JugadoresDataSource.JugadorRepositoryCallback{
            override fun onJugadorResponse(jugador: Jugador) {
                val listPlayer = listOf(jugador)
                jugadorListLiveData.value=Resource.success(listPlayer)
            }

            override fun onJugadorError(msg: String?) {
                jugadorListLiveData.value= Resource.error(msg.orEmpty(), emptyList())
            }

        })
    }

    fun addJugador(jugador: Jugador, equiposViewModel: EquiposViewModel,equipo: Equipo) {
        repositorio.addJugador(
            jugador,
            object :
                JugadoresDataSource.JugadorRepositoryCallback {
                override fun onJugadorResponse(jugador: Jugador) {
                    jugadorAddedLiveData.value = Resource.success(jugador)
                    //getJugadores()
                    val listaJugadores: MutableList<Int> = equipo.idJugadores
                    listaJugadores.add(jugador.id)
                    val equipoEditado  = Equipo(equipo.id,equipo.nombre,equipo.escudo, listaJugadores)
                    equiposViewModel.updateEquipo(equipoEditado.id,equipoEditado)
                }

                override fun onJugadorError(msg: String?) {
                    jugadorAddedLiveData.value = Resource.error(msg.orEmpty(), jugador)
                }
            })
    }

    fun deleteJugador(jugador: Jugador,equiposViewModel: EquiposViewModel,equipo: Equipo) {
        repositorio.deleteJugador(
            jugador,
            object :
                JugadoresDataSource.JugadorRepositoryCallback {
                override fun onJugadorResponse(jugador: Jugador) {
                    jugadorDeletedLiveData.value = Resource.success(jugador)
                    val listaJugadores : MutableList<Int> = equipo.idJugadores
                    listaJugadores.remove(jugador.id)
                    val equipoEditado = Equipo(equipo.id,equipo.nombre,equipo.escudo, listaJugadores)
                    equiposViewModel.updateEquipo(equipoEditado.id,equipoEditado,true)
                    getJugadoresPorEquipo(equipo.id)
                }

                override fun onJugadorError(msg: String?) {
                    jugadorDeletedLiveData.value = Resource.error(msg.orEmpty(), jugador)
                }

            })
    }

    fun updateJugador(id: Int,jugador: Jugador) {
        repositorio.updateJugador(
            id,
            jugador,
            object :
                JugadoresDataSource.JugadorRepositoryCallback {
                override fun onJugadorResponse(jugador: Jugador) {
                    jugadorUpdatedLiveData.value = Resource.success(jugador)
                }

                override fun onJugadorError(msg: String?) {
                    jugadorUpdatedLiveData.value = Resource.error(msg.orEmpty(), jugador)
                }

            })
    }





}