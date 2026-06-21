package com.example.tiochepedelivery.logic

import com.example.tiochepedelivery.data.DatosApp
import com.example.tiochepedelivery.model.DetallePedido
import com.example.tiochepedelivery.model.ItemCarrito
import com.example.tiochepedelivery.model.Pedido
import com.example.tiochepedelivery.model.Producto

object PedidoManager {

    val estadosPedido = listOf(
        "Recibido",
        "Confirmado",
        "En preparación",
        "Listo para entrega",
        "En camino",
        "Entregado"
    )

    fun agregarAlCarrito(producto: Producto) {
        val itemExistente = DatosApp.carrito.find { it.producto.id == producto.id }

        if (itemExistente != null) {
            itemExistente.cantidad += 1
        } else {
            DatosApp.carrito.add(ItemCarrito(producto, 1))
        }
    }

    fun cantidadCarrito(): Int {
        return DatosApp.carrito.sumOf { it.cantidad }
    }

    fun subtotalCarrito(): Double {
        return DatosApp.carrito.sumOf { it.producto.precio * it.cantidad }
    }

    fun totalCarrito(): Double {
        val delivery = if (DatosApp.carrito.isNotEmpty()) 3.00 else 0.00
        return subtotalCarrito() + delivery
    }

    fun vaciarCarrito() {
        DatosApp.carrito.clear()
    }

    fun confirmarPedido(
        cliente: String,
        telefono: String,
        direccion: String,
        referencia: String,
        metodoPago: String,
        observaciones: String
    ): Pedido? {
        if (DatosApp.carrito.isEmpty()) {
            return null
        }

        val detalles = DatosApp.carrito.map {
            DetallePedido(
                nombreProducto = it.producto.nombre,
                cantidad = it.cantidad,
                precioUnitario = it.producto.precio
            )
        }

        val pedido = Pedido(
            id = DatosApp.siguienteIdPedido,
            cliente = cliente,
            telefono = telefono,
            direccion = direccion,
            referencia = referencia,
            metodoPago = metodoPago,
            observaciones = observaciones,
            detalles = detalles,
            total = totalCarrito(),
            estado = "Recibido"
        )

        DatosApp.siguienteIdPedido++
        DatosApp.pedidos.add(pedido)
        DatosApp.carrito.clear()

        return pedido
    }

    fun cambiarEstado(pedido: Pedido, nuevoEstado: String) {
        pedido.estado = nuevoEstado
    }

    fun pedidosRecepcion(): List<Pedido> {
        return DatosApp.pedidos.filter {
            it.estado == "Recibido" || it.estado == "Confirmado"
        }
    }

    fun pedidosCocina(): List<Pedido> {
        return DatosApp.pedidos.filter {
            it.estado == "Confirmado" || it.estado == "En preparación"
        }
    }

    fun pedidosRepartidor(): List<Pedido> {
        return DatosApp.pedidos.filter {
            it.estado == "Listo para entrega" || it.estado == "En camino"
        }
    }

    fun pedidosEntregados(): Int {
        return DatosApp.pedidos.count { it.estado == "Entregado" }
    }

    fun pedidosCancelados(): Int {
        return DatosApp.pedidos.count { it.estado == "Cancelado" }
    }

    fun totalVendido(): Double {
        return DatosApp.pedidos
            .filter { it.estado == "Entregado" }
            .sumOf { it.total }
    }
}