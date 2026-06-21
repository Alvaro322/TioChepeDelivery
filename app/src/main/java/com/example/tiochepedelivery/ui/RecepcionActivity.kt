package com.example.tiochepedelivery.ui

import android.os.Bundle
import com.example.tiochepedelivery.R
import com.example.tiochepedelivery.logic.PedidoManager

class RecepcionActivity : BasePantallaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recepcion)

        configurarPantalla("Recepción", "Confirmación de pedidos")
        mostrarRecepcion()
    }

    private fun mostrarRecepcion() {
        limpiar()

        titulo("Pedidos recibidos")

        val pedidos = PedidoManager.pedidosRecepcion()

        if (pedidos.isEmpty()) {
            texto("No hay pedidos pendientes en recepción.")
            texto("Cuando el cliente confirme un pedido, aparecerá aquí.")
        }

        pedidos.forEach { pedido ->
            resumenPedido(pedido)

            if (pedido.estado == "Recibido") {
                boton("Confirmar y enviar a cocina") {
                    PedidoManager.cambiarEstado(pedido, "Confirmado")
                    mensaje("Pedido confirmado correctamente")
                    mostrarRecepcion()
                }

                botonSecundario("Cancelar pedido") {
                    PedidoManager.cambiarEstado(pedido, "Cancelado")
                    mensaje("Pedido cancelado")
                    mostrarRecepcion()
                }
            } else {
                texto("Este pedido ya fue confirmado y está listo para cocina.", 15f, true)
            }

            linea()
        }

        botonSecundario("Actualizar") {
            mostrarRecepcion()
        }

        botonSecundario("Volver") {
            finish()
        }
    }
}