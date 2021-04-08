package com.example.frontend_lnfs.model

import com.example.frontend_lnfs.model.api.EquipoDto

object EquipoMapper {
    fun transformObjectBoToDto(bo: Equipo): EquipoDto {
        return EquipoDto(
            bo.nombre,
            bo.escudo,
            bo.idJugadores
        )
    }
}