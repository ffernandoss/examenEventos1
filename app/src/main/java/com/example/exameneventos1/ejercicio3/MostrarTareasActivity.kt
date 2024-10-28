// MostrarTareasActivity.kt
package com.example.exameneventos1.ejercicio3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos1.ui.theme.ExamenEventos1Theme

class MostrarTareasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nombresTareas = intent.getStringArrayListExtra("NOMBRES_TAREAS") ?: listOf()
        setContent {
            ExamenEventos1Theme {
                MostrarTareasScreen(nombresTareas, onBackClick = {
                    val intent = Intent(this, TareasEjercicio3Activity::class.java)
                    startActivity(intent)
                })
            }
        }
    }
}

@Composable
fun MostrarTareasScreen(nombresTareas: List<String>, onBackClick: () -> Unit) {
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
                Text(text = nombre)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MostrarTareasScreenPreview() {
    ExamenEventos1Theme {
        MostrarTareasScreen(nombresTareas = listOf("Tarea 1", "Tarea 2"), onBackClick = {})
    }
}