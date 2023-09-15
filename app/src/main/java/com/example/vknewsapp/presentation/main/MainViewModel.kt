package com.example.vknewsapp.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknewsapp.data.repository.NewsFeedRepositoryImpl
import com.example.vknewsapp.domain.usecases.CheckAuthStateUseCase
import com.example.vknewsapp.domain.usecases.GetAuthUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getAuthStateFlowUseCase = GetAuthUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    val authState = getAuthStateFlowUseCase()

    fun performAuthResult(){
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }

}