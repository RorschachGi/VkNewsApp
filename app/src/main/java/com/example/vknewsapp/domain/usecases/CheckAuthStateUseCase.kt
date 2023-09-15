package com.example.vknewsapp.domain.usecases

import com.example.vknewsapp.domain.repository.NewsFeedRepository

class CheckAuthStateUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(){
        return repository.checkAuthState()
    }

}