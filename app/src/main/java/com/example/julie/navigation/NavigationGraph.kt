package com.example.julie.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.julie.TestScreen
import com.example.julie.signin.SignInScreen

@Composable
internal fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Destination.Test.name) {
            TestScreen(modifier = modifier, paddingValues = paddingValues)
        }

        composable(route = Destination.SignIn.name) {
            SignInScreen(
                modifier = modifier,
                signInViewModel = hiltViewModel(),
                paddingValues = paddingValues,
                onSignedIn = {
                    navController.navigate(Destination.Test.name) {
                        popUpTo(Destination.Test.name) { inclusive = true }
                    }
                }
            )
        }
    }
}
