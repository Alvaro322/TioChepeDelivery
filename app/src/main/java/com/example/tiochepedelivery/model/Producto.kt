package com.example.tiochepedelivery.model

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    var disponible: Boolean = true
)