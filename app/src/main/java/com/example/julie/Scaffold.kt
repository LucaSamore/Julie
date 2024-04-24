package com.example.julie

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.julie.components.NeubrutalBottomBar
import com.example.julie.navigation.Destination
import com.example.julie.navigation.NavigationGraph
import com.example.julie.ui.theme.NeobrutalismTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun JulieApplication(startDestination: String, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        backStackEntry?.destination?.route?.split("/")?.first() ?: Destination.SignIn.name

    Scaffold(
        topBar = {
            if (
                currentScreen != Destination.SignIn.name &&
                    currentScreen != Destination.SignUp.name &&
                    currentScreen != Destination.VerifyEmail.name &&
                    currentScreen != Destination.PasswordReset.name
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = currentScreen, style = NeobrutalismTheme.typography.screenTitle)
                    },
                    colors =
                        TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = NeobrutalismTheme.colors.buttonPrimary
                        )
                )
            }
        },
        bottomBar = {
            if (
                currentScreen != Destination.SignIn.name &&
                    currentScreen != Destination.SignUp.name &&
                    currentScreen != Destination.VerifyEmail.name &&
                    currentScreen != Destination.PasswordReset.name
            ) {
                NeubrutalBottomBar(modifier = modifier, navController = navController)
            }
        }
    ) { innerPadding ->
        NavigationGraph(
            navController = navController,
            startDestination = startDestination,
            paddingValues = innerPadding
        )
    }
}
