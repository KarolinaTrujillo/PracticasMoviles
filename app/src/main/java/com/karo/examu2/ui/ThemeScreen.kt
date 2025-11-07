package com.karo.examu2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.karo.examu2.data.datastore.ThemeDataStore
import com.karo.examu2.viewmodel.ThemeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ThemeScreen(navController: NavController) {
    val context = LocalContext.current
    val dataStore = remember { ThemeDataStore(context) }
    val viewModel = remember { ThemeViewModel(dataStore) }
    val scope = rememberCoroutineScope()

    var isDark by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.darkMode.collectLatest { isDark = it }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Cambiar tema persistente")
        Switch(
            checked = isDark,
            onCheckedChange = { checked ->
                isDark = checked
                scope.launch { viewModel.setDarkMode(checked) }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("dashboard") }) {
            Text("Volver")
        }
    }
}
