package com.example.frontend_lnfs.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.frontend_lnfs.R
import com.example.sprint2.FormularioFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (currentFragment == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, Portada())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuprincipal,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_lista->supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container,TeamListFragment())
                ?.addToBackStack(null)
                ?.commit()
            R.id.menu_nuevo->supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container,FormularioFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        return super.onOptionsItemSelected(item)
    }
}
