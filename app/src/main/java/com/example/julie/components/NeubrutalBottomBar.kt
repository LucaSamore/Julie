package com.example.julie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.julie.R
import com.example.julie.navigation.Destination
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation

@Composable
internal fun NeubrutalBottomBar(modifier: Modifier, navController: NavHostController) {
    NavigationBar(containerColor = NeobrutalismTheme.colors.contentSecondary) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = backStackEntry?.destination?.route ?: Destination.SignIn.name

        NavigationBarItem(
            selected = currentScreen == Destination.Home.name,
            onClick = {
                navController.navigate(Destination.Home.name) {
                    popUpTo(Destination.Home.name) { inclusive = true }
                }
            },
            icon = {
                Box(
                    modifier =
                        modifier
                            .neubrutalismElevation()
                            .background(
                                color =
                                    if (currentScreen == Destination.Home.name)
                                        NeobrutalismTheme.colors.buttonPrimary
                                    else NeobrutalismTheme.colors.contentPrimary
                            )
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.baseline_home_filled_24),
                        contentDescription = "Home",
                        tint =
                            if (currentScreen == Destination.Home.name)
                                NeobrutalismTheme.colors.text
                            else Color.White,
                        modifier = modifier.padding(6.dp).size(32.dp, 32.dp)
                    )
                }
            },
            colors =
                NavigationBarItemDefaults.colors(
                    indicatorColor = NeobrutalismTheme.colors.contentSecondary
                )
        )

        NavigationBarItem(
            selected = currentScreen == Destination.SmartphoneUsage.name,
            onClick = {
                navController.navigate(Destination.SmartphoneUsage.name) {
                    popUpTo(Destination.SmartphoneUsage.name) { inclusive = true }
                }
            },
            icon = {
                Box(
                    modifier =
                        modifier
                            .neubrutalismElevation()
                            .background(
                                color =
                                    if (currentScreen == Destination.SmartphoneUsage.name)
                                        NeobrutalismTheme.colors.buttonPrimary
                                    else NeobrutalismTheme.colors.contentPrimary
                            )
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.baseline_pie_chart_24),
                        contentDescription = "Data Visualization",
                        tint =
                            if (currentScreen == Destination.SmartphoneUsage.name)
                                NeobrutalismTheme.colors.text
                            else Color.White,
                        modifier = modifier.padding(6.dp).size(32.dp, 32.dp)
                    )
                }
            },
            colors =
                NavigationBarItemDefaults.colors(
                    indicatorColor = NeobrutalismTheme.colors.contentSecondary
                )
        )

        NavigationBarItem(
            selected = currentScreen == Destination.Leaderboard.name,
            onClick = {
                navController.navigate(Destination.Leaderboard.name) {
                    popUpTo(Destination.Leaderboard.name) { inclusive = true }
                }
            },
            icon = {
                Box(
                    modifier =
                        modifier
                            .neubrutalismElevation()
                            .background(
                                color =
                                    if (currentScreen == Destination.Leaderboard.name)
                                        NeobrutalismTheme.colors.buttonPrimary
                                    else NeobrutalismTheme.colors.contentPrimary
                            )
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.baseline_leaderboard_24),
                        contentDescription = "Leaderboard",
                        tint =
                            if (currentScreen == Destination.Leaderboard.name)
                                NeobrutalismTheme.colors.text
                            else Color.White,
                        modifier = modifier.padding(6.dp).size(32.dp, 32.dp)
                    )
                }
            },
            colors =
                NavigationBarItemDefaults.colors(
                    indicatorColor = NeobrutalismTheme.colors.contentSecondary
                )
        )

        NavigationBarItem(
            selected = currentScreen == Destination.Settings.name,
            onClick = {
                navController.navigate(Destination.Settings.name) {
                    popUpTo(Destination.Settings.name) { inclusive = true }
                }
            },
            icon = {
                Box(
                    modifier =
                        modifier
                            .neubrutalismElevation()
                            .background(
                                color =
                                    if (currentScreen == Destination.Settings.name)
                                        NeobrutalismTheme.colors.buttonPrimary
                                    else NeobrutalismTheme.colors.contentPrimary
                            )
                ) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Settings",
                        tint =
                            if (currentScreen == Destination.Settings.name)
                                NeobrutalismTheme.colors.text
                            else Color.White,
                        modifier = modifier.padding(6.dp).size(32.dp, 32.dp)
                    )
                }
            },
            colors =
                NavigationBarItemDefaults.colors(
                    indicatorColor = NeobrutalismTheme.colors.contentSecondary
                )
        )
    }
}
