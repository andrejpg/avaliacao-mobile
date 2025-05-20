package com.example.avaliacaomobile.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avaliacaomobile.R
import com.example.avaliacaomobile.adapter.CarModelAdapter
import com.example.avaliacaomobile.data.dao.CarModelDAO
import com.example.avaliacaomobile.model.CarModel

class MainActivity : AppCompatActivity() {
    lateinit var listView: ListView
    lateinit var carDAO: CarModelDAO
    lateinit var searchView: SearchView
    lateinit var carAdapter: CarModelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        searchView = findViewById(R.id.searchView)

        listView = findViewById(R.id.listView)
        carDAO = CarModelDAO(this)

        val carN1 = CarModel(
            modelo = "Corolla",
            marca = "Toyota",
            ano = "2020"
        )
        carDAO.addCarModel(carN1)
        val carN2 = CarModel(
            modelo = "Civic",
            marca = "Honda",
            ano = "2025"
        )
        carDAO.addCarModel(carN2)

        listCars()

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedCar = parent.getItemAtPosition(position) as CarModel
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("carId", selectedCar.id)
            }
            startActivity(intent)
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                carAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                carAdapter.filter.filter(newText)
                return true
            }
        })

    }

    private fun listCars() {
        val cars = carDAO.getAllCarModels().toMutableList()
        if(cars.isEmpty()) {
            listView.visibility = ListView.GONE
        } else {
            listView.visibility = ListView.VISIBLE
            carAdapter = CarModelAdapter(this, cars)
            listView.adapter = carAdapter
        }
    }

    fun addCar(view: View) {
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        listCars()
    }
}