package com.example.frontend_lnfs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frontend_lnfs.R
import com.example.frontend_lnfs.model.Equipo
import com.example.frontend_lnfs.model.onClicked
import com.example.frontend_lnfs.model.onLongClicked

class EquipoAdapter(var teamList:MutableList<Equipo>, val onClicked: onClicked.TeamClicked, val onLongClicked: onLongClicked.TeamLongClicked):RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder>() {
    override fun getItemCount(): Int =teamList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_equipo,parent,false)
        return EquipoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        val equipo=teamList[position]
        holder.bind(equipo,onClicked,onLongClicked)
    }

    inner class EquipoViewHolder(view:View):RecyclerView.ViewHolder(view){
        val escudo= itemView.findViewById<ImageView>(R.id.equipo_escudo)
        val nombre = itemView.findViewById<TextView>(R.id.equipo_nombre)

        fun bind(equipo:Equipo, onClicked: onClicked.TeamClicked, onLongClicked: onLongClicked.TeamLongClicked){
            nombre.text = equipo.nombre
            Glide
                .with(itemView.context)
                .load(equipo.escudo)
                .centerInside()
                //.placeholder(R.drawable.portadavacia)
                .into(escudo)

            itemView.setOnClickListener {
                onClicked.onClicked(equipo)
            }

            itemView.setOnLongClickListener {
                onLongClicked.teamLongClicked(equipo)
            }
        }
    }
}