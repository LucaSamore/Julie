package com.example.julie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.julie.components.neubrutalism.NeubrutalBottomBar
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
            if (!canShowTopAppBar(currentScreen)) {
                return@Scaffold
            }

            TopAppBar(
                title = {
                    Text(text = currentScreen, style = NeobrutalismTheme.typography.screenTitle)
                },
                colors =
                    TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = NeobrutalismTheme.colors.buttonPrimary
                    ),
                actions = {
                    if (canShowTopBarButton(currentScreen)) {
                        return@TopAppBar
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
                                            navController.navigate(Destination.HomeCaption.name)
                                        }
                                        Destination.SmartphoneUsage.name -> {
                                            navController.navigate(
                                                Destination.SmartphoneUsageCaption.name
                                            )
                                        }
                                        else -> {
                                            navController.popBackStack()
                                        }
                                    }
                                }
                    ) {
                        Icon(
                            imageVector =
                                ImageVector.vectorResource(
                                    if (isCaption(currentScreen)) R.drawable.baseline_arrow_back_24
                                    else R.drawable.baseline_info_outline_24
                                ),
                            contentDescription = "Info",
                            tint = NeobrutalismTheme.colors.text,
                            modifier = modifier.size(32.dp, 32.dp).padding(6.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (canShowBottomBar(currentScreen)) {
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

private fun canShowTopAppBar(currentScreen: String): Boolean {
    val excludedScreens =
        listOf(
            Destination.SignIn.name,
            Destination.SignUp.name,
            Destination.VerifyEmail.name,
            Destination.PasswordReset.name,
            Destination.Story.name
        )
    return currentScreen !in excludedScreens
}

private fun canShowTopBarButton(currentScreen: String): Boolean {
    val excludedScreens =
        listOf(
            Destination.Home.name,
            Destination.SmartphoneUsage.name,
            Destination.HomeCaption.name,
            Destination.SmartphoneUsageCaption.name
        )
    return currentScreen !in excludedScreens
}

private fun canShowBottomBar(currentScreen: String): Boolean {
    val excludedScreens =
        listOf(
            Destination.SignIn.name,
            Destination.SignUp.name,
            Destination.VerifyEmail.name,
            Destination.PasswordReset.name,
            Destination.Story.name
        )
    return currentScreen !in excludedScreens
}

private fun isCaption(currentScreen: String) =
    currentScreen == Destination.HomeCaption.name ||
        currentScreen == Destination.SmartphoneUsageCaption.name
