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
import kotlinx.android.synthetic.main.form_edit_jugador_fragment.*
import kotlinx.android.synthetic.main.fragment_formulario.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FormularioEditJugadorFragment : Fragment() {
    //Pruebas para el SaveInstance.
    var url:String=""
    private val jugadoresViewModel: JugadoresViewModel by lazy {
        ViewModelProviders.of(this).get(JugadoresViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.form_edit_jugador_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val boton = view.findViewById<Button>(R.id.guardar)

        val jugador: Jugador =arguments?.getParcelable("jugador")!!

        val posiciones = resources.getStringArray(R.array.posiciones)
        nombreJugador.setText(jugador.nombre)
        sobrenombre.setText(jugador.sobrenombre)
        posicion.post{
            posicion.setSelection(posiciones.indexOf(jugador.posicion))
        }


        boton.setOnClickListener {

            var valido = true
            if(nombreJugador.text.isEmpty()){
                valido=false
                Toast.makeText(context,"El nombre del jugador está vacío",Toast.LENGTH_SHORT).show()
            }
            if (sobrenombre.text.isEmpty()){
                valido=false
                Toast.makeText(context,"El sobrenombre del jugador está vacío",Toast.LENGTH_SHORT).show()
            }
            if (valido) {

                val jugadorEditado = Jugador(
                    jugador.id,
                    jugador.equipo,
                    nombreJugador.text.toString(),
                    sobrenombre.text.toString(),
                    posicion.selectedItem.toString()
                )

                jugadoresViewModel.updateJugador(jugadorEditado.id, jugadorEditado)
                observeAddedJugador()
                activity?.supportFragmentManager?.popBackStack()
            }
        }

    }


    private fun observeAddedJugador() {
        jugadoresViewModel.jugadorUpdatedLiveData.observe(viewLifecycleOwner, Observer { jugador ->
            if (jugador.status == Resource.Status.SUCCESS) {
                Toast.makeText(context, "Jugador ${jugador.data.nombre} Added to Backend", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,jugador.message,Toast.LENGTH_SHORT).show()
            }
        })
    }
}