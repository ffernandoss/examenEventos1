package com.example.exameneventos1.ejercicio3

import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos1.ui.theme.ExamenEventos1Theme

// Actividad principal para el tercer ejercicio del examen
class Ejercicio3MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Establece el contenido de la actividad usando Jetpack Compose
        setContent {
            ExamenEventos1Theme {
                // Usa un Scaffold para proporcionar una estructura básica de la pantalla
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Llama a la función composable para la pantalla del ejercicio 3
                    Exercise3Screen(modifier = Modifier.padding(innerPadding)) {
                        // Inicia TareasEjercicio3Activity al hacer clic en el botón
                        val intent = Intent(this, TareasEjercicio3Activity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

// Función composable para la pantalla del ejercicio 3
@Composable
fun Exercise3Screen(modifier: Modifier = Modifier, onButtonClick: () -> Unit) {
    // Estructura de la interfaz de usuario usando una columna
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Texto de bienvenida
        Text(text = "Bienvenido al tercer ejercicio del examen")
        Spacer(modifier = Modifier.height(16.dp))
        // Botón que ejecuta la acción del tercer ejercicio
        Button(onClick = onButtonClick) {
            Text(text = "Acción del tercer ejercicio")
        }
    }
}

// Función de vista previa para la pantalla del ejercicio 3
@Preview(showBackground = true)
@Composable
fun Exercise3ScreenPreview() {
    ExamenEventos1Theme {
        // Vista previa de la pantalla del ejercicio 3
        Exercise3Screen(onButtonClick = {})
    }
}