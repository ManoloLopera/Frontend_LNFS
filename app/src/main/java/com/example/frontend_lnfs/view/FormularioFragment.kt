package com.example.sprint2


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
import com.example.frontend_lnfs.model.Equipo
import com.example.frontend_lnfs.model.EquiposViewModel
import com.example.frontend_lnfs.model.Resource
import kotlinx.android.synthetic.main.fragment_formulario.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FormularioFragment : Fragment() {
    var url:String=""
    private val equiposViewModel: EquiposViewModel by lazy {
        ViewModelProviders.of(this).get(EquiposViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_formulario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val boton = view.findViewById<Button>(R.id.guardar)


        boton.setOnClickListener {
            url=urlEscudo.text.toString()
            var valido = true
            if (nombreClub.text.isEmpty())
            {
                valido=false
                Toast.makeText(context,"El nombre del Club está vacío",Toast.LENGTH_LONG).show()
            }
            if (urlEscudo.text.toString()==""){
                url="https://m-tv.imgix.net/b949d9a77f6575beb96aa03ce32e6a7dd9fb3c0a6810d1dad000ccadb6e86c0c.png"
            }
            if (valido){
                var equipo = Equipo(45,nombreClub.text.toString(),url, mutableListOf())
                equiposViewModel.addEquipo(equipo)
                observeAddedEquipo()
                activity?.supportFragmentManager?.popBackStack()
            }

        }
    }


    private fun observeAddedEquipo() {
        equiposViewModel.equipoAddedLiveData.observe(viewLifecycleOwner, Observer { equipo ->
            if (equipo.status == Resource.Status.SUCCESS) {
                Toast.makeText(
                    context,
                    "Equipo ${equipo.data.nombre} Added to Backend",
                    Toast.LENGTH_LONG
                ).show()
            }else{
                Toast.makeText(context,equipo.message,Toast.LENGTH_SHORT).show()
            }
        })
    }

}