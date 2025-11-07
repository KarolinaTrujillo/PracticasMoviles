package com.karo.examu2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate("theme") }, Modifier.padding(8.dp)) {
            Text("Cambiar tema")
        }
        Button(onClick = { navController.navigate("user") }, Modifier.padding(8.dp)) {
            Text("Guardar usuario")
        }
    }
}
