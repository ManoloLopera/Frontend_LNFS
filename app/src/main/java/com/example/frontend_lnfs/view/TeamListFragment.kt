package com.example.frontend_lnfs.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.frontend_lnfs.R
import com.example.frontend_lnfs.adapter.EquipoAdapter
import com.example.frontend_lnfs.model.*

class TeamListFragment :Fragment(){

    lateinit var recyclerView: RecyclerView
    lateinit var teamAdapter:EquipoAdapter
    lateinit var swipe: SwipeRefreshLayout
    var listaVacia:MutableList<Equipo> = mutableListOf()
    lateinit var clicked:onClicked.TeamClicked
    lateinit var longClicked:onLongClicked.TeamLongClicked
    lateinit var alertDialog: AlertDialog.Builder

    private val viewModel: EquiposViewModel by lazy {
        ViewModelProviders.of(this).get(EquiposViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lista_equipos,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe=view.findViewById(R.id.swipe)
        val detalleFragment = DetailsFragment()

        clicked= object : onClicked.TeamClicked {
            override fun onClicked(equipo: Equipo) {

                var equipoSeleccionado = Bundle()
                equipoSeleccionado.putParcelable("equipo", equipo)
                detalleFragment.arguments=equipoSeleccionado
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.container,detalleFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }

        }

        longClicked = object: onLongClicked.TeamLongClicked {
            override fun teamLongClicked(equipo: Equipo): Boolean {
                val editFragment = FormularioEditFragment()
                alertDialog=AlertDialog.Builder(context!!)
                alertDialog.setTitle("¿Qué quieres hacer con el equipo \"${equipo.nombre}\" ?")
                alertDialog.setMessage("Seleccione en Modificar si quiere cambiar alguno de sus atributos, o pulse Eliminar si desea eliminar este equipo")

                alertDialog.setPositiveButton("Eliminar"){dialog, which ->
                    viewModel.deleteEquipo(equipo)
                    swipe.isRefreshing=true
                    Toast.makeText(context,"El equipo ha sido eliminado",Toast.LENGTH_LONG).show()
                }

                alertDialog.setNeutralButton("Cancelar"){dialog, which ->
                    dialog.cancel()
                }

                alertDialog.setNegativeButton("Modificar"){dialog, which ->
                    val args=Bundle()
                    args.putParcelable("equipo",equipo)
                    editFragment.arguments=args
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.container,editFragment)
                        ?.addToBackStack(null)
                        ?.commit()
                }

                alertDialog.show()
                return true
            }

        }

        teamAdapter= EquipoAdapter(listaVacia,clicked,longClicked)

        recyclerView=view.findViewById<RecyclerView>(R.id.lista_equipos).apply {
            layoutManager= GridLayoutManager(context,2)
            adapter=teamAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        observeTeamList()
        viewModel.getEquipos()
        swipe.setOnRefreshListener {
            viewModel.getEquipos()
        }


    }

    private fun observeTeamList() {
        viewModel.equipoListLiveData.observe(viewLifecycleOwner , Observer {
            resource -> when(resource.status)
            {
                Resource.Status.SUCCESS->{
                    teamAdapter.teamList= resource.data as MutableList<Equipo>
                    teamAdapter.notifyDataSetChanged()
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