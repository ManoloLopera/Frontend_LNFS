package com.example.frontend_lnfs.model

import com.example.frontend_lnfs.model.api.JugadorDto

object JugadorMapper {
    fun transformObjectBoToDto(jo:Jugador): JugadorDto {
        return JugadorDto(
            jo.equipo,
            jo.nombre,
            jo.sobrenombre,
            jo.posicion
        )
    }
}