package com.example.vknewsapp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.StatisticItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(){
    val feedPost = remember {
        mutableStateOf(FeedPost())
    }
    Scaffold(
        bottomBar = {
            BottomNavigation{
                val selectedItemPosition = remember {
                    mutableStateOf(0)
                }
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )
                items.forEachIndexed{index, item ->
                    BottomNavigationItem(
                        selected = selectedItemPosition.value == index,
                        onClick = { selectedItemPosition.value = index },
                        icon = {Icon(item.icon, contentDescription = null)},
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    ) {
        PostCard(
            modifier = Modifier.padding(8.dp),
            feedPost = feedPost.value,
            onStatisticsItemClickListener = { newItem ->
                val oldStatistics = feedPost.value.statistics
                val newStatistics = mutableListOf<StatisticItem>()
                oldStatistics.forEach {
                    if(it == newItem){
                        val newStatisticItem = it.copy(count = it.count + 1)
                        newStatistics.add(newStatisticItem)
                    }else{
                        newStatistics.add(it)
                    }
                }

                feedPost.value = feedPost.value.copy(statistics = newStatistics)
            }
        )
    }
}