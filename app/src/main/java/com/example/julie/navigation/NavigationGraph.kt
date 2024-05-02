package com.example.julie.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.julie.home.HomeScreen
import com.example.julie.passwordreset.PasswordResetScreen
import com.example.julie.settings.SettingsScreen
import com.example.julie.signin.SignInScreen
import com.example.julie.signup.SignUpScreen
import com.example.julie.smartphoneusage.SmartphoneUsageScreen
import com.example.julie.smartphoneusage.TestStory
import com.example.julie.verifyemail.VerifyEmailScreen

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
        composable(route = Destination.Home.name) {
            HomeScreen(
                modifier = modifier,
                homeViewModel = hiltViewModel(),
                paddingValues = paddingValues
            )
        }

        composable(route = Destination.SmartphoneUsage.name) {
            SmartphoneUsageScreen(
                modifier = modifier,
                smartphoneUsageViewModel = hiltViewModel(),
                paddingValues = paddingValues,
                navController = navController
            )
            //            {
            //                navController.navigate(Destination.Story.name) {
            //                    popUpTo(Destination.SmartphoneUsage.name) { inclusive = true }
            //                }
            //            }
        }

        composable(route = "${Destination.Story.name}/{appName}") {
            TestStory(
                appName = it.arguments?.getString("appName") ?: "",
                modifier = modifier,
                storyViewModel = hiltViewModel(),
                paddingValues = paddingValues
            ) {
                navController.navigate(Destination.SmartphoneUsage.name) {
                    popUpTo(Destination.Story.name) { inclusive = true }
                }
            }
        }

        composable(route = Destination.Settings.name) {
            SettingsScreen(
                modifier = modifier,
                settingsViewModel = hiltViewModel(),
                paddingValues = paddingValues
            ) {
                navController.navigate(Destination.SignIn.name) {
                    popUpTo(Destination.Settings.name) { inclusive = true }
                }
            }
        }

        composable(route = Destination.SignIn.name) {
            SignInScreen(
                modifier = modifier,
                signInViewModel = hiltViewModel(),
                paddingValues = paddingValues,
                onEmailNotVerified = { navController.navigate(Destination.VerifyEmail.name) },
                onGoToSignUpScreen = { navController.navigate(Destination.SignUp.name) },
                onGoToPasswordResetScreen = {
                    navController.navigate(Destination.PasswordReset.name)
                }
            ) {
                navController.navigate(Destination.Home.name) {
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
                navController.navigate(Destination.SignIn.name) {
                    popUpTo(Destination.SignIn.name) { inclusive = true }
                }
            }
        }

        composable(route = Destination.PasswordReset.name) {
            PasswordResetScreen(
                modifier = modifier,
                passwordResetViewModel = hiltViewModel(),
                paddingValues = paddingValues
            ) {
                navController.popBackStack()
            }
        }

        composable(route = Destination.VerifyEmail.name) {
            VerifyEmailScreen(
                modifier = modifier,
                verifyEmailViewModel = hiltViewModel(),
                paddingValues = paddingValues,
            ) {
                navController.navigate(Destination.SignIn.name) {
                    popUpTo(Destination.VerifyEmail.name) { inclusive = true }
                }
            }
        }
    }
}
