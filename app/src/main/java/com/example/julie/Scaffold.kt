package com.example.julie

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.julie.navigation.NavigationGraph

@Composable
internal fun JulieApplication(startDestination: String) {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        NavigationGraph(
            navController = navController,
            startDestination = startDestination,
            paddingValues = innerPadding
        )
    }
}
