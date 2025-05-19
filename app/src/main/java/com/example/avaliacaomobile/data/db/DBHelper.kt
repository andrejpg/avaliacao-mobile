package com.example.avaliacaomobile.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object {
            const val DATABASE_NAME = "car_models.db"
            const val DATABASE_VERSION = 1
            const val TABLE_NAME = "carModels"
        }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                marca TEXT NOT NULL,
                modelo TEXT NOT NULL,
                ano TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    }