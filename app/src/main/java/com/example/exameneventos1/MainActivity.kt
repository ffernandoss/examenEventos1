package com.example.exameneventos1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos1.ui.theme.ExamenEventos1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenEventos1Theme {
                // Variable de estado para almacenar el idioma seleccionado
                var language by remember { mutableStateOf("es") }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WelcomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        // Acción al hacer clic en el botón para ir a la lista de tareas
                        onButtonClick = {
                            val intent = Intent(this, TaskListActivity::class.java)
                            intent.putExtra("LANGUAGE", language)
                            startActivity(intent)
                        },
                        // Acción al cambiar el idioma
                        onLanguageChange = { newLanguage ->
                            language = newLanguage
                        },
                        // Idioma actual
                        language = language
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    onLanguageChange: (String) -> Unit,
    language: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Texto de bienvenida
        Text(text = if (language == "es") "Bienvenido al primer ejercicio del examen" else "Welcome to the first exam exercise")
        Spacer(modifier = Modifier.height(16.dp))
        // Botón para ir a la lista de tareas
        Button(onClick = onButtonClick) {
            Text(text = if (language == "es") "Ir a lista de tareas" else "Go to task list")
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Botón para cambiar el idioma
        Button(onClick = { onLanguageChange(if (language == "es") "en" else "es") }) {
            Text(text = if (language == "es") "Seleccionar idioma: Inglés" else "Select Language: Spanish")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    ExamenEventos1Theme {
        // Vista previa de la pantalla de bienvenida
        WelcomeScreen(onButtonClick = {}, onLanguageChange = {}, language = "es")
    }
}