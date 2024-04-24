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
            onClick = { /*TODO*/},
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
                        Icons.Filled.Home,
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
            selected = currentScreen == Destination.Test.name,
            onClick = { /*TODO*/},
            icon = {
                Box(
                    modifier =
                        modifier
                            .neubrutalismElevation()
                            .background(
                                color =
                                    if (currentScreen == Destination.Test.name)
                                        NeobrutalismTheme.colors.buttonPrimary
                                    else NeobrutalismTheme.colors.contentPrimary
                            )
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.baseline_data_usage_24),
                        contentDescription = "Data Visualization",
                        tint =
                            if (currentScreen == Destination.Test.name)
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
            selected = currentScreen == Destination.Test.name,
            onClick = { /*TODO*/},
            icon = {
                Box(
                    modifier =
                        modifier
                            .neubrutalismElevation()
                            .background(
                                color =
                                    if (currentScreen == Destination.Test.name)
                                        NeobrutalismTheme.colors.buttonPrimary
                                    else NeobrutalismTheme.colors.contentPrimary
                            )
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.baseline_leaderboard_24),
                        contentDescription = "Leaderboard",
                        tint =
                            if (currentScreen == Destination.Test.name)
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
            selected = currentScreen == Destination.Test.name,
            onClick = { /*TODO*/},
            icon = {
                Box(
                    modifier =
                        modifier
                            .neubrutalismElevation()
                            .background(
                                color =
                                    if (currentScreen == Destination.Test.name)
                                        NeobrutalismTheme.colors.buttonPrimary
                                    else NeobrutalismTheme.colors.contentPrimary
                            )
                ) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Settings",
                        tint =
                            if (currentScreen == Destination.Test.name)
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
