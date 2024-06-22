package com.example.julie.components.neubrutalism

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.julie.R
import com.example.julie.bounceClick
import com.example.julie.navigation.Destination
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation

@Composable
internal fun NeubrutalBottomBar(modifier: Modifier, navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: Destination.SignIn.name
    val navItems =
        listOf(
            NavigationItemData(
                destination = Destination.Home,
                icon = ImageVector.vectorResource(R.drawable.baseline_home_filled_24),
                contentDescription = "Home"
            ),
            NavigationItemData(
                destination = Destination.SmartphoneUsage,
                icon = ImageVector.vectorResource(R.drawable.baseline_pie_chart_24),
                contentDescription = "Data Visualization"
            ),
            NavigationItemData(
                destination = Destination.Leaderboard,
                icon = ImageVector.vectorResource(R.drawable.baseline_leaderboard_24),
                contentDescription = "Leaderboard"
            ),
            NavigationItemData(
                destination = Destination.Settings,
                icon = Icons.Filled.Settings,
                contentDescription = "Settings"
            )
        )

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        NavigationBar(containerColor = NeobrutalismTheme.colors.contentSecondary) {
            navItems.forEach { item ->
                NavigationBarItem(
                    selected = currentScreen == item.destination.name,
                    onClick = {
                        navController.navigate(item.destination.name) {
                            popUpTo(item.destination.name) { inclusive = true }
                        }
                    },
                    icon = {
                        NavigationIcon(
                            modifier = modifier,
                            item = item,
                            isSelected = currentScreen == item.destination.name
                        )
                    },
                    colors =
                        NavigationBarItemDefaults.colors(
                            indicatorColor = NeobrutalismTheme.colors.contentSecondary
                        ),
                    modifier = modifier.bounceClick()
                )
            }
        }
    }
}

@Composable
private fun NavigationIcon(modifier: Modifier, item: NavigationItemData, isSelected: Boolean) {
    Box(
        modifier =
            modifier
                .neubrutalismElevation()
                .background(
                    color =
                        if (isSelected) NeobrutalismTheme.colors.buttonPrimary
                        else NeobrutalismTheme.colors.contentPrimary
                )
    ) {
        Icon(
            item.icon,
            contentDescription = item.contentDescription,
            tint = if (isSelected) NeobrutalismTheme.colors.text else Color.White,
            modifier = modifier.padding(6.dp).size(32.dp, 32.dp)
        )
    }
}

private data class NavigationItemData(
    val destination: Destination,
    val icon: ImageVector,
    val contentDescription: String
)

private object NoRippleTheme : RippleTheme {
    @Composable override fun defaultColor() = Color.Unspecified

    @Composable override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}
