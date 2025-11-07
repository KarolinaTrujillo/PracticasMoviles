package com.karo.examu2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karo.examu2.ui.DashboardScreen
import com.karo.examu2.ui.ThemeScreen
import com.karo.examu2.ui.UserScreen
import com.karo.examu2.ui.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "dashboard"
                ) {
                    composable("dashboard") { DashboardScreen(navController) }
                    composable("theme") { ThemeScreen(navController) }
                    composable("user") { UserScreen(navController) }
                }
            }
        }
    }
}
