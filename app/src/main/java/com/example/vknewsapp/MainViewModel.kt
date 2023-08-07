package com.example.vknewsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.StatisticItem

class MainViewModel: ViewModel() {

    private val _feedPost = MutableLiveData(FeedPost())
    val feedPost: LiveData<FeedPost>
        get() = _feedPost

    fun updateCount(item: StatisticItem){
        val oldStatistics = feedPost.value?.statistics ?: throw java.lang.IllegalStateException()
        val newStatistics = mutableListOf<StatisticItem>()
        oldStatistics.forEach {
            if(it.type == item.type){
                val newStatisticItem = it.copy(count = it.count + 1)
                newStatistics.add(newStatisticItem)
            }else{
                newStatistics.add(it)
            }
        }
        _feedPost.value = feedPost.value?.copy(statistics = newStatistics)
    }

}