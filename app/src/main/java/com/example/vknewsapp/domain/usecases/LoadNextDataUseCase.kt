package com.example.vknewsapp.domain.usecases

import com.example.vknewsapp.domain.repository.NewsFeedRepository

class LoadNextDataUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(){
        repository.loadNextData()
    }

}