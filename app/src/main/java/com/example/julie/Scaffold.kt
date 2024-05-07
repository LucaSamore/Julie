package com.example.julie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.julie.components.NeubrutalBottomBar
import com.example.julie.navigation.Destination
import com.example.julie.navigation.NavigationGraph
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation

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
                    currentScreen != Destination.PasswordReset.name &&
                    currentScreen != Destination.Story.name
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = currentScreen, style = NeobrutalismTheme.typography.screenTitle)
                    },
                    colors =
                        TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = NeobrutalismTheme.colors.buttonPrimary
                        ),
                    actions = {
                        if (
                            currentScreen != Destination.Home.name &&
                                currentScreen != Destination.SmartphoneUsage.name
                        ) {
                            return@CenterAlignedTopAppBar
                        }
                        Box(
                            modifier =
                                modifier
                                    .padding(horizontal = 12.dp)
                                    .neubrutalismElevation(offsetX = 2.dp, offsetY = 2.dp)
                                    .height(32.dp)
                                    .background(color = NeobrutalismTheme.colors.background)
                                    .clickable {
                                        when (currentScreen) {
                                            Destination.Home.name -> {
                                                // Navigate to home caption
                                            }
                                            Destination.SmartphoneUsage.name -> {
                                                // Navigate to smartphone usage caption
                                            }
                                        }
                                    }
                        ) {
                            Icon(
                                imageVector =
                                    ImageVector.vectorResource(R.drawable.baseline_info_outline_24),
                                contentDescription = "Info",
                                tint = NeobrutalismTheme.colors.text,
                                modifier = modifier.size(32.dp, 32.dp).padding(6.dp)
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (
                currentScreen != Destination.SignIn.name &&
                    currentScreen != Destination.SignUp.name &&
                    currentScreen != Destination.VerifyEmail.name &&
                    currentScreen != Destination.PasswordReset.name &&
                    currentScreen != Destination.Story.name
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
