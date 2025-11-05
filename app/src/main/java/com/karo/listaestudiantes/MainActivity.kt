package com.karo.listaestudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.karo.listaestudiantes.MainActivity
import com.karo.listaestudiantes.ui.theme.ListaestudiantesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaestudiantesTheme {
                AppNavigation()
            }
        }
    }
}