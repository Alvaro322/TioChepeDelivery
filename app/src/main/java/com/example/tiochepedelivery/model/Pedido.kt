package com.example.tiochepedelivery.model

data class Pedido(
    val id: Int,
    val cliente: String,
    val telefono: String,
    val direccion: String,
    val referencia: String,
    val metodoPago: String,
    val observaciones: String,
    val detalles: List<DetallePedido>,
    val total: Double,
    var estado: String,
    var repartidor: String = "Carlos"
)