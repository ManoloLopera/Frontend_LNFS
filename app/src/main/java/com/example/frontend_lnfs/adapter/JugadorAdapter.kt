package com.example.frontend_lnfs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_lnfs.R
import com.example.frontend_lnfs.model.Jugador
import com.example.frontend_lnfs.model.onClicked
import com.example.frontend_lnfs.model.onLongClicked

class JugadorAdapter(var playerList:List<Jugador>, val onClicked: onClicked.PlayerClicked, val onLongClicked: onLongClicked.PlayerLongClicked):
    RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder>() {
    override fun getItemCount(): Int = playerList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jugador, parent, false)
        return JugadorViewHolder(view)
    }

    override fun onBindViewHolder(holder: JugadorViewHolder, position: Int) {
        val jugador = playerList[position]
        holder.bind(jugador, onClicked, onLongClicked)
    }

    inner class JugadorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sobrenombre = itemView.findViewById<TextView>(R.id.player_sobrenombre)
        val position= itemView.findViewById<TextView>(R.id.player_position)

        fun bind(jugador: Jugador, onClicked: onClicked.PlayerClicked, onLongClicked: onLongClicked.PlayerLongClicked) {
            sobrenombre.text = jugador.sobrenombre
            position.text = jugador.posicion

            itemView.setOnClickListener {
                onClicked.onClicked(jugador)
            }

            itemView.setOnLongClickListener {
                onLongClicked.playerLongClicked(jugador)
            }
        }
    }
}