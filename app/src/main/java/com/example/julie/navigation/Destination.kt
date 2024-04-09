package com.example.julie.navigation

sealed class Destination(val name: String) {
    data object SignIn : Destination("Sign In")

    data object Home : Destination("Home")
}
