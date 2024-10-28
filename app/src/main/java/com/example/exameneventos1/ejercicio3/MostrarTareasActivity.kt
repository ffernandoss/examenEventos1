// MostrarTareasActivity.kt
package com.example.exameneventos1.ejercicio3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos1.ui.theme.ExamenEventos1Theme

class MostrarTareasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nombresTareas = intent.getStringArrayListExtra("NOMBRES_TAREAS") ?: listOf()
        val listaTareas = loadTareas(this)

        setContent {
            ExamenEventos1Theme {
                MostrarTareasScreen(
                    nombresTareas = nombresTareas,
                    listaTareas = listaTareas,
                    onBackClick = {
                        val intent = Intent(this, TareasEjercicio3Activity::class.java)
                        startActivity(intent)
                    },
                    onTaskClick = { tarea ->
                        val intent = Intent(this, DatosActivity::class.java).apply {
                            putExtra("NOMBRE_TAREA", tarea.nombre)
                            putExtra("DESCRIPCION_TAREA", tarea.descripcion)
                            putExtra("FECHA_TAREA", tarea.fecha)
                            putExtra("COSTE_TAREA", tarea.coste)
                            putExtra("PRIORIDAD_TAREA", tarea.prioridad)
                        }
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun MostrarTareasScreen(
    nombresTareas: List<String>,
    listaTareas: List<Tarea>,
    onBackClick: () -> Unit,
    onTaskClick: (Tarea) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = onBackClick) {
            Text("Volver")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(nombresTareas) { nombre ->
                val tarea = listaTareas.find { it.nombre == nombre }
                if (tarea != null) {
                    Text(
                        text = nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTaskClick(tarea) }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MostrarTareasScreenPreview() {
    ExamenEventos1Theme {
        MostrarTareasScreen(
            nombresTareas = listOf("Tarea 1", "Tarea 2"),
            listaTareas = listOf(
                Tarea("Tarea 1", "Descripción 1", "01/01/2023", 100.0, true),
                Tarea("Tarea 2", "Descripción 2", "02/02/2023", 200.0, false)
            ),
            onBackClick = {},
            onTaskClick = {}
        )
    }
}