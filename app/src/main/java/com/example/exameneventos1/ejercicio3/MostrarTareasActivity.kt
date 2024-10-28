// MostrarTareasActivity.kt
package com.example.exameneventos1.ejercicio3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos1.ui.theme.ExamenEventos1Theme

class MostrarTareasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nombresTareas = intent.getStringArrayListExtra("NOMBRES_TAREAS") ?: listOf()
        var listaTareas = loadTareas(this)

        setContent {
            var showDoneTasks by remember { mutableStateOf(false) }
            var selectedTask by remember { mutableStateOf<Tarea?>(null) }
            var tareas by remember { mutableStateOf(listaTareas) }
            var taskToDelete by remember { mutableStateOf("") }

            ExamenEventos1Theme {
                MostrarTareasScreen(
                    nombresTareas = nombresTareas,
                    listaTareas = tareas,
                    showDoneTasks = showDoneTasks,
                    selectedTask = selectedTask,
                    taskToDelete = taskToDelete,
                    onBackClick = {
                        finish()
                    },
                    onTaskClick = { tarea ->
                        selectedTask = tarea
                    },
                    onToggleShowDoneTasks = {
                        showDoneTasks = !showDoneTasks
                    },
                    onMarkDone = { tarea ->
                        val updatedList = tareas.toMutableList().apply {
                            val index = indexOf(tarea)
                            if (index != -1) {
                                set(index, tarea.copy(hecha = true))
                            }
                        }
                        saveTareas(this@MostrarTareasActivity, updatedList)
                        tareas = updatedList
                        selectedTask = null
                    },
                    onShowData = { tarea ->
                        val intent = Intent(this, DatosActivity::class.java).apply {
                            putExtra("NOMBRE_TAREA", tarea.nombre)
                            putExtra("DESCRIPCION_TAREA", tarea.descripcion)
                            putExtra("FECHA_TAREA", tarea.fecha)
                            putExtra("COSTE_TAREA", tarea.coste)
                            putExtra("PRIORIDAD_TAREA", tarea.prioridad)
                        }
                        startActivity(intent)
                    },
                    onDeleteTask = { tarea ->
                        val updatedList = tareas.toMutableList().apply {
                            remove(tarea)
                        }
                        saveTareas(this@MostrarTareasActivity, updatedList)
                        tareas = updatedList
                        selectedTask = null
                    },
                    onTaskToDeleteChange = { taskToDelete = it },
                    onDeleteTaskByName = {
                        val tarea = tareas.find { it.nombre == taskToDelete }
                        if (tarea != null) {
                            val updatedList = tareas.toMutableList().apply {
                                remove(tarea)
                            }
                            saveTareas(this@MostrarTareasActivity, updatedList)
                            tareas = updatedList
                            taskToDelete = ""
                        }
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
    showDoneTasks: Boolean,
    selectedTask: Tarea?,
    taskToDelete: String,
    onBackClick: () -> Unit,
    onTaskClick: (Tarea) -> Unit,
    onToggleShowDoneTasks: () -> Unit,
    onMarkDone: (Tarea) -> Unit,
    onShowData: (Tarea) -> Unit,
    onDeleteTask: (Tarea) -> Unit,
    onTaskToDeleteChange: (String) -> Unit,
    onDeleteTaskByName: () -> Unit
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
        Button(onClick = onToggleShowDoneTasks) {
            Text("Mostrar/ocultar tareas hechas")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = taskToDelete,
            onValueChange = onTaskToDeleteChange,
            label = { Text("Nombre de la tarea a eliminar") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onDeleteTaskByName) {
            Text("Eliminar tarea")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(nombresTareas) { nombre ->
                val tarea = listaTareas.find { it.nombre == nombre && (showDoneTasks || !it.hecha) }
                if (tarea != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTaskClick(tarea) }
                            .padding(8.dp)
                            .background(if (tarea.hecha) Color.Gray else Color.Transparent)
                    ) {
                        Text(text = nombre)
                        if (selectedTask == tarea) {
                            Row {
                                Button(onClick = { onMarkDone(tarea) }) {
                                    Text("Marcar hecha")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = { onShowData(tarea) }) {
                                    Text("Mostrar datos")
                                }
                            }
                        }
                    }
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
            showDoneTasks = false,
            selectedTask = null,
            taskToDelete = "",
            onBackClick = {},
            onTaskClick = {},
            onToggleShowDoneTasks = {},
            onMarkDone = {},
            onShowData = {},
            onDeleteTask = {},
            onTaskToDeleteChange = {},
            onDeleteTaskByName = {}
        )
    }
}