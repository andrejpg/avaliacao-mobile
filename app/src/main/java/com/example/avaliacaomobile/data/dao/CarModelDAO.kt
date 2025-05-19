package com.example.avaliacaomobile.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.avaliacaomobile.data.db.DBHelper
import com.example.avaliacaomobile.model.CarModel

class CarModelDAO (private val context: Context) {

    private val dbHelper = DBHelper(context)

    fun addCarModel(carModel: CarModel): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("marca", carModel.marca)
            put("modelo", carModel.modelo)
            put("ano", carModel.ano)
        }
        val id = db.insert(DBHelper.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllCarModels(): List<CarModel> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null)
        val charList = mutableListOf<CarModel>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val marca = cursor.getString(cursor.getColumnIndexOrThrow("marca"))
            val modelo = cursor.getString(cursor.getColumnIndexOrThrow("modelo"))
            val ano = cursor.getString(cursor.getColumnIndexOrThrow("ano"))
            charList.add(CarModel(id, marca, modelo, ano))
    }
    cursor.close()
    db.close()
    return charList
    }

    fun getCarModelById(id: Int): CarModel? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null, "id = ?", arrayOf(id.toString()), null, null, null)
        var carModel: CarModel? = null
        if (cursor.moveToFirst()) {
            val marca = cursor.getString(cursor.getColumnIndexOrThrow("marca"))
            val modelo = cursor.getString(cursor.getColumnIndexOrThrow("modelo"))
            val ano = cursor.getString(cursor.getColumnIndexOrThrow("ano"))
            carModel = CarModel(id, marca, modelo, ano)
        }
        cursor.close()
        db.close()
        return carModel
    }

    fun updateCarModel(carModel: CarModel): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("marca", carModel.marca)
            put("modelo", carModel.modelo)
            put("ano", carModel.ano)
    }
        val rowsAffected = db.update(DBHelper.TABLE_NAME, values, "id = ?", arrayOf(carModel.id.toString()))
        db.close()
        return rowsAffected
    }

    fun deleteCarModel(id: Int): Int {
        val db = dbHelper.writableDatabase
        val rowsDeleted = db.delete(DBHelper.TABLE_NAME, "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }
}