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


/**
 * A simple [Fragment] subclass.
 */
class DetailsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var swipe: SwipeRefreshLayout
    lateinit var playerAdapter: JugadorAdapter
    lateinit var idJugadores:MutableList<Int>
    lateinit var playerClicked: onClicked.PlayerClicked
    lateinit var playerLongClicked: onLongClicked.PlayerLongClicked
    lateinit var jugadores: List<Jugador>
    var equipo:Equipo? = null
    lateinit var alertDialog: AlertDialog.Builder

    private val viewModel: JugadoresViewModel by lazy {
        ViewModelProviders.of(this).get(JugadoresViewModel::class.java)
    }

    private val viewModelEquipo: EquiposViewModel by lazy {
        ViewModelProviders.of(this).get(EquiposViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        swipe=view.findViewById(R.id.swipe_jugadores)
        jugadores= mutableListOf()

        val annadirJugador = annadirJugador
        val formularioJugador = FormularioJugador()
        annadirJugador.setOnClickListener {
            var equipo: Equipo?=arguments?.getParcelable("equipo")
            var equipoSeleccionado = Bundle()
            equipoSeleccionado.putParcelable("equipo", equipo)
            formularioJugador.arguments=equipoSeleccionado
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container,formularioJugador)
                ?.addToBackStack(null)
                ?.commit()
        }

        equipo=arguments?.getParcelable("equipo")
        if (equipo != null) {
            nombreClub.text = equipo?.nombre

            Glide
                .with(this)
                .load(equipo?.escudo)
                .centerInside()
                .into(escudo)

            idJugadores=equipo?.idJugadores!!
            //Log.i("jugadores",idJugadores.size.toString())
        }

        val detalleJugador = DetalleJugadorFragment()
        playerClicked = object: onClicked.PlayerClicked{
            override fun onClicked(jugador: Jugador) {
                var jugadorSeleccionado = Bundle()
                jugadorSeleccionado.putParcelable("jugador", jugador)
                detalleJugador.arguments=jugadorSeleccionado
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.container,detalleJugador)
                    ?.addToBackStack(null)
                    ?.commit()
            }

        }

        val editJugadorFragment = FormularioEditJugadorFragment()
        playerLongClicked = object : onLongClicked.PlayerLongClicked {
            override fun playerLongClicked(jugador: Jugador): Boolean {
                //val editFragment = FormularioEditPlayerFragment()
                alertDialog=AlertDialog.Builder(context!!)
                alertDialog.setTitle("¿Qué quieres hacer con el jugador \"${jugador.nombre} \" ?")
                alertDialog.setMessage("Seleccione en Modificar si quiere cambiar alguno de sus atributos, o pulse Eliminar si desea eliminar este jugador")

                alertDialog.setPositiveButton("Eliminar"){dialog, which ->
                    viewModel.deleteJugador(jugador,viewModelEquipo,equipo!!)
                    swipe.isRefreshing=true
                    Toast.makeText(context,"El jugador ha sido eliminado",Toast.LENGTH_LONG).show()
                }

                alertDialog.setNeutralButton("Cancelar"){dialog, which ->
                    dialog.cancel()
                }

                alertDialog.setNegativeButton("Modificar"){dialog, which ->
                    val jugadorSeleccionado=Bundle()
                    jugadorSeleccionado.putParcelable("jugador",jugador)
                    editJugadorFragment.arguments=jugadorSeleccionado
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.container,editJugadorFragment)
                        ?.addToBackStack(null)
                        ?.commit()
                }

                alertDialog.show()
                return true

            }

        }

        playerAdapter= JugadorAdapter(jugadores,playerClicked,playerLongClicked)



        recyclerView = view.findViewById<RecyclerView>(R.id.lista_jugadores).apply{
            layoutManager= LinearLayoutManager(context)
            adapter=playerAdapter
        }

    }


    override fun onStart() {
        super.onStart()
        observeJugadores()
        viewModel.getJugadoresPorEquipo(equipo?.id!!)
        swipe.setOnRefreshListener {
            viewModel.getJugadoresPorEquipo(equipo?.id!!)
        }
    }

    private fun observeJugadores() {
        viewModel.jugadorListLiveData.observe(viewLifecycleOwner, Observer {
            resource->when(resource.status)
            {
                Resource.Status.SUCCESS->{
                    playerAdapter.playerList = resource.data
                    playerAdapter.notifyDataSetChanged()
                    Log.i("jugadores",playerAdapter.playerList.size.toString())
                    swipe.isRefreshing=false
                }

                Resource.Status.ERROR->{
                    swipe.isRefreshing=false
                    Toast.makeText(context,resource.message,Toast.LENGTH_SHORT).show()
                }

                Resource.Status.LOADING->{
                    swipe.isRefreshing=true
                }
            }
        })
    }
}

