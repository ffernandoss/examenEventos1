package com.example.exameneventos1.ejercicio2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos1.ui.theme.ExamenEventos1Theme

class ListaCompraActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenEventos1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListaCompraScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ListaCompraScreen(modifier: Modifier = Modifier) {
    var producto by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var listaCompra by remember { mutableStateOf(listOf<Producto>()) }
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }

    if (showToast) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, "El campo de producto es obligatorio", Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = producto,
            onValueChange = { producto = it },
            label = { Text("Producto") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (producto.isBlank()) {
                showToast = true
            } else {
                val nuevoProducto = Producto(producto, cantidad, precio)
                listaCompra = listaCompra + nuevoProducto
                producto = ""
                cantidad = ""
                precio = ""
            }
        }) {
            Text("Añadir")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(listaCompra) { item ->
                Text(text = "${item.producto} - ${item.cantidad} - ${item.precio}")
            }
        }
    }
}

data class Producto(val producto: String, val cantidad: String, val precio: String)

@Preview(showBackground = true)
@Composable
fun ListaCompraScreenPreview() {
    ExamenEventos1Theme {
        ListaCompraScreen()
    }
}