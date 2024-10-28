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

// Actividad principal para la lista de compras
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

// Función composable para la pantalla de la lista de compras
@Composable
fun ListaCompraScreen(modifier: Modifier = Modifier) {
    // Variables de estado para los campos de entrada y la lista de compras
    var producto by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var productoAEliminar by remember { mutableStateOf("") }
    var listaCompra by remember { mutableStateOf(listOf<Producto>()) }
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    // Mostrar un Toast si showToast es verdadero
    if (showToast) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }

    // Función para verificar si un valor es numérico
    fun isNumeric(value: String): Boolean {
        return value.toDoubleOrNull() != null
    }

    // Función para calcular el precio total de la lista de compras
    fun calcularPrecioTotal(): Double {
        return listaCompra.filter { it.precio.isNotBlank() }
            .sumOf { (it.cantidad.ifBlank { "1" }.toDouble()) * it.precio.toDouble() }
    }

    // Estructura de la interfaz de usuario
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Campo de texto para el nombre del producto
        TextField(
            value = producto,
            onValueChange = { producto = it },
            label = { Text("Producto") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Campo de texto para la cantidad del producto
        TextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Campo de texto para el precio del producto
        TextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Botón para añadir un producto a la lista de compras
        Button(onClick = {
            when {
                producto.isBlank() -> {
                    toastMessage = "El campo de producto es obligatorio"
                    showToast = true
                }
                cantidad.isNotBlank() && !isNumeric(cantidad) -> {
                    toastMessage = "El campo de cantidad debe ser un número"
                    showToast = true
                }
                precio.isNotBlank() && !isNumeric(precio) -> {
                    toastMessage = "El campo de precio debe ser un número"
                    showToast = true
                }
                listaCompra.any { it.producto == producto } -> {
                    toastMessage = "El producto ya está en la lista"
                    showToast = true
                }
                else -> {
                    val nuevoProducto = Producto(producto, cantidad, precio)
                    listaCompra = listaCompra + nuevoProducto
                    producto = ""
                    cantidad = ""
                    precio = ""
                }
            }
        }) {
            Text("Añadir")
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Campo de texto para el nombre del producto a eliminar
        TextField(
            value = productoAEliminar,
            onValueChange = { productoAEliminar = it },
            label = { Text("Producto a eliminar") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Botón para eliminar un producto de la lista de compras
        Button(onClick = {
            val productoEncontrado = listaCompra.find { it.producto == productoAEliminar }
            if (productoEncontrado != null) {
                listaCompra = listaCompra - productoEncontrado
                toastMessage = "Producto eliminado"
            } else {
                toastMessage = "No se ha encontrado el producto"
            }
            showToast = true
            productoAEliminar = ""
        }) {
            Text("Eliminar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Lista de productos en la lista de compras
        LazyColumn {
            items(listaCompra) { item ->
                Text(text = "${item.producto} - ${item.cantidad.ifBlank { "1" }} - ${item.precio}")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Mostrar el precio total de la lista de compras
        Text(text = "Precio total: ${calcularPrecioTotal()}")
    }
}

// Clase de datos para representar un producto
data class Producto(val producto: String, val cantidad: String, val precio: String)

// Función de vista previa para la pantalla de la lista de compras
@Preview(showBackground = true)
@Composable
fun ListaCompraScreenPreview() {
    ExamenEventos1Theme {
        ListaCompraScreen()
    }
}