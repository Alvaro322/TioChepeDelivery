package com.example.tiochepedelivery.data

import com.example.tiochepedelivery.model.ItemCarrito
import com.example.tiochepedelivery.model.Pedido
import com.example.tiochepedelivery.model.Producto

object DatosApp {

    val productos = mutableListOf(
        Producto(
            id = 1,
            nombre = "1/4 Pollo a la Brasa",
            descripcion = "Con papas fritas, ensalada y cremas",
            precio = 15.00,
            categoria = "Pollos"
        ),
        Producto(
            id = 2,
            nombre = "1/2 Pollo a la Brasa",
            descripcion = "Con papas fritas, ensalada y cremas",
            precio = 28.00,
            categoria = "Pollos"
        ),
        Producto(
            id = 3,
            nombre = "Pollo entero familiar",
            descripcion = "Pollo entero con papas, ensalada y cremas",
            precio = 55.00,
            categoria = "Combos"
        ),
        Producto(
            id = 4,
            nombre = "Salchipollo",
            descripcion = "Salchipapa con presa de pollo",
            precio = 12.00,
            categoria = "Combos"
        ),
        Producto(
            id = 5,
            nombre = "Parrilla personal",
            descripcion = "Carne, pollo, papas y ensalada",
            precio = 22.00,
            categoria = "Parrillas"
        ),
        Producto(
            id = 6,
            nombre = "Gaseosa 1.5 L",
            descripcion = "Bebida familiar",
            precio = 8.00,
            categoria = "Bebidas"
        )
    )

    val carrito = mutableListOf<ItemCarrito>()

    val pedidos = mutableListOf<Pedido>()

    var siguienteIdPedido = 1

    var clienteNombre = "Álvaro Bujaico"
    var clienteTelefono = "999888777"
    var clienteDireccion = "Jr. Lima 245, Pampas, Tayacaja"
    var clienteReferencia = "Frente al parque principal"
}