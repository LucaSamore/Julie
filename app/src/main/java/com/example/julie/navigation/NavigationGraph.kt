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
import com.example.julie.signup.SignUpScreen

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
            TestScreen(
                modifier = modifier,
                testScreenViewModel = hiltViewModel(),
                paddingValues = paddingValues
            ) {
                navController.navigate(Destination.SignIn.name) {
                    popUpTo(Destination.Test.name) { inclusive = true }
                }
            }
        }

        composable(route = Destination.SignIn.name) {
            SignInScreen(
                modifier = modifier,
                signInViewModel = hiltViewModel(),
                paddingValues = paddingValues,
                onGoToSignUpScreen = { navController.navigate(Destination.SignUp.name) }
            ) {
                navController.navigate(Destination.Test.name) {
                    popUpTo(Destination.SignIn.name) { inclusive = true }
                }
            }
        }

        composable(route = Destination.SignUp.name) {
            SignUpScreen(
                modifier = modifier,
                signUpViewModel = hiltViewModel(),
                paddingValues = paddingValues,
                onBackToSignInScreen = { navController.popBackStack() }
            ) {
                navController.navigate(Destination.Test.name) {
                    popUpTo(Destination.SignIn.name) { inclusive = true }
                }
            }
        }
    }
}
