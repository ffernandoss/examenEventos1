package com.example.exameneventos1.ejercicio2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos1.ui.theme.ExamenEventos1Theme

// Actividad principal para el segundo ejercicio del examen
class Ejercicio2MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenEventos1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Llama a la función composable para la pantalla del ejercicio 2
                    Exercise2Screen(modifier = Modifier.padding(innerPadding)) {
                        // Inicia la actividad ListaCompraActivity al hacer clic en el botón
                        val intent = Intent(this, ListaCompraActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

// Función composable para la pantalla del ejercicio 2
@Composable
fun Exercise2Screen(modifier: Modifier = Modifier, onButtonClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Texto de bienvenida
        Text(text = "Bienvenido al segundo ejercicio del examen")
        Spacer(modifier = Modifier.height(16.dp))
        // Botón que ejecuta la acción del segundo ejercicio
        Button(onClick = onButtonClick) {
            Text(text = "Acción del segundo ejercicio")
        }
    }
}

// Función de vista previa para la pantalla del ejercicio 2
@Preview(showBackground = true)
@Composable
fun Exercise2ScreenPreview() {
    ExamenEventos1Theme {
        // Vista previa de la pantalla del ejercicio 2
        Exercise2Screen(onButtonClick = {})
    }
}