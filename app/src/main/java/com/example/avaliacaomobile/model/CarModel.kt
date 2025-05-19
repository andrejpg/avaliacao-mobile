package com.example.avaliacaomobile.model

data class CarModel(
    val id: Int = 0,
    val marca: String,
    val modelo: String,
    val ano: String
){
    override fun toString(): String {
        return modelo //retorna só o modelo do carro na lista
    }
}
