package com.example.exameneventos1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { showDeleteDialog = true }) {
            Text(text = "Eliminar tarea")
        }
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
        Text(text = "Lista de tareas", fontWeight = FontWeight.Bold)
        LazyColumn {
            items(tareasPendientes) { tarea ->
                Text(text = tarea, modifier = Modifier.padding(8.dp))
            }
        }
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text(text = "Eliminar tarea") },
                text = {
                    Column {
                        TextField(
                            value = taskToDelete,
                            onValueChange = { taskToDelete = it },
                            label = { Text("Introduce el nombre de la tarea a borrar") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (errorMessage.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = errorMessage, color = Color.Red)
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (taskToDelete.isNotBlank()) {
                            if (tareasPendientes.contains(taskToDelete)) {
                                tareasPendientes = tareasPendientes - taskToDelete
                                taskToDelete = ""
                                errorMessage = ""
                                showDeleteDialog = false
                            } else {
                                errorMessage = "No se encontró la tarea"
                            }
                        }
                    }) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDeleteDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
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