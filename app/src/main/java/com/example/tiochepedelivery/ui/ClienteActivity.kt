package com.example.tiochepedelivery.ui

import android.os.Bundle
import com.example.tiochepedelivery.R
import com.example.tiochepedelivery.data.DatosApp
import com.example.tiochepedelivery.logic.PedidoManager

class ClienteActivity : BasePantallaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)

        configurarPantalla("Cliente", "Catálogo, carrito y seguimiento")
        mostrarMenu()
    }

    private fun mostrarMenu() {
        limpiar()

        titulo("Panel del cliente")
        texto("Desde aquí el cliente puede ver productos, agregar al carrito, confirmar su pedido y revisar el seguimiento.")

        boton("Ver catálogo") {
            mostrarCatalogo()
        }

        boton("Ver carrito (${PedidoManager.cantidadCarrito()})") {
            mostrarCarrito()
        }

        boton("Seguimiento del pedido") {
            mostrarSeguimiento()
        }

        boton("Historial de pedidos") {
            mostrarHistorial()
        }

        botonSecundario("Volver") {
            finish()
        }
    }

    private fun mostrarCatalogo() {
        limpiar()

        titulo("Catálogo de productos")

        DatosApp.productos.forEach { producto ->
            texto(producto.nombre, 17f, true)
            texto(producto.descripcion)
            texto("Categoría: ${producto.categoria}")
            texto("Precio: S/ %.2f".format(producto.precio))
            texto("Disponibilidad: ${if (producto.disponible) "Disponible" else "Agotado"}")

            if (producto.disponible) {
                boton("Agregar al carrito") {
                    PedidoManager.agregarAlCarrito(producto)
                    mensaje("Producto agregado al carrito")
                    mostrarCatalogo()
                }
            } else {
                texto("Este producto no está disponible.", 15f, true)
            }

            linea()
        }

        botonSecundario("Ir al carrito") {
            mostrarCarrito()
        }

        botonSecundario("Volver") {
            mostrarMenu()
        }
    }

    private fun mostrarCarrito() {
        limpiar()

        titulo("Carrito de compras")

        if (DatosApp.carrito.isEmpty()) {
            texto("El carrito está vacío.")
            botonSecundario("Volver al catálogo") {
                mostrarCatalogo()
            }
            botonSecundario("Volver al menú") {
                mostrarMenu()
            }
            return
        }

        DatosApp.carrito.forEach { item ->
            texto(item.producto.nombre, 17f, true)
            texto("Cantidad: ${item.cantidad}")
            texto("Precio unitario: S/ %.2f".format(item.producto.precio))
            texto("Subtotal: S/ %.2f".format(item.producto.precio * item.cantidad))
            linea()
        }

        texto("Subtotal: S/ %.2f".format(PedidoManager.subtotalCarrito()), 16f, true)
        texto("Delivery: S/ 3.00", 16f, true)
        texto("Total: S/ %.2f".format(PedidoManager.totalCarrito()), 18f, true)

        boton("Continuar pedido") {
            mostrarConfirmacion()
        }

        botonSecundario("Vaciar carrito") {
            PedidoManager.vaciarCarrito()
            mensaje("Carrito vaciado")
            mostrarCarrito()
        }

        botonSecundario("Volver al catálogo") {
            mostrarCatalogo()
        }

        botonSecundario("Volver al menú") {
            mostrarMenu()
        }
    }

    private fun mostrarConfirmacion() {
        limpiar()

        titulo("Confirmación del pedido")

        val nombre = campo("Nombre del cliente", DatosApp.clienteNombre)
        val telefono = campo("Teléfono", DatosApp.clienteTelefono)
        val direccion = campo("Dirección de entrega", DatosApp.clienteDireccion)
        val referencia = campo("Referencia", DatosApp.clienteReferencia)
        val observaciones = campo("Observaciones: sin ají, más papas, etc.")

        texto("Método de pago", 16f, true)

        val metodoPago = selector(
            listOf(
                "Pago contra entrega",
                "Efectivo",
                "Yape",
                "Plin"
            )
        )

        espacio()

        texto("Total a pagar: S/ %.2f".format(PedidoManager.totalCarrito()), 18f, true)

        boton("Confirmar pedido") {
            if (
                nombre.text.isBlank() ||
                telefono.text.isBlank() ||
                direccion.text.isBlank()
            ) {
                mensaje("Completa nombre, teléfono y dirección")
            } else {
                val pedido = PedidoManager.confirmarPedido(
                    cliente = nombre.text.toString(),
                    telefono = telefono.text.toString(),
                    direccion = direccion.text.toString(),
                    referencia = referencia.text.toString(),
                    metodoPago = metodoPago.selectedItem.toString(),
                    observaciones = observaciones.text.toString()
                )

                if (pedido != null) {
                    mensaje("Pedido registrado correctamente")
                    mostrarSeguimiento()
                } else {
                    mensaje("El carrito está vacío")
                }
            }
        }

        botonSecundario("Volver al carrito") {
            mostrarCarrito()
        }
    }

    private fun mostrarSeguimiento() {
        limpiar()

        titulo("Seguimiento del pedido")

        if (DatosApp.pedidos.isEmpty()) {
            texto("Todavía no tienes pedidos registrados.")
            botonSecundario("Volver") {
                mostrarMenu()
            }
            return
        }

        val pedido = DatosApp.pedidos.last()

        texto("Pedido #${pedido.id}", 18f, true)
        texto("Cliente: ${pedido.cliente}")
        texto("Dirección: ${pedido.direccion}")
        texto("Total: S/ %.2f".format(pedido.total))
        texto("Estado actual: ${pedido.estado}", 18f, true)

        linea()

        titulo("Avance del pedido")

        if (pedido.estado == "Cancelado") {
            texto("✗ Pedido cancelado", 18f, true)
        } else {
            val indiceActual = PedidoManager.estadosPedido.indexOf(pedido.estado)

            PedidoManager.estadosPedido.forEachIndexed { index, estado ->
                val marca = if (index <= indiceActual) "✓" else "○"
                texto("$marca $estado")
            }
        }

        botonSecundario("Ver historial") {
            mostrarHistorial()
        }

        botonSecundario("Volver") {
            mostrarMenu()
        }
    }

    private fun mostrarHistorial() {
        limpiar()

        titulo("Historial de pedidos")

        if (DatosApp.pedidos.isEmpty()) {
            texto("No existen pedidos registrados.")
            botonSecundario("Volver") {
                mostrarMenu()
            }
            return
        }

        DatosApp.pedidos.forEach { pedido ->
            texto("Pedido #${pedido.id}", 17f, true)
            texto("Estado: ${pedido.estado}")
            texto("Total: S/ %.2f".format(pedido.total))
            texto("Dirección: ${pedido.direccion}")
            linea()
        }

        botonSecundario("Volver") {
            mostrarMenu()
        }
    }
}