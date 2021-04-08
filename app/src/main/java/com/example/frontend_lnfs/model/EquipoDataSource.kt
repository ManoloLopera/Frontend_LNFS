package com.example.frontend_lnfs.model

interface EquipoDataSource {
    fun getEquipos(callback: EquipoListRepositoryCallback)
    fun getEquipo(id: Int, callback: EquipoRepositoryCallback)
    fun addEquipo(equipo: Equipo, callback: EquipoRepositoryCallback)
    fun deleteEquipo(equipo: Equipo, callback: EquipoRepositoryCallback)
    fun updateEquipo(id: Int, equipo: Equipo?, callback: EquipoRepositoryCallback)

    interface EquipoListRepositoryCallback {
        fun onEquipoResponse(equipos: List<Equipo>)
        fun onEquipoError(msg: String?)
        fun onEquipoLoading()
    }
    interface EquipoRepositoryCallback {
        fun onEquipoResponse(equipo: Equipo)
        fun onEquipoError(msg: String?)
    }
}