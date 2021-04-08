package com.example.frontend_lnfs.view


import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.frontend_lnfs.R
import com.example.frontend_lnfs.model.*
import kotlinx.android.synthetic.main.fragment_formulario.*
import kotlinx.android.synthetic.main.fragment_formulario_jugador.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FormularioJugador : Fragment() {
    private val jugadoresViewModel: JugadoresViewModel by lazy {
        ViewModelProviders.of(this).get(JugadoresViewModel::class.java)
    }
    private val equiposViewModel: EquiposViewModel by lazy {
        ViewModelProviders.of(this).get(EquiposViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_formulario_jugador, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val equipo: Equipo?=arguments?.getParcelable("equipo")
        if (equipo != null) {
           nombreEquipo.setText(equipo.nombre)
        }
        var listaJugadores: MutableList<Int> = equipo!!.idJugadores
        val boton = view.findViewById<Button>(R.id.guardar)


        boton.setOnClickListener {
            val equipoEditado  = Equipo(equipo.id,equipo.nombre,equipo.escudo, listaJugadores)
            var valido = true

            if(nombreJugador.text.isEmpty()){
                valido=false
                Toast.makeText(context,"El nombre del jugador está vacío",Toast.LENGTH_SHORT).show()
            }
            if (sobrenombre.text.isEmpty()){
                valido=false
                Toast.makeText(context,"El sobrenombre del jugador está vacío",Toast.LENGTH_SHORT).show()
            }

            if (valido){
                var jugador= Jugador(12,equipo.id,nombreJugador.text.toString(),sobrenombre.text.toString(),posicion.selectedItem.toString())
                jugadoresViewModel.addJugador(jugador, equiposViewModel,equipoEditado)
                observeAddedJugador()
                activity?.supportFragmentManager?.popBackStack()
            }

        }
    }




    private fun observeAddedJugador() {
        jugadoresViewModel.jugadorAddedLiveData.observe(viewLifecycleOwner, Observer { jugador ->
            if (jugador.status == Resource.Status.SUCCESS) {
                Toast.makeText(
                    context,
                    "Jugador ${jugador.data.nombre} Added to Backend",
                    Toast.LENGTH_LONG
                ).show()
            }else{
                Toast.makeText(context,jugador.message,Toast.LENGTH_SHORT).show()
            }
        })
    }
}