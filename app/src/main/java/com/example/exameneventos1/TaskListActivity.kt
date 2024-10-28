package com.example.exameneventos1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos1.ui.theme.ExamenEventos1Theme

class TaskListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenEventos1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TaskListScreen(modifier: Modifier = Modifier) {
    var taskName by remember { mutableStateOf("") }
    var tareasPendientes by remember { mutableStateOf(listOf<String>()) }
    var tareasHechas by remember { mutableStateOf(listOf<String>()) }
    var selectedTask by remember { mutableStateOf<String?>(null) }
    var selectedDoneTask by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (taskName.isNotBlank()) {
                tareasPendientes = tareasPendientes + taskName
                taskName = ""
            }
        }) {
            Text(text = "Añadir tarea")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Nombre de la tarea") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Lista de tareas pendientes", fontWeight = FontWeight.Bold)
        LazyColumn {
            items(tareasPendientes) { tarea ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            selectedTask = if (selectedTask == tarea) null else tarea
                        }
                ) {
                    Text(text = tarea, modifier = Modifier.weight(1f))
                    if (selectedTask == tarea) {
                        Button(onClick = {
                            tareasPendientes = tareasPendientes - tarea
                            tareasHechas = tareasHechas + tarea
                            selectedTask = null
                        }) {
                            Text("Hecha")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            tareasPendientes = tareasPendientes - tarea
                            selectedTask = null
                        }) {
                            Text("Borrar")
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Lista de tareas hechas", fontWeight = FontWeight.Bold)
        LazyColumn {
            items(tareasHechas) { tarea ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            selectedDoneTask = if (selectedDoneTask == tarea) null else tarea
                        }
                ) {
                    Text(
                        text = tarea,
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Green)
                    )
                    if (selectedDoneTask == tarea) {
                        Button(onClick = {
                            tareasHechas = tareasHechas - tarea
                            tareasPendientes = tareasPendientes + tarea
                            selectedDoneTask = null
                        }) {
                            Text("Deshacer")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            tareasHechas = tareasHechas - tarea
                            selectedDoneTask = null
                        }) {
                            Text("Borrar")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    ExamenEventos1Theme {
        TaskListScreen()
    }
}