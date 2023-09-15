package com.example.vknewsapp.domain.entity

sealed class AuthState{

    object Authorized: AuthState()

    object NotAuthorized: AuthState()

    object Initial: AuthState()

}
