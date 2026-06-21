package com.example.tiochepedelivery.ui

import android.os.Bundle
import com.example.tiochepedelivery.R
import com.example.tiochepedelivery.data.DatosApp
import com.example.tiochepedelivery.logic.PedidoManager

class AdministradorActivity : BasePantallaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrador)

        configurarPantalla("Administrador", "Resumen general del sistema")
        mostrarAdministrador()
    }

    private fun mostrarAdministrador() {
        limpiar()

        titulo("Resumen del día")

        texto("Pedidos registrados: ${DatosApp.pedidos.size}")
        texto("Pedidos entregados: ${PedidoManager.pedidosEntregados()}")
        texto("Pedidos cancelados: ${PedidoManager.pedidosCancelados()}")
        texto("Total vendido: S/ %.2f".format(PedidoManager.totalVendido()), 18f, true)

        linea()

        titulo("Estados de pedidos")

        val recibidos = DatosApp.pedidos.count { it.estado == "Recibido" }
        val confirmados = DatosApp.pedidos.count { it.estado == "Confirmado" }
        val preparacion = DatosApp.pedidos.count { it.estado == "En preparación" }
        val listos = DatosApp.pedidos.count { it.estado == "Listo para entrega" }
        val camino = DatosApp.pedidos.count { it.estado == "En camino" }
        val entregados = DatosApp.pedidos.count { it.estado == "Entregado" }
        val cancelados = DatosApp.pedidos.count { it.estado == "Cancelado" }

        texto("Recibidos: $recibidos")
        texto("Confirmados: $confirmados")
        texto("En preparación: $preparacion")
        texto("Listos para entrega: $listos")
        texto("En camino: $camino")
        texto("Entregados: $entregados")
        texto("Cancelados: $cancelados")

        linea()

        titulo("Productos del catálogo")

        DatosApp.productos.forEach { producto ->
            texto("${producto.nombre} - S/ %.2f".format(producto.precio), 16f, true)
            texto("Categoría: ${producto.categoria}")
            texto("Estado: ${if (producto.disponible) "Disponible" else "Agotado"}")

            botonSecundario(
                if (producto.disponible) "Marcar como agotado" else "Marcar como disponible"
            ) {
                producto.disponible = !producto.disponible
                mensaje("Estado del producto actualizado")
                mostrarAdministrador()
            }

            linea()
        }

        titulo("Usuarios del sistema")

        texto("Cliente: realiza pedidos y consulta seguimiento.")
        texto("Recepción: confirma o cancela pedidos.")
        texto("Cocina: prepara los pedidos confirmados.")
        texto("Repartidor: entrega los pedidos listos.")
        texto("Administrador: supervisa pedidos, productos y ventas.")

        linea()

        titulo("Últimos pedidos")

        if (DatosApp.pedidos.isEmpty()) {
            texto("Todavía no hay pedidos registrados.")
        } else {
            DatosApp.pedidos.takeLast(5).forEach { pedido ->
                texto("Pedido #${pedido.id}", 16f, true)
                texto("Cliente: ${pedido.cliente}")
                texto("Estado: ${pedido.estado}")
                texto("Total: S/ %.2f".format(pedido.total))
                linea()
            }
        }

        botonSecundario("Actualizar resumen") {
            mostrarAdministrador()
        }

        botonSecundario("Volver") {
            finish()
        }
    }
}