package com.example.frontend_lnfs.model


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frontend_lnfs.model.api.EquipoRepository
import com.example.frontend_lnfs.model.api.EquipoRepositoryVolley

class EquiposViewModel : ViewModel() {
    val equipoListLiveData = MutableLiveData<Resource<List<Equipo>>>()
    val equipoAddedLiveData = MutableLiveData<Resource<Equipo>>()
    val equipoDeletedLiveData = MutableLiveData<Resource<Equipo>>()
    val equipoUpdatedLiveData = MutableLiveData<Resource<Equipo>>()
    val repositorio= EquipoRepository

    fun getEquipos() {
        repositorio.getEquipos(object :
            EquipoDataSource.EquipoListRepositoryCallback {
            override fun onEquipoResponse(equipos: List<Equipo>) {
                equipoListLiveData.value = Resource.success(equipos)
            }

            override fun onEquipoError(msg: String?) {
                equipoListLiveData.value = Resource.error(msg.orEmpty(), emptyList())
            }

            override fun onEquipoLoading() {
                equipoListLiveData.value = Resource.loading(emptyList())
            }
        })

    }

    fun getEquipo(id: Int){
        repositorio.getEquipo(id,object : EquipoDataSource.EquipoRepositoryCallback{
            override fun onEquipoResponse(equipo: Equipo) {
                equipoUpdatedLiveData.value = Resource.success(equipo)
            }

            override fun onEquipoError(msg: String?) {
                val equipo=Equipo(1, "", "", mutableListOf())
                equipoUpdatedLiveData.value = Resource.error(msg.toString(),equipo)
            }

        })
    }

    fun addEquipo(equipo: Equipo) {
        repositorio.addEquipo(
            equipo,
            object :
                EquipoDataSource.EquipoRepositoryCallback {
                override fun onEquipoResponse(equipo: Equipo) {
                    equipoAddedLiveData.value = Resource.success(equipo)
                    getEquipos()
                }

                override fun onEquipoError(msg: String?) {
                    equipoAddedLiveData.value = Resource.error(msg.orEmpty(), equipo)
                }
            })
    }

    fun deleteEquipo(equipo: Equipo) {
        repositorio.deleteEquipo(
            equipo,
            object :
                EquipoDataSource.EquipoRepositoryCallback {
                override fun onEquipoResponse(equipo: Equipo) {
                    equipoDeletedLiveData.value = Resource.success(equipo)
                    getEquipos()
                }

                override fun onEquipoError(msg: String?) {
                    equipoDeletedLiveData.value = Resource.error(msg.orEmpty(), equipo)
                }

            })
    }

    fun updateEquipo(id: Int,equipo: Equipo,vieneDeJugador:Boolean =false) {
        repositorio.updateEquipo(
            id,
            equipo,
            object :
                EquipoDataSource.EquipoRepositoryCallback {
                override fun onEquipoResponse(equipo: Equipo) {
                    equipoUpdatedLiveData.value = Resource.success(equipo)
                    if (vieneDeJugador){
                        getEquipo(id)
                    }else{
                        getEquipos()
                    }

                }

                override fun onEquipoError(msg: String?) {
                    equipoUpdatedLiveData.value = Resource.error(msg.orEmpty(), equipo)
                }

            })
    }



}