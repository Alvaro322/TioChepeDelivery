package com.example.tiochepedelivery.ui

import android.os.Bundle
import com.example.tiochepedelivery.R
import com.example.tiochepedelivery.logic.PedidoManager

class CocinaActivity : BasePantallaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocina)

        configurarPantalla("Cocina", "Preparación de pedidos")
        mostrarCocina()
    }

    private fun mostrarCocina() {
        limpiar()

        titulo("Pedidos para preparar")

        val pedidos = PedidoManager.pedidosCocina()

        if (pedidos.isEmpty()) {
            texto("No hay pedidos pendientes para cocina.")
            texto("Los pedidos aparecerán aquí cuando recepción los confirme.")
        }

        pedidos.forEach { pedido ->
            resumenPedido(pedido)

            if (pedido.estado == "Confirmado") {
                boton("Marcar en preparación") {
                    PedidoManager.cambiarEstado(pedido, "En preparación")
                    mensaje("Pedido marcado en preparación")
                    mostrarCocina()
                }
            }

            if (pedido.estado == "En preparación") {
                boton("Marcar listo para entrega") {
                    PedidoManager.cambiarEstado(pedido, "Listo para entrega")
                    mensaje("Pedido listo para entrega")
                    mostrarCocina()
                }
            }

            linea()
        }

        botonSecundario("Actualizar") {
            mostrarCocina()
        }

        botonSecundario("Volver") {
            finish()
        }
    }
}