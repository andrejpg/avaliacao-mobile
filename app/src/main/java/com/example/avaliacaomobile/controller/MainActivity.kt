package com.example.avaliacaomobile.controller

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avaliacaomobile.R
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var carsListView: ListView
    lateinit var listAdapter: ArrayAdapter<String>
    lateinit var carsList: ArrayList<String>
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        carsListView = findViewById(R.id.listView)
        searchView = findViewById(R.id.searchView)

        carsList = ArrayList()
        carsList.add("Honda HR-V")
        carsList.add("Renault Kwid")
        carsList.add("Volskwagen Polo")
        listAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            carsList
        )
        carsListView.adapter = listAdapter

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (carsList.contains(query)) {
                    listAdapter.filter.filter(query)
                } else {
                    Toast.makeText(this@MainActivity, "Sem resultados.", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listAdapter.filter.filter(newText)
                return false
            }
        })
    }
}