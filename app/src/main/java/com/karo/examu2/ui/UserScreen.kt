package com.karo.examu2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import com.karo.examu2.data.room.UserDatabase
import com.karo.examu2.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun UserScreen(navController: NavController) {
    val context = LocalContext.current
    val dbResult = remember { kotlin.runCatching { UserDatabase.getDatabase(context) } }
    val db = dbResult.getOrNull()
    val dbError = dbResult.exceptionOrNull()

    if (db == null) {

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Error al inicializar la base de datos")
            Spacer(Modifier.height(8.dp))
            Text(dbError?.localizedMessage ?: "Error desconocido")
            Spacer(Modifier.height(16.dp))
            Button(onClick = { navController.navigate("dashboard") }) {
                Text("Volver")
            }
        }
        return
    }

    val viewModel = remember { UserViewModel(db.userDao()) }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var ageText by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    var runtimeError by remember { mutableStateOf<String?>(null) }


    var users by remember { mutableStateOf<List<com.karo.examu2.data.room.User>>(emptyList()) }


    LaunchedEffect(Unit) {
        try {
            viewModel.users.collectLatest { list ->
                users = list
            }
        } catch (e: Exception) {
            runtimeError = "Error al leer usuarios: ${e.localizedMessage}"
        }
    }

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        runtimeError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = ageText,
            onValueChange = { ageText = it.filter { ch -> ch.isDigit() } },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        errorMsg?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Row {
            Button(onClick = {
                val age = ageText.toIntOrNull()
                if (name.isBlank()) {
                    errorMsg = "El nombre es obligatorio"
                    return@Button
                }
                if (age == null) {
                    errorMsg = "Edad inválida"
                    return@Button
                }
                if (email.isBlank() || !email.contains("@")) {
                    errorMsg = "Correo inválido"
                    return@Button
                }
                errorMsg = null

                scope.launch {
                    try {
                        viewModel.saveUser(name, age, email)

                        name = ""
                        ageText = ""
                        email = ""

                    } catch (e: Exception) {
                        errorMsg = "Error al guardar: ${e.localizedMessage}"
                    }
                }
            }) {
                Text("Guardar usuario")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { navController.navigate("dashboard") }) {
                Text("Volver")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Usuarios guardados", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        if (users.isEmpty()) {
            Text("Ningún usuario guardado")
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(users) { user ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "Nombre: ${user.name}")
                            Text(text = "Edad: ${user.age}")
                            Text(text = "Email: ${user.email}")
                        }
                    }
                }
            }
        }
    }
}
