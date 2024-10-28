// DatosActivity.kt
package com.example.exameneventos1.ejercicio3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos1.ui.theme.ExamenEventos1Theme

class DatosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nombre = intent.getStringExtra("NOMBRE_TAREA") ?: ""
        val descripcion = intent.getStringExtra("DESCRIPCION_TAREA") ?: ""
        val fecha = intent.getStringExtra("FECHA_TAREA") ?: ""
        val coste = intent.getDoubleExtra("COSTE_TAREA", 0.0)
        val prioridad = intent.getBooleanExtra("PRIORIDAD_TAREA", false)

        setContent {
            ExamenEventos1Theme {
                DatosTareaScreen(
                    nombre = nombre,
                    descripcion = descripcion,
                    fecha = fecha,
                    coste = coste,
                    prioridad = prioridad,
                    onBackClick = {
                        val intent = Intent(this, TareasEjercicio3Activity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun DatosTareaScreen(
    nombre: String,
    descripcion: String,
    fecha: String,
    coste: Double,
    prioridad: Boolean,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Nombre: $nombre")
        Text(text = "Descripción: $descripcion")
        Text(text = "Fecha: $fecha")
        Text(text = "Coste: $coste")
        Text(text = "Prioridad: ${if (prioridad) "Alta" else "Baja"}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick) {
            Text("Volver")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DatosTareaScreenPreview() {
    ExamenEventos1Theme {
        DatosTareaScreen(
            nombre = "Tarea 1",
            descripcion = "Descripción de la tarea 1",
            fecha = "01/01/2023",
            coste = 100.0,
            prioridad = true,
            onBackClick = {}
        )
    }
}