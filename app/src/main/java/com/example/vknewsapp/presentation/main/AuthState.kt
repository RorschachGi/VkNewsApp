package com.example.vknewsapp.presentation.main

sealed class AuthState{

    object Authorized: AuthState()

    object NotAuthorized: AuthState()

    object Initial: AuthState()

}
