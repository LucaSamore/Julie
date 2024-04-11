package com.example.julie.signin

import androidx.lifecycle.ViewModel
import com.example.data.authentication.AuthenticationService
import com.example.data.di.FirebaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel
@Inject
constructor(@FirebaseService private val authenticationService: AuthenticationService) :
    ViewModel() {}
