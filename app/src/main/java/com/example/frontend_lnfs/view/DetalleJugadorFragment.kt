package com.example.frontend_lnfs.view




import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.frontend_lnfs.R
import com.example.frontend_lnfs.adapter.JugadorAdapter
import com.example.frontend_lnfs.model.*
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details_jugador.*


/**
 * A simple [Fragment] subclass.
 */
class DetalleJugadorFragment : Fragment() {


    var jugador: Jugador? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_jugador, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //   swipe=view.findViewById(R.id.swipe_jugadores)
        //  jugadores= mutableListOf()


        jugador = arguments?.getParcelable("jugador")
        if (jugador != null) {
            nombreJugador.text = jugador?.nombre
            posicion.text = jugador?.posicion
            sobrenombre.text = jugador?.sobrenombre

        }


    }
}




