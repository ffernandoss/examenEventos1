package com.example.exameneventos1.ejercicio3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.app.DatePickerDialog
import java.util.Calendar
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos1.ui.theme.ExamenEventos1Theme

// Actividad para mostrar la lista del tema 3
class TareasEjercicio3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenEventos1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Llama a la función composable para la pantalla de tareas del ejercicio 3
                    TareasEjercicio3Screen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Clase de datos para representar una tarea
data class Tarea(
    val nombre: String,
    val descripcion: String,
    val fecha: String,
    val coste: Double,
    val prioridad: Boolean
)

// Función composable para la pantalla de tareas del ejercicio 3
@Composable
fun TareasEjercicio3Screen(modifier: Modifier = Modifier) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var coste by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf(false) }
    var listaTareas by remember { mutableStateOf(listOf<Tarea>()) }
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    if (showToast) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }

    fun isNumeric(value: String): Boolean {
        return value.toDoubleOrNull() != null
    }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            fecha = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Fecha: $fecha")
            Button(onClick = { datePickerDialog.show() }) {
                Text("Seleccionar Fecha")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = coste,
            onValueChange = { coste = it },
            label = { Text("Coste") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Prioridad")
            Switch(
                checked = prioridad,
                onCheckedChange = { prioridad = it }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            when {
                nombre.isBlank() -> {
                    toastMessage = "El campo de nombre es obligatorio"
                    showToast = true
                }
                descripcion.isBlank() -> {
                    toastMessage = "El campo de descripción es obligatorio"
                    showToast = true
                }
                fecha.isBlank() -> {
                    toastMessage = "El campo de fecha es obligatorio"
                    showToast = true
                }
                coste.isBlank() -> {
                    toastMessage = "El campo de coste es obligatorio"
                    showToast = true
                }
                !isNumeric(coste) -> {
                    toastMessage = "El campo de coste debe ser un número"
                    showToast = true
                }
                else -> {
                    val nuevaTarea = Tarea(nombre, descripcion, fecha, coste.toDouble(), prioridad)
                    listaTareas = listaTareas + nuevaTarea
                    nombre = ""
                    descripcion = ""
                    fecha = ""
                    coste = ""
                    prioridad = false
                }
            }
        }) {
            Text("Añadir")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(listaTareas) { tarea ->
                Text(text = "${tarea.nombre} - ${tarea.descripcion} - ${tarea.fecha} - ${tarea.coste} - ${if (tarea.prioridad) "Alta" else "Baja"}")
            }
        }
    }
}
// Función de vista previa para la pantalla de tareas del ejercicio 3
@Preview(showBackground = true)
@Composable
fun TareasEjercicio3ScreenPreview() {
    ExamenEventos1Theme {
        // Vista previa de la pantalla de tareas del ejercicio 3
        TareasEjercicio3Screen()
    }
}