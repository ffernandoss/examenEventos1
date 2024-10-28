package com.example.exameneventos1.ejercicio3

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos1.ui.theme.ExamenEventos1Theme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar

data class Tarea(
    val nombre: String,
    val descripcion: String,
    val fecha: String,
    val coste: Double,
    val prioridad: Boolean
)

class TareasEjercicio3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenEventos1Theme {
                TareasEjercicio3Screen(onShowTasksClick = { listaTareas ->
                    val intent = Intent(this, MostrarTareasActivity::class.java)
                    val nombresTareas = listaTareas.map { it.nombre }
                    intent.putStringArrayListExtra("NOMBRES_TAREAS", ArrayList(nombresTareas))
                    startActivity(intent)
                })
            }
        }
    }
}

@Composable
fun TareasEjercicio3Screen(modifier: Modifier = Modifier, onShowTasksClick: (List<Tarea>) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var coste by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val listaTareas = remember { mutableStateOf(loadTareas(context)) }
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
                    listaTareas.value = listaTareas.value + nuevaTarea
                    saveTareas(context, listaTareas.value)
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
        Button(onClick = { onShowTasksClick(listaTareas.value) }) {
            Text("Mostrar todas las tareas")
        }
    }
}

fun saveTareas(context: android.content.Context, listaTareas: List<Tarea>) {
    val sharedPreferences = context.getSharedPreferences("TareasPrefs", android.content.Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val gson = Gson()
    val json = gson.toJson(listaTareas)
    editor.putString("TareasList", json)
    editor.apply()
}

fun loadTareas(context: android.content.Context): List<Tarea> {
    val sharedPreferences = context.getSharedPreferences("TareasPrefs", android.content.Context.MODE_PRIVATE)
    val gson = Gson()
    val json = sharedPreferences.getString("TareasList", null)
    val type = object : TypeToken<List<Tarea>>() {}.type
    return if (json != null) gson.fromJson(json, type) else listOf()
}

@Preview(showBackground = true)
@Composable
fun TareasEjercicio3ScreenPreview() {
    ExamenEventos1Theme {
        TareasEjercicio3Screen(onShowTasksClick = {})
    }
}