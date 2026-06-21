package com.example.tiochepedelivery.ui

import android.os.Bundle
import com.example.tiochepedelivery.R
import com.example.tiochepedelivery.logic.PedidoManager

class RepartidorActivity : BasePantallaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repartidor)

        configurarPantalla("Repartidor", "Pedidos asignados para entrega")
        mostrarRepartidor()
    }

    private fun mostrarRepartidor() {
        limpiar()

        titulo("Pedidos para entregar")

        val pedidos = PedidoManager.pedidosRepartidor()

        if (pedidos.isEmpty()) {
            texto("No hay pedidos asignados para reparto.")
            texto("Los pedidos aparecerán aquí cuando cocina los marque como listos.")
        }

        pedidos.forEach { pedido ->
            resumenPedido(pedido)

            texto("Repartidor asignado: ${pedido.repartidor}", 16f, true)

            if (pedido.estado == "Listo para entrega") {
                boton("Iniciar entrega") {
                    PedidoManager.cambiarEstado(pedido, "En camino")
                    mensaje("Pedido marcado como en camino")
                    mostrarRepartidor()
                }
            }

            if (pedido.estado == "En camino") {
                boton("Marcar como entregado") {
                    PedidoManager.cambiarEstado(pedido, "Entregado")
                    mensaje("Pedido entregado correctamente")
                    mostrarRepartidor()
                }
            }

            linea()
        }

        botonSecundario("Actualizar") {
            mostrarRepartidor()
        }

        botonSecundario("Volver") {
            finish()
        }
    }
}