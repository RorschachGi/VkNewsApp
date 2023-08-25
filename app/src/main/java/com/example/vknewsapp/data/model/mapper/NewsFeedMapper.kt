package com.example.vknewsapp.data.model.mapper

import com.example.vknewsapp.data.model.NewsFeedResponseDto
import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.StatisticItem
import com.example.vknewsapp.domain.StatisticType
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapResponseToPosts(responseDto: NewsFeedResponseDto): List<FeedPost>{
        val result = mutableListOf<FeedPost>()
        val posts = responseDto.newsFeedContent.posts
        val groups = responseDto.newsFeedContent.groups

        for(post in posts){
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: break
            val feedPost = FeedPost(
                id = post.id,
                communityName = group.name,
                publicationDate = post.date.toString(),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachment?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticType.VIEWS, post.views.count),
                    StatisticItem(type = StatisticType.COMMENTS, post.comments.count),
                    StatisticItem(type = StatisticType.SHARES, post.reposts.count)
                )
            )
            result.add(feedPost)
        }
        return result
    }
}