package com.example.julie.navigation

sealed class Destination(val name: String) {
    data object SignIn : Destination("Sign In")

    data object SignUp : Destination("Sign Up")

    data object Home : Destination("Home")

    data object Test : Destination("Test")
}
