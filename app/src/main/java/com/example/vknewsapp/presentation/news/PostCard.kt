package com.example.vknewsapp.presentation.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vknewsapp.R
import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.StatisticItem
import com.example.vknewsapp.domain.StatisticType
import com.example.vknewsapp.ui.theme.DarkRed

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onLikeClickListener: (StatisticItem) -> Unit,
    onViewsClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            PostHeader(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = feedPost.contentText)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = feedPost.contentImageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(
                statistics = feedPost.statistics,
                onLikeClickListener = onLikeClickListener,
                onViewsClickListener = onViewsClickListener,
                onShareClickListener = onShareClickListener,
                onCommentClickListener = onCommentClickListener,
                isFavourite = feedPost.isLiked
            )
        }
    }
}

@Composable
private fun PostHeader(
    feedPost: FeedPost
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = feedPost.communityImageUrl,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = feedPost.communityName,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = feedPost.publicationDate,
                color = MaterialTheme.colors.onSecondary
            )
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = null,
            tint = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    onLikeClickListener: (StatisticItem) -> Unit,
    onViewsClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    isFavourite: Boolean
) {
    Row {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                text = formatStatisticCount(viewsItem.count),
                onItemClickListener = {
                    onViewsClickListener(viewsItem)
                }
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(
                iconResId = R.drawable.ic_share,
                text = formatStatisticCount(sharesItem.count),
                onItemClickListener = {
                    onShareClickListener(sharesItem)
                }
            )
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comment,
                text = formatStatisticCount(commentsItem.count),
                onItemClickListener = {
                    onCommentClickListener(commentsItem)
                }
            )
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = if(isFavourite) R.drawable.ic_like_set else R.drawable.ic_like,
                text = formatStatisticCount(likesItem.count),
                onItemClickListener = {
                    onLikeClickListener(likesItem)
                },
                tint = if(isFavourite) DarkRed else MaterialTheme.colors.onSecondary
            )
        }
    }
}

private fun formatStatisticCount(count: Int): String{
    return if(count > 100_000){
        String.format("%sK", (count / 1000))
    }else if(count > 1000){
        String.format("%.1fk", (count / 1000f))
    }else{
        count.toString()
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem{
    return this.find{it.type == type} ?: throw java.lang.IllegalStateException()
}

@Composable
private fun IconWithText(
    iconResId: Int,
    text: String,
    onItemClickListener: () -> Unit,
    tint: Color = MaterialTheme.colors.onSecondary
) {
    Row(
        modifier = Modifier.clickable {
            onItemClickListener()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = MaterialTheme.colors.onSecondary
        )
    }
}
