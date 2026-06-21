package com.example.tiochepedelivery

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.tiochepedelivery.ui.AdministradorActivity
import com.example.tiochepedelivery.ui.ClienteActivity
import com.example.tiochepedelivery.ui.CocinaActivity
import com.example.tiochepedelivery.ui.RecepcionActivity
import com.example.tiochepedelivery.ui.RepartidorActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnCliente).setOnClickListener {
            startActivity(Intent(this, ClienteActivity::class.java))
        }

        findViewById<Button>(R.id.btnRecepcion).setOnClickListener {
            startActivity(Intent(this, RecepcionActivity::class.java))
        }

        findViewById<Button>(R.id.btnCocina).setOnClickListener {
            startActivity(Intent(this, CocinaActivity::class.java))
        }

        findViewById<Button>(R.id.btnRepartidor).setOnClickListener {
            startActivity(Intent(this, RepartidorActivity::class.java))
        }

        findViewById<Button>(R.id.btnAdministrador).setOnClickListener {
            startActivity(Intent(this, AdministradorActivity::class.java))
        }
    }
}