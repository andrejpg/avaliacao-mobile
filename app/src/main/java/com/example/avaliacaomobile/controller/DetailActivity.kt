package com.example.avaliacaomobile.controller

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avaliacaomobile.R
import com.example.avaliacaomobile.data.dao.CarModelDAO
import com.example.avaliacaomobile.model.CarModel

class DetailActivity : AppCompatActivity() {
    private lateinit var carDAO: CarModelDAO
    private var carId: Int = 0
    private lateinit var editTextModelo: EditText
    private lateinit var editTextMarca: EditText
    private lateinit var editTextAno: EditText
    private lateinit var btnDeletar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        carDAO = CarModelDAO(this)
        editTextModelo = findViewById(R.id.editTextModelo)
        editTextMarca = findViewById(R.id.editTextMarca)
        editTextAno = findViewById(R.id.editTextAno)
        btnDeletar = findViewById(R.id.btnDeletarCarro)

        carId = intent.getIntExtra("carId", 0)
        if (carId != 0) {
            btnDeletar.visibility = Button.VISIBLE
            editCar()
        }
    }

    fun saveCar(view: View) {
        if (editTextModelo.text.isNotEmpty() && editTextMarca.text.isNotEmpty() && editTextAno.text.isNotEmpty()) {
            if (carId == 0) {
                val newCar = CarModel(
                    modelo = editTextModelo.text.toString(),
                    marca = editTextMarca.text.toString(),
                    ano = editTextAno.text.toString()
                )
                carDAO.addCarModel(newCar)
                Toast.makeText(this, "Modelo adicionado!", Toast.LENGTH_SHORT).show()
            } else {
                val updatedCar = CarModel(
                    id = carId,
                    modelo = editTextModelo.text.toString(),
                    marca = editTextMarca.text.toString(),
                    ano = editTextAno.text.toString()
                )
                carDAO.updateCarModel(updatedCar)
                Toast.makeText(this, "Modelo atualizado!", Toast.LENGTH_SHORT).show()
            }
            finish()
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editCar() {
        val car = carDAO.getCarModelById(carId)
        car?.let {
            editTextModelo.setText(it.modelo)
            editTextMarca.setText(it.marca)
            editTextAno.setText(it.ano)
        }
    }

    fun deleteCar(view :View) {
        carDAO.deleteCarModel(carId)
        Toast.makeText(this, "Modelo deletado!", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun goBack(view: View) {
        finish()
    }
}