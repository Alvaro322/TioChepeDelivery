package com.example.tiochepedelivery.ui

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Space
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.tiochepedelivery.R
import com.example.tiochepedelivery.model.Pedido

open class BasePantallaActivity : ComponentActivity() {

    protected lateinit var contenedor: LinearLayout
    protected lateinit var tvHeader: TextView
    protected lateinit var tvSubHeader: TextView

    protected fun configurarPantalla(titulo: String, subtitulo: String) {
        contenedor = findViewById(R.id.contenedor)
        tvHeader = findViewById(R.id.tvHeader)
        tvSubHeader = findViewById(R.id.tvSubHeader)

        tvHeader.text = titulo
        tvSubHeader.text = subtitulo
    }

    protected fun limpiar() {
        contenedor.removeAllViews()
    }

    protected fun dp(valor: Int): Int {
        return (valor * resources.displayMetrics.density).toInt()
    }

    protected fun espacio(alto: Int = 10) {
        val espacio = Space(this)
        espacio.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dp(alto)
        )
        contenedor.addView(espacio)
    }

    protected fun titulo(texto: String) {
        val tv = TextView(this)
        tv.text = texto
        tv.textSize = 20f
        tv.setTextColor(Color.parseColor("#111111"))
        tv.setTypeface(null, Typeface.BOLD)
        tv.setPadding(0, dp(8), 0, dp(8))
        contenedor.addView(tv)
    }

    protected fun texto(texto: String, tamano: Float = 16f, negrita: Boolean = false) {
        val tv = TextView(this)
        tv.text = texto
        tv.textSize = tamano
        tv.setTextColor(Color.parseColor("#222222"))
        tv.setPadding(0, dp(4), 0, dp(4))

        if (negrita) {
            tv.setTypeface(null, Typeface.BOLD)
        }

        contenedor.addView(tv)
    }

    protected fun boton(texto: String, accion: () -> Unit) {
        val btn = Button(this)
        btn.text = texto
        btn.isAllCaps = false
        btn.textSize = 16f
        btn.setTextColor(Color.WHITE)
        btn.setBackgroundColor(Color.parseColor("#D62828"))
        btn.setOnClickListener { accion() }

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, dp(6), 0, dp(6))
        btn.layoutParams = params

        contenedor.addView(btn)
    }

    protected fun botonSecundario(texto: String, accion: () -> Unit) {
        val btn = Button(this)
        btn.text = texto
        btn.isAllCaps = false
        btn.textSize = 16f
        btn.setTextColor(Color.WHITE)
        btn.setBackgroundColor(Color.parseColor("#F77F00"))
        btn.setOnClickListener { accion() }

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, dp(6), 0, dp(6))
        btn.layoutParams = params

        contenedor.addView(btn)
    }

    protected fun campo(hint: String, valorInicial: String = ""): EditText {
        val editText = EditText(this)
        editText.hint = hint
        editText.setText(valorInicial)
        editText.textSize = 16f
        editText.setPadding(dp(12), dp(10), dp(12), dp(10))

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, dp(6), 0, dp(6))
        editText.layoutParams = params

        contenedor.addView(editText)
        return editText
    }

    protected fun selector(opciones: List<String>): Spinner {
        val spinner = Spinner(this)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            opciones
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adaptador
        contenedor.addView(spinner)

        return spinner
    }

    protected fun linea() {
        val tv = TextView(this)
        tv.text = "────────────────────────"
        tv.gravity = Gravity.CENTER
        tv.setTextColor(Color.GRAY)
        tv.setPadding(0, dp(8), 0, dp(8))
        contenedor.addView(tv)
    }

    protected fun mensaje(texto: String) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
    }

    protected fun resumenPedido(pedido: Pedido) {
        titulo("Pedido #${pedido.id}")
        texto("Cliente: ${pedido.cliente}")
        texto("Teléfono: ${pedido.telefono}")
        texto("Dirección: ${pedido.direccion}")
        texto("Referencia: ${pedido.referencia}")
        texto("Método de pago: ${pedido.metodoPago}")
        texto("Observaciones: ${pedido.observaciones.ifBlank { "Sin observaciones" }}")
        texto("Estado: ${pedido.estado}", 16f, true)
        texto("Total: S/ %.2f".format(pedido.total), 16f, true)

        texto("Productos:", 16f, true)

        pedido.detalles.forEach {
            texto("- ${it.cantidad} x ${it.nombreProducto} | S/ %.2f".format(it.precioUnitario))
        }
    }
}